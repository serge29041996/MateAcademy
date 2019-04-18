package com.homework11;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class for getting top 10 of words in the text.
 * Solution for task: https://stepik.org/lesson/12781/step/13?unit=3128.
 */
public class StreamApiTopWords {
  /**
   * Realization find frequency of word in text.
   * @param args arguments to application
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String text = scanner.nextLine();
    String[] parsedData = splitStringByPunctualityAndBlankSigns(text);
    Map<String, Long> map = Arrays.stream(parsedData)
        .map(String::toLowerCase)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    map.entrySet()
        .stream()
        .sorted(Comparator.comparing(Entry::getKey))
        .sorted((e1, e2) -> (int)(e2.getValue() - (long)e1.getValue()))
        .map(Entry::getKey)
        .limit(10)
        .forEach(System.out::println);
  }

  private static String[] splitStringByPunctualityAndBlankSigns(String data) {
    return data.split("[\\p{Punct}\\p{Blank}]+");
  }
}
