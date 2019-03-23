package com.homework1;

import java.util.Arrays;
import java.util.Random;

/**
 * Solution for task 1.6
 */
public class MatrixOperation {
  public static void main(String[] args) {
    int[][] matrix = fillMatrix(5, 5);
    System.out.println("Initial matrix: ");
    System.out.println(Arrays.deepToString(matrix));
    System.out.println("Min element in matrix is : " + findMinElementInMatrix(matrix));
    System.out.println("Max element in matrix is : " + findMaxElementInMatrix(matrix));
  }

  private static int findMinElementInMatrix(int[][] matrix) {
    int minElement = Integer.MAX_VALUE;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] < minElement) {
          minElement = matrix[i][j];
        }
      }
    }
    return minElement;
  }

  private static int findMaxElementInMatrix(int[][] matrix) {
    int maxElement = Integer.MIN_VALUE;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] > maxElement) {
          maxElement = matrix[i][j];
        }
      }
    }
    return maxElement;
  }

  private static int[][] fillMatrix(int numberRow, int numberColumn) {
    int[][] matrix = new int[numberRow][numberColumn];
    Random random = new Random();
    for (int i = 0; i < numberRow; i++) {
      for (int j = 0; j < numberColumn; j++) {
        matrix[i][j] = random.nextInt(100);
      }
    }
    return matrix;
  }
}
