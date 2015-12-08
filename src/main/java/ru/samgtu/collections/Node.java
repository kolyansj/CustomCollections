package ru.samgtu.collections;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Nikolay on 04.11.2015.
 */
public class Node<T> implements Cloneable {

    private static final Logger LOG = LogManager.getLogger(Node.class);

    private Node<T> next;
    private Node<T> prev;
    private T value;
    private transient int index;

    Node(T value) {
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public T getValue() {
        return value;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (index != node.index) return false;
        if (value != null ? !value.equals(node.value) : node.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + index;
        return result;
    }

    @Override
    public Object clone() {
        Node<T> clone = null;
        try {
            clone = (Node<T>) super.clone();
            if (next.index != 0)
                clone.next = (Node<T>) next.clone();
            else
                clone.next = null;
            try {
                clone.value = (T) value.getClass().getMethod("clone").invoke(value);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                LOG.error("content type not support cloning", ex);
                clone.value = value;
            }
            if (clone.index == 0) {
                Node<T> item = clone;
                do {
                    if (item.getNext() == null) {
                        item.setNext(clone);
                        item.getNext().setPrev(item);
                        break;
                    } else {
                        item.getNext().setPrev(item);
                    }
                    item = item.getNext();
                } while (true);
            }
        } catch (CloneNotSupportedException ex) {
            LOG.error("cloning not supported", ex);
            clone = this;
        }
        return clone;
    }

    @Override
    public String toString() {
        return "Node{value = " + value +", index = " + index + '}';
    }
}
