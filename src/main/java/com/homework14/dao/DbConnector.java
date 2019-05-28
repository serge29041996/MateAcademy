package com.homework14.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * Class for connection with database.
 */
public class DbConnector {
  private static final String URL_TO_DATABASE = "jdbc:mysql://localhost:3306/website"
      + "?serverTimezone=UTC";
  private static final String LOGIN = "Serge@localhost";
  private static final String PASSWORD = "root";
  private static final Logger LOGGER = Logger.getLogger(DbConnector.class);

  /**
   * Get connection for working with database.
   *
   * @return connection to database
   */
  public static Connection getConnection() {
    Connection connection = null;
    int numberFailedAttempts = 0;
    while (numberFailedAttempts < 3) {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(URL_TO_DATABASE, LOGIN, PASSWORD);
        break;
      } catch (SQLException e) {
        LOGGER.error((numberFailedAttempts + 1) + " attempt connect to database", e);
        numberFailedAttempts++;
      } catch (ClassNotFoundException e) {
        LOGGER.error("Driver for connection was not found", e);
        numberFailedAttempts++;
      }
    }
    if (numberFailedAttempts < 3) {
      return connection;
    } else {
      LOGGER.error("Cannot connect to database.");
      throw new ConnectionToDatabaseException("Невозможно связаться с базой данных.");
    }
  }
}
