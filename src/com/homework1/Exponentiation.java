package com.homework1;

/**
 * Solution of task 1.3
 */
public class Exponentiation {

  public static void main(String[] args) {
    System.out.println("Squaring for 2 is " + exponentiation(2, 2));
    System.out.println("Cubing for 3 is " + exponentiation(3, 3));
    System.out.println("1 power to 0 = " + exponentiation(1, 0));
    System.out.println("2 power to 10 = " + exponentiation(2, 10));
    System.out.println("0 power to 10 = " + exponentiation(0, 10));
  }

  private static int exponentiation(int number, int power) {
    if (power == 0) {
      return 1;
    } else if (number == 0) {
      return 0;
    } else {
      int currentResult = number;
      for (int i = 0; i < power - 1; i++) {
        currentResult *= number;
      }
      return currentResult;
    }
  }
}
