package com.homework4;

import java.util.NoSuchElementException;

/**
 * Tests for realization of array list.
 */
public class ArrayListTests {
  /**
   * Tests for array list.
   * @param args arguments for application
   */
  public static void main(String[] args) {
    testToStringEmptyArrayList();
    testAddOneElementToArrayList();
    testAddTwoElementsToArrayList();
    testAddThreeElementsToArrayList();
    testAddElementToSecondPosition();
    testAddElementToLastPosition();
    testAddElementToEnd();
    testAddElementToFirstPosition();
    testAddElementToNonexistentPosition();
    testAddArrayListWithOneElement();
    testAddArrayListWithThirtyFiveElements();
    testAddArrayListWithTwoElementsToEmptyArrayList();
    testAddEmptyArrayList();
    testGetElementWithNegativeIndexFromArrayList();
    testGetElementWithNonexistentIndexFromArrayList();
    testGetElementWithIndexFromEmptyArrayList();
    testGetElementWithPositiveIndexFromArrayList();
    testSetNewValueToSecondElementArrayList();
    testSetNewValueToOneElementArrayList();
    testSetNewValueToLastElementArrayList();
    testRemoveFirstElementArrayListByIndex();
    testRemoveLastElementArrayListByIndex();
    testRemoveMiddleElementArrayListByIndex();
    testRemoveNonexistentElementArrayListByIndex();
    testRemoveExistentElementArrayListByValue();
    testRemoveNonexistentElementArrayListByValue();
    testRemoveElementFromEmptyArrayListByValue();
    testAddAndRemovingElementFromBigList();
  }

  private static void testToStringEmptyArrayList() {
    System.out.println("1. Test to string with empty array list:");
    List<Integer> list = new ArrayList<>();
    outputArrayList(list);
    System.out.println();
  }

  private static void testAddOneElementToArrayList() {
    System.out.println("2. Test add one element to array list:");
    List<Integer> list = new ArrayList<>();
    System.out.println("Array list before adding element:");
    outputArrayList(list);
    addElementsToArrayList(list, 1, 0);
    System.out.println("Array list after adding one element:");
    outputArrayList(list);
    System.out.println("Size of array list: " + list.size());
    System.out.println();
  }

  private static void testAddTwoElementsToArrayList() {
    System.out.println("3. Test add two elements to array list:");
    List<Integer> list = new ArrayList<>();
    System.out.println("Array list before adding elements:");
    outputArrayList(list);
    addElementsToArrayList(list, 2, 0);
    System.out.println("Array list after adding two elements:");
    System.out.println(list);
    System.out.println("Size of array list: " + list.size());
    System.out.println();
  }

  private static void testAddThreeElementsToArrayList() {
    System.out.println("4. Test add three elements to array list:");
    List<Integer> list = new ArrayList<>(3);
    System.out.println("Array list before adding elements:");
    outputArrayList(list);
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list after adding three elements:");
    System.out.println(list);
    System.out.println("Size of array list: " + list.size());
    System.out.println();
  }

  private static void testAddElementToSecondPosition() {
    System.out.println("5. Test add element to second position in array list:");
    List<Integer> list = new ArrayList<>(3);
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list before adding elements:");
    outputArrayList(list);
    list.add(4, 1);
    System.out.println("Array list after adding element 4 to second position:");
    System.out.println(list);
    System.out.println("Size of array list: " + list.size());
    System.out.println();
  }

  private static void testAddElementToLastPosition() {
    System.out.println("6. Test add element to penultimate position in array list:");
    List<Integer> list = new ArrayList<>();
    System.out.println("Array list before adding elements:");
    outputArrayList(list);
    addElementsToArrayList(list, 3, 0);
    list.add(4, 2);
    System.out.println("Array list after adding element 4 to penultimate position:");
    System.out.println(list);
    System.out.println("Size of array list: " + list.size());
    System.out.println();
  }

