package com.homework14.dao;

import com.homework13.dao.DuplicateUserException;
import com.homework13.dao.NoSuchUserException;
import com.homework13.model.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * Class for working with user from database.
 */
public class UserDao {
  public static void saveUser(User newUser) throws DuplicateUserException {
    if (findUser(newUser.getLogin()).isPresent()) {
      throw new DuplicateUserException();
    } else {
      try(Connection connection = DbConnector.getConnection();
          Statement statement = connection.createStatement()) {
        String insertRequest = "insert into users(login, password) values('" +
            newUser.getLogin() +"', '" + newUser.getPassword() + "');";
        statement.execute(insertRequest);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static User getUser(String login) throws NoSuchUserException {
    Optional<User> findUser = findUser(login);
    if (!findUser.isPresent()) {
      throw new NoSuchUserException();
    } else {
      return findUser.get();
    }
  }

  public static int count() {
    try (Connection connection = DbConnector.getConnection();
        Statement statement = connection.createStatement()) {
      String countRequest = "select count(*) from users;";
      ResultSet resultSet = statement.executeQuery(countRequest);
      resultSet.next();
      return resultSet.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
  }

  public static void deleteAll() {
    try (Connection connection = DbConnector.getConnection();
        Statement statement = connection.createStatement()) {
      String truncateRequest = "truncate users;";
      statement.execute(truncateRequest);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static Optional<User> findUser(String login) {
    try (Connection connection = DbConnector.getConnection();
        Statement statement = connection.createStatement()) {
      String selectRequest = "select * from users where login='" + login + "';";
      ResultSet resultSet = statement.executeQuery(selectRequest);
      if (resultSet.next()) {
        String password = resultSet.getString("password");
        User resultUser = new User(login, password);
        return Optional.of(resultUser);
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }
}
