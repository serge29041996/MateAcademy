package com.homework9;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Solution for task: https://stepik.org/lesson/12781/step/11?unit=3128
 */
public class FindMinAndMaxTask {
  /**
   * Tests for finding min and max element for different comparator.
   * @param args arguments for application
   */
  public static void main(String[] args) {
    List<Integer> firstList = new ArrayList<>();
    fillIntegerListWithElements(firstList, 10);
    System.out.println("First list:");
    System.out.println(firstList);
    IncreasingIntegerComparator increasingComparator = new IncreasingIntegerComparator();
    firstList.sort(increasingComparator);
    System.out.println("First list after increasing sorting:");
    System.out.println(firstList);
    CheckIntegerBiConsumer consumer = new CheckIntegerBiConsumer();
    System.out.println("Find min and max element using stream with increasing comparator:");
    findMinMax(firstList.stream(), increasingComparator, consumer);
    System.out.println();
    List<Integer> secondList = new ArrayList<>();
    fillIntegerListWithElements(secondList, 12);
    System.out.println("Second list:");
    System.out.println(secondList);
    DecreasingIntegerComparator decreasingComparator = new DecreasingIntegerComparator();
    secondList.sort(decreasingComparator);
    System.out.println("Copy list after decreasing sorting:");
    System.out.println(secondList);
    System.out.println("Find min and max element using stream with decreasing comparator:");
    findMinMax(secondList.stream(), decreasingComparator, consumer);
  }

  private static <T> void findMinMax(Stream<? extends T> stream, Comparator<? super T> order,
      BiConsumer<? super T, ? super T> minMaxConsumer) {
    List<? extends T> createdList = stream.distinct().collect(Collectors.toList());
    Stream<? extends T> firstStream = createdList.stream();
    Optional<? extends T> minResult = firstStream.min(order);
    T min = minResult.orElse(null);
    System.out.println("Min element:" + min);
    Stream<? extends T> secondStream = createdList.stream();
    Optional<? extends T> maxResult = secondStream.max(order);
    T max = maxResult.orElse(null);
    System.out.println("Max element:" + max);
    System.out.println("Check elements to consumer:");
    minMaxConsumer.accept(min, max);
  }

  private static void fillIntegerListWithElements(List<Integer> list, int count) {
    Random rand = new Random();
    for (int i = 0; i < count; i++) {
      list.add(rand.nextInt(count));
    }
  }
}
