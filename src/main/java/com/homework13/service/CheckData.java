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
    StringBuilder stringBuilder = new StringBuilder();
    if ("".equals(login.trim())) {
      stringBuilder.append("Вы не ввели логин.");
    }
    if ("".equals(password.trim())) {
      stringBuilder.append("Вы не ввели пароль.");
    }
    return stringBuilder.toString();
  }
}
