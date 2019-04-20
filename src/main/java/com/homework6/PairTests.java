package com.homework6;

/**
 * Tests for class Pair.
 */
public class PairTests {
  public static void main(String[] args) {
    System.out.println("Create first pair with values 1 and hello");
    Pair<Integer, String> pair1 = Pair.of(1, "hello");
    System.out.println(pair1);
    System.out.println("First value of pair : " + pair1.getFirst());
    System.out.println("Second value of pair : " + pair1.getSecond());
    System.out.println("Create second pair with values 1 and hello");
    Pair<Integer, String> pair2 = Pair.of(1, "hello");
    System.out.println(pair2);
    System.out.println("Both pairs are equal? : " + pair1.equals(pair2));
    System.out.println("Both pairs have equal hash code? : "
        + (pair1.hashCode() == pair2.hashCode()));
  }
}
