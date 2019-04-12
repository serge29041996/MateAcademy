package com.homework9;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Solution for task: https://stepik.org/lesson/12781/step/11?unit=3128
 */
public class PseudoGeneratorTask {
  /**
   * Test pseudo generator.
   * @param args arguments for application
   */
  public static void main(String[] args) {
    IntStream generatedStream = pseudoRandomStream(13);
    int[] generatedArray = generatedStream.toArray();
    System.out.println("Generated stream with 10 elements begin from 13:");
    System.out.println(Arrays.toString(generatedArray));
  }

  private static IntStream pseudoRandomStream(int seed) {
    return IntStream.iterate(seed, n -> mid(n * n));
  }

  private static int mid(int number) {
    int result = 0;
    int basisNumber = 10;
    int power = 0;
    int currentBasisNumber = (int)Math.pow(basisNumber, power);
    while (number / currentBasisNumber > 0) {
      if (power > 0 && power < 4) {
        result = (number % (currentBasisNumber * basisNumber)) / basisNumber;
      }
      power++;
      currentBasisNumber *= basisNumber;
    }

    return result;
  }
}
