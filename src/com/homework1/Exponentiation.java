package com.homework1;

/**
 * Solution of task 1.3
 */
public class Exponentiation {
  public static void main(String[] args) {
    System.out.println("Squaring for 2 is " + squaring(2));
    System.out.println("Cubing for 3 is " + cubing(3));
    System.out.println("1 power to 0 = " + exponentiation(1, 0));
    System.out.println("2 power to 10 = " + exponentiation(2, 10));
    System.out.println("0 power to 10 = " + exponentiation(0, 10));
  }

  private static int squaring(int number) {
    return number * number;
  }

  private static int cubing(int number) {
    return number * number * number;
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
