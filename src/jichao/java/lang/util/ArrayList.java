package jichao.java.lang.util;

import com.sun.org.apache.xalan.internal.xsltc.dom.ArrayNodeListIterator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by zhangj52 on 4/20/2016.
 */
public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, Serializable {
    private static final long serialVersionUIS = 8683452581122892189L;

    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] EMPTY_ELEMENTDATA = {};

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    transient Object[] elementData;
    private int size;

    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        if ((size = elementData.length) != 0) {
            if (elementData.getClass() != Object[].class) {
                elementData = Arrays.copyOf(elementData, size, Object[].class);
            }
        } else {
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    public static void main(final String[] args) {
        java.util.ArrayList<String> a = new java.util.ArrayList<>();
        a.add("a");
        a.get(-1);
        //a.get(3);

    }

    public void trimToSize() {
        modCount++;
        if (size < elementData.length) {
            elementData = (size == 0) ? EMPTY_ELEMENTDATA : Arrays.copyOf(elementData, size);
        }
    }

    public void ensureCapacity(int minCapacity) {
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) ? 0 : DEFAULT_CAPACITY;
        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }

    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            Object obj = elementData[i];
            if (obj == null) {
                if (o == null) {
                    return i;
                }
            } else {
                if (obj.equals(o)) {
                    return i;
                }

            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            Object obj = elementData[i];
            if (obj == null) {
                if (o == null) {
                    return i;
                }
            } else {
                if (obj.equals(o)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public Object clone() {
        try {
            ArrayList<?> v = (ArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            return v;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    E elementData(int index) {
        return (E) elementData[index];
    }

    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    public E set(int index, E element) {
        rangeCheck(index);
        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacityInternal(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    public E remove(int index) {
        rangeCheck(index);
        modCount++;
        E oldValue = elementData(index);
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
        return oldValue;
    }

    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            E element = elementData(i);
            if (element == null) {
                remove(i);
                return true;
            } else {
                if (element.equals(o)) {
                    remove(i);
                    return true;
                }
            }

        }
        return false;
    }

    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }

        elementData[--size] = null;
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    public void clear() {
        modCount++;
        //elementData = EMPTY_ELEMENTDATA; Clear data, not the contaner, the container is still there
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size:" + size;
    }

    public boolean addAll(Collection<? extends E> c) {
        /*        if (c == null) {
            throw new NullPointerException("Input collection in null!");
        } else {
            for (E e : c) {
                add(e);
            }
        }
        return true;
        */
        //allocate a new array to hold all the data to be added.
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        //Comparing added one by one, more efficient sometimes. Plz Google it.
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        Object[] elements = c.toArray();
        ensureCapacityInternal(size + elements.length);
        //in jdk, there is if( (size-index) > 0) it means append
        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(elementData, index, elementData, index + elements.length, numMoved);
        }
        System.arraycopy(elements, 0, elementData, index, elements.length);
        size += elements.length;
        return elements.length != 0;
    }

    /**
     * I think my version is more clear.
     *
     * @param fromIndex
     * @param toIndex
     */
    protected void removeRange(int fromIndex, int toIndex) {
        modCount++;
        //Check if input valid
        if (!(0 < fromIndex && fromIndex <= toIndex && toIndex <= size)) {
            throw new IllegalArgumentException("Message!");
        }
        //Special cases
        if (fromIndex == toIndex) {
            return;
        }
        //Clear the elements to be removed.
        for (int i = fromIndex; i < toIndex; i++) {
            elementData[i] = null;
        }
        //copy toIndex:len-1 to fromIndex
        if (toIndex < size) {
            int numMoved = size - toIndex;
            System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);
        }
        size = size - (toIndex - fromIndex);
    }

    private void rangeCheck(int index) {
        /*
        if (index < 0 || index > size - 1) {
            throw new IllegalArgumentException("Out of array range: "+index);
        }*/
        if (index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return batchRemove(c, false);
    }

    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return batchRemove(c, true);
    }

    private boolean batchRemove(Collection<?> c, boolean complement) {
        final Object[] elementData = this.elementData;
        int r = 0, w = 0;
        boolean modified = false;
        try {
            for (; r < size; r++) {
                if (c.contains(elementData[r]) == complement) {
                    elementData[w++] = elementData[r];
                }
            }
        } finally {
            if (r != size) {
                System.arraycopy(elementData, r, elementData, w, size - r);
                w += size - r;
            }
            if (w != size) {
                for (int i = w; i < size; i++) {
                    elementData[i] = null;
                }
                modCount += size - w;
                size = w;
                modified = true;
            }
        }
        return modified;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        int expectedModCount = modCount;
        s.defaultWriteObject();
        s.write(size);
        for (int i = 0; i < size; i++) {
            s.writeObject(elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        elementData = EMPTY_ELEMENTDATA;
        stream.defaultReadObject();
        stream.readInt();
        if (size > 0) {
            ensureCapacityInternal(size);
            Object[] a = elementData;
            for (int i = 0; i < size; i++) {
                a[i] = stream.readObject();
            }
        }

    }

    public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index :" + index);
        }
        return new ListItr(index);
    }

    private class Itr implements Iterator<E> {
        int cursor;
        int lastRet = -1;
        int expectedModCount = modCount; //A little like closure
        public boolean hasNext(){
            return cursor != size;
        }

        public E next() {
            checkForComodification();
            int i = cursor;
            if (i > size) {
                throw new NoSuchElementException();
            }

            Object[] elementData = ArrayList.this.elementData;

            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            cursor = i+1;
            return (E)elementData[lastRet = i];
        }

        public void remove(){
            if (lastRet < 0) {
                throw new IllegalStateException();
            }

            checkForComodification();

            try {
                ArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int size = ArrayList.this.size;
            int i = cursor;
            if (i > size) {
                return;
            }
            final Object[] elementData = ArrayList.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }

            while (i != size && modCount == expectedModCount) {
                consumer.accept((E)elementData[i++]);
            }

            cursor = i;
            lastRet = i -1 ;
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }

        }
    }


}
