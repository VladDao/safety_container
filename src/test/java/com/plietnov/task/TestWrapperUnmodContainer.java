package com.plietnov.task;

import com.plietnov.task.entity.Computer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestWrapperUnmodContainer {
    private static final Computer COMPUTER = new Computer(999999999, "test", "test", "test");
    private WrapperUnmodContainer<Computer> wrapList;
    private List<Computer> firstPartOfList = new ArrayList<>();
    private List<Computer> secondPartOfList = new ArrayList<>();
    private List<Computer> listForTest = new ArrayList<>();
    private Computer computerZeroElement;

    @Before
    public void initObject() {
        computerZeroElement = new Computer(0, "0", "0", "0");
        firstPartOfList.add(computerZeroElement);
        firstPartOfList.add(new Computer(1, "firstPart| PC ==> 1", "firstPart type № 1", "firstPart| class № 1"));
        firstPartOfList.add(new Computer(2, "firstPart| PC ==> 2", "firstPart type № 2", "firstPart| class № 2"));
        firstPartOfList.add(new Computer(3, "firstPart| PC ==> 3", "firstPart type № 3", "firstPart| class № 3"));
        firstPartOfList.add(new Computer(4, "firstPart| PC ==> 4", "firstPart type № 4", "firstPart| class № 4"));
        firstPartOfList.add(new Computer(5, "firstPart| PC ==> 5", "firstPart type № 5", "firstPart| class № 5"));
        secondPartOfList.add(new Computer(10, "firstPart| PC ==> 10", "firstPart type № 10", "firstPart| class № 10"));
        secondPartOfList.add(new Computer(11, "firstPart| PC ==> 11", "firstPart type № 11", "firstPart| class № 11"));
        secondPartOfList.add(new Computer(12, "firstPart| PC ==> 12", "firstPart type № 12", "firstPart| class № 12"));
        secondPartOfList.add(new Computer(13, "firstPart| PC ==> 13", "firstPart type № 13", "firstPart| class № 13"));
        secondPartOfList.add(new Computer(14, "firstPart| PC ==> 14", "firstPart type № 14", "firstPart| class № 14"));
        secondPartOfList.add(new Computer(15, "firstPart| PC ==> 15", "firstPart type № 15", "firstPart| class № 15"));
        listForTest.add(new Computer(20, "firstPart| PC ==> 20", "firstPart type № 20", "firstPart| class № 20"));
        listForTest.add(new Computer(21, "firstPart| PC ==> 21", "firstPart type № 21", "firstPart| class № 21"));
        listForTest.add(new Computer(22, "firstPart| PC ==> 22", "firstPart type № 22", "firstPart| class № 22"));
        listForTest.add(new Computer(23, "firstPart| PC ==> 23", "firstPart type № 23", "firstPart| class № 23"));
        listForTest.add(new Computer(24, "firstPart| PC ==> 24", "firstPart type № 24", "firstPart| class № 24"));
        listForTest.add(new Computer(25, "firstPart| PC ==> 25", "firstPart type № 25", "firstPart| class № 25"));
        wrapList = new WrapperUnmodContainer<>(firstPartOfList, secondPartOfList);
    }

    @Test
    public void add_AddElement_ShouldAddElement() {
        wrapList.add(wrapList.size(), COMPUTER);
        assertEquals(wrapList.get(wrapList.size() - 1), COMPUTER);
    }

    @Test
    public void addByIndex_AddElement_ShouldAddElement() {
        wrapList.add(firstPartOfList.size(), COMPUTER);
        assertEquals(wrapList.get(firstPartOfList.size()), COMPUTER);
    }

    @Test
    public void addAll_AddAllFromList_ShouldAddList() {
        wrapList.addAll(10, listForTest);
        assertTrue(wrapList.containsAll(listForTest));
    }

    @Test
    public void containsAll_CheckContainsElement_ShouldReturnTrue() {
        assertTrue(wrapList.containsAll(secondPartOfList));
    }

    @Test
    public void addAll_AddEmptyList_ShouldReturnFalse() {
        List lpList = new ArrayList<Computer>();
        assertFalse(wrapList.addAll(lpList));
    }

    @Test
    public void addAll_AddNull_ShouldReturnFalse() {
        assertFalse(wrapList.addAll(null));
    }

    @Test
    public void addAllByIndex_AddEmptyList_ShouldReturnFalse() {
        List lpList = new ArrayList<Computer>();
        assertFalse(wrapList.addAll(0, lpList));
    }

    @Test
    public void addAllByIndex_AddNullByIndex_ShouldReturnFalse() {
        assertFalse(wrapList.addAll(0, null));
    }

    @Test
    public void remove_RemoveObjectWhichMissing_ShouldReturnFalse() {
        assertFalse(wrapList.remove(COMPUTER));
    }

    @Test
    public void remove_RemoveNullWhichMissing_ShouldReturnFalse() {
        assertFalse(wrapList.remove(null));
    }

    @Test
    public void get_GetElementFromList_ShouldBeEquals() {
        assertEquals(computerZeroElement, wrapList.get(0));
    }

    @Test
    public void lastIndexOfNull_GetLastIndexNull_ShouldBeEquals() {
        wrapList.set(6, null);
        assertEquals(6, wrapList.lastIndexOf(null));
    }

    @Test
    public void remove_RemoveFromModPart_ShouldBeEquals() {
        wrapList.add(firstPartOfList.size(), COMPUTER);
        assertTrue(wrapList.remove(COMPUTER));
    }

    @Test
    public void containsAll_TestContainsAll_ShouldBeTrue() {
        wrapList.addAll(listForTest);
        assertTrue(wrapList.containsAll(listForTest));
    }

    @Test
    public void lastIndexOf_GetLastIndexElement_ShouldBeEquals() {
        assertEquals(1, wrapList.lastIndexOf(wrapList.get(1)));
    }

    @Test
    public void indexOf_GetIndexElement_ShouldBeEquals() {
        assertEquals(0, wrapList.indexOf(wrapList.get(0)));
    }

    @Test
    public void removeByIndex_RemoveElementByIndex_ShouldBeEquals() {
        assertEquals(wrapList.get(6), wrapList.remove(6));
    }

    @Test
    public void retainAll_RemoveAllElementExcept_ShouldNotContainElementFromSecondPart() {
        wrapList.retainAll(firstPartOfList);
        assertFalse(wrapList.containsAll(secondPartOfList));
    }

    @Test
    public void removeAll_RemoveAll_ShouldNotContainElementFromSecondPart() {
        wrapList.removeAll(secondPartOfList);
        assertFalse(wrapList.containsAll(secondPartOfList));
    }

    @Test
    public void isEmpty_TestIsEmpty_ShouldReturnTrue() {
        WrapperUnmodContainer wrap = new WrapperUnmodContainer(new ArrayList(), secondPartOfList);
        assertFalse(wrap.isEmpty());
    }

    @Test
    public void iterator_IterateOverList_ShouldBeEquals() {
        Iterator itr = wrapList.iterator();
        int count = 0;
        while (itr.hasNext()) {
            itr.next();
            count++;
        }
        assertEquals(wrapList.size(), count);
    }

    /**
     * Negative Tests
     */
    @Test(expected = IllegalStateException.class)
    public void add_AddToUnmodPart_ShouldBeIllegalStateException() {
        wrapList.add(0, COMPUTER);
    }

    @Test(expected = IllegalStateException.class)
    public void remove_RemoveFromUnmodPart_ShouldBeIllegalStateException() {
        wrapList.remove(0);
    }

    @Test(expected = IllegalStateException.class)
    public void clear_ClearWhenFirstPartNotEmpty_ShouldBeexception() {
        wrapList.clear();
    }

    @Test(expected = IllegalStateException.class)
    public void retainAll_RetainAllUnmodPart_ShouldBeIllegalStateException() {
        wrapList.retainAll(secondPartOfList);
    }

    @Test(expected = IllegalStateException.class)
    public void removeAll_RemoveAllUnmodPart_ShouldBeIllegalStateException() {
        wrapList.removeAll(firstPartOfList);
    }

    @Test(expected = IllegalStateException.class)
    public void add_AddAllUnmodPart_ShouldBeIllegalStateException() {
        wrapList.addAll(0, secondPartOfList);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void get_GetElementIndexOfBounds_ShouldBeIndexOutOfBoundsException() {
        wrapList.get(wrapList.size() * 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void set_SetToUnmodPart_ShouldBeIllegalStateException() {
        wrapList.set(wrapList.size() + 1, COMPUTER);
    }

    @Test(expected = NoSuchElementException.class)
    public void iterator_TestEmptyIterator_ShouldBeNoSuchElementException() {
        Iterator iterator = new WrapperUnmodContainer<>(new ArrayList<>(), new ArrayList<>()).iterator();
        iterator.next();
    }
}
