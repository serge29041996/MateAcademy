package com.homework4;

/**
 * Interface of list.
 */
public interface List<T> {
  void add(T value);

  void add(T value, int index);

  void addAll(List<T> list);

  T get(int index);

  void set(T value, int index);

  T remove(int index); // возвращаем элемент, который удалили

  T remove(T t); // удалять первый, который встретится

  int size();

  boolean isEmpty();
}
