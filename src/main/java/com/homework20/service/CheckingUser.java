package com.homework20.service;

import com.homework13.model.User;
import com.homework19.dao.UserDao;
import java.util.Optional;

/**
 * Service for checking existence user in database.
 */
public class CheckingUser {

  private CheckingUser() {
  }

  /**
   * Check existence user with same login and mail for saving.
   *
   * @param userDao dao for working with database
   * @param userForChecking user for saving
   * @return result of checking
   */
  public static String checkExistenceUserWithSameLoginAndMailForSave(UserDao userDao,
      User userForChecking) {
    StringBuilder resultChecking = new StringBuilder();
    Optional<User> gettingUserWithSameLogin = userDao.getUserByLogin(userForChecking.getLogin());
    gettingUserWithSameLogin
        .ifPresent(user -> addMessageForExistField(resultChecking, "Пользователь с логином ",
            userForChecking.getLogin()));
    Optional<User> gettingUserWithSameMail = userDao.getUserByMail(userForChecking.getMail());
    gettingUserWithSameMail
        .ifPresent(user -> addMessageForExistField(resultChecking,
            "Пользователь с электронной почтой ", userForChecking.getMail()));
    return resultChecking.toString();
  }

  /**
   * Check existence user with same login and mail for updating.
   *
   * @param userDao dao for working with database
   * @param userForChecking user for saving
   * @return result of checking
   */
  public static String checkExistenceUserWithSameLoginAndMailForUpdate(UserDao userDao,
      User userForChecking) {
    StringBuilder resultChecking = new StringBuilder();
    Optional<User> gettingUserWithSameLogin = userDao.getUserByLogin(userForChecking.getLogin());
    if (gettingUserWithSameLogin.isPresent()) {
      User userWithSameLogin = gettingUserWithSameLogin.get();
      if (userWithSameLogin.getId() != userForChecking.getId()) {
        addMessageForExistField(resultChecking, "Пользователь с логином ",
            userForChecking.getLogin());
      }
    }
    Optional<User> gettingUserWithSameMail = userDao.getUserByMail(userForChecking.getMail());
    if (gettingUserWithSameMail.isPresent()) {
      User userWithSameMail = gettingUserWithSameMail.get();
      if (userWithSameMail.getId() != userForChecking.getId()) {
        addMessageForExistField(resultChecking, "Пользователь с электронной почтой ",
            userForChecking.getMail());
      }
    }
    return resultChecking.toString();
  }

  private static void addMessageForExistField(StringBuilder resultMessage, String message,
      String field) {
    resultMessage.append(message);
    resultMessage.append(field);
    resultMessage.append(" уже существует.");
  }
}
