package com.homework17.util;

import java.util.Random;

/**
 * Class for generation code for buying good.
 */
public class RandomCodeGenerator {
  private RandomCodeGenerator() {}

  /**
   * Generate four sign code for confirmation purchase.
   * @return generated code with value between 1000 and 9999
   */
  public static String generateFourSignCode() {
    Random random = new Random();
    int generatedCode = random.nextInt(9000) + 1000;
    return String.valueOf(generatedCode);
  }
}
