package com.homework4;

import java.util.NoSuchElementException;

/**
 * Realization of the array list.
 */
public class ArrayList<T> implements List<T> {
  private static final int INITIAL_CAPACITY = 10;
  private int capacity;
  private T[] elements;
  private int size = 0;

  public ArrayList() {
    this.capacity = INITIAL_CAPACITY;
    this.elements = (T[])new Object[capacity];
  }

  public ArrayList(int capacity) {
    this.capacity = capacity;
    this.elements = (T[])new Object[capacity];
  }

  @Override
  public void add(T value) {
    ensureCapacity(1, true);
    addElement(value, size);
  }

  @Override
  public void add(T value, int index) {
    if (index < 0 || index > size) {
      throw new IllegalArgumentException("Illegal index for adding: " + index);
    } else {
      ensureCapacity(1, true);
      if (index < size) {
        shiftRightElements(index);
      }
      addElement(value, index);
    }
  }

  @Override
  public void addAll(List<T> list) {
    if (list instanceof ArrayList) {
      ensureCapacity(list.size(), true);
      System.arraycopy((T[])((ArrayList) list).elements, 0, elements, size, list.size());
      size += list.size();
    } else if (list instanceof LinkedList) {
      for (int i = 0; i < list.size(); i++) {
        add(list.get(i));
      }
      size += list.size();
    }
  }

  @Override
  public T get(int index) {
    checkIndex(index, "getting");
    return elements[index];
  }

  @Override
  public void set(T value, int index) {
    checkIndex(index, "setting");
    elements[index] = value;
  }

  @Override
  public T remove(int index) {
    checkIndex(index, "removing");
    return removeElement(index);
  }

  @Override
  public T remove(T t) {
    for (int i = 0; i < size; i++) {
      if (elements[i].equals(t)) {
        return removeElement(i);
      }
    }
    throw new NoSuchElementException("Element " + t + " is not contain.");
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public String toString() {
    StringBuilder resultString = new StringBuilder("Array List: [");
    for (int i = 0; i < size; i++) {
      if (i > 0) {
        resultString.append(",");
      }
      resultString.append(elements[i]);
    }
    resultString.append("]");
    return resultString.toString();
  }

  private void addElement(T value, int index) {
    elements[index] = value;
    size++;
  }

  private void ensureCapacity(int numberAddedElement, boolean increase) {
      changeElements(increase, size + numberAddedElement);
  }

  private void changeElements(boolean increase, int needCapacity) {
    int currentCapacity = capacity;
    if (increase) {
      while (needCapacity > currentCapacity) {
        currentCapacity *= 2;
      }
    } else {
      while (currentCapacity / 2 > size) {
        currentCapacity /= 2;
      }
    }
    capacity = currentCapacity;
    T[] newElements = (T[])new Object[capacity];
    System.arraycopy(elements, 0, newElements, 0, size);
    elements = newElements;
  }

  private void shiftRightElements(int index) {
    System.arraycopy(elements, index, elements, index + 1, (size - index + 1));
  }

  private void checkIndex(int index, String operation) {
    if (index < 0 || index > size || size == 0) {
      throw new ArrayIndexOutOfBoundsException("Invalid index for " + operation + ": " + index);
    }
  }

  private void shiftLeftElements(int index) {
    System.arraycopy(elements, index + 1, elements, index, (size - index - 1));
  }

  private T removeElement(int index) {
    T valueRemovingElement = elements[index];
    if (index < size) {
      shiftLeftElements(index);
    } else {
      elements[index] = null;
    }
    size--;
    ensureCapacity(0, false);
    return valueRemovingElement;
  }
}
