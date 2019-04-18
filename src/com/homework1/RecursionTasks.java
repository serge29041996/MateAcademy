package com.homework1;

/**
 * Solution of task 1.4
 */
public class RecursionTasks {
  public static void main(String[] args) {
    System.out.println("5! = " + computeFactorial(5));
    System.out.println();
    System.out.println();
    System.out.println("sin(pi/6) with Taylor series = "
        + computeTaylorSeriesForSinus(Math.PI / 6, 0.0001, 0));
    System.out.println("sin(pi/6) with Math.sin = " + Math.sin(Math.PI / 6));
    System.out.println();
    System.out.println();
    System.out.println("sin(pi/4) with Taylor series = "
        + computeTaylorSeriesForSinus(Math.PI / 4, 0.0001, 0));
    System.out.println("sin(pi/4) with Math.sin = " + Math.sin(Math.PI / 4));
    System.out.println();
    System.out.println();
    System.out.println("sin(pi/3) with Taylor series = "
        + computeTaylorSeriesForSinus(Math.PI / 3, 0.0001, 0));
    System.out.println("sin(pi/3) with Math.sin = " + Math.sin(Math.PI / 3));
    System.out.println();
    System.out.println();
    System.out.println("sin(pi/2) with Taylor series = "
        + computeTaylorSeriesForSinus(Math.PI / 2, 0.0001, 0));
    System.out.println("sin(pi/2) with Math.sin = " + Math.sin(Math.PI / 2));
    System.out.println();
    System.out.println();
    System.out.println("sin(pi) with Taylor series = "
        + computeTaylorSeriesForSinus(Math.PI, 0.0001, 0));
    System.out.println("sin(pi) with Math.sin = " + Math.sin(Math.PI));
    System.out.println();
    System.out.println();
    System.out.println("sin(3*pi/2) with Taylor series = "
        + computeTaylorSeriesForSinus(3 * Math.PI / 2, 0.0001, 0));
    System.out.println("sin(3*pi/2) with Math.sin = " + Math.sin(3 * Math.PI / 2));
    System.out.println();
    System.out.println();
    System.out.println("sin(2*pi) with Taylor series = "
        + computeTaylorSeriesForSinus(2 * Math.PI, 0.0001, 0));
    System.out.println("sin(2*pi) with Math.sin = " + Math.sin(2 * Math.PI));
    System.out.println();
    System.out.println();
    System.out.println("sin(1) with Taylor series = "
        + computeTaylorSeriesForSinus(1, 0.0001, 0));
    System.out.println("sin(1) with Math.sin = " + Math.sin(1));
    System.out.println();
    System.out.println();
    System.out.println("sin(7) with Taylor series = "
        + computeTaylorSeriesForSinus(7, 0.0001, 0));
    System.out.println("sin(7) with Math.sin = " + Math.sin(7));
    System.out.println();
    System.out.println();
    System.out.println("sin(10) with Taylor series = "
        + computeTaylorSeriesForSinus(10, 0.0001, 0));
    System.out.println("sin(10) with Math.sin = " + Math.sin(10));
    System.out.println();
    System.out.println();
    System.out.println("The 1 number Fibonacci: "
        + computeFibonacciNumber(1, 1, 1));
    System.out.println();
    System.out.println();
    System.out.println("The 7 number Fibonacci: "
        + computeFibonacciNumber(1, 1, 7));
    System.out.println();
    System.out.println();
    System.out.println("The 10 number Fibonacci: "
        + computeFibonacciNumber(1, 1, 10));
  }

  private static double computeTaylorSeriesForSinus(double number, double accuracy,
      int orderNumber) {
    double result =
        Math.pow(-1, orderNumber) * Math.pow(number, 2 * orderNumber + 1) / computeFactorial(
            2 * orderNumber + 1);
    if (Math.abs(result) > accuracy) {
      return result + computeTaylorSeriesForSinus(number, accuracy, orderNumber + 1);
    } else {
      return result;
    }
  }

  private static double computeFactorial(int number) {
    if (number == 0 || number == 1) {
      return 1;
    } else {
      return number * computeFactorial(number - 1);
    }
  }

  private static int computeFibonacciNumber(int firstNumber, int secondNumber,
      int desiredPosition) {
    if (firstNumber == 1 && secondNumber == 1 && (desiredPosition == 1 || desiredPosition == 2)) {
      return 1;
    } else if (desiredPosition > 1) {
      int nextDesiredPosition =
          (firstNumber == 1 && secondNumber == 1) ? desiredPosition - 2 : desiredPosition - 1;
      int result = firstNumber + secondNumber;
      firstNumber = secondNumber;
      secondNumber = result;
      return computeFibonacciNumber(firstNumber, secondNumber, nextDesiredPosition);
    } else {
      return secondNumber;
    }
  }
}
