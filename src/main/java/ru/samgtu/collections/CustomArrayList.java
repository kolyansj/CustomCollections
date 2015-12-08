package ru.samgtu.collections;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

/**
 * Created by Nikolay on 04.11.2015.
 */
public class CustomArrayList<T> extends CustomAbstractList<T> {

    private static final Logger LOG = LogManager.getLogger(CustomArrayList.class);

    private Object[] array;

    public CustomArrayList(T... array) {
        this.array = array;
    }

    @Override
    public void add(int index, T value) {
        LOG.trace("index: " + index + " | value: " + value);
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException("Index < 0 or index >= size");
        }
        Object[] newArray = new Object[array.length + 1];
        System.arraycopy(array, 0, newArray , 0, array.length);
        newArray[index] = value;
        int len = array.length - index;
        System.arraycopy(array, index, newArray, index + 1, len);
        array = newArray;
        modify();
        array = newArray;
    }

    @Override
    public void add(T value) {
        LOG.trace("value: " + value);
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        Object[] newArray = new Object[array.length + 1];
        System.arraycopy(array, 0, newArray , 0, array.length);
        newArray[newArray.length - 1] = value;
        modify();
        array = newArray;
    }

    @Override
    public T delete(int index) {
        LOG.trace("index: " + index);
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException("Index < 0 or index >= size");
        }
        T delete = (T) array[index];
        LOG.debug("found object: " + delete);
        Object[] newArray = new Object[array.length - 1];
        int len = array.length - index - 1;
        System.arraycopy(array, index + 1, array, index, len);
        System.arraycopy(array, 0, newArray, 0, newArray.length);
        array = newArray;
        modify();
        return delete;
    }

    @Override
    public boolean delete(T value) {
        LOG.trace("value: " + value);
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        Object[] newArray = new Object[array.length - 1];
        for (int i = 0; i < array.length; i++) {
            Object delete = array[i];
            if (value.equals(delete)) {
                LOG.debug("delete index: " + i);
                int len = array.length - i - 1;
                System.arraycopy(array, i + 1, array, i, len);
                System.arraycopy(array, 0, newArray, 0, newArray.length);
                array = newArray;
                modify();
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(T value) {
        LOG.trace("value: " + value);
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        for (int i = 0; i < array.length; i++) {
            Object obj = array[i];
            if (value.equals(obj)) {
                LOG.debug("found index: " + i);
                return i;
            }
        }
        return -1;
    }

    @Override
    public T get(int index) {
        LOG.trace("index: " + index);
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException("Index < 0 or index >= size");
        }
        return (T) array[index];
    }

    @Override
    public void set(int index, T value) {
        LOG.trace("index: " + index + " | value: " + value);
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException("Index < 0 or index >= size");
        }
        array[index] = value;
        modify();
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public T[] toArray(T[] array) {
        if (array == null) {
            throw new NullPointerException("Input array is null");
        }
        LOG.trace("array length: " + array.length + " | component type: " + array.getClass().getComponentType());
        array = (T[]) Array.newInstance(array.getClass().getComponentType(), this.array.length);
        System.arraycopy(this.array, 0, array, 0, array.length);
        return array;
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomArrayListIterator<>();
    }

    private class CustomArrayListIterator<T> extends AbstractIterator<T> {

        private Object[] array;

        private CustomArrayListIterator() {
            super();
        }

        @Override
        protected void update() {
            array = CustomArrayList.this.array;
        }

        @Override
        public boolean hasNext() {
            check();
            return current < array.length;
        }

        @Override
        public T next() {
            check();
            T value = (T) array[current];
            current++;
            return value;
        }
    }

    @Override
    public Object clone() {
        CustomArrayList<T> clone = null;
        try {
            clone = (CustomArrayList<T>) super.clone();
            for (int i = 0; i < array.length; i++) {
                try {
                    clone.array[i] = this.array[i].getClass().getMethod("clone").invoke(this.array[i]);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    LOG.error("content type not support cloning", ex);
                    clone.array[i] = this.array[i];
                }
            }
        }  catch (CloneNotSupportedException ex) {
            LOG.error("cloning not supported", ex);
            clone = this;
        }
        return clone;
    }
}
