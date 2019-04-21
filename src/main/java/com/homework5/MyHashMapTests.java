package com.homework5;

/**
 * Tests for realization of hash map.
 */
public class MyHashMapTests {
  /**
   * Tests for realization of hash map.
   * @param args args for application
   */
  public static void main(String[] args) {
    MyHashMap<Integer, Integer> hashMap = new MyHashMap<>();
    System.out.println("Empty hash map:");
    System.out.println(hashMap);
    hashMap.put(1, 11);
    System.out.println("After put key 1 and value 11:");
    System.out.println(hashMap);
    hashMap.put(null, Integer.MIN_VALUE);
    System.out.println("After put key null and value " + Integer.MIN_VALUE + ":");
    System.out.println(hashMap);
    hashMap.put(null, Integer.MAX_VALUE);
    System.out.println("After put key null and value " + Integer.MAX_VALUE + ":");
    System.out.println(hashMap);
    hashMap.put(0, 10);
    System.out.println("After put key 0 and value 10:");
    System.out.println(hashMap);
    System.out.println("Removed element with key 1: " + hashMap.remove(1));
    System.out.println("Hash map after removing element with key 1:");
    System.out.println(hashMap);
    System.out.println("Removed element with key null: " + hashMap.remove(null));
    System.out.println("Hash map after removing element with key null:");
    System.out.println(hashMap);
    System.out.println("Removed element with key 0: " + hashMap.remove(0));
    System.out.println("Hash map after removing element with key 0:");
    System.out.println(hashMap);
    System.out.println("Empty hash map:");
    System.out.println(hashMap);
    for (int i = 0; i < 30; i++) {
      hashMap.put(i, i + 10);
    }
    System.out.println("After putting 30 pairs of elements:");
    System.out.println(hashMap);
    hashMap.clear();
    System.out.println("After clearing hash map:");
    System.out.println(hashMap);
    System.out.println("Size of hash map: " + hashMap.size());
    hashMap.put(0, 10);
    System.out.println("After put key 0 and value 10:");
    System.out.println(hashMap);
    hashMap.put(null, Integer.MAX_VALUE);
    System.out.println("After put key null and value " + Integer.MAX_VALUE + ":");
    System.out.println(hashMap);
    System.out.println("Element with key null: " + hashMap.get(null));
    System.out.println("Element with key 0: " + hashMap.get(0));
    System.out.println("Removed element with key 0: " + hashMap.remove(0));
    System.out.println("Hash map after removing element with key 0:");
    System.out.println(hashMap);
  }
}
