package com.homework1;

/**
 * Solution for task 1.2
 */
public class Rhombus {
  public static void main(String[] args) {
    drawRhombusAsParallelogram(3);
    drawCanonicalRhombus(3);
  }

  private static void drawRhombusAsParallelogram(int height) {
    System.out.println("Rhombus draw as parallelogram:");
    int beginIndex = height - 1;
    int endIndex = 2 * height - 2;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < 2 * height - 1; j++) {
        if (i == 0 || i == height - 1) {
          if (j >= beginIndex && j <= endIndex) {
            System.out.print("*  ");
          } else {
            System.out.print(" ");
          }
        } else if (j == beginIndex || j == endIndex) {
          System.out.print("*  ");
        } else if (j > beginIndex && j < endIndex) {
          System.out.print("   ");
        } else {
          System.out.print(" ");
        }
      }
      beginIndex--;
      endIndex--;
      System.out.println();
    }
  }

  private static void drawCanonicalRhombus(int height) {
    System.out.println("Rhombus draw as canonical:");
    int widthRhombus = 2 * (height - 1);
    int heightRhombus = 2 * (height - 1);
    int currentMinWidth = (height - 1);
    int currentMaxWidth = (height - 1);
    for (int i = 0; i <= heightRhombus; i++) {
      for (int j = 0; j <= widthRhombus; j++) {
        if (j == currentMinWidth || j == currentMaxWidth) {
          System.out.print("*");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
      if (currentMaxWidth < widthRhombus && i < (height - 1)) {
        currentMinWidth--;
        currentMaxWidth++;
      } else {
        currentMinWidth++;
        currentMaxWidth--;
      }
    }
  }
}
