package com.homework4;

import java.util.NoSuchElementException;

/**
 * Realization of the linked list.
 */
public class LinkedList<T> implements List<T> {
  private int size = 0;
  private Node<T> firstElement;
  private Node<T> lastElement;

  @Override
  public void add(T value) {
    if (isEmpty()) {
      firstElement = new Node<>(null, value, null);
      lastElement = firstElement;
    } else if (size == 1) {
      lastElement = new Node<>(firstElement, value, null);
      firstElement.nextNode = lastElement;
    } else {
      Node<T> currentNode = new Node<>(lastElement, value, null);
      lastElement.nextNode = currentNode;
      lastElement = currentNode;
    }
    size++;
  }

  @Override
  public void add(T value, int index) {
    if (isEmpty() || index >= size) {
      add(value);
    } else if (index < 0) {
      throw new IllegalArgumentException("Index cannot be less than 0");
    } else {
      int currentIndex = 0;
      Node<T> currentNode = firstElement;
      while (currentIndex < index) {
        currentNode = currentNode.nextNode;
        currentIndex++;
      }
      Node<T> newNode = new Node<>(currentNode.previousNode, value, currentNode);
      if (currentIndex > 0) {
        currentNode.previousNode.nextNode = newNode;
      } else {
        firstElement = newNode;
      }
      currentNode.previousNode = newNode;
      size++;
    }
  }

  @Override
  public void addAll(List<T> list) {
    if (list instanceof LinkedList) {
      if (isEmpty()) {
        firstElement = ((LinkedList<T>) list).getFirstElement();
        lastElement = ((LinkedList<T>) list).getLastElement();
      } else {
        Node<T> firstElementAddedList = ((LinkedList<T>) list).getFirstElement();
        Node<T> lastElementAddedList = ((LinkedList<T>) list).getLastElement();
        lastElement.nextNode = firstElementAddedList;
        lastElement = lastElementAddedList;
      }
      size += list.size();
    } else if (list instanceof ArrayList) {
      int sizeAddedList = list.size();
      for (int i = 0; i < sizeAddedList; i++) {
        add(list.get(i));
      }
      size += sizeAddedList;
    }
  }

  @Override
  public T get(int index) {
    return getNode(index, "getting").value;
  }

  @Override
  public void set(T value, int index) {
    Node<T> nodeForModify = getNode(index, "setting");
    nodeForModify.value = value;
  }

  @Override
  public T remove(int index) {
    Node<T> nodeForRemoving = getNode(index, "removing");
    return removeElement(nodeForRemoving, index);
  }

  @Override
  public T remove(T t) {
    if (isEmpty()) {
      throw new NoSuchElementException("No element in linked list: " + t);
    } else {
      int currentIndex = 0;
      Node<T> currentNode = firstElement;
      if (currentNode.value.equals(t)) {
        return removeElement(currentNode, currentIndex);
      }
      while (currentNode.nextNode != null) {
        currentNode = currentNode.nextNode;
        currentIndex++;
        if (currentNode.value.equals(t)) {
          return removeElement(currentNode, currentIndex);
        }
      }
      throw new NoSuchElementException("No element in linked list: " + t);
    }
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
    StringBuilder result = new StringBuilder("Linked List: [");
    if (!isEmpty()) {
      result.append(firstElement.value);
      Node<T> currentNode = firstElement.nextNode;
      while (currentNode != null) {
        result.append(", ");
        result.append(currentNode.value);
        currentNode = currentNode.nextNode;
      }
    }
    result.append("]");
    return result.toString();
  }

  private Node<T> getNode(int index, String operation) {
    if (index < 0 || index > (size - 1)) {
      throw new ArrayIndexOutOfBoundsException("Invalid index for " + operation
          + " element: " + index);
    } else {
      if (size == 0) {
        throw new ArrayIndexOutOfBoundsException("No elements in linked list.");
      } else if (size == 1) {
        return firstElement;
      } else {
        int currentIndex = 0;
        Node<T> currentNode = firstElement;
        while (currentIndex < index) {
          currentNode = currentNode.nextNode;
          currentIndex++;
        }
        return currentNode;
      }
    }
  }

  private T removeElement(Node<T> nodeForRemoving, int index) {
    if (index == 0) {
      firstElement = nodeForRemoving.nextNode;
      nodeForRemoving.nextNode = null;
      firstElement.previousNode = null;
    } else if (index == (size - 1)) {
      lastElement = nodeForRemoving.previousNode;
      lastElement.nextNode = null;
      nodeForRemoving.previousNode = null;
    } else {
      nodeForRemoving.previousNode.nextNode = nodeForRemoving.nextNode;
      nodeForRemoving.nextNode.previousNode = nodeForRemoving.previousNode;
      nodeForRemoving.previousNode = null;
      nodeForRemoving.nextNode = null;
    }
    size--;
    return nodeForRemoving.value;
  }

  private Node<T> getFirstElement() {
    return firstElement;
  }

  private Node<T> getLastElement() {
    return lastElement;
  }

  private class Node<E> {
    private Node<E> previousNode;
    private E value;
    private Node<E> nextNode;

    public Node(Node<E> previousNode, E value, Node<E> nextNode) {
      this.previousNode = previousNode;
      this.value = value;
      this.nextNode = nextNode;
    }
  }
}
