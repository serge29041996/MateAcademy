package com.homework2;

import java.util.Arrays;
import java.util.Random;

/**
 * Solution for task 2.3.
 */
public class SortUtils {

  private SortUtils() {
  }

  public static void main(String[] args) {
    int[] unsortedArray = generateIntArray(20);
    System.out.println("Unsorted array: ");
    System.out.println(Arrays.toString(unsortedArray));
    long beginTime = System.nanoTime();
    int[] sortedArray = SortUtils.sortIntArrayWithBubbleSort(unsortedArray);
    long endTime = System.nanoTime();
    System.out.println("Sorted array with bubble sort algorithm:");
    System.out.println(Arrays.toString(sortedArray));
    System.out.println("Time of sorting with bubble sort: " + (endTime - beginTime) + " ns");
    System.out.println("Sorted array with merge sort algorithm:");
    beginTime = System.nanoTime();
    sortedArray =
        SortUtils.sortIntArrayWithMergeSort(unsortedArray, 0, unsortedArray.length - 1);
    endTime = System.nanoTime();
    System.out.println(Arrays.toString(sortedArray));
    System.out.println("Time of sorting with merge sort: " + (endTime - beginTime) + " ns");
  }

  public static int[] sortIntArrayWithBubbleSort(int[] unsortedArray) {
    int[] sortedArray = new int[unsortedArray.length];
    System.arraycopy(unsortedArray, 0, sortedArray, 0, unsortedArray.length);
    int numberSwap = 0;
    int temp = 0;
    boolean isSortedArray = false;
    for (int i = 1; i <= sortedArray.length && !isSortedArray; i++) {
      for (int j = 0; j < sortedArray.length - i; j++) {
        if (sortedArray[j] > sortedArray[j + 1]) {
          temp = sortedArray[j + 1];
          sortedArray[j + 1] = sortedArray[j];
          sortedArray[j] = temp;
          numberSwap++;
        }
      }
      if (numberSwap == 0) {
        isSortedArray = true;
      }
    }
    return sortedArray;
  }

  public static int[] sortIntArrayWithMergeSort(int[] unsortedArray, int beginPosition,
      int endPosition) {
    if (beginPosition < endPosition) {
      int splitPosition = (beginPosition + endPosition) / 2;
      int[] leftSortedArray = sortIntArrayWithMergeSort(unsortedArray, beginPosition,
          splitPosition);
      int[] rightSortedArray = sortIntArrayWithMergeSort(unsortedArray, splitPosition + 1,
          endPosition);
      return mergeArray(leftSortedArray, rightSortedArray);
    } else {
      return new int[]{unsortedArray[beginPosition]};
    }
  }

  private static int[] mergeArray(int[] leftArray, int[] rightArray) {
    int numberElements = leftArray.length + rightArray.length;
    int[] mergedArray = new int[numberElements];
    int currentPositionLeftArray = 0;
    int currentPositionRightArray = 0;
    int currentPositionMergedArray = 0;
    while (currentPositionLeftArray < leftArray.length
        && currentPositionRightArray < rightArray.length) {
      if (leftArray[currentPositionLeftArray] < rightArray[currentPositionRightArray]) {
        mergedArray[currentPositionMergedArray] = leftArray[currentPositionLeftArray];
        currentPositionLeftArray++;
        currentPositionMergedArray++;
      } else {
        mergedArray[currentPositionMergedArray] = rightArray[currentPositionRightArray];
        currentPositionRightArray++;
        currentPositionMergedArray++;
      }
    }

    while (currentPositionLeftArray < leftArray.length) {
      mergedArray[currentPositionMergedArray] = leftArray[currentPositionLeftArray];
      currentPositionLeftArray++;
      currentPositionMergedArray++;
    }

    while (currentPositionRightArray < rightArray.length) {
      mergedArray[currentPositionMergedArray] = rightArray[currentPositionRightArray];
      currentPositionRightArray++;
      currentPositionMergedArray++;
    }

    return mergedArray;
  }

  private static int[] generateIntArray(int length) {
    int[] array = new int[length];
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      array[i] = random.nextInt(2 * length);
    }

    return array;
  }
}
