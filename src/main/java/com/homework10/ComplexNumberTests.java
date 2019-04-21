package com.homework10;

/**
 * Tests for equals and hashCode.
 * Task: https://stepik.org/lesson/12769/step/9?unit=3117
 */
public class ComplexNumberTests {
  /**
   * Tests for equals and hashCode of complex number.
   * @param args arguments for application
   */
  public static void main(String[] args) {
    ComplexNumber c1 = new ComplexNumber(1, 1);
    System.out.println("First complex number:");
    System.out.println(c1);
    ComplexNumber c2 = new ComplexNumber(2, -9);
    System.out.println("Second complex number:");
    System.out.println(c2);
    System.out.println("First and second complex numbers are equal? : " + c1.equals(c2));
    System.out.println("First and second complex numbers has equal hash code? : "
        + (c1.hashCode() == c2.hashCode()));
    System.out.println("First complex number equals to yourself? : " + c1.equals(c1));
    System.out.println("First complex number equals to null? : " + c1.equals(null));
    ComplexNumber c3 = new ComplexNumber(1, 1);
    System.out.println();
    System.out.println("Third complex number:");
    System.out.println(c3);
    System.out.println("First and third complex numbers are equal? : " + c1.equals(c3));
    System.out.println("First and third complex numbers has equal hash code? : "
        + (c1.hashCode() == c3.hashCode()));
  }
}
