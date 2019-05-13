package com.homework19.dao;

import com.homework13.dao.DuplicateUserException;
import com.homework13.dao.NoSuchUserException;
import com.homework13.model.User;
import com.homework18.utils.HashUtils;
import com.homework19.utils.HibernateSessionFactoryUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Realization dao for working with users using hibernate.
 */
public class UserDaoHibernateImpl implements UserDao {
  private static final Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class);

  @Override
  public void saveUser(User newUser) throws DuplicateUserException {
    LOGGER.debug("Try save user with login " + newUser.getLogin());
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query queryFindByLogin = session.createQuery("from User where login = :login");
    queryFindByLogin.setParameter("login", newUser.getLogin());
    List<User> findUserWithSameLogin = queryFindByLogin.list();
    Query queryFindByMail = session.createQuery("from User where mail = :mail");
    queryFindByMail.setParameter("mail", newUser.getMail());
    List<User> findUserWithSameMail = queryFindByMail.list();
    if (findUserWithSameLogin.size() != 0) {
      LOGGER.debug("Exist user with login " + newUser.getLogin());
      session.close();
      throw new DuplicateUserException("Exist login");
    } else if (findUserWithSameMail.size() != 0) {
      LOGGER.debug("Exist user with mail " + newUser.getMail());
      session.close();
      throw new DuplicateUserException("Exist mail");
    } else {
      String salt = HashUtils.generateSalt();
      String hashPassword = HashUtils.getSha512SecurePassword(newUser.getPassword(), salt);
      User userForSaving = new User(newUser.getLogin(), hashPassword, newUser.getRole(),
          newUser.getMail(), salt);
      Transaction transaction = session.beginTransaction();
      session.save(userForSaving);
      transaction.commit();
      session.close();
      LOGGER.debug("Successful save information about user with login " + newUser.getLogin());
    }
  }

  @Override
  public User getUser(long id) throws NoSuchUserException {
    LOGGER.debug("Get user with id " + id);
    User findUserById = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()
        .get(User.class, id);
    if (findUserById == null) {
      LOGGER.debug("User with id " + id + " has not existed");
      throw new NoSuchUserException();
    } else {
      LOGGER.debug("Successful find user with id " + id);
      return findUserById;
    }
  }

  @Override
  public User getUser(String login) throws NoSuchUserException {
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query queryFindByLogin = session.createQuery("from User where login = :login");
    queryFindByLogin.setParameter("login", login);
    List<User> findUserByLogin = queryFindByLogin.list();
    LOGGER.debug("Get user with login " + login);
    if (findUserByLogin.size() == 0) {
      LOGGER.debug("User with login " + login + " has not existed");
      throw new NoSuchUserException();
    } else {
      LOGGER.debug("Successful find user with login " + login);
      return findUserByLogin.get(0);
    }
  }

  @Override
  public long count() {
    LOGGER.debug("Get number of users in website");
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
    Root<User> root = criteriaQuery.from(User.class);
    criteriaQuery.select(criteriaBuilder.count(root));
    Query<Long> query = session.createQuery(criteriaQuery);
    List<Long> numberUserResult = query.getResultList();
    if (numberUserResult == null) {
      return 0;
    } else {
      LOGGER.debug("Successfully get number of users in website");
      return numberUserResult.get(0);
    }
  }

  @Override
  public void deleteAll() {
    LOGGER.debug("Delete all users with role user");
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Transaction transaction = session.beginTransaction();
    Query query = session.createQuery("delete from User where role = :role");
    query.setParameter("role", "user");
    query.executeUpdate();
    transaction.commit();
    session.close();
  }

  @Override
  public List<User> getAllUsers() {
    LOGGER.debug("Get list of all users");
    List<User> users = (List<User>) HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()
        .createQuery("from User")
        .list();
    LOGGER.debug("Successfully get list with all users");
    if (users == null) {
      users = new ArrayList<>();
    }
    return users;
  }

  @Override
  public void deleteUser(long id) {
    LOGGER.debug("Delete user with id " + id);
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Transaction transaction = session.beginTransaction();
    Query query = session.createQuery("delete from User where id = :id");
    query.setParameter("id", id);
    query.executeUpdate();
    transaction.commit();
    session.close();
    LOGGER.debug("Successful delete user with id " + id);
  }

  @Override
  public void updateUser(User newUser) throws DuplicateUserException {
    LOGGER.debug("Update user with id " + newUser.getId());
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query queryFindByLogin = session.createQuery("from User where login = :login");
    queryFindByLogin.setParameter("login", newUser.getLogin());
    List<User> findUserWithSameLogin = queryFindByLogin.list();
    Query queryFindByMail = session.createQuery("from User where mail = :mail");
    queryFindByMail.setParameter("mail", newUser.getMail());
    List<User> findUserWithSameMail = queryFindByMail.list();
    session.close();
    if (checkOpportunityUpdateUser("login", findUserWithSameLogin, newUser)
        && checkOpportunityUpdateUser("mail", findUserWithSameMail, newUser)) {
      session = HibernateSessionFactoryUtil
          .getSessionFactory()
          .openSession();
      Transaction transaction = session.beginTransaction();
      newUser.setPassword(HashUtils.getSha512SecurePassword(newUser.getPassword(), newUser.getSalt()));
      session.update(newUser);
      transaction.commit();
      session.close();
    }
    LOGGER.debug("Successful update user with id " + newUser.getId());
  }

  @Override
  public void updateUserRole(long id, String role) {
    LOGGER.debug("Update role to " + role + " of user with id " + id);
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query query = session.createQuery("update User set role = :role where id = :id");
    query.setParameter("role", role);
    query.setParameter("id", id);
    Transaction transaction = session.beginTransaction();
    query.executeUpdate();
    transaction.commit();
    LOGGER.debug("Successful update role of user with id " + id);
  }

  private boolean checkOpportunityUpdateUser(String field, List<User> result, User newUser)
      throws DuplicateUserException {
    if (result.size() != 0) {
      if (result.get(0).getId() == newUser.getId()) {
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
