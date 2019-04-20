package com.homework4;

import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for my realization of array list.
 */
public class ArrayListTest {
  private static final String TEST_VALUE = "test";
  private List<String> stringListWithTwoElements;

  @Before
  public void init() {
    stringListWithTwoElements = new ArrayList<>();
    stringListWithTwoElements.add("1");
    stringListWithTwoElements.add("2");
  }

  @Test
  public void addFirstElementToEmptyArrayList() {
    List<String> emptyList = new ArrayList<>();
    emptyList.add(TEST_VALUE);
    String gettingObject = emptyList.get(0);
    Assert.assertEquals(TEST_VALUE, gettingObject);
    Assert.assertEquals(1, emptyList.size());
  }

  @Test
  public void addFirstElementToFilledArrayList(){
    List<Integer> integerFilledList = generateElementsForArrayList(10);
    int sizeFilledList = integerFilledList.size();
    integerFilledList.add(10);
    int gettingNumber = integerFilledList.get(sizeFilledList);
    Assert.assertEquals(10, gettingNumber);
    Assert.assertEquals(sizeFilledList + 1, integerFilledList.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddElementWithNegativeIndex() {
    stringListWithTwoElements.add(TEST_VALUE, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddElementWithGreaterIndexThanSize() {
    int currentSizeStringList = stringListWithTwoElements.size();
    stringListWithTwoElements.add(TEST_VALUE, currentSizeStringList + 2);
  }

  @Test
  public void testAddElementToBeginOfArrayList() {
    int currentSizeStringList = stringListWithTwoElements.size();
    String firstElementInStringList = stringListWithTwoElements.get(0);
    stringListWithTwoElements.add(TEST_VALUE, 0);
    String gettingObject = stringListWithTwoElements.get(0);
    String previousFirstElement = stringListWithTwoElements.get(1);
    Assert.assertEquals(TEST_VALUE, gettingObject);
    Assert.assertEquals(firstElementInStringList, previousFirstElement);
    Assert.assertEquals(currentSizeStringList + 1, stringListWithTwoElements.size());
  }

  @Test
  public void testAddElementToMiddleOfArrayList() {
    int currentSizeStringList = stringListWithTwoElements.size();
    int middlePosition = currentSizeStringList / 2;
    String middleElementInStringList = stringListWithTwoElements.get(middlePosition);
    stringListWithTwoElements.add(TEST_VALUE, middlePosition);
    String gettingObject = stringListWithTwoElements.get(middlePosition);
    String previousFirstElement = stringListWithTwoElements.get(middlePosition + 1);
    Assert.assertEquals(TEST_VALUE, gettingObject);
    Assert.assertEquals(middleElementInStringList, previousFirstElement);
    Assert.assertEquals(currentSizeStringList + 1, stringListWithTwoElements.size());
  }

  @Test
  public void testAddElementToEndOfArrayList() {
    int currentSizeStringList = stringListWithTwoElements.size();
    String lastElementInStringList = stringListWithTwoElements.get(currentSizeStringList - 1);
    stringListWithTwoElements.add(TEST_VALUE, currentSizeStringList);
    String gettingObject = stringListWithTwoElements.get(currentSizeStringList);
    String previousFirstElement = stringListWithTwoElements.get(currentSizeStringList - 1);
    Assert.assertEquals(TEST_VALUE, gettingObject);
    Assert.assertEquals(lastElementInStringList, previousFirstElement);
    Assert.assertEquals(currentSizeStringList + 1, stringListWithTwoElements.size());
  }

  @Test
  public void addAllElementsFromNonEmptyArrayList() throws Exception {
    List<String> secondStringListWithTwoElements = new ArrayList<>();
    secondStringListWithTwoElements.add("3");
    secondStringListWithTwoElements.add("4");
    int currentSizeFirstStringList = stringListWithTwoElements.size();
    int currentSizeSecondStringList = secondStringListWithTwoElements.size();
    stringListWithTwoElements.addAll(secondStringListWithTwoElements);
    for (int i = currentSizeFirstStringList, j = 0;
        i < currentSizeFirstStringList + currentSizeSecondStringList - 1; i++, j++) {
      Assert.assertEquals(
          stringListWithTwoElements.get(i), secondStringListWithTwoElements.get(j));
    }
    Assert.assertEquals(currentSizeFirstStringList + currentSizeSecondStringList,
        stringListWithTwoElements.size());
  }

  @Test
  public void addAllElementsFromEmptyArrayList() {
    List<String> emptyList = new ArrayList<>();
    int currentSizeEmptyList = emptyList.size();
    int currentSizeFirstStringList = stringListWithTwoElements.size();
    String lastElementOfFirstStringList =
        stringListWithTwoElements.get(currentSizeFirstStringList - 1);
    stringListWithTwoElements.addAll(emptyList);
    int sizeJoinedList = stringListWithTwoElements.size();
    Assert.assertEquals(currentSizeFirstStringList + currentSizeEmptyList,
        sizeJoinedList);
    Assert.assertEquals(lastElementOfFirstStringList,
        stringListWithTwoElements.get(sizeJoinedList - 1));
  }

  @Test
  public void addAllElementsFromLinkedList() {
    List<String> linkedList = new LinkedList<>();
    linkedList.add("3");
    linkedList.add("4");
    int currentSizeFirstStringList = stringListWithTwoElements.size();
    int currentSizeLinkedList = linkedList.size();
    stringListWithTwoElements.addAll(linkedList);
    for (int i = currentSizeFirstStringList, j = 0;
        i < currentSizeFirstStringList + currentSizeLinkedList - 1; i++, j++) {
      Assert.assertEquals(
          stringListWithTwoElements.get(i), linkedList.get(j));
    }
    Assert.assertEquals(currentSizeFirstStringList + currentSizeLinkedList,
        stringListWithTwoElements.size());
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void getElementByIndexFromEmptyArrayList() {
    List<String> list = new ArrayList<>();
    list.get(0);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void getElementByIndexFromNegativeIndex() {
    stringListWithTwoElements.get(-1);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void getElementByIndexEqualSize() {
    int currentSizeFirstStringWithTwoElements = stringListWithTwoElements.size();
    stringListWithTwoElements.get(currentSizeFirstStringWithTwoElements);
  }
  
  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void getElementByIndexFromIndexGreaterThanSize() {
    int currentSizeFirstStringList = stringListWithTwoElements.size();
    stringListWithTwoElements.get(currentSizeFirstStringList + 2);
  }

  @Test
  public void getElementByExistenceIndex() {
    int currentSizeFirstStringList = stringListWithTwoElements.size();
    stringListWithTwoElements.add(TEST_VALUE, currentSizeFirstStringList);
    String gettingObject = stringListWithTwoElements.get(currentSizeFirstStringList);
    Assert.assertEquals(TEST_VALUE, gettingObject);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void setElementByNegativeIndex() {
    stringListWithTwoElements.set(TEST_VALUE, -1);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void setElementToEmptyArrayList() {
    List<String> list = new ArrayList<>();
    list.set(TEST_VALUE, 0);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void setElementByIndexEqualSize() {
    int currentSizeFirstStringWithTwoElements = stringListWithTwoElements.size();
    stringListWithTwoElements.set(TEST_VALUE, currentSizeFirstStringWithTwoElements);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void setElementByIndexGreaterThanSize() {
    int currentSizeFirstString = stringListWithTwoElements.size();
    stringListWithTwoElements.set(TEST_VALUE, currentSizeFirstString + 2);
  }

  @Test
  public void setNewValueForExistenceIndex() {
    int currentSizeFirstString = stringListWithTwoElements.size();
    String currentValueLastElement = stringListWithTwoElements.get(currentSizeFirstString - 1);
    stringListWithTwoElements.set(TEST_VALUE, currentSizeFirstString - 1);
    String gettingValue = stringListWithTwoElements.get(currentSizeFirstString - 1);
    Assert.assertEquals(TEST_VALUE, gettingValue);
    Assert.assertNotEquals(currentValueLastElement, gettingValue);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void removeElementByNegativeIndex() {
    stringListWithTwoElements.remove(-1);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void removeElementFromEmptyArrayList() {
    List<String> list = new ArrayList<>();
    list.remove(0);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void removeElementByIndexEqualSize() {
    int currentSizeFirstStringWithTwoElements = stringListWithTwoElements.size();
    stringListWithTwoElements.remove(currentSizeFirstStringWithTwoElements);
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void removeElementByIndexGreaterThanSize() {
    int currentSizeFirstString = stringListWithTwoElements.size();
    stringListWithTwoElements.remove(currentSizeFirstString + 2);
  }

  @Test()
  public void removeElementByExistenceIndex() {
    int currentSizeFirstString = stringListWithTwoElements.size();
    String currentValueLastElement = stringListWithTwoElements.get(currentSizeFirstString - 1);
    String removedElement = stringListWithTwoElements.remove(currentSizeFirstString - 1);
    Assert.assertEquals(removedElement, currentValueLastElement);
    Assert.assertEquals(currentSizeFirstString - 1, stringListWithTwoElements.size());
  }

  @Test(expected = NoSuchElementException.class)
  public void removeElementByNotExistenceValue() {
    stringListWithTwoElements.remove(TEST_VALUE);
  }

  @Test
  public void removeElementByExistenceValue() {
    int currentSizeFirstString = stringListWithTwoElements.size();
    String currentValueLastElement = stringListWithTwoElements.get(currentSizeFirstString - 1);
    String removedElement = stringListWithTwoElements.remove(currentValueLastElement);
    Assert.assertEquals(removedElement, currentValueLastElement);
    Assert.assertEquals(currentSizeFirstString - 1, stringListWithTwoElements.size());
  }

  @Test
  public void removeHalfElementsFromFilledArrayList() {
    List<Integer> integerFilledList = generateElementsForArrayList(11);
    int currentSizeIntegerFilledList = integerFilledList.size();
    for (int i = currentSizeIntegerFilledList; i < 2 * currentSizeIntegerFilledList; i++) {
      integerFilledList.add(i);
    }
    for (int i = 0; i < currentSizeIntegerFilledList; i++) {
      integerFilledList.remove(i);
    }
    Assert.assertEquals(currentSizeIntegerFilledList, integerFilledList.size());
  }

  @Test
  public void sizeOfEmptyArrayList() {
    List<String> list = new ArrayList<>();
    Assert.assertEquals(0, list.size());
  }

  @Test
  public void sizeOfNotEmptyArrayList() {
    Assert.assertEquals(2, stringListWithTwoElements.size());
  }

  @Test
  public void isEmptyForEmptyArrayList() {
    List<String> list = new ArrayList<>();
    Assert.assertTrue(list.isEmpty());
  }

  @Test
  public void isEmptyForNotEmptyArrayList() {
    Assert.assertFalse(stringListWithTwoElements.isEmpty());
  }

  @Test
  public void toStringEmptyArrayList() {
    String result = new ArrayList<String>().toString();
    Assert.assertEquals("Array List: []", result);
  }

  @Test
  public void toStringWithNonEmptyArrayList() {
    String result = stringListWithTwoElements.toString();
    Assert.assertEquals("Array List: [1,2]", result);
  }
  
  private List<Integer> generateElementsForArrayList(int numberElements) {
    List<Integer> filledList = new ArrayList<>();
    for (int i = 0; i < numberElements; i++) {
      filledList.add(i);
    }
    return filledList;
  }
}