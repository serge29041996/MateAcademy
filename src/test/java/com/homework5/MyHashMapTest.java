package com.homework5;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for my realization of hash map.
 */
public class MyHashMapTest {
  private static final String TEST_KEY = "1";
  private static final String TEST_VALUE = "test";
  private static MyHashMap<String, String> hashMapWithOneElement;

  @Before
  public void init() {
    hashMapWithOneElement = new MyHashMap<>();
    hashMapWithOneElement.put(TEST_KEY, TEST_VALUE);
  }

  @Test
  public void putNewKeyValue(){
    MyHashMap<String, String> hashMap = new MyHashMap<>();
    hashMap.put(TEST_KEY, TEST_VALUE);
    String gettingValue = hashMap.get(TEST_KEY);
    Assert.assertEquals(TEST_VALUE, gettingValue);
    Assert.assertEquals(1, hashMap.size());
  }

  @Test
  public void putNewValueForExistingKey() {
    String gettingValue = hashMapWithOneElement.get(TEST_KEY);
    hashMapWithOneElement.put(TEST_KEY, "new_value");
    String valueAfterPutting = hashMapWithOneElement.get(TEST_KEY);
    Assert.assertNotEquals(gettingValue, valueAfterPutting);
    Assert.assertEquals("new_value", valueAfterPutting);
    Assert.assertEquals(1, hashMapWithOneElement.size());
  }

  @Test
  public void removeExistingValue() {
    int currentSizeMap = hashMapWithOneElement.size();
    String removingValue = hashMapWithOneElement.remove(TEST_KEY);
    Assert.assertEquals(TEST_VALUE, removingValue);
    Assert.assertEquals(currentSizeMap - 1, hashMapWithOneElement.size());
  }

  @Test
  public void removeNonExistingValue() {
    MyHashMap<String, String> hashMap = new MyHashMap<>();
    Assert.assertNull(hashMap.remove(TEST_KEY));
  }

  @Test
  public void clear() {
    hashMapWithOneElement.clear();
    Assert.assertEquals(0, hashMapWithOneElement.size());
  }

  @Test
  public void sizeEmptyHashMap() {
    MyHashMap<String, String> hashMap = new MyHashMap<>();
    Assert.assertEquals(0, hashMap.size());
  }

  @Test
  public void sizeNonEmptyHashMap() {
    Assert.assertEquals(1, hashMapWithOneElement.size());
  }

  @Test
  public void getExistingValue() {
    Assert.assertEquals(TEST_VALUE, hashMapWithOneElement.get(TEST_KEY));
  }

  @Test
  public void getValueFromEmptyHashMap() {
    MyHashMap<String, String> hashMap = new MyHashMap<>();
    Assert.assertNull(hashMap.get(TEST_KEY));
  }

  @Test
  public void toStringFromEmptyHashMap() {
    MyHashMap<String, String> hashMap = new MyHashMap<>();
    Assert.assertEquals("HashMap: []", hashMap.toString());
  }

  @Test
  public void toStringFromNonEmptyHashMap() {
    Assert.assertEquals("HashMap: [1=test]", hashMapWithOneElement.toString());
  }
}