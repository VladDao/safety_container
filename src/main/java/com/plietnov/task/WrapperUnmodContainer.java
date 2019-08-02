package com.plietnov.task;

import com.plietnov.task.entity.Product;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

final public class WrapperUnmodContainer<T extends Product> implements List<T> {

    private static final String CANNOT_CHANGE_UNMODIFIABLE_PART_OF_LIST = "Cannot change unmodifiable part of the list";

    private List<T> unmodifiableList;
    private List<T> modifiableList;

    public WrapperUnmodContainer(List<T> unmodifiableList, List<T> modifiableList) {
        this.unmodifiableList = unmodifiableList;
        this.modifiableList = modifiableList;
    }

    /**
     * Throw IllegalStateException if element by this index can't be changed
     * using in(@add, @remove, @set, addAll, @removeAll, @retainAll)
     *
     * @param index
     */
    private void checkIllegalIndex(int index) {
        if (index < unmodifiableList.size()) {
            throw new IllegalStateException(CANNOT_CHANGE_UNMODIFIABLE_PART_OF_LIST);
        }
    }

    /**
     * Throws IndexOutOfBoundsException if index out of range
     * using in (@add, @addAll) by index
     *
     * @param index
     */
    private void checkIndexOutOfRange(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException(CANNOT_CHANGE_UNMODIFIABLE_PART_OF_LIST);
        }
    }

    /**
     * Check validations input index before
     * (@get, @set, @remove) by index
     *
     * @param index
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
    }

    @Override
    public void add(int index, T element) {
        checkIndexOutOfRange(index);
        checkIllegalIndex(index);
        modifiableList.add(calculateIndexModifiablePart(index), element);
    }

    @Override
    public boolean add(T element) {
        return modifiableList.add(element);
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);
        checkIllegalIndex(index);
        return modifiableList.set(calculateIndexModifiablePart(index), element);
    }

    /**
     * Calculate index modifiable part of list from root index
     *
     * @param index
     * @return index modifiable part
     */
    private int calculateIndexModifiablePart(int index) {
        return index - unmodifiableList.size();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        return modifiableList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        checkIndexOutOfRange(index);
        checkIllegalIndex(index);
        return modifiableList.addAll(calculateIndexModifiablePart(index), c);
    }

    @Override
    public void clear() {
        if (!unmodifiableList.isEmpty()) {
            throw new IllegalStateException(CANNOT_CHANGE_UNMODIFIABLE_PART_OF_LIST);
        }
        modifiableList.clear();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (T t : unmodifiableList) {
            if (c.contains(t)) {
                throw new IllegalStateException(CANNOT_CHANGE_UNMODIFIABLE_PART_OF_LIST);
            }
        }
        return modifiableList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (T t : unmodifiableList) {
            if (!c.contains(t)) {
                throw new IllegalStateException(CANNOT_CHANGE_UNMODIFIABLE_PART_OF_LIST);
            }
        }
        return modifiableList.retainAll(c);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        int newLength = size() > a.length ? size() : a.length;
        Stream arrayStream = Stream.concat(unmodifiableList.stream(), modifiableList.stream());
        return (T[]) Arrays.copyOf(arrayStream.toArray(), newLength, a.getClass());
    }

    @Override
    public Object[] toArray() {
        return Stream.of(unmodifiableList, modifiableList).flatMap(Collection::stream).toArray();
    }

    @Override
    public boolean isEmpty() {
        return unmodifiableList.isEmpty() && modifiableList.isEmpty();
    }

    @Override
    public int size() {
        return unmodifiableList.size() + modifiableList.size();
    }

    @Override
    public boolean remove(Object o) {
        if (unmodifiableList.contains(o)) {
            throw new IllegalStateException(CANNOT_CHANGE_UNMODIFIABLE_PART_OF_LIST);
        }
        return modifiableList.remove(o);
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        checkIllegalIndex(index);
        return modifiableList.remove(calculateIndexModifiablePart(index));
    }

    @Override
    public boolean contains(Object o) {
        return unmodifiableList.contains(o) || modifiableList.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object t : c.toArray()) {
            if (contains(t)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        if (index < unmodifiableList.size()) {
            return unmodifiableList.get(index);
        }
        return modifiableList.get(calculateIndexModifiablePart(index));
    }

    @Override
    public int indexOf(Object o) {
        int part1 = unmodifiableList.indexOf(o);
        if (part1 > -1) {
            return part1;
        }
        int part2 = modifiableList.indexOf(o);
        return calculateIndex(part2);
    }

    private int calculateIndex(int part2) {
        return part2 > -1 ? part2 + unmodifiableList.size() : -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int part2 = modifiableList.lastIndexOf(o);
        if (part2 > -1) {
            return calculateIndex(part2);
        }
        return unmodifiableList.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        return new WrapListIterator(unmodifiableList, modifiableList);
    }

    private class WrapListIterator implements Iterator<T> {
        private Iterator<T> unmodifiable;
        private Iterator<T> modifiable;

        private WrapListIterator(List<T> unmodifiableList, List<T> modifiableList) {
            this.unmodifiable = unmodifiableList.iterator();
            this.modifiable = modifiableList.iterator();
        }

        @Override
        public boolean hasNext() {
            return unmodifiable.hasNext() || modifiable.hasNext();
        }

        @Override
        public T next() {
            if (unmodifiable.hasNext()) {
                return unmodifiable.next();
            }
            return modifiable.next();
        }
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }
}
