package com.plietnov.task;

import com.plietnov.task.entity.Product;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

public class SafetyContainer<T extends Product> implements Serializable, List<T> {

    private static final int INITIAL_CAPACITY = 0;

    private T[] array;

    public SafetyContainer() {
        array = (T[]) new Product[INITIAL_CAPACITY];
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    final T[] getArray() {
        return array;
    }

    final void setArray(T[] a) {
        array = a;
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Object[] toArray() {
        T[] array = getArray();
        return Arrays.copyOf(array, array.length);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] arrayCopy = (T[]) getArray();
        int length = arrayCopy.length;
        if (a.length < length) {
            return (T[]) Arrays.copyOf(arrayCopy, length, a.getClass());
        }
        System.arraycopy(arrayCopy, 0, a, 0, length);
        if (a.length > length) {
            a[length] = null;
        }
        return a;
    }

    @Override
    public boolean add(T element) {
        T[] arrayCopy = getArray();
        int length = arrayCopy.length;
        arrayCopy = Arrays.copyOf(arrayCopy, length + 1);
        arrayCopy[length] = element;
        setArray(arrayCopy);
        return true;
    }

    @Override
    public void add(int index, T element) {
        checkIndexAdd(index);
        T[] arrayCopy = getArray();
        int length = arrayCopy.length;
        arrayCopy = Arrays.copyOf(arrayCopy, length + 1);
        System.arraycopy(arrayCopy, index, arrayCopy, index + 1, length - index);
        arrayCopy[index] = element;
        setArray(arrayCopy);
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T[] arrayCopy = getArray();
        T arrayCopyOut = arrayCopy[index];
        setArray(fastRemove(index, arrayCopy));
        return arrayCopyOut;
    }

    private T[] fastRemove(int index, T[] arrayCopy) {
        System.arraycopy(arrayCopy, index + 1, arrayCopy, index, arrayCopy.length - index - 1);
        return Arrays.copyOf(arrayCopy, arrayCopy.length - 1);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object t : c.toArray()) {
            if (!contains(t))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        Product[] products = Stream
                .concat(Arrays.stream(getArray()), c.stream())
                .toArray(Product[]::new);
        setArray((T[]) products);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        checkIndexAdd(index);
        T[] copyArray = getArray();
        Stream firstPart = Arrays.stream(copyArray, 0, index);
        Stream lastPart = Arrays.stream(copyArray, index, copyArray.length);
        T[] result = (T[]) Stream.concat(Stream
                .concat(firstPart, c.stream()), lastPart)
                .toArray(Product[]::new);
        setArray(result);
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return array[index];
    }

    @Override
    public T set(int index, T element) {
        T[] arrayCopy = getArray();
        checkIndex(index);
        T old = arrayCopy[index];
        arrayCopy[index] = element;
        setArray(arrayCopy);
        return old;
    }

    /**
     * Check validations input index before add date
     *
     * @param index
     */
    private void checkIndexAdd(int index) {
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
    }

    /**
     * Check validations input index before get or set
     *
     * @param index
     */
    private void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        T[] arrayCopy = getArray();
        T[] result = (T[]) Arrays.stream(arrayCopy)
                .filter(t -> !c.contains(t))
                .toArray(Product[]::new);
        setArray(result);
        return arrayCopy.length != getArray().length;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        T[] arrayCopy = getArray();
        T[] result = (T[]) Arrays.stream(arrayCopy)
                .filter(c::contains)
                .toArray(Product[]::new);
        setArray(result);
        return arrayCopy.length != getArray().length;
    }

    @Override
    public void clear() {
        array = (T[]) new Product[INITIAL_CAPACITY];
    }

    @Override
    public int indexOf(Object o) {
        T[] arrayCopy = getArray();
        if (o == null) {
            for (int i = 0; i < arrayCopy.length; i++) {
                if (arrayCopy[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < arrayCopy.length; i++) {
                if (o.equals(arrayCopy[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new ContainerIterator();
    }

    private class ContainerIterator implements Iterator<T> {
        private int cursor;
        private T[] iteratorStorage;

        private ContainerIterator() {
            iteratorStorage = getArray();
        }

        @Override
        public boolean hasNext() {
            return cursor != iteratorStorage.length;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return iteratorStorage[cursor++];
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        T[] arrayCopy = getArray();
        if (o == null) {
            for (int i = arrayCopy.length - 1; i >= 0; i--) {
                if (arrayCopy[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = arrayCopy.length - 1; i >= 0; i--) {
                if (arrayCopy[i].equals(o)) {
                    return i;
                }
            }
        }
        return -1;
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
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }
}
