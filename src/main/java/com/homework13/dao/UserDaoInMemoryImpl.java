package com.homework13.dao;

import com.homework13.model.User;
import com.homework19.dao.UserDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

/**
 * Realization in-memory database.
 */
public class UserDaoInMemoryImpl implements UserDao {
  private static final List<User> users = new ArrayList<>();
  private static final Logger logger = Logger.getLogger(UserDaoInMemoryImpl.class);

  /**
   * Save user.
   *
   * @param newUser information about new user
   */
  @Override
  public void save(User newUser) {
    logger.debug("Try save information about user with login " + newUser.getLogin());
    newUser.setId(users.size());
    users.add(newUser);
    logger.debug("Successful save information about user with login " + newUser.getLogin());
  }

  @Override
  public Optional<User> get(long id) {
    logger.debug("Successful find user with id " + id);
    try {
      return Optional.of(users.get((int) id));
    } catch (IndexOutOfBoundsException e) {
      return Optional.empty();
    }
  }

  /**
   * Get user from database.
   *
   * @param login login of need user
   * @return find user
   */
  @Override
  public Optional<User> getUserByLogin(String login) {
    logger.debug("Successful find user with login " + login);
    return users.stream()
        .filter(u -> u.getLogin().equals(login))
        .findFirst();
  }

  @Override
  public Optional<User> getUserByMail(String mail) {
    logger.debug("Successful find user with mail " + mail);
    return users.stream()
        .filter(u -> u.getMail().equals(mail))
        .findFirst();
  }

  @Override
  public long count() {
    logger.debug("Successful get count of users");
    return users.size();
  }

  @Override
  public void deleteAll() {
    logger.debug("Successful delete all users");
    users.clear();
  }

  @Override
  public List<User> getAll() {
    List<User> usersForGetting = new ArrayList<>(users);
    logger.debug("Successful get all users");
    return usersForGetting;
  }

  @Override
  public void delete(User user) {
    logger.debug("Delete user with id " + user.getId());
    users.remove(user);
    logger.debug("Successful delete user with id " + user.getId());
  }

  @Override
  public void update(User newUser) {
    logger.debug("Update user information with id " + newUser.getId());
    Optional<User> userForUpdate = get(newUser.getId());
    if (userForUpdate.isPresent()) {
      User userFromDb = userForUpdate.get();
      userFromDb.setLogin(newUser.getLogin());
      userFromDb.setPassword(newUser.getPassword());
      userFromDb.setMail(newUser.getMail());
      userFromDb.setSalt(newUser.getSalt());
      userFromDb.setRole(newUser.getRole());
      logger.debug("Successful update user information with id " + newUser.getId());
    }
  }

  @Override
  public void updateUserRole(long id, String role) {
    logger.debug("Update role for user with id " + id);
    Optional<User> userForUpdate = get(id);
    if (userForUpdate.isPresent()) {
      User userFromDb = userForUpdate.get();
      userFromDb.setRole(role);
      logger.debug("Successful update user information with id " + id);
    }
  }
}
