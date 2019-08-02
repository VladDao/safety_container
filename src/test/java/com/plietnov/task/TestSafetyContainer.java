package com.plietnov.task;

import com.plietnov.task.entity.Computer;
import com.plietnov.task.entity.Laptop;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestSafetyContainer {
    private static final Computer COMPUTER = new Computer(987465, "testAdd", "bla bla", "bla bla");

    private SafetyContainer<Computer> sf = new SafetyContainer<>();
    private SafetyContainer<Computer> sf2 = new SafetyContainer<>();

    @Before
    public void initObject() {
        sf.add(new Laptop(1, "some PC 1", "type 1", "class 1", "deskription 1"));
        sf.add(new Laptop(2, "some PC 2", "type 2", "class 2", "deskription 1"));
        sf.add(new Laptop(3, "some PC 3", "type 3", "class 3", "deskription 2"));
        sf.add(new Laptop(4, "some PC 4", "type 4", "class 4", "deskription 4"));
        sf.add(new Laptop(5, "some PC 5", "type 5", "class 5", "deskription 5"));
        sf2.add(new Laptop(10, "some PC 10", "type 10", "class 10", "deskription 10"));
        sf2.add(new Laptop(20, "some PC 20", "type 20", "class 20", "deskription 10"));
    }

    @Test
    public void addByIndex_AddElement_ShouldAddElement() {
        sf.add(1, COMPUTER);
        assertEquals(COMPUTER, sf.get(1));
    }

    @Test
    public void addByIndex_AddNull_ShouldAddElement() {
        sf.add(1, null);
        assertNull(sf.get(1));
    }

    @Test
    public void removeByIndex_RemoveElement_ShouldRemoveElement() {
        sf.remove(4);
        assertEquals(4, sf.size());
    }

    @Test
    public void iterator_TestIterator_ShouldBeEquals() {
        Iterator<Computer> iterator = sf.iterator();
        int count = 0;
        sf.clear();
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals((5), count);
    }

    @Test
    public void get_GetElementByIndex_ShouldBeEquals() {
        sf.add(0, COMPUTER);
        assertEquals(COMPUTER, sf.get(0));
    }

    @Test
    public void ifEmpty_CallIsEmpty_ShouldBeTrue() {
        sf.clear();
        assertTrue(sf.isEmpty());
    }

    @Test
    public void ifEmpty_CallIsEmpty_ShouldBeFalse() {
        assertFalse(sf.isEmpty());
    }

    @Test
    public void containsAll_TestContainsAll_ShouldBeFalse() {
        assertFalse(sf.containsAll(sf2));
    }

    @Test
    public void containsAll_CallContainsAll_ShouldBeTrue() {
        sf.addAll(sf2);
        assertTrue(sf.containsAll(sf2));
    }

    @Test
    public void addAllByIndex_AddList_ShouldAddList() {
        sf2.add(new Computer());
        assertTrue(sf.addAll(0, sf2));
    }

    @Test
    public void removeAll_RemoveAllFromList_ShouldBeTrue() {
        SafetyContainer<Computer> test = new SafetyContainer<>();
        test.addAll(sf);
        test.addAll(sf2);
        test.removeAll(sf);
        assertFalse(test.containsAll(sf));
    }

    @Test
    public void retainAll_RetainAllFromList_ShouldBeTrue() {
        SafetyContainer<Computer> test = new SafetyContainer<>();
        test.addAll(sf);
        test.addAll(sf2);
        test.retainAll(sf2);
        assertFalse(test.containsAll(sf));
    }

    @Test
    public void lastIndexOf_GetIndexOfNull_ShouldBeEquals() {
        sf.set(4, null);
        assertEquals(4, sf.lastIndexOf(null));
    }

    @Test
    public void indexOf_GetIndexByObject_ShouldBeEquals() {
        sf.add(COMPUTER);
        assertEquals(sf.size() - 1, sf.indexOf(COMPUTER));
    }

    @Test
    public void remove_RemoveObject_ShouldBeTrue() {
        assertFalse(sf.remove(COMPUTER));
    }

    @Test
    public void addAll_AddEmptyList_ShouldBeFalse() {
        List lpList = new ArrayList<Laptop>();
        assertFalse(sf.addAll(lpList));
    }

    @Test
    public void dddAll_AddNull_ShouldBeFalse() {
        assertFalse(sf.addAll(null));
    }

    @Test
    public void addAll_AddAllByIndexEmptyList_ShouldBeFalse() {
        assertFalse(sf.addAll(0, new ArrayList<>()));
    }

    @Test
    public void set_SetElementByIndex_ShouldBeEquals() {
        Computer newCompucter = new Computer();
        sf.add(0, newCompucter);
        assertEquals(newCompucter, sf.set(0, COMPUTER));
    }

    @Test
    public void lastIndex_GetIndexOfNull_ShouldBeEquals() {
        int index = 4;
        sf.set(4, null);
        assertEquals(index, sf.lastIndexOf(null));
    }

    @Test
    public void iterator_ModifiedIterator_ShouldNotChanged() {
        Iterator itr = sf.iterator();
        sf.add(COMPUTER);
        Iterator itr2 = sf.iterator();
        int count = 0, count2 = 0;
        while (itr.hasNext()) {
            count++;
            itr.next();
        }
        while (itr2.hasNext()) {
            count2++;
            itr2.next();
        }
        assertNotEquals(count, count2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void add_AddByIndex_ShouldBeException() {
        sf.add(sf.size() + 1, COMPUTER);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addAll_AddAllByIndex_ShouldBeException() {
        sf.addAll(sf.size() * 5, sf2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get_GetByOutOfBoundIndex_ShouldBeException() {
        Computer pc = sf.get(sf.size() * 5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void set_SetBIndex_ShouldBeException() {
        sf.set(sf.size() * 5, COMPUTER);
    }

    @Test(expected = NoSuchElementException.class)
    public void iterator_IteratorByEmptyList_ShouldBeException() {
        Iterator iterator = new SafetyContainer<>().iterator();
        iterator.next();
    }
}
