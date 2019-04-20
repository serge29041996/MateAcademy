package com.homework9;

import java.util.function.BiConsumer;

/**
 * Consumer for two integer values.
 */
public class CheckIntegerBiConsumer implements BiConsumer<Integer, Integer> {
  @Override
  public void accept(Integer integer, Integer integer2) {
    if (integer == null || integer2 == null) {
      System.out.println("One of the element is null");
    }
  }
}
