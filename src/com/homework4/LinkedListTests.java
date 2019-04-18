package com.homework4;

import java.util.NoSuchElementException;

/**
 * Tests for realization of linked list.
 */
public class LinkedListTests {
  /**
   * Tests for linked list.
   * @param args arguments for application
   */
  public static void main(String[] args) {
    testToStringEmptyLinkedList();
    testAddOneElementToLinkedList();
    testAddTwoElementsToLinkedList();
    testAddThreeElementsToLinkedList();
    testAddElementToSecondPosition();
    testAddElementToLastPosition();
    testAddElementToEnd();
    testAddElementToFirstPosition();
    testAddElementToNonexistentPosition();
    testAddLinkedListWithOneElement();
    testAddLinkedListWithTwoElements();
    testAddLinkedListWithTwoElementsToEmptyLinkedList();
    testAddEmptyLinkedList();
    testGetElementWithNegativeIndexFromLinkedList();
    testGetElementWithNonexistentIndexFromLinkedList();
    testGetElementWithIndexFromEmptyLinkedList();
    testGetElementWithPositiveIndexFromLinkedList();
    testSetNewValueToSecondElementLinkedList();
    testSetNewValueToOneElementLinkedList();
    testSetNewValueToLastElementLinkedList();
    testRemoveFirstElementLinkedListByIndex();
    testRemoveLastElementLinkedListByIndex();
    testRemoveMiddleElementLinkedListByIndex();
    testRemoveNonexistentElementLinkedListByIndex();
    testRemoveExistentElementLinkedListByValue();
    testRemoveNonexistentElementLinkedListByValue();
    testRemoveElementFromEmptyLinkedListByValue();
  }

  private static void testToStringEmptyLinkedList() {
    System.out.println("1. Test to string with empty linked list:");
    List<Integer> list = new LinkedList<>();
    outputLinkedList(list);
    System.out.println();
  }

  private static void testAddOneElementToLinkedList() {
    System.out.println("2. Test add one element to linked list:");
    List<Integer> list = new LinkedList<>();
    System.out.println("Linked list before adding element:");
    outputLinkedList(list);
    addElementsToLinkedList(list, 1, 0);
    System.out.println("Linked list after adding one element:");
    outputLinkedList(list);
    System.out.println("Size of linked list: " + list.size());
    System.out.println();
  }

  private static void testAddTwoElementsToLinkedList() {
    System.out.println("3. Test add two elements to linked list:");
    List<Integer> list = new LinkedList<>();
    System.out.println("Linked list before adding elements:");
    outputLinkedList(list);
    addElementsToLinkedList(list, 2, 0);
    System.out.println("Linked list after adding two elements:");
    System.out.println(list);
    System.out.println("Size of linked list: " + list.size());
    System.out.println();
  }

  private static void testAddThreeElementsToLinkedList() {
    System.out.println("4. Test add three elements to linked list:");
    List<Integer> list = new LinkedList<>();
    System.out.println("Linked list before adding elements:");
    outputLinkedList(list);
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list after adding three elements:");
    System.out.println(list);
    System.out.println("Size of linked list: " + list.size());
    System.out.println();
  }

  private static void testAddElementToSecondPosition() {
    System.out.println("5. Test add element to second position in linked list:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list before adding elements:");
    outputLinkedList(list);
    list.add(4, 1);
    System.out.println("Linked list after adding element 4 to second position:");
    System.out.println(list);
    System.out.println("Size of linked list: " + list.size());
    System.out.println();
  }

  private static void testAddElementToLastPosition() {
    System.out.println("6. Test add element to penultimate position in linked list:");
    List<Integer> list = new LinkedList<>();
    System.out.println("Linked list before adding elements:");
    outputLinkedList(list);
    addElementsToLinkedList(list, 3, 0);
    list.add(4, 2);
    System.out.println("Linked list after adding element 4 to penultimate position:");
    System.out.println(list);
    System.out.println("Size of linked list: " + list.size());
    System.out.println();
  }

