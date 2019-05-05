package com.homework17.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for random code generator.
 */
public class RandomCodeGeneratorTest {
  @Test
  public void testGenerateFourSignNumber() {
    for (int i = 0; i < 10000; i++) {
      String generatedValue = RandomCodeGenerator.generateFourSignCode();
      int value = Integer.parseInt(generatedValue);
      if (value < 1000 || value > 9999) {
        Assert.fail("Generated invalid value: " + value);
      }
    }
  }
}
