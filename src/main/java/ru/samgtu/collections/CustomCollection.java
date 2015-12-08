package ru.samgtu.collections;

/**
 * Created by Nikolay on 04.11.2015.
 */
public interface CustomCollection<T> extends Iterable<T>, Cloneable {
    void add(int index, T value);
    void add(T value);
    T delete(int index);
    boolean delete(T value);
    int indexOf(T value);
    T get(int index);
    void set(int index, T value);
    boolean isEmpty();
    int size();
    T[] toArray(T[] array);
}
