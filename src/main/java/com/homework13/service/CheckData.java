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
   *
   * @param login login
   * @param password password
   * @return empty string, if login and password have not empty, otherwise message about error.
   */
  public static String checkUserData(String login, String password) {
    StringBuilder stringBuilder = new StringBuilder();
    checkString(login, "логин", stringBuilder);
    checkString(password, "пароль", stringBuilder);
    return stringBuilder.toString();
  }

  /**
   * Check login, password and mail.
   *
   * @param login login
   * @param password password
   * @param mail mail
   * @return empty string, if login, password and mail are valid; otherwise message about error.
   */
  public static String checkUserData(String login, String password, String mail) {
    StringBuilder stringBuilder = new StringBuilder(checkUserData(login, password));
    if (checkString(mail, "электронную почту", stringBuilder)) {
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
   *
   * @param login login
   * @param password password
   * @param mail mail
   * @param role role of user
   * @return empty string, if login, password and mail are valid; otherwise message about error.
   */
  public static String checkUserData(String login, String password, String mail, String role) {
    StringBuilder stringBuilder = new StringBuilder(checkUserData(login, password, mail));
    checkString(role, "роль", stringBuilder);
    return stringBuilder.toString();
  }

  /**
   * Check on null attribute and set default value.
   *
   * @param request request, where need attribute located
   * @param nameAttribute name of need attribute
   */
  public static void checkOnNullAndSetDefaultValueForAttribute(HttpServletRequest request,
      String nameAttribute) {
    Object resultMessage = request.getAttribute(nameAttribute);
    if (resultMessage == null) {
      request.setAttribute(nameAttribute, "");
    }
  }

  /**
   * Check on null attribute and set need value.
   *
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

  /**
   * Check data about good.
   *
   * @param name name of good
   * @param description description of good
   * @param price price of good
   * @return empty string if entered valid information, otherwise error message
   */
  public static String checkGoodData(String name, String description, String price, String count) {
    StringBuilder stringBuilder = new StringBuilder();
    checkString(name, "название", stringBuilder);
    checkString(description, "описание", stringBuilder);
    checkNumber(price, "цену", stringBuilder);
    checkNumber(count, "количество", stringBuilder);
    return stringBuilder.toString();
  }

  private static boolean checkString(String valueField, String nameField,
      StringBuilder stringBuilder) {
    if (valueField == null || "".equals(valueField.trim())) {
      stringBuilder.append("Вы не ввели ");
      stringBuilder.append(nameField);
      stringBuilder.append(".");
      return false;
    } else {
      return true;
    }
  }

  private static void checkNumber(String valueField, String nameField,
      StringBuilder stringBuilder) {
    if (checkString(valueField, nameField, stringBuilder)) {
      try {
        Double.parseDouble(valueField);
      } catch (NumberFormatException e) {
        stringBuilder.append("Вы ввели " + nameField + ", которое не является числом.");
      }
    }
  }
}
