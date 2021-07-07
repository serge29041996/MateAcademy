package com.homework14.dao;

import com.homework13.model.User;
import com.homework19.dao.UserDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

/**
 * Realization dao for working with user in database using JDBC.
 */
public class UserDaoJdbcImpl implements UserDao {
  private static final Logger LOGGER = Logger.getLogger(UserDaoJdbcImpl.class);

  /**
   * Save user to database.
   *
   * @param newUser information about user
   */
  @Override
  public void save(User newUser) {
    LOGGER.debug("Try save user with login " + newUser.getLogin());
    String insertRequest = "INSERT INTO users(login, password, role, mail, salt) "
        + "VALUES(?, ?, ?, ?, ?);";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(insertRequest)) {
      statement.setString(1, newUser.getLogin());
      statement.setString(2, newUser.getPassword());
      statement.setString(3, newUser.getRole());
      statement.setString(4, newUser.getMail());
      statement.setString(5, newUser.getSalt());
      statement.execute();
      LOGGER.debug("Successful save information about user with login " + newUser.getLogin());
    } catch (SQLException e) {
      LOGGER.error("Cannot execute insert sql request about user with login "
          + newUser.getLogin(), e);
    }
  }

  /**
   * Get user by login.
   *
   * @param login login of need user
   * @return find user
   */
  @Override
  public Optional<User> getUserByLogin(String login) {
    LOGGER.debug("Successful find user with login " + login);
    return findUserByLogin(login);
  }

  @Override
  public Optional<User> getUserByMail(String mail) {
    LOGGER.debug("Successful find user with mail " + mail);
    return findUserByMail(mail);
  }

  /**
   * Get user by id.
   *
   * @param id id of need user
   * @return find user
   */
  @Override
  public Optional<User> get(long id) {
    LOGGER.debug("Successful find user with id " + id);
    return findUserById(id);
  }

  /**
   * Get number of users in database.
   *
   * @return number of users
   */
  @Override
  public long count() {
    LOGGER.debug("Get number of users in website");
    String countRequest = "SELECT COUNT(*) FROM users;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(countRequest)) {
      ResultSet resultSet = statement.executeQuery();
      resultSet.next();
      LOGGER.debug("Successfully get number of users in website");
      return resultSet.getLong(1);
    } catch (SQLException e) {
      LOGGER.error("Cannot execute select sql request ", e);
      return -1;
    }
  }

  /**
   * Clear database.
   */
  @Override
  public void deleteAll() {
    LOGGER.debug("Delete all users with role user");
    String deleteRequest = "DELETE FROM users";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.execute();
      LOGGER.debug("Successfully delete all users with role user");
    } catch (SQLException e) {
      LOGGER.error("Cannot execute delete all users with role user sql request ", e);
    }
  }

  /**
   * Get all users from database.
   *
   * @return ArrayList of all users
   */
  @Override
  public List<User> getAll() {
    LOGGER.debug("Get list of all users");
    String getAllRequest = "SELECT * FROM users;";
    List<User> users = new ArrayList<>();
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(getAllRequest)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        User newUser = readUserFromResultSet(resultSet);
        users.add(newUser);
      }
      LOGGER.debug("Successfully get list with all users");
    } catch (SQLException e) {
      LOGGER.error("Cannot execute sql request " + getAllRequest, e);
    }
    return users;
  }

  /**
   * Delete user by id.
   *
   * @param user user for deleting
   */
  @Override
  public void delete(User user) {
    LOGGER.debug("Delete user with id " + user.getId());
    String deleteRequest = "DELETE FROM users WHERE id=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.setLong(1, user.getId());
      statement.execute();
      LOGGER.debug("Successful delete user with id " + user.getId());
    } catch (SQLException e) {
      LOGGER.error("Cannot execute delete sql request ", e);
    }
  }

  /**
   * Update information about user.
   *
   * @param newUser exist user with new information
   */
  @Override
  public void update(User newUser) {
    LOGGER.debug("Update user with id " + newUser.getId());
    String updateUser = "UPDATE users SET login=?, password=?, mail=?, role=? WHERE id=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateUser)) {
      statement.setString(1, newUser.getLogin());
      statement.setString(2, newUser.getPassword());
      statement.setString(3, newUser.getMail());
      statement.setString(4, newUser.getRole());
      statement.setLong(5, newUser.getId());
      statement.execute();
      LOGGER.debug("Successful update user with id " + newUser.getId());
    } catch (SQLException e) {
      LOGGER.debug("Cannot execute update request user with id " + newUser.getId(), e);
    }
  }

  /**
   * Update role of user.
   *
   * @param id id user for updating
   * @param newRole new role for user
   */
  @Override
  public void updateUserRole(long id, String newRole) {
    LOGGER.debug("Update role to " + newRole + " of user with id " + id);
    String updateRoleRequest = "UPDATE users SET role=? WHERE id=?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateRoleRequest)) {
      statement.setString(1, newRole);
      statement.setLong(2, id);
      statement.execute();
      LOGGER.debug("Successful update role of user with id " + id);
    } catch (SQLException e) {
      LOGGER.debug("Cannot execute update role request for user with id " + id, e);
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
      LOGGER.error("Cannot execute sql select for finding user with login " + login, e);
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
      LOGGER.error("Cannot execute sql select for finding user with id " + needId, e);
      return Optional.empty();
    }
  }

  private Optional<User> findUserByMail(String mail) {
    String selectRequest = "SELECT * FROM users where mail=?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectRequest)) {
      statement.setString(1, mail);
      ResultSet resultSet = statement.executeQuery();
      return getUserFromResultSet(resultSet);
    } catch (SQLException e) {
      LOGGER.error("Cannot execute sql select for finding user with mail " + mail, e);
      return Optional.empty();
    }
  }

  private Optional<User> getUserFromResultSet(ResultSet resultSet) {
    try {
      if (resultSet.next()) {
        User resultUser = readUserFromResultSet(resultSet);
        return Optional.of(resultUser);
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      LOGGER.error("Cannot read information about user ", e);
      return Optional.empty();
    }
  }

  private User readUserFromResultSet(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    String login = resultSet.getString("login");
    String password = resultSet.getString("password");
    String role = resultSet.getString("role");
    String mail = resultSet.getString("mail");
    String salt = resultSet.getString("salt");
    return new User(id, login, password, role, mail, salt);
  }
}
