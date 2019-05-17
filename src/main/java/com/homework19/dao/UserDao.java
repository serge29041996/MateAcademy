package com.homework19.dao;

import com.homework13.dao.DuplicateUserException;
import com.homework13.dao.NoSuchUserException;
import com.homework13.model.User;
import java.util.List;

/**
 * Interface for working with users in database.
 */
public interface UserDao {
  void saveUser(User newUser) throws DuplicateUserException;

  User getUser(long id) throws NoSuchUserException;

  User getUser(String login) throws NoSuchUserException;

  long count();

  void deleteAll();

  List<User> getAllUsers();

  void deleteUser(long id);

  void updateUser(User newUser) throws DuplicateUserException;

  void updateUserRole(long id, String role);
}
