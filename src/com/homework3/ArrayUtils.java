package com.homework3;

/**
 * Class for merge 2 arrays in one and generation array with need length.
 */
public class ArrayUtils {
  private ArrayUtils() {
  }

  /**
   * Create array with elements in ascending order.
   *
   * @param length length of need generated array
   * @param isOdd mark true if need array with odd elements, else will be created array with even
   *        elements.
   * @return array with need length and need elements (odd or even)
   */
  public static int[] generateSortedIntArray(int length, boolean isOdd) {
    int[] generatedArray = new int[length];
    int multipleElement = 2;
    int addElement;
    if (isOdd) {
      addElement = 1;
    } else {
      addElement = 0;
    }

    for (int i = 0; i < length; i++) {
      generatedArray[i] = addElement + multipleElement * i;
    }

    return generatedArray;
  }

  /**
   * Merge two arrays to one.
   * @param leftArray left array for merging
   * @param rightArray right array for merging
   * @return merged array
   */
  public static int[] mergeTwoIntArrays(int[] leftArray, int[] rightArray) {
    int[] mergedArray = new int[leftArray.length + rightArray.length];
    int currentMergedArrayPosition = 0;
    int currentLeftArrayPosition = 0;
    int currentRightArrayPosition = 0;
    while (currentLeftArrayPosition < leftArray.length
        && currentRightArrayPosition < rightArray.length) {
      if (leftArray[currentLeftArrayPosition] < rightArray[currentRightArrayPosition]) {
        mergedArray[currentMergedArrayPosition] = leftArray[currentLeftArrayPosition];
        currentLeftArrayPosition++;
      } else {
        mergedArray[currentMergedArrayPosition] = rightArray[currentRightArrayPosition];
        currentRightArrayPosition++;
      }
      currentMergedArrayPosition++;
    }

    currentMergedArrayPosition = saveAllRemainElementsToMergeArray(currentLeftArrayPosition,
        leftArray, currentMergedArrayPosition, mergedArray);

    saveAllRemainElementsToMergeArray(currentRightArrayPosition,
        rightArray, currentMergedArrayPosition, mergedArray);

    return mergedArray;
  }

  private static int saveAllRemainElementsToMergeArray(int currentArrayPosition, int[] array,
      int currentMergedArrayPosition, int[] mergedArray) {
    while (currentArrayPosition < array.length) {
      mergedArray[currentMergedArrayPosition] = array[currentArrayPosition];
      currentArrayPosition++;
      currentMergedArrayPosition++;
    }
    return currentMergedArrayPosition;
  }
}
