package com.homework7.handler;

import com.homework7.dao.ClientDao;
import com.homework7.dao.HumanDao;
import com.homework7.di.Inject;
import com.homework7.model.Client;
import com.homework7.model.Human;
import java.time.LocalDate;
import java.util.Scanner;

public class ConsoleHandler {

  @Inject
  private static ClientDao clientDao;

  @Inject
  private static HumanDao humanDao;

  /**
   * Handle request of user in console.
   */
  public static void handle() {
    while (true) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("1 - если вносите клиента, 2 - если получаете всех клиентов, "
          + "3 - если вносите человека, 4 - если получаете всех людей, 5 - для выхода");
      int consoleChoice = Integer.parseInt(scanner.nextLine());
      switch (consoleChoice) {
        case 1:
          addClientInfo(scanner);
          break;
        case 2:
          System.out.println(clientDao.get());
          break;
        case 3:
          addHumanInfo(scanner);
          break;
        case 4:
          System.out.println(humanDao.get());
          break;
        default:
          return;
      }
    }
  }

  private static String readName(Scanner scanner) {
    System.out.println("Введите имя");
    return scanner.nextLine();
  }

  private static void addClientInfo(Scanner scanner) {
    System.out.println("Введите информацию о клиенте");
    String name = readName(scanner);
    System.out.println("Введите телефон");
    String phone = scanner.nextLine();
    Client client = new Client(name, phone);
    clientDao.save(client);
  }

  private static void addHumanInfo(Scanner scanner) {
    System.out.println("Введите информацию о человеке");
    String name = readName(scanner);
    System.out.println("Введите фамилию");
    String surname = scanner.nextLine();
    System.out.println("Введите дату рождения в формате Год-Месяц-День");
    String birthDayString = scanner.nextLine();
    LocalDate birthDay = LocalDate.parse(birthDayString);
    Human human = new Human(name, surname, birthDay);
    humanDao.save(human);
  }
}
