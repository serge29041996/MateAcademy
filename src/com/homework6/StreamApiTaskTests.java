package com.homework6;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Solution for task with *: do work code.
 */
public class StreamApiTaskTests {
  /**
   * Part of code, which should work.
   * @param args arguments to command line
   */
  public static void main(String[] args) {
    MailMessage firstMessage = new MailMessage(
        "Robert Howard",
        "H.P. Lovecraft",
        "This \"The Shadow over Innsmouth\" story is real masterpiece, Howard!"
    );

    assert firstMessage.getFrom().equals("Robert Howard") : "Wrong firstMessage from address";
    assert firstMessage.getTo().equals("H.P. Lovecraft") : "Wrong firstMessage to address";
    assert firstMessage.getContent().endsWith("Howard!") : "Wrong firstMessage content ending";

    MailMessage secondMessage = new MailMessage(
        "Jonathan Nolan",
        "Christopher Nolan",
        "Брат, почему все так хвалят только тебя, когда практически все сценарии написал я. "
            + "Так не честно!"
    );

    MailMessage thirdMessage = new MailMessage(
        "Stephen Hawking",
        "Christopher Nolan",
        "Я так и не понял Интерстеллар."
    );

    List<MailMessage> messages = Arrays.asList(
        firstMessage, secondMessage, thirdMessage
    );

    MailService<String> mailService = new MailService<>();

    messages.stream().forEachOrdered(mailService);

    Map<String, List<String>> mailBox = mailService.getMailBox();

    assert mailBox.get("H.P. Lovecraft").equals(
        Arrays.asList(
            "This \"The Shadow over Innsmouth\" story is real masterpiece, Howard!"
        )
    ) : "wrong mailService mailbox content (1)";

    assert mailBox.get("Christopher Nolan").equals(
        Arrays.asList(
            "Брат, почему все так хвалят только тебя, когда практически все сценарии написал я. "
                + "Так не честно!",
            "Я так и не понял Интерстеллар."
        )
    ) : "wrong mailService mailbox content (2)";

    String randomFrom = "...";
    String randomTo = "...";
    int randomSalary = 100;

    assert mailBox.get(randomTo)
        .equals(Collections.<String>emptyList()) : "wrong mailService mailbox content (3)";

    Salary salary1 = new Salary("Facebook", "Mark Zuckerberg", 1);
    Salary salary2 = new Salary("FC Barcelona", "Lionel Messi", Integer.MAX_VALUE);
    Salary salary3 = new Salary(randomFrom, randomTo, randomSalary);

    MailService<Integer> salaryService = new MailService<>();

    Arrays.asList(salary1, salary2, salary3).forEach(salaryService);

    Map<String, List<Integer>> salaries = salaryService.getMailBox();
    assert salaries.get(salary1.getTo())
        .equals(Arrays.asList(1)) : "wrong salaries mailbox content (1)";
    assert salaries.get(salary2.getTo())
        .equals(Arrays.asList(Integer.MAX_VALUE)) : "wrong salaries mailbox content (2)";
    assert salaries.get(randomTo)
        .equals(Arrays.asList(randomSalary)) : "wrong salaries mailbox content (3)";
  }

  public static class MailMessage implements Sendable<String> {
    private String from;
    private String to;
    private String content;

    public MailMessage(String from, String to, String content) {
      this.from = from;
      this.to = to;
      this.content = content;
    }

    public String getFrom() {
      return from;
    }

    public String getTo() {
      return to;
    }

    public String getContent() {
      return content;
    }

    @Override
    public String getKey() {
      return getTo();
    }

    @Override
    public String getValue() {
      return getContent();
    }
  }

  public static class Salary implements Sendable<Integer> {
    private String placeWork;
    private String to;
    private int salary;

    public Salary(String placeWork, String to, int salary) {
      this.placeWork = placeWork;
      this.to = to;
      this.salary = salary;
    }

    public String getTo() {
      return to;
    }

    public int getSalary() {
      return salary;
    }

    @Override
    public String getKey() {
      return getTo();
    }

    @Override
    public Integer getValue() {
      return getSalary();
    }
  }

  public static class MailService<T> implements Consumer<Sendable<T>> {
    private Map<String, List<T>> result = new MyHashMap<>();

    @Override
    public void accept(Sendable<T> sendableT) {
      List<T> elements =
          result.get(sendableT.getKey()) == Collections.EMPTY_LIST ? new LinkedList<T>() :
              result.get(sendableT.getKey());
      elements.add(sendableT.getValue());
      result.put(sendableT.getKey(), elements);
    }

    public Map<String, List<T>> getMailBox() {
      return result;
    }
  }

  public static interface Sendable<T> {
    String getKey();

    T getValue();
  }

  public static class MyHashMap<K, V> extends HashMap<K, V> {
    @Override
    public V get(Object key) {
      return super.get(key) == null ? (V) Collections.EMPTY_LIST : super.get(key);
    }
  }
}
