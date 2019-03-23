package com.homework1;

/**
 * Solution of task 1.4
 */
public class RecursionTasks {
  public static void main(String[] args) {
    System.out.println("5! = " + computeFactorial(5));
    System.out.println("sin(pi/6) with Taylor series = " +
        computeTaylorSeriesForSinus(Math.PI / 6, 0.0001, 0));
    System.out.println("sin(pi/6) with Math.sin = " + Math.sin(Math.PI / 6));
    System.out.println("sin(pi/4) with Taylor series = " +
        computeTaylorSeriesForSinus(Math.PI / 4, 0.0001, 0));
    System.out.println("sin(pi/4) with Math.sin = " + Math.sin(Math.PI / 4));
    System.out.println("sin(pi/3) with Taylor series = " +
        computeTaylorSeriesForSinus(Math.PI / 3, 0.0001, 0));
    System.out.println("sin(pi/3) with Math.sin = " + Math.sin(Math.PI / 3));
    System.out.println("sin(pi/2) with Taylor series = " +
        computeTaylorSeriesForSinus(Math.PI / 2, 0.0001, 0));
    System.out.println("sin(pi/2) with Math.sin = " + Math.sin(Math.PI / 2));
    System.out.println("sin(pi) with Taylor series = " +
        computeTaylorSeriesForSinus(Math.PI, 0.0001, 0));
    System.out.println("sin(pi) with Math.sin = " + Math.sin(Math.PI));
    System.out.println("sin(3*pi/2) with Taylor series = " +
        computeTaylorSeriesForSinus(3 * Math.PI / 2, 0.0001, 0));
    System.out.println("sin(3*pi/2) with Math.sin = " + Math.sin(3 * Math.PI / 2));
    System.out.println("sin(2*pi) with Taylor series = " +
        computeTaylorSeriesForSinus(2 * Math.PI, 0.0001, 0));
    System.out.println("sin(2*pi) with Math.sin = " + Math.sin(2 * Math.PI));
    System.out.println("The 7 number Fibonacci" +
        computeFibonacciNumber(1, 1, 7));
  }

  private static double computeTaylorSeriesForSinus(double x, double accuracy, int n) {
    double result = Math.pow(-1, n) * Math.pow(x, 2 * n + 1) / computeFactorial(2 * n + 1);
    if (Math.abs(result) > accuracy) {
      return result + computeTaylorSeriesForSinus(x, accuracy, n + 1);
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

  private static int computeFibonacciNumber(int firstNumber, int secondNumber, int desiredPosition) {
    if (firstNumber == 1 && secondNumber == 1) {
      if (desiredPosition == 1 || desiredPosition == 2) {
        return 1;
      } else {
        int result = firstNumber + secondNumber;
        firstNumber = secondNumber;
        secondNumber = result;
        return computeFibonacciNumber(firstNumber, secondNumber, desiredPosition - 2);
      }
    } else if (desiredPosition > 1) {
      int result = firstNumber + secondNumber;
      firstNumber = secondNumber;
      secondNumber = result;
      return computeFibonacciNumber(firstNumber, secondNumber, desiredPosition - 1);
    } else {
      return secondNumber;
    }
  }
}
