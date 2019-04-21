package com.homework6;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Find symmetric difference for two sets.
 */
public class SymmetricDifferenceTask {
  /**
   * Test find symmetric difference of two sets.
   * @param args arguments to application
   */
  public static void main(String[] args) {
    Set<Integer> set1 = new HashSet<>();
    addElementsToSet(set1, 1, 3);
    System.out.println("First set:");
    System.out.println(set1);
    Set<Integer> set2 = new HashSet<>();
    addElementsToSet(set2, 0, 2);
    System.out.println("Second set:");
    System.out.println(set2);
    Set<Integer> symmetricDifference = symmetricDifference(set1, set2);
    System.out.println("Symmetric difference set:");
    System.out.println(symmetricDifference);
    Set<Integer> set = Collections.unmodifiableSet(set2);
    System.out.println("Unmodifiable set from second set");
    System.out.println(set);
    symmetricDifference = symmetricDifference(set1, set);
    System.out.println("Symmetric difference from first and unmodifiable set:");
    System.out.println(symmetricDifference);
  }

  /**
   * Find symmetric difference of two sets.
   * @param set1 first set
   * @param set2 second set
   * @param <T> class or interface, which object is saving in sets
   * @return symmetric difference of two sets.
   */
  private static <T> Set<T> symmetricDifference(Set<? extends T> set1, Set<? extends T> set2) {
    Set<T> symmetricDifference = new HashSet<>();
    Set<T> firstDifference = new HashSet<>(set1);
    Set<T> secondDifference = new HashSet<>(set2);
    firstDifference.removeAll(set2);
    secondDifference.removeAll(set1);
    symmetricDifference.addAll(firstDifference);
    symmetricDifference.addAll(secondDifference);
    return symmetricDifference;
  }

  private static void addElementsToSet(Set<Integer> set, int beginValue, int endValue) {
    for (int i = beginValue; i <= endValue; i++) {
      set.add(i);
    }
  }
}
