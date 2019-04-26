package com.homework14.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class for connection with database.
 */
public class DbConnector {
  private static final String URL_TO_DATABASE = "jdbc:mysql://localhost:3306/website?serverTimezone=UTC";
  private static final String LOGIN = "Serge@localhost";
  private static final String PASSWORD = "root";

  public static Connection getConnection() {
    Connection connection = null;
    int numberFailedAttempts = 0;
    while (numberFailedAttempts < 3) {
      try {
        connection = DriverManager.getConnection(URL_TO_DATABASE, LOGIN, PASSWORD);
        break;
      } catch (SQLException e) {
        numberFailedAttempts++;
      }
    }
    if (numberFailedAttempts < 3) {
      return connection;
    } else {
      throw new ConnectionToDatabaseException("Невозможно связаться с базой данных.");
    }
  }
}
