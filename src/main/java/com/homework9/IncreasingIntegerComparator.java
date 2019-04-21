package com.homework9;

import java.util.Comparator;

/**
 * Realization integer comparator for increasing sequence.
 */
public class IncreasingIntegerComparator implements Comparator<Integer> {
  @Override
  public int compare(Integer o1, Integer o2) {
    return o1 - o2;
  }
}
