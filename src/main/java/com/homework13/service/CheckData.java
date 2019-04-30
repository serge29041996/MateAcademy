package com.homework13.service;

import javax.servlet.http.HttpServletRequest;

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

  /**
   * Check on null attribute and set default value.
   * @param request request, where need attribute located
   * @param nameAttribute name of need attribute
   */
  public static void checkOnNullAndSetDefaultValueForAttribute(HttpServletRequest request,
      String nameAttribute) {
    Object resultMessage = request.getAttribute(nameAttribute);
    if (resultMessage == null) {
      request.setAttribute(nameAttribute,"");
    }
  }

  /**
   * Check on null attribute and set need value.
   * @param request request, where need attribute located
   * @param value value for need attribute
   * @param nameAttribute name of need attribute
   */
  public static void checkOnNullAndSetValueForAttribute(HttpServletRequest request,
      String nameAttribute, String value) {
    if (request.getAttribute(nameAttribute) == null) {
      request.setAttribute(nameAttribute, value);
    }
  }
}
