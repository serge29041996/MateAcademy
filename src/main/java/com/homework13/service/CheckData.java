package com.homework13.service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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
    StringBuilder stringBuilder = new StringBuilder();
    if (login == null || "".equals(login.trim())) {
      stringBuilder.append("Вы не ввели логин.");
    }
    if (password == null || "".equals(password.trim())) {
      stringBuilder.append("Вы не ввели пароль.");
    }
    return stringBuilder.toString();
  }

  /**
   * Check login, password and mail.
   * @param login login
   * @param password password
   * @param mail mail
   * @return  empty string, if login,password and mail are valid; otherwise message about error.
   */
  public static String checkUserData(String login, String password, String mail) {
    StringBuilder stringBuilder = new StringBuilder(checkUserData(login, password));
    if (mail == null || "".equals(mail.trim())) {
      stringBuilder.append("Вы не ввели электронную почту.");
    } else {
      try {
        InternetAddress internetAddress = new InternetAddress(mail);
        internetAddress.validate();
      } catch (AddressException e) {
        stringBuilder.append("Введёна неправильная электронная почта.");
      }
    }
    return stringBuilder.toString();
  }

  /**
   * Check login, password and mail.
   * @param login login
   * @param password password
   * @param mail mail
   * @param role role of user
   * @return  empty string, if login,password and mail are valid; otherwise message about error.
   */
  public static String checkUserData(String login, String password, String mail, String role) {
    StringBuilder stringBuilder = new StringBuilder(checkUserData(login, password, mail));
    if (role == null || "".equals(role.trim())) {
      stringBuilder.append("Вы не выбрали роль.");
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
