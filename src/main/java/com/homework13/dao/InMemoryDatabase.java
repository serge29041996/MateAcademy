package com.homework13.dao;

import com.homework13.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

/**
 * Realization in-memory database.
 */
public class InMemoryDatabase {
  private static final List<User> DATABASE = new ArrayList<>();
  private static final Logger LOGGER = Logger.getLogger(InMemoryDatabase.class);

  /**
   * Save user.
   * @param newUser information about new user
   * @throws DuplicateUserException when user with login already exist
   */
  public static void saveUser(User newUser) throws DuplicateUserException {
    LOGGER.debug("Try save user with login " + newUser.getLogin());
    Optional<User> duplicateUser = findUser(newUser.getLogin());
    if (duplicateUser.isPresent()) {
      LOGGER.debug("Exist user with login " + newUser.getLogin());
      throw new DuplicateUserException();
    } else {
      LOGGER.debug("Successful save information about user with login " + newUser.getLogin());
      DATABASE.add(newUser);
    }
  }

  /**
   * Get user from database.
   * @param login login of need user
   * @return find user
   * @throws NoSuchUserException when user with login has not find
   */
  public static User getUser(String login) throws NoSuchUserException {
    LOGGER.debug("Try get user with login " + login);
    Optional<User> duplicateUser = findUser(login);
    if (duplicateUser.isPresent()) {
      LOGGER.debug("Successful find user with login " + login);
      return duplicateUser.get();
    } else {
      LOGGER.debug("User with login " + login + " has not existed");
      throw new NoSuchUserException();
    }
  }

  private static Optional<User> findUser(String login) {
    return DATABASE.stream()
        .filter(u -> u.getLogin().equals(login))
        .findFirst();
  }

  public static int count() {
    return DATABASE.size();
  }

  public static void deleteAll() {
    DATABASE.clear();
  }
}
