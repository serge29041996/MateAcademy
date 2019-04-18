package com.homework2;

/**
 * Solution for task 2.3.
 */
public class SortUtils {

  private SortUtils() {
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
          swap(sortedArray, j);
          numberSwap++;
        }
      }
      if (numberSwap == 0) {
        isSortedArray = true;
      }
    }
    return sortedArray;
  }

  private static void swap(int[] array, int indexForSwap) {
    int temp = array[indexForSwap + 1];
    array[indexForSwap + 1] = array[indexForSwap];
    array[indexForSwap] = temp;
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
}
