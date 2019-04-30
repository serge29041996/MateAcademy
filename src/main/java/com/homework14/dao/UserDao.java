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
  /**
   * Save user to database.
   * @param newUser information about user
   * @throws DuplicateUserException when user with login of new user is exist
   */
  public void saveUser(User newUser) throws DuplicateUserException {
    if (findUserByLogin(newUser.getLogin()).isPresent()) {
      throw new DuplicateUserException();
    } else {
      String insertRequest = "INSERT INTO users(login, password) VALUES(?, ?);";
      try (Connection connection = DbConnector.getConnection();
          PreparedStatement statement = connection.prepareStatement(insertRequest)) {
        statement.setString(1, newUser.getLogin());
        statement.setString(2, newUser.getPassword());
        statement.execute();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Get user by login.
   * @param login login of need user
   * @return find user
   * @throws NoSuchUserException when user with login not found
   */
  public User getUser(String login) throws NoSuchUserException {
    Optional<User> findUser = findUserByLogin(login);
    if (!findUser.isPresent()) {
      throw new NoSuchUserException();
    } else {
      return findUser.get();
    }
  }

  /**
   * Get user by id.
   * @param id id of need user
   * @return find user
   * @throws NoSuchUserException when user with id not found
   */
  public User getUser(long id) throws NoSuchUserException {
    Optional<User> findUser = findUserById(id);
    if (!findUser.isPresent()) {
      throw new NoSuchUserException();
    } else {
      return findUser.get();
    }
  }

  /**
   * Get number of users in database.
   * @return number of users
   */
  public int count() {
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

  /**
   * Clear database.
   */
  public void deleteAll() {
    try (Connection connection = DbConnector.getConnection();
        Statement statement = connection.createStatement()) {
      String truncateRequest = "TRUNCATE users;";
      statement.execute(truncateRequest);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Get all users from database.
   * @return ArrayList of all users
   */
  public List<User> getAllUsers() {
    String getAllRequest = "SELECT * FROM users;";
    List<User> users = new ArrayList<>();
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(getAllRequest)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        long id = resultSet.getLong("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        User newUser = new User(id, login, password);
        newUser.setId(id);
        users.add(newUser);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return users;
  }

  /**
   * Delete user by id.
   * @param id id of user for deleting
   */
  public void deleteUser(long id) {
    String deleteRequest = "DELETE FROM users WHERE id=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.setLong(1, id);
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Update information about user.
   * @param newUser exist user with new information
   */
  public void updateUser(User newUser) {
    String updateUser = "UPDATE users SET login=?, password=? WHERE id=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateUser)) {
      statement.setString(1,newUser.getLogin());
      statement.setString(2,newUser.getPassword());
      statement.setLong(3,newUser.getId());
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private Optional<User> findUserByLogin(String login) {
    String selectRequest = "SELECT * FROM users WHERE login=?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectRequest)) {
      statement.setString(1, login);
      ResultSet resultSet = statement.executeQuery();
      return getUserFromResultSet(resultSet);
    } catch (SQLException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  private Optional<User> findUserById(long needId) {
    String selectRequest = "SELECT * FROM users where id=?;";
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

  private Optional<User> getUserFromResultSet(ResultSet resultSet) {
    try {
      if (resultSet.next()) {
        long id = resultSet.getLong("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        User resultUser = new User(id, login, password);
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
