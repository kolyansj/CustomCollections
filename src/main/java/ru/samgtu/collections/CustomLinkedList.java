package ru.samgtu.collections;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * Created by Nikolay on 04.11.2015.
 */
public class CustomLinkedList<T> extends CustomAbstractList<T> {

    private static final Logger LOG = LogManager.getLogger(CustomLinkedList.class);

    private Node<T> first;
    private int size;

    public CustomLinkedList(T... array) {
        for (int i = 0; i < array.length; i++) {
            addLast(array[i]);
        }
    }

    @Override
    public void add(int index, T value) {
        LOG.trace("index: " + index + " | value: " + value);
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        if (index < 0) {
            throw new IllegalArgumentException("Index < 0");
        }
        if (index == 0) {
            addFirst(value);
            return;
        }
        if (index >= size) {
            addLast(value);
            return;
        }

        final Node<T> item = getItem(index);
        final Node<T> prev = item.getPrev();
        final Node<T> elem = new Node<>(value);

        elem.setPrev(prev);
        prev.setNext(elem);
        elem.setNext(item);
        item.setPrev(elem);

        modify();
        size++;
    }

    @Override
    public void add(T value) {
        LOG.trace("value: " + value);
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        addLast(value);
    }

    public void addFirst(T value) {
        LOG.trace("value: " + value);
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        final Node<T> elem = new Node<>(value);
        modify();
        if (first == null) {
            first = elem;
            first.setNext(elem);
            first.setPrev(elem);
            size++;
            return;
        }
        final Node<T> last = getItem(size - 1);
        elem.setPrev(last);
        elem.setNext(first);
        last.setNext(elem);
        first.setPrev(elem);
        first = elem;
        size++;
    }

    public void addLast(T value) {
        LOG.trace("value: " + value);
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        final Node<T> elem = new Node<>(value);
        modify();
        if (first == null) {
            first = elem;
            first.setNext(elem);
            first.setPrev(elem);
            size++;
            return;
        }
        final Node<T> last = getItem(size - 1);
        elem.setNext(first);
        elem.setPrev(last);
        last.setNext(elem);
        first.setPrev(elem);
        size++;
    }

    @Override
    public T delete(int index) {
        LOG.trace("index: " + index);
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index < 0 or index >= size");
        }
        if (index == 0) {
            return deleteFirst();
        }
        if (index == size - 1) {
            return deleteLast();
        }

        final Node<T> del = getItem(index);
        final Node<T> next = del.getNext();
        final Node<T> prev = del.getPrev();

        LOG.debug("delete node object: " + del.toString());

        prev.setNext(next);
        next.setPrev(prev);

        modify();
        size--;
        return del.getValue();
    }

    @Override
    public boolean delete(T value) {
        LOG.trace("value: " + value);
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        if (!isEmpty()) {
            Node<T> item = first;
            do {
                if (item.getValue().equals(value)) {
                    final Node<T> next = item.getNext();
                    final Node<T> prev = item.getPrev();

                    prev.setNext(next);
                    next.setPrev(prev);

                    modify();
                    size--;
                    return true;
                }
                item = item.getNext();
            } while (item != first);
        }
        return false;
    }

    public T deleteFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }

        LOG.debug("delete node object: " + first.toString());

        final T value = first.getValue();
        final Node<T> next = first.getNext();
        final Node<T> last = getItem(size - 1);
        last.setNext(next);
        next.setPrev(last);
        first = next;

        modify();
        size--;
        return value;
    }

    public T deleteLast() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }

        LOG.debug("delete node object: " + first.getPrev().toString());

        final T value = first.getPrev().getValue();
        final Node<T> prev = first.getPrev().getPrev();
        first.setPrev(prev);
        prev.setNext(first);

        modify();
        size--;
        return value;
    }

    @Override
    public int indexOf(T value) {
        LOG.trace("value: " + value);
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        if (!isEmpty()) {
            Node<T> item = first;
            int idx = 0;
            do {
                if (item.getValue().equals(value)) {
                    LOG.debug("found index: " + idx);
                    return idx;
                }
                item = item.getNext();
                idx++;
            } while (item != first);
        }
        return -1;
    }

    @Override
    public T get(int index) {
        LOG.trace("index: " + index);
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index < 0 or index >= size");
        }
        return getItem(index).getValue();
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        return first.getValue();
    }

    public T getLast() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        return getItem(size - 1).getValue();
    }

    @Override
    public void set(int index, T value) {
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index < 0 or index >= size");
        }
        getItem(index).setValue(value);
        modify();
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T[] toArray(T[] array) {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        if (array == null) {
            throw new NullPointerException("Input array is null");
        }
        LOG.trace("array length: " + array.length + " | component type: " + array.getClass().getComponentType());
        array = (T[]) Array.newInstance(array.getClass().getComponentType(), size);
        Node<T> item = first;
        int idx = 0;
        do {
            array[idx++] = item.getValue();
            item = item.getNext();
        } while(item != first);
        return array;
    }

    @Override
    public Iterator<T> iterator() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        return new CustomLinkedListIterator<>();
    }

    private class CustomLinkedListIterator<T> extends AbstractIterator<T> {

        private Node<T> node;
        private int size;

        private CustomLinkedListIterator() {
            super();
        }

        // this is don't correctly work!!!
        @Override
        protected void update() {
            this.size = CustomLinkedList.this.size;
            node = (Node<T>) getItem(current);
        }

        @Override
        public boolean hasNext() {
            check();
            return current < size;
        }

        @Override
        public T next() {
            check();
            T value = node.getValue();
            node = node.getNext();
            current++;
            return value;
        }
    }

    @Override
    public Object clone() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        Node<T> item = first;
        int idx = 0;
        do {
            item.setIndex(idx++);
            item = item.getNext();
        } while(item != first);

        CustomLinkedList<T> clone = null;
        try {
            clone = (CustomLinkedList<T>) super.clone();
            clone.first = (Node<T>) this.first.clone();
        } catch (CloneNotSupportedException ex) {
            LOG.error("cloning not supported", ex);
            clone = this;
        }
        return clone;
    }

    private Node<T> getItem(int index) {
        LOG.trace("index: " + index);
        Node<T> x = first;
        for (int i = 0; i < index; i++) {
            x = x.getNext();
        }
        return x;
    }
}
