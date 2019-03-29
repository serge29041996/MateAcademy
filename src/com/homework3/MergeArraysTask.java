package com.homework3;

import java.util.Arrays;

/**
 * Example of using merge of sorting arrays.
 */
public class MergeArraysTask {
  public static void main(String[] args) {
    boolean isNeedArrayWithOddElement = true;
    System.out.println("1. When left and right arrays have equal number elements: ");
    int[] leftArray = ArrayUtils.generateSortedIntArray(10, isNeedArrayWithOddElement);
    System.out.println(Arrays.toString(leftArray));
    int[] rightArray = ArrayUtils.generateSortedIntArray(10, !isNeedArrayWithOddElement);
    System.out.println(Arrays.toString(rightArray));
    System.out.println("Merged array:");
    int[] mergedArray = ArrayUtils.mergeTwoIntArrays(leftArray, rightArray);
    System.out.println(Arrays.toString(mergedArray));
    System.out.println();
    System.out.println();
    System.out.println("2. When left array has more elements than right: ");
    leftArray = ArrayUtils.generateSortedIntArray(15, isNeedArrayWithOddElement);
    System.out.println(Arrays.toString(leftArray));
    rightArray = ArrayUtils.generateSortedIntArray(10, !isNeedArrayWithOddElement);
    System.out.println(Arrays.toString(rightArray));
    System.out.println("Merged array:");
    mergedArray = ArrayUtils.mergeTwoIntArrays(leftArray, rightArray);
    System.out.println(Arrays.toString(mergedArray));
    System.out.println();
    System.out.println();
    System.out.println("3. When right array has more elements than left: ");
    leftArray = ArrayUtils.generateSortedIntArray(10, isNeedArrayWithOddElement);
    System.out.println(Arrays.toString(leftArray));
    rightArray = ArrayUtils.generateSortedIntArray(15, !isNeedArrayWithOddElement);
    System.out.println(Arrays.toString(rightArray));
    System.out.println("Merged array:");
    mergedArray = ArrayUtils.mergeTwoIntArrays(leftArray, rightArray);
    System.out.println(Arrays.toString(mergedArray));
    System.out.println();
    System.out.println();
    System.out.println("4. When left array has 0 elements: ");
    leftArray = ArrayUtils.generateSortedIntArray(0, isNeedArrayWithOddElement);
    System.out.println(Arrays.toString(leftArray));
    rightArray = ArrayUtils.generateSortedIntArray(10, !isNeedArrayWithOddElement);
    System.out.println(Arrays.toString(rightArray));
    System.out.println("Merged array:");
    mergedArray = ArrayUtils.mergeTwoIntArrays(leftArray, rightArray);
    System.out.println(Arrays.toString(mergedArray));
    System.out.println();
    System.out.println();
    System.out.println("5. When right array has 0 elements: ");
    leftArray = ArrayUtils.generateSortedIntArray(10, isNeedArrayWithOddElement);
    System.out.println(Arrays.toString(leftArray));
    rightArray = ArrayUtils.generateSortedIntArray(0, !isNeedArrayWithOddElement);
    System.out.println(Arrays.toString(rightArray));
    System.out.println("Merged array:");
    mergedArray = ArrayUtils.mergeTwoIntArrays(leftArray, rightArray);
    System.out.println(Arrays.toString(mergedArray));
    System.out.println();
    System.out.println();
  }
}
