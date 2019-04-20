package com.homework4;

import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for my realization of hash map
 */
public class LinkedListTest {
  private static final String TEST_VALUE = "test";
  private List<String> linkedListWithTwoElements;

  @Before
  public void init() {
    linkedListWithTwoElements = new LinkedList<>();
    linkedListWithTwoElements.add("1");
    linkedListWithTwoElements.add("2");
  }

  @Test
  public void addFirstElementToLinkedList() {
    List<String> list = new LinkedList<>();
    list.add(TEST_VALUE);
    String gettingElement = list.get(0);
    Assert.assertEquals(TEST_VALUE, gettingElement);
    Assert.assertEquals(1, list.size());
  }

  @Test
  public void addTwoElementsToLinkedList() {
    List<String> list = new LinkedList<>();
    list.add(TEST_VALUE);
    list.add(TEST_VALUE + "1");
    String gettingElement = list.get(1);
    Assert.assertEquals(TEST_VALUE + "1", gettingElement);
    Assert.assertEquals(2, list.size());
  }

  @Test
  public void addThreeElementsToLinkedList() {
    List<String> list = new LinkedList<>();
    list.add(TEST_VALUE);
    list.add(TEST_VALUE + "1");
    list.add(TEST_VALUE + "2");
    String gettingElement = list.get(2);
    Assert.assertEquals(TEST_VALUE + "2", gettingElement);
    Assert.assertEquals(3, list.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addElementToNegativeIndex() {
    linkedListWithTwoElements.add("3", -1);
  }

  @Test
  public void addElementByIndexToEmptyLinkedList() {
    List<String> list = new LinkedList<>();
    list.add(TEST_VALUE, 0);
    String gettingElement = list.get(0);
    Assert.assertEquals(TEST_VALUE, gettingElement);
    Assert.assertEquals(1, list.size());
  }

  @Test
  public void addElementByIndexToTheEndOfLinkedList() {
    int currentNumberElementsLinkedList = linkedListWithTwoElements.size();
    linkedListWithTwoElements.add(TEST_VALUE, currentNumberElementsLinkedList);
    String currentLastElement = linkedListWithTwoElements.get(currentNumberElementsLinkedList);
    Assert.assertEquals(TEST_VALUE, currentLastElement);
    Assert.assertEquals(currentNumberElementsLinkedList + 1,
        linkedListWithTwoElements.size());
  }

  @Test
  public void addElementByIndexToBeginLinkedList() {
    int currentNumberElements = linkedListWithTwoElements.size();
    String currentFirstElement = linkedListWithTwoElements.get(0);
    linkedListWithTwoElements.add(TEST_VALUE, 0);
    String newCurrentFirstElement = linkedListWithTwoElements.get(0);
    String previousFirstElement = linkedListWithTwoElements.get(1);
    Assert.assertEquals(TEST_VALUE, newCurrentFirstElement);
    Assert.assertEquals(currentFirstElement, previousFirstElement);
    Assert.assertEquals(currentNumberElements + 1, linkedListWithTwoElements.size());
  }

  @Test
  public void addElementByIndexToMiddlePosition() {
    int currentNumberElements = linkedListWithTwoElements.size();
    int middlePosition = linkedListWithTwoElements.size() / 2;
    String currentFirstElement = linkedListWithTwoElements.get(middlePosition);
    linkedListWithTwoElements.add(TEST_VALUE, middlePosition);
    String newCurrentFirstElement = linkedListWithTwoElements.get(middlePosition);
    String previousFirstElement = linkedListWithTwoElements.get(middlePosition + 1);
    Assert.assertEquals(TEST_VALUE, newCurrentFirstElement);
    Assert.assertEquals(currentFirstElement, previousFirstElement);
    Assert.assertEquals(currentNumberElements + 1, linkedListWithTwoElements.size());
  }

  @Test
  public void addAllElementsFromEmptyLinkedList() {
    List<String> emptyList = new LinkedList<>();
    int currentNumberElements = linkedListWithTwoElements.size();
    String lastElement = linkedListWithTwoElements.get(currentNumberElements - 1);
    linkedListWithTwoElements.addAll(emptyList);
    int numberElementsAfterAdding = linkedListWithTwoElements.size();
    String currentLastElement = linkedListWithTwoElements.get(numberElementsAfterAdding - 1);
    Assert.assertEquals(currentNumberElements, numberElementsAfterAdding);
    Assert.assertEquals(lastElement, currentLastElement);
  }

  @Test
  public void addAllElementsFromNonEmptyLinkedList() {
    List<String> nonEmptyLinkedList = new LinkedList<>();
    nonEmptyLinkedList.add("3");
    nonEmptyLinkedList.add("4");
    int numberElementsNonEmptyList = nonEmptyLinkedList.size();
    int numberElementsToAddingList = linkedListWithTwoElements.size();
    linkedListWithTwoElements.addAll(nonEmptyLinkedList);
    int numberElementsAfterAdding = linkedListWithTwoElements.size();
    for (int i = numberElementsToAddingList, j = 0; i < numberElementsAfterAdding; i++, j++) {
      Assert.assertEquals(nonEmptyLinkedList.get(j), linkedListWithTwoElements.get(i));
    }
    Assert.assertEquals(numberElementsNonEmptyList + numberElementsToAddingList,
        numberElementsAfterAdding);
  }

  @Test
  public void addAllElementsFromNonEmptyArrayList() {
    List<String> nonEmptyArrayList = new ArrayList<>();
    nonEmptyArrayList.add("5");
    nonEmptyArrayList.add("6");
    int numberElementsNonEmptyList = nonEmptyArrayList.size();
    int numberElementsToAddingList = linkedListWithTwoElements.size();
    linkedListWithTwoElements.addAll(nonEmptyArrayList);
    int numberElementsAfterAdding = linkedListWithTwoElements.size();
    for (int i = numberElementsToAddingList, j = 0; i < numberElementsAfterAdding; i++, j++) {
      Assert.assertEquals(nonEmptyArrayList.get(j), linkedListWithTwoElements.get(i));
    }
    Assert.assertEquals(numberElementsNonEmptyList + numberElementsToAddingList,
        numberElementsAfterAdding);
  }

  @Test
  public void addElementsFromNonEmptyLinkedListToEmptyLinkedList() {
    List<String> emptyArray = new LinkedList<>();
    int numberElementsEmptyArray = emptyArray.size();
    int numberElementsLinkedList = linkedListWithTwoElements.size();
    String firstElementLinkedList = linkedListWithTwoElements.get(0);
    String lastElementLinkedList = linkedListWithTwoElements.get(numberElementsLinkedList - 1);
    emptyArray.addAll(linkedListWithTwoElements);
    String firstElementAfterAdding = emptyArray.get(0);
    String lastElementAfterAdding = emptyArray.get(numberElementsLinkedList - 1);
    Assert.assertEquals(firstElementLinkedList, firstElementAfterAdding);
    Assert.assertEquals(lastElementLinkedList, lastElementAfterAdding);
    Assert.assertEquals(numberElementsEmptyArray + numberElementsLinkedList,
        linkedListWithTwoElements.size());
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void getElementFromLinkedListWithNegativeIndex() {
    linkedListWithTwoElements.get(-1);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void getElementFromLinkedListWithIndexEqualSize() {
    int currentNumberElements = linkedListWithTwoElements.size();
    linkedListWithTwoElements.get(currentNumberElements);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void getElementFromEmptyLinkedList() {
    List<String> list = new LinkedList<>();
    list.get(0);
  }

  @Test
  public void getElementFromLinkedListWithOneElement() {
    List<String> list = new LinkedList<>();
    list.add(TEST_VALUE);
    String gettingValue = list.get(0);
    Assert.assertEquals(TEST_VALUE, gettingValue);
  }

  @Test
  public void getElementFromLinkedListWithTwoElements() {
    int currentNumberElements = linkedListWithTwoElements.size();
    String gettingElements = linkedListWithTwoElements.get(currentNumberElements - 1);
    Assert.assertEquals("2", gettingElements);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void setElementFromLinkedListWithNegativeIndex() {
    linkedListWithTwoElements.set(TEST_VALUE, -1);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void setElementFromLinkedListWithIndexEqualSize() {
    int currentNumberElements = linkedListWithTwoElements.size();
    linkedListWithTwoElements.set(TEST_VALUE, currentNumberElements);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void setElementFromEmptyLinkedList() {
    List<String> list = new LinkedList<>();
    list.set(TEST_VALUE, 0);
  }

  @Test
  public void setElementFromLinkedListWithOneElement() {
    String newElement = "5";
    List<String> list = new LinkedList<>();
    list.add(TEST_VALUE);
    list.set(newElement, 0);
    String afterSettingElement = list.get(0);
    Assert.assertEquals(newElement, afterSettingElement);
  }

  @Test
  public void setElementFromLinkedListWithTwoElements() {
    int currentNumberElements = linkedListWithTwoElements.size();
    linkedListWithTwoElements.set(TEST_VALUE, currentNumberElements - 1);
    String elementAfterSetting = linkedListWithTwoElements.get(currentNumberElements - 1);
    Assert.assertEquals(TEST_VALUE, elementAfterSetting);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void removeElementByIndexFromLinkedListWithNegativeIndex() {
    linkedListWithTwoElements.remove(-1);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void removeElementByIndexFromLinkedListWithIndexEqualSize() {
    int currentNumberElements = linkedListWithTwoElements.size();
    linkedListWithTwoElements.remove(currentNumberElements);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void removeElementByIndexFromEmptyLinkedList() {
    List<String> list = new LinkedList<>();
    list.remove(0);
  }

  @Test
  public void removeElementByIndexFromLinkedListWithOneElement() {
    List<String> list = new LinkedList<>();
    list.add(TEST_VALUE);
    String removingElement = list.remove(0);
    Assert.assertEquals(TEST_VALUE, removingElement);
    Assert.assertEquals(0, list.size());
  }

  @Test
  public void removeLastElementByIndexFromLinkedListWithTwoElements() {
    int currentNumberElements = linkedListWithTwoElements.size();
    int lastIndex = currentNumberElements - 1;
    String lastElement = linkedListWithTwoElements.get(lastIndex);
    String removingElement =  linkedListWithTwoElements.remove(lastIndex);
    Assert.assertEquals(lastElement, removingElement);
    Assert.assertEquals(currentNumberElements - 1, linkedListWithTwoElements.size());
  }

  @Test
  public void removeFirstElementByIndexFromLinkedListWithTwoElements() {
    int currentNumberElements = linkedListWithTwoElements.size();
    String currentFirstElement = linkedListWithTwoElements.get(0);
    String currentSecondElement = linkedListWithTwoElements.get(1);
    String removingElement = linkedListWithTwoElements.remove(0);
    String firstElementAfterRemoving = linkedListWithTwoElements.get(0);
    Assert.assertEquals(currentFirstElement, removingElement);
    Assert.assertEquals(currentSecondElement, firstElementAfterRemoving);
    Assert.assertEquals(currentNumberElements - 1, linkedListWithTwoElements.size());
  }

  @Test
  public void removeElementByIndexFromMiddleLinkedList() {
    linkedListWithTwoElements.add(TEST_VALUE);
    int currentNumberElements = linkedListWithTwoElements.size();
    int middlePosition = currentNumberElements / 2;
    String currentMiddleElement = linkedListWithTwoElements.get(middlePosition);
    String nextToMiddleElement = linkedListWithTwoElements.get(middlePosition + 1);
    String removingElement = linkedListWithTwoElements.remove(middlePosition);
    String middleElementAfterRemoving = linkedListWithTwoElements.get(middlePosition);
    Assert.assertEquals(currentMiddleElement, removingElement);
    Assert.assertEquals(nextToMiddleElement, middleElementAfterRemoving);
    Assert.assertEquals(currentNumberElements - 1, linkedListWithTwoElements.size());
  }

  @Test(expected = NoSuchElementException.class)
  public void removeElementByValueFromEmptyLinkedList() {
    List<String> list = new LinkedList<>();
    list.remove(TEST_VALUE);
  }

  @Test
  public void removeElementByValueFromLinkedListWithOneValue() {
    List<String> list = new LinkedList<>();
    list.add(TEST_VALUE);
    int numberElements = list.size();
    String removedElement = list.remove(TEST_VALUE);
    Assert.assertEquals(TEST_VALUE, removedElement);
    Assert.assertEquals(numberElements - 1, list.size());
  }

  @Test
  public void removeExistElementFromLinkedList() {
    int numberElements = linkedListWithTwoElements.size();
    int lastIndex = numberElements - 1;
    String lastElement = linkedListWithTwoElements.get(lastIndex);
    String removingElement = linkedListWithTwoElements.remove(lastElement);
    Assert.assertEquals(lastElement, removingElement);
    Assert.assertEquals(numberElements - 1, linkedListWithTwoElements.size());
  }

  @Test(expected = NoSuchElementException.class)
  public void removeNonExistElementFromLinkedList() {
    linkedListWithTwoElements.remove(TEST_VALUE);
  }

  @Test
  public void sizeFromEmptyLinkedList() {
    List<String> list = new LinkedList<>();
    Assert.assertEquals(0, list.size());
  }

  @Test
  public void sizeFromNonEmptyLinkedList() {
    Assert.assertEquals(2, linkedListWithTwoElements.size());
  }

  @Test
  public void isEmptyFromEmptyLinkedList() throws Exception {
    List<String> list = new LinkedList<>();
    Assert.assertTrue(list.isEmpty());
  }

  @Test
  public void isEmptyFromNonEmptyLinkedList() throws Exception {
    Assert.assertFalse(linkedListWithTwoElements.isEmpty());
  }

  @Test
  public void toStringFromEmptyList() {
    List<String> emptyList = new LinkedList<>();
    Assert.assertEquals("Linked List: []", emptyList.toString());
  }

  @Test
  public void toStringFromNonEmptyList() {
    Assert.assertEquals("Linked List: [1, 2]", linkedListWithTwoElements.toString());
  }

}