package com.homework9;

import java.util.Comparator;

/**
 * Realization comparator for decreasing sequence.
 */
public class DecreasingIntegerComparator implements Comparator<Integer> {
  @Override
  public int compare(Integer o1, Integer o2) {
    return o2 - o1;
  }
}
