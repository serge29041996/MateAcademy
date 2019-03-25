package com.homework1;

/**
 * Solution for task 1.2
 */
public class Rhombus {

  public static void main(String[] args) {
    // drawRhombusAsParallelogram(3);
    drawCanonicalRhombus(10);
  }

  private static void drawRhombusAsParallelogram(int height) {
    System.out.println("Rhombus draw as parallelogram:");
    int beginIndex = height - 1;
    int endIndex = 2 * height - 2;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < 2 * height - 1; j++) {
        outputLinesOfRhombusAsParallelogram(i, j, beginIndex, endIndex, height);
      }
      beginIndex--;
      endIndex--;
      System.out.println();
    }
  }

  private static void outputLinesOfRhombusAsParallelogram(int indexOuterCycle, int indexInnerCycle,
      int beginIndex, int endIndex, int height) {
    if (indexOuterCycle == 0 || indexOuterCycle == height - 1) {
      outputTopAndBottomEdgesRhombusAsParallelogram(indexInnerCycle, beginIndex, endIndex);
    } else if (indexInnerCycle == beginIndex || indexInnerCycle == endIndex) {
      System.out.print("*  ");
    } else if (indexInnerCycle > beginIndex && indexInnerCycle < endIndex) {
      System.out.print("   ");
    } else {
      System.out.print(" ");
    }
  }

  private static void outputTopAndBottomEdgesRhombusAsParallelogram(int indexInnerCycle,
      int beginIndex, int endIndex) {
    if (indexInnerCycle >= beginIndex && indexInnerCycle <= endIndex) {
      System.out.print("*  ");
    } else {
      System.out.print(" ");
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
        outputLinesForCanonicalRhombus(j, currentMinWidth, currentMaxWidth);
      }
      System.out.println();
      int[] updatedMinAndMaxWidth = changeMinAndMaxWidth(i, height, widthRhombus, currentMinWidth,
          currentMaxWidth);
      currentMinWidth = updatedMinAndMaxWidth[0];
      currentMaxWidth = updatedMinAndMaxWidth[1];
    }
  }

  private static void outputLinesForCanonicalRhombus(int indexInnerCycle, int currentMinWidth,
      int currentMaxWidth) {
    if (indexInnerCycle == currentMinWidth || indexInnerCycle == currentMaxWidth) {
      System.out.print("*");
    } else {
      System.out.print(" ");
    }
  }

  private static int[] changeMinAndMaxWidth(int indexCycle, int height, int widthRhombus,
      int currentMinWidth, int currentMaxWidth) {
    int[] minAndMaxWidth = new int[2];
    if (currentMaxWidth < widthRhombus && indexCycle < (height - 1)) {
      minAndMaxWidth[0] = --currentMinWidth;
      minAndMaxWidth[1] = ++currentMaxWidth;
    } else {
      minAndMaxWidth[0] = ++currentMinWidth;
      minAndMaxWidth[1] = --currentMaxWidth;
    }
    return minAndMaxWidth;
  }
}
