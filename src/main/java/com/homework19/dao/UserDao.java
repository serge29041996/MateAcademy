package com.homework19.dao;

import com.homework13.model.User;
import com.homework20.dao.GenericDao;
import java.util.Optional;

/**
 * Interface for working with users in database.
 */
public interface UserDao extends GenericDao<User> {

  Optional<User> getUserByLogin(String login);

  Optional<User> getUserByMail(String mail);

  void updateUserRole(long id, String role);
}
