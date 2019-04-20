package com.homework6;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Solution for task with *: do code to compile and work.
 */
public class StreamApiTaskTests {
  /**
   * Part of code, which should work.
   * @param args arguments to command line
   */
  public static void main(String[] args) {
    List<MailMessage> messages = formMailMessages();
    MailService<String> mailService = new MailService<>();
    messages.stream().forEachOrdered(mailService);
    Map<String, List<String>> mailBox = mailService.getMailBox();
    String randomFrom = "...";
    String randomTo = "...";
    int randomSalary = 100;
    checkMailBox(mailBox, randomTo);

    List<Salary> salariesList = formSalaries(randomFrom, randomTo, randomSalary);
    MailService<Integer> salaryService = new MailService<>();
    salariesList.forEach(salaryService);
    Map<String, List<Integer>> salaries = salaryService.getMailBox();
    checkSalaries(salaries, salariesList, randomTo, randomSalary);
  }

  private static List<MailMessage> formMailMessages() {
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

    return Arrays.asList(firstMessage, secondMessage, thirdMessage);
  }

  private static void checkMailBox(Map<String, List<String>> mailBox, String randomTo) {
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

    assert mailBox.get(randomTo)
        .equals(Collections.<String>emptyList()) : "wrong mailService mailbox content (3)";
  }

  private static List<Salary> formSalaries(String from, String to, int salary) {
    Salary salary1 = new Salary("Facebook", "Mark Zuckerberg", 1);
    Salary salary2 = new Salary("FC Barcelona", "Lionel Messi", Integer.MAX_VALUE);
    Salary salary3 = new Salary(from, to, salary);
    return Arrays.asList(salary1, salary2, salary3);
  }

  private static void checkSalaries(Map<String, List<Integer>> salaries, List<Salary> salariesList,
      String randomTo, int randomSalary) {
    assert salaries.get(salariesList.get(0).getTo())
        .equals(Arrays.asList(1)) : "wrong salaries mailbox content (1)";
    assert salaries.get(salariesList.get(0).getTo())
        .equals(Arrays.asList(Integer.MAX_VALUE)) : "wrong salaries mailbox content (2)";
    assert salaries.get(randomTo)
        .equals(Arrays.asList(randomSalary)) : "wrong salaries mailbox content (3)";
  }
}