  private static void testAddElementToEnd() {
    System.out.println("7. Test add element to last position in array list:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list before adding elements:");
    outputArrayList(list);
    list.add(4, 3);
    System.out.println("Array list after adding element 4 to last position:");
    System.out.println(list);
    System.out.println("Size of array list: " + list.size());
    System.out.println();
  }

  private static void testAddElementToFirstPosition() {
    System.out.println("8. Test add element to first position in array list:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list before adding elements:");
    outputArrayList(list);
    list.add(4, 0);
    System.out.println("Array list after adding element 4 to first position:");
    System.out.println(list);
    System.out.println("Size of array list: " + list.size());
    System.out.println();
  }

  private static void testAddElementToNonexistentPosition() {
    System.out.println("9. Test add element to nonexistent position in array list:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list before adding elements:");
    outputArrayList(list);
    try {
      System.out.println("Try add element 4 to index -1");
      list.add(4, -1);
      System.out.println("Array list after adding element 4 to first position:");
      System.out.println(list);
      System.out.println("Size of array list: " + list.size());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void testAddArrayListWithOneElement() {
    System.out.println("10. Test add another Array list with one element:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("First array list before adding elements:");
    outputArrayList(list);
    List<Integer> newList = new ArrayList<>();
    addElementsToArrayList(newList, 1, 10);
    System.out.println("Second array list before adding elements:");
    outputArrayList(newList);
    list.addAll(newList);
    System.out.println("Combined array list after adding second Array list:");
    System.out.println(list);
    System.out.println("Size of combined array list: " + list.size());
    System.out.println();
  }

  private static void testAddArrayListWithThirtyFiveElements() {
    System.out.println("11. Test add another Array list with thirty five elements:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 10, 0);
    System.out.println("First array list before adding elements:");
    outputArrayList(list);
    List<Integer> newList = new ArrayList<>();
    addElementsToArrayList(newList, 35, 10);
    System.out.println("Second array list before adding elements:");
    outputArrayList(newList);
    list.addAll(newList);
    System.out.println("Combined array list after adding second Array list:");
    System.out.println(list);
    System.out.println("Size of combined array list: " + list.size());
    System.out.println();
  }

  private static void testAddArrayListWithTwoElementsToEmptyArrayList() {
    System.out.println("12. Test add another array list to empty array list:");
    List<Integer> list = new ArrayList<>();
    System.out.println("First array list before adding elements:");
    outputArrayList(list);
    List<Integer> newList = new ArrayList<>();
    addElementsToArrayList(newList, 2, 10);
    System.out.println("Second array list before adding elements:");
    outputArrayList(newList);
    list.addAll(newList);
    System.out.println("Combined array list after adding second Array list:");
    System.out.println(list);
    System.out.println("Size of combined array list: " + list.size());
    System.out.println();
  }

  private static void testAddEmptyArrayList() {
    System.out.println("13. Test add empty Array list:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("First array list before adding elements:");
    outputArrayList(list);
    List<Integer> newList = new ArrayList<>();
    System.out.println("Second array list before adding elements:");
    outputArrayList(newList);
    list.addAll(newList);
    System.out.println("Combined array list after adding second Array list:");
    System.out.println(list);
    System.out.println("Size of combined array list: " + list.size());
    System.out.println();
  }

  private static void testGetElementWithNegativeIndexFromArrayList() {
    System.out.println("14. Test get element from negative index:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list:");
    outputArrayList(list);
    try {
      System.out.println("Try get element with index -1");
      list.get(-1);
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void testGetElementWithNonexistentIndexFromArrayList() {
    System.out.println("15. Test get element from nonexistent index:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list:");
    outputArrayList(list);
    try {
      System.out.println("Try get element with index 100");
      list.get(100);
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void testGetElementWithIndexFromEmptyArrayList() {
    System.out.println("16. Test get element from empty array list:");
    List<Integer> list = new ArrayList<>();
    System.out.println("Array list:");
    outputArrayList(list);
    try {
      System.out.println("Try get element from empty array list");
      list.get(0);
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void testGetElementWithPositiveIndexFromArrayList() {
    System.out.println("17. Test get element from not empty array list with positive index:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list:");
    outputArrayList(list);
    Integer needElement = list.get(1);
    System.out.println("Second element in the array list is: " + needElement);
    System.out.println();
  }

  private static void testSetNewValueToSecondElementArrayList() {
    System.out.println("18. Test set new value to second element:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list:");
    outputArrayList(list);
    list.set(10, 1);
    System.out.println("Array list after setting value 10 to second element:");
    System.out.println(list);
    System.out.println();
  }

  private static void testSetNewValueToOneElementArrayList() {
    System.out.println("19. Test set new value to first element:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 1, 0);
    System.out.println("Array list:");
    outputArrayList(list);
    list.set(20, 0);
    System.out.println("Array list after setting value 20 to first element:");
    System.out.println(list);
    System.out.println();
  }

  private static void testSetNewValueToLastElementArrayList() {
    System.out.println("20. Test set new value to last element:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list:");
    outputArrayList(list);
    list.set(30, 2);
    System.out.println("Array list after setting value 30 to last element:");
    System.out.println(list);
    System.out.println();
  }

  private static void testRemoveFirstElementArrayListByIndex() {
    System.out.println("21. Test remove first element by index:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list:");
    outputArrayList(list);
    list.remove(0);
    System.out.println("Array list after removing first element by index:");
    System.out.println(list);
    System.out.println("Size of array list: " + list.size());
    System.out.println();
  }

  private static void testRemoveLastElementArrayListByIndex() {
    System.out.println("22. Test remove last element by index:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list:");
    outputArrayList(list);
    list.remove(2);
    System.out.println("Array list after removing last element by index:");
    System.out.println(list);
    System.out.println("Size of array list: " + list.size());
    System.out.println();
  }

  private static void testRemoveMiddleElementArrayListByIndex() {
    System.out.println("23. Test remove middle element by index:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list:");
    outputArrayList(list);
    list.remove(1);
    System.out.println("Array list after removing second element by index:");
    System.out.println(list);
    System.out.println("Size of array list: " + list.size());
    System.out.println();
  }

  private static void testRemoveNonexistentElementArrayListByIndex() {
    System.out.println("24. Test remove element by nonexistent index:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list:");
    outputArrayList(list);
    try {
      System.out.println("Try remove element with index 5");
      list.remove(5);
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void testRemoveExistentElementArrayListByValue() {
    System.out.println("24. Test remove first element by existent value:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list:");
    outputArrayList(list);
    list.remove(new Integer(1));
    System.out.println("Array list after removing element with value 1:");
    System.out.println(list);
    System.out.println("Size of Array list: " + list.size());
    System.out.println();
  }

  private static void testRemoveNonexistentElementArrayListByValue() {
    System.out.println("25. Test remove nonexistent value from nonempty array list:");
    List<Integer> list = new ArrayList<>();
    addElementsToArrayList(list, 3, 0);
    System.out.println("Array list:");
    outputArrayList(list);
    try {
      list.remove(new Integer(-1000));
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void testRemoveElementFromEmptyArrayListByValue() {
    System.out.println("26. Test remove nonexistent value from empty array list:");
    List<Integer> list = new ArrayList<>();
    System.out.println("Array list:");
    outputArrayList(list);
    try {
      list.remove(new Integer(100));
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void testAddAndRemovingElementFromBigList() {
    System.out.println("27. Test adding 1000 elements and removing 900 elements:");
    List<Integer> list = new ArrayList<>(1000);
    addElementsToArrayList(list, 1000, 0);
    System.out.println("Size of array list: " + list.size());
    int currentListSize = list.size();
    for (int i = 0; i < currentListSize - 100; i++) {
      list.remove(0);
    }
    System.out.println("Size of array list after removing nine hundred elements: " + list.size());
  }

  private static void addElementsToArrayList(List<Integer> list, int numberElements,
      int additionalElement) {
    for (int i = 0; i < numberElements; i++) {
      list.add(i + 1 + additionalElement);
    }
  }

  private static void outputArrayList(List list) {
    System.out.println(list);
  }

}