  private static void testAddElementToEnd() {
    System.out.println("7. Test add element to last position in linked list:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list before adding elements:");
    outputLinkedList(list);
    list.add(4, 3);
    System.out.println("Linked list after adding element 4 to last position:");
    System.out.println(list);
    System.out.println("Size of linked list: " + list.size());
    System.out.println();
  }

  private static void testAddElementToFirstPosition() {
    System.out.println("8. Test add element to first position in linked list:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list before adding elements:");
    outputLinkedList(list);
    list.add(4, 0);
    System.out.println("Linked list after adding element 4 to first position:");
    System.out.println(list);
    System.out.println("Size of linked list: " + list.size());
    System.out.println();
  }

  private static void testAddElementToNonexistentPosition() {
    System.out.println("9. Test add element to nonexistent position in linked list:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list before adding elements:");
    outputLinkedList(list);
    try {
      System.out.println("Try add element 4 to index -1");
      list.add(4, -1);
      System.out.println("Linked list after adding element 4 to first position:");
      System.out.println(list);
      System.out.println("Size of linked list: " + list.size());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void testAddLinkedListWithOneElement() {
    System.out.println("10. Test add another linked list with one element:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("First linked list before adding elements:");
    outputLinkedList(list);
    List<Integer> newList = new LinkedList<>();
    addElementsToLinkedList(newList, 1, 10);
    System.out.println("Second linked list before adding elements:");
    outputLinkedList(newList);
    list.addAll(newList);
    System.out.println("Combined linked list after adding second linked list:");
    System.out.println(list);
    System.out.println("Size of combined linked list: " + list.size());
    System.out.println();
  }

  private static void testAddLinkedListWithTwoElements() {
    System.out.println("11. Test add another linked list with two elements:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("First linked list before adding elements:");
    outputLinkedList(list);
    List<Integer> newList = new LinkedList<>();
    addElementsToLinkedList(newList, 2, 10);
    System.out.println("Second linked list before adding elements:");
    outputLinkedList(newList);
    list.addAll(newList);
    System.out.println("Combined linked list after adding second linked list:");
    System.out.println(list);
    System.out.println("Size of combined linked list: " + list.size());
    System.out.println();
  }

  private static void testAddLinkedListWithTwoElementsToEmptyLinkedList() {
    System.out.println("12. Test add another linked list to empty linked list:");
    List<Integer> list = new LinkedList<>();
    System.out.println("First linked list before adding elements:");
    outputLinkedList(list);
    List<Integer> newList = new LinkedList<>();
    addElementsToLinkedList(newList, 2, 10);
    System.out.println("Second linked list before adding elements:");
    outputLinkedList(newList);
    list.addAll(newList);
    System.out.println("Combined linked list after adding second linked list:");
    System.out.println(list);
    System.out.println("Size of combined linked list: " + list.size());
    System.out.println();
  }

  private static void testAddEmptyLinkedList() {
    System.out.println("13. Test add empty linked list:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("First linked list before adding elements:");
    outputLinkedList(list);
    List<Integer> newList = new LinkedList<>();
    System.out.println("Second linked list before adding elements:");
    outputLinkedList(newList);
    list.addAll(newList);
    System.out.println("Combined linked list after adding second linked list:");
    System.out.println(list);
    System.out.println("Size of combined linked list: " + list.size());
    System.out.println();
  }

  private static void testGetElementWithNegativeIndexFromLinkedList() {
    System.out.println("14. Test get element from negative index:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list:");
    outputLinkedList(list);
    try {
      System.out.println("Try get element with index -1");
      list.get(-1);
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void testGetElementWithNonexistentIndexFromLinkedList() {
    System.out.println("15. Test get element from nonexistent index:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list:");
    outputLinkedList(list);
    try {
      System.out.println("Try get element with index 100");
      list.get(100);
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void testGetElementWithIndexFromEmptyLinkedList() {
    System.out.println("16. Test get element from empty linked list:");
    List<Integer> list = new LinkedList<>();
    System.out.println("Linked list:");
    outputLinkedList(list);
    try {
      System.out.println("Try get element from empty linked list");
      list.get(0);
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void testGetElementWithPositiveIndexFromLinkedList() {
    System.out.println("17. Test get element from not empty linked list with positive index:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list:");
    outputLinkedList(list);
    Integer needElement = list.get(1);
    System.out.println("Second element in the linked list is: " + needElement);
    System.out.println();
  }

  private static void testSetNewValueToSecondElementLinkedList() {
    System.out.println("18. Test set new value to second element:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list:");
    outputLinkedList(list);
    list.set(10, 1);
    System.out.println("Linked list after setting value 10 to second element:");
    System.out.println(list);
    System.out.println();
  }

  private static void testSetNewValueToOneElementLinkedList() {
    System.out.println("19. Test set new value to first element:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 1, 0);
    System.out.println("Linked list:");
    outputLinkedList(list);
    list.set(20, 0);
    System.out.println("Linked list after setting value 20 to first element:");
    System.out.println(list);
    System.out.println();
  }

  private static void testSetNewValueToLastElementLinkedList() {
    System.out.println("20. Test set new value to last element:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list:");
    outputLinkedList(list);
    list.set(30, 2);
    System.out.println("Linked list after setting value 30 to last element:");
    System.out.println(list);
    System.out.println();
  }

  private static void testRemoveFirstElementLinkedListByIndex() {
    System.out.println("21. Test remove first element by index:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list:");
    outputLinkedList(list);
    list.remove(0);
    System.out.println("Linked list after removing first element by index:");
    System.out.println(list);
    System.out.println("Size of linked list: " + list.size());
    System.out.println();
  }

  private static void testRemoveLastElementLinkedListByIndex() {
    System.out.println("22. Test remove last element by index:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list:");
    outputLinkedList(list);
    list.remove(2);
    System.out.println("Linked list after removing last element by index:");
    System.out.println(list);
    System.out.println("Size of linked list: " + list.size());
    System.out.println();
  }

  private static void testRemoveMiddleElementLinkedListByIndex() {
    System.out.println("23. Test remove middle element by index:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list:");
    outputLinkedList(list);
    list.remove(1);
    System.out.println("Linked list after removing second element by index:");
    System.out.println(list);
    System.out.println("Size of linked list: " + list.size());
    System.out.println();
  }

  private static void testRemoveNonexistentElementLinkedListByIndex() {
    System.out.println("24. Test remove element by nonexistent index:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list:");
    outputLinkedList(list);
    try {
      System.out.println("Try remove element with index 5");
      list.remove(5);
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void testRemoveExistentElementLinkedListByValue() {
    System.out.println("24. Test remove first element by existent value:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list:");
    outputLinkedList(list);
    list.remove(new Integer(1));
    System.out.println("Linked list after removing element with value 1:");
    System.out.println(list);
    System.out.println("Size of linked list: " + list.size());
    System.out.println();
  }

  private static void testRemoveNonexistentElementLinkedListByValue() {
    System.out.println("25. Test remove nonexistent value from nonempty linked list:");
    List<Integer> list = new LinkedList<>();
    addElementsToLinkedList(list, 3, 0);
    System.out.println("Linked list:");
    outputLinkedList(list);
    try {
      list.remove(new Integer(-1000));
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void testRemoveElementFromEmptyLinkedListByValue() {
    System.out.println("26. Test remove nonexistent value from empty linked list:");
    List<Integer> list = new LinkedList<>();
    System.out.println("Linked list:");
    outputLinkedList(list);
    try {
      list.remove(new Integer(100));
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
    System.out.println();
  }

  private static void addElementsToLinkedList(List<Integer> list, int numberElements,
      int additionalElement) {
    for (int i = 0; i < numberElements; i++) {
      list.add(i + 1 + additionalElement);
    }
  }

  private static void outputLinkedList(List linkedList) {
    System.out.println(linkedList);
  }
}
