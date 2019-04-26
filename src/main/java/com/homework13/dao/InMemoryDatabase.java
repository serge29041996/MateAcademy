package com.homework13.dao;

import com.homework13.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Realization in-memory database.
 */
public class InMemoryDatabase {
  private static final List<User> DATABASE = new ArrayList<>();

  /**
   * Save user.
   * @param newUser information about new user
   * @throws DuplicateUserException when user with login already exist
   */
  public static void saveUser(User newUser) throws DuplicateUserException {
    Optional<User> duplicateUser = findUser(newUser.getLogin());
    if (duplicateUser.isPresent()) {
      throw new DuplicateUserException();
    } else {
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
    Optional<User> duplicateUser = findUser(login);
    if (duplicateUser.isPresent()) {
      return duplicateUser.get();
    } else {
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
