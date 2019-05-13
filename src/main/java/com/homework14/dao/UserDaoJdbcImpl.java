package com.homework14.dao;

import com.homework13.dao.DuplicateUserException;
import com.homework13.dao.NoSuchUserException;
import com.homework13.model.User;
import com.homework16.model.Role;
import com.homework18.utils.HashUtils;
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
 * Class for working with user from database.
 */
public class UserDaoJdbcImpl implements UserDao {
  private static final Logger LOGGER = Logger.getLogger(UserDaoJdbcImpl.class);

  /**
   * Save user to database.
   * @param newUser information about user
   * @throws DuplicateUserException when user with login of new user is exist
   */
  public void saveUser(User newUser) throws DuplicateUserException {
    LOGGER.debug("Try save user with login " + newUser.getLogin());
    if (findUserByLogin(newUser.getLogin()).isPresent()) {
      LOGGER.debug("Exist user with login " + newUser.getLogin());
      throw new DuplicateUserException("Exist login");
    } else if (findUserByMail(newUser.getMail()).isPresent()) {
      LOGGER.debug("Exist user with mail " + newUser.getMail());
      throw new DuplicateUserException("Exist mail");
    } else {
      String insertRequest = "INSERT INTO users(login, password, role, mail, salt) "
          + "VALUES(?, ?, ?, ?, ?);";
      try (Connection connection = DbConnector.getConnection();
          PreparedStatement statement = connection.prepareStatement(insertRequest)) {
        String salt = HashUtils.generateSalt();
        statement.setString(1, newUser.getLogin());
        statement.setString(2, HashUtils.getSha512SecurePassword(newUser.getPassword(), salt));
        statement.setString(3, newUser.getRole());
        statement.setString(4, newUser.getMail());
        statement.setString(5, salt);
        statement.execute();
        LOGGER.debug("Successful save information about user with login " + newUser.getLogin());
      } catch (SQLException e) {
        LOGGER.error("Cannot execute insert sql request about user with login "
            + newUser.getLogin(), e);
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
    LOGGER.debug("Get user with login " + login);
    if (!findUser.isPresent()) {
      LOGGER.debug("User with login " + login + " has not existed");
      throw new NoSuchUserException();
    } else {
      LOGGER.debug("Successful find user with login " + login);
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
    LOGGER.debug("Get user with id " + id);
    if (!findUser.isPresent()) {
      LOGGER.debug("User with id " + id + " has not existed");
      throw new NoSuchUserException();
    } else {
      LOGGER.debug("Successful find user with id " + id);
      return findUser.get();
    }
  }

  /**
   * Get number of users in database.
   * @return number of users
   */
  public long count() {
    LOGGER.debug("Get number of users in website");
    String countRequest = "SELECT COUNT(*) FROM users;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(countRequest)) {
      ResultSet resultSet = statement.executeQuery();
      resultSet.next();
      LOGGER.debug("Successfully get number of users in website");
      return resultSet.getInt(1);
    } catch (SQLException e) {
      LOGGER.error("Cannot execute select sql request ", e);
      return -1;
    }
  }

  /**
   * Clear database.
   */
  public void deleteAll() {
    LOGGER.debug("Delete all users with role user");
    String deleteRequest = "DELETE FROM users WHERE role=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.setString(1, Role.USER.getValue());
      statement.execute();
      LOGGER.debug("Successfully delete all users with role user");
    } catch (SQLException e) {
      LOGGER.error("Cannot execute delete all users with role user sql request ", e);
    }
  }

  /**
   * Get all users from database.
   * @return ArrayList of all users
   */
  public List<User> getAllUsers() {
    LOGGER.debug("Get list of all users");
    String getAllRequest = "SELECT * FROM users;";
    List<User> users = new ArrayList<>();
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(getAllRequest)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        long id = resultSet.getLong("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        String role = resultSet.getString("role");
        String mail = resultSet.getString("mail");
        String salt = resultSet.getString("salt");
        User newUser = new User(id, login, password, role, mail, salt);
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
   * @param id id of user for deleting
   */
  public void deleteUser(long id) {
    LOGGER.debug("Delete user with id " + id);
    String deleteRequest = "DELETE FROM users WHERE id=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.setLong(1, id);
      statement.execute();
      LOGGER.debug("Successful delete user with id " + id);
    } catch (SQLException e) {
      LOGGER.error("Cannot execute delete sql request ", e);
    }
  }

  /**
   * Update information about user.
   * @param newUser exist user with new information
   */
  public void updateUser(User newUser) throws DuplicateUserException {
    LOGGER.debug("Update user with id " + newUser.getId());
    Optional<User> findUserWithSameLogin = findUserByLogin(newUser.getLogin());
    Optional<User> findUserWithSameMail = findUserByMail(newUser.getMail());
    if (checkOpportunityUpdateUser("login", findUserWithSameLogin, newUser)
        && checkOpportunityUpdateUser("mail", findUserWithSameMail, newUser)) {
      updateInformationAboutUser(newUser);
    }
  }

  /**
   * Update role of user.
   * @param id id user for updating
   * @param newRole new role for user
   */
  public void updateUserRole(long id, String newRole) {
    LOGGER.debug("Update role to " + newRole + " of user with id " + id);
    String updateRoleRequest = "UPDATE users SET role=? WHERE id=?";
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
        long id = resultSet.getLong("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        String role = resultSet.getString("role");
        String mail = resultSet.getString("mail");
        String salt = resultSet.getString("salt");
        User resultUser = new User(id, login, password, role, mail, salt);
        return Optional.of(resultUser);
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      LOGGER.error("Cannot read information about user ", e);
      return Optional.empty();
    }
  }

  private void updateInformationAboutUser(User newUser) {
    LOGGER.debug("User with login " + newUser.getLogin() + " have not existed. Can update "
        + "information");
    String updateUser = "UPDATE users SET login=?, password=?, mail=?, role=? WHERE id=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateUser)) {
      statement.setString(1,newUser.getLogin());
      statement.setString(2,HashUtils.getSha512SecurePassword(newUser.getPassword(),
          newUser.getSalt()));
      statement.setString(3,newUser.getMail());
      statement.setString(4,newUser.getRole());
      statement.setLong(5,newUser.getId());
      statement.execute();
      LOGGER.debug("Successful update user with id " + newUser.getId());
    } catch (SQLException e) {
      LOGGER.debug("Cannot execute update request user with id " + newUser.getId(), e);
    }
  }

  private boolean checkOpportunityUpdateUser(String field, Optional<User> result, User newUser)
      throws DuplicateUserException {
    if (result.isPresent()) {
      User findUserWithSameLoginValue = result.get();
      if (findUserWithSameLoginValue.getId() == newUser.getId()) {
        return true;
      } else {
        LOGGER.debug("User with " + field + " already exist");
        throw new DuplicateUserException("Exist " + field);
      }
    } else {
      return true;
    }
  }
}
