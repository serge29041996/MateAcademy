package com.homework13.service;

/**
 * Class for checking data.
 */
public class CheckData {
  /**
   * Check login and password.
   * @param login login
   * @param password password
   * @return empty string, if login and password have not empty, otherwise message about error.
   */
  public static String checkUserData(String login, String password) {
    if (login == null) {
      login = "";
    } else {
      login = login.trim();
    }
    if (password == null) {
      password = "";
    } else {
      password = password.trim();
    }
    StringBuilder stringBuilder = new StringBuilder();
    if ("".equals(login)) {
      stringBuilder.append("Вы не ввели логин.");
    }
    if ("".equals(password)) {
      stringBuilder.append("Вы не ввели пароль.");
    }
    return stringBuilder.toString();
  }
}
