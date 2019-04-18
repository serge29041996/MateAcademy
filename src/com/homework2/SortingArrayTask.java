package com.homework2;

import java.util.Arrays;
import java.util.Random;

/**
 * Solution for task 2.3.
 */
public class SortingArrayTask {
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

  private static int[] generateIntArray(int length) {
    int[] array = new int[length];
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      array[i] = random.nextInt(2 * length);
    }

    return array;
  }
}
