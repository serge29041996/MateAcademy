package com.homework5;

/**
 * Realization of HashMap.
 */
public class MyHashMap<K, V> {
  private static final int INITIAL_CAPACITY = 16;
  private static final double INITIAL_LOAD_FACTOR = 0.7;
  private int size = 0;
  private double loadFactor;
  private Node<K,V>[] table;

  public MyHashMap() {
    this(INITIAL_CAPACITY);
  }

  public MyHashMap(int capacity) {
    this(capacity, INITIAL_LOAD_FACTOR);
  }

  /**
   * Constructor with capacity and load factor.
   * @param capacity size of table for saving data
   * @param loadFactor max coefficient, when table expands
   * @throws IllegalArgumentException when capacity less than 2 or load factor less than 0
   */
  public MyHashMap(int capacity, double loadFactor) {
    if (capacity < 2) {
      throw new IllegalArgumentException("Capacity cannot be less than 2");
    }
    if (loadFactor < 0) {
      throw new IllegalArgumentException("Load factor cannot be less than 0");
    }
    this.loadFactor = loadFactor;
    this.table = (Node<K, V>[]) new Node[capacity];
  }

  /**
   * Put new key and value if their has not in table or rewrite value for existing key.
   * @param key key for getting
   * @param value value, which associated with key
   */
  public void put(K key, V value) {
    int[] hashAndIndex = computeHashAndIndex(key);
    int hash = hashAndIndex[0];
    int indexForSaving = hashAndIndex[1];
    Node<K, V> elementTable = table[indexForSaving];
    if (elementTable == null) {
      table[indexForSaving] = new Node<>(hash, key, value, null);
      size++;
    } else if (hash == elementTable.hash
        && (key == elementTable.key || checkEquals(key, elementTable.key))) {
      elementTable.value = value;
    } else {
      table[indexForSaving] = new Node<>(hash, key, value, elementTable);
      size++;
    }
    checkLoad(true);
  }

  /**
   * Remove value with this key.
   * @param key key for deleting value
   * @return value, if it found with key or null if such value missing
   */
  public V remove(K key) {
    int[] hashAndIndex = computeHashAndIndex(key);
    int hash = hashAndIndex[0];
    int indexForRemoving = hashAndIndex[1];
    Node<K, V> previousNode = null;
    for (Node<K, V> e = table[indexForRemoving]; e != null; e = e.next) {
      if (hash == e.hash && (key == e.key || checkEquals(key, e.key))) {
        if (e.next == null && previousNode == null) {
          table[indexForRemoving] = null;
          size--;
          checkLoad(false);
          return e.value;
        } else {
          previousNode.next = null;
          size--;
          checkLoad(false);
          return e.value;
        }
      }
      previousNode = e;
    }
    return null;
  }

  /**
   * Clear map from all elements.
   */
  public void clear() {
    for (int i = 0; i < table.length && size > 0; i++) {
      size = removeNextNode(table[i], size);
      table[i] = null;
    }
    table = (Node<K, V>[])new Node[INITIAL_CAPACITY];
  }

  public int size() {
    return size;
  }

  /**
   * Get value with help of key.
   * @param key key for searching value
   * @return found value or null, if value with key is missing
   */
  public V get(K key) {
    int[] hashAndIndex = computeHashAndIndex(key);
    int hash = hashAndIndex[0];
    int indexForGetting = hashAndIndex[1];
    for (Node<K, V> node = table[indexForGetting]; node != null; node = node.next) {
      if (node.hash == hash && (node.key == key || checkEquals(key, node.key))) {
        return node.value;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("HashMap: [");
    int currentSize = size;
    for (int i = 0; i < table.length && currentSize > 0; i++) {
      for (Node<K, V> node = table[i]; node != null; node = node.next) {
        currentSize--;
        stringBuilder.append(node.key);
        stringBuilder.append("=");
        stringBuilder.append(node.value);
        if (currentSize > 0) {
          stringBuilder.append(",");
        }
      }
    }
    stringBuilder.append("]");
    return stringBuilder.toString();
  }

  private int[] computeHashAndIndex(K key) {
    int[] datas = new int[2];
    if (key == null) {
      datas[0] = 0;
    } else {
      datas[0] = Math.abs(key.hashCode());
    }
    datas[1] = datas[0] % table.length;
    return datas;
  }

  private void checkLoad(boolean increase) {
    int newCapacity = table.length;
    boolean isChanged = false;
    if (increase) {
      while (size >= loadFactor * newCapacity) {
        newCapacity *= 2;
        isChanged = true;
      }
    } else {
      while (loadFactor * newCapacity > size && newCapacity > INITIAL_CAPACITY) {
        newCapacity /= 2;
        isChanged = true;
      }
    }
    if (isChanged) {
      changeSize(newCapacity);
    }
  }

  private void changeSize(int newCapacity) {
    Node<K,V>[] newTable = (Node<K, V>[])new Node[newCapacity];
    int currentSize = size;
    int newIndex;
    Node<K, V> nextNode;
    for (int i = 0; i < table.length && currentSize > 0; i++) {
      for (Node<K, V> e = table[i]; e != null; e = nextNode) {
        newIndex = e.hash % newCapacity;
        if (newTable[newIndex] == null) {
          newTable[newIndex] = e;
        } else {
          newTable[newIndex].next = e;
        }
        nextNode = e.next;
        e.next = null;
        currentSize--;
      }
    }
    table = newTable;
  }

  private int removeNextNode(Node<K, V> node, int currentSize) {
    if (node != null) {
      int size = removeNextNode(node.next, currentSize);
      node.next = null;
      return --size;
    } else {
      return currentSize;
    }
  }

  private boolean checkEquals(K firstKey, K secondKey) {
    if (firstKey == null && secondKey != null) {
      return false;
    } else {
      return firstKey.equals(secondKey);
    }
  }

  private class Node<K, V> {
    private final int hash;
    private K key;
    private V value;
    private Node<K, V> next;

    private Node(int hash, K key, V value, Node<K, V> next) {
      this.hash = hash;
      this.key = key;
      this.value = value;
      this.next = next;
    }
  }
}
