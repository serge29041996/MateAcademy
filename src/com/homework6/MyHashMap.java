package com.homework6;

import java.util.Collections;
import java.util.HashMap;

/**
 * Changed hash map, which work with list as value.
 */
public class MyHashMap<K, V> extends HashMap<K, V> {
  @Override
  public V get(Object key) {
    return super.get(key) == null ? (V) Collections.EMPTY_LIST : super.get(key);
  }
}
