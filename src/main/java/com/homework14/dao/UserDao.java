package com.homework14.dao;

import com.homework13.dao.DuplicateUserException;
import com.homework13.dao.NoSuchUserException;
import com.homework13.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class for working with user from database.
 */
public class UserDao {
  public static void saveUser(User newUser) throws DuplicateUserException {
    if (findUserByLogin(newUser.getLogin()).isPresent()) {
      throw new DuplicateUserException();
    } else {
      String insertRequest = "INSERT INTO users(login, password) VALUES(?, ?);";
      try(Connection connection = DbConnector.getConnection();
          PreparedStatement statement = connection.prepareStatement(insertRequest)) {
        statement.setString(1, newUser.getLogin());
        statement.setString(2, newUser.getPassword());
        statement.execute();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static User getUser(String login) throws NoSuchUserException {
    Optional<User> findUser = findUserByLogin(login);
    if (!findUser.isPresent()) {
      throw new NoSuchUserException();
    } else {
      return findUser.get();
    }
  }

  public static User getUser(long id) throws NoSuchUserException {
    Optional<User> findUser = findUserById(id);
    if (!findUser.isPresent()) {
      throw new NoSuchUserException();
    } else {
      return findUser.get();
    }
  }

  public static int count() {
    String countRequest = "SELECT COUNT(*) FROM users;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(countRequest)) {
      ResultSet resultSet = statement.executeQuery();
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

  public static List<User> getAllUsers() {
    String getAllRequest = "SELECT * FROM users;";
    List<User> users = new ArrayList<>();
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(getAllRequest)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        long id = resultSet.getLong("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        User newUser = new User(login, password);
        newUser.setId(id);
        users.add(newUser);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return users;
  }

  public static void deleteUser(long id) {
    String deleteRequest = "DELETE FROM users WHERE id=?";
    try(Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void updateUser(User newUser) {
    String updateUser = "UPDATE users SET login=?, password=? WHERE id=?";
    try(Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateUser)) {
      statement.setString(1,newUser.getLogin());
      statement.setString(2,newUser.getPassword());
      statement.setLong(3,newUser.getId());
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static Optional<User> findUserByLogin(String login) {
    String selectRequest = "SELECT * FROM users WHERE login=?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectRequest)) {
      statement.setString(1, login);
      ResultSet resultSet = statement.executeQuery(selectRequest);
      return getUserFromResultSet(resultSet);
    } catch (SQLException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  private static Optional<User> findUserById(long needId) {
    String selectRequest = "SELECT * FROM user where id=?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectRequest)) {
      statement.setLong(1, needId);
      ResultSet resultSet = statement.executeQuery();
      return getUserFromResultSet(resultSet);
    } catch (SQLException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  private static Optional<User> getUserFromResultSet(ResultSet resultSet) {
    try {
      if (resultSet.next()) {
        long id = resultSet.getLong("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        User resultUser = new User(login, password);
        resultUser.setId(id);
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
