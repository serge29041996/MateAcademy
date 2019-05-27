package com.homework19.dao;

import com.homework13.model.User;
import com.homework19.utils.HibernateSessionFactoryUtil;
import com.homework20.dao.GenericDaoImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Realization dao for working with users in database using Hibernate.
 */
public class UserDaoHibernateImpl extends GenericDaoImpl<User> implements UserDao {
  private static final Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class);

  @Override
  public Optional<User> getUserByLogin(String login) {
    LOGGER.debug("Get user with login " + login);
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      Query queryFindByLogin = session.createQuery("from User where login = :login");
      queryFindByLogin.setParameter("login", login);
      List<User> findUserByLogin = queryFindByLogin.list();
      LOGGER.debug("Get user with login " + login);
      LOGGER.debug("Successful find user with login " + login);
      return Optional.of(findUserByLogin.get(0));
    } catch (Exception e) {
      LOGGER.error("Unable get user with login " + login, e);
      return Optional.empty();
    }
  }

  @Override
  public Optional<User> getUserByMail(String mail) {
    LOGGER.debug("Get user with mail " + mail);
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      Query queryFindByLogin = session.createQuery("from User where mail = :mail");
      queryFindByLogin.setParameter("mail", mail);
      List<User> findUserByLogin = queryFindByLogin.list();
      LOGGER.debug("Get user with mail " + mail);
      LOGGER.debug("Successful find user with mail " + mail);
      return Optional.of(findUserByLogin.get(0));
    } catch (Exception e) {
      LOGGER.error("Unable get user with mail " + mail, e);
      return Optional.empty();
    }
  }

  @Override
  public void updateUserRole(long id, String role) {
    LOGGER.debug("Update role to " + role + " of user with id " + id);
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      Query query = session.createQuery("update User set role = :role where id = :id");
      query.setParameter("role", role);
      query.setParameter("id", id);
      Transaction transaction = session.beginTransaction();
      query.executeUpdate();
      transaction.commit();
      LOGGER.debug("Successful update role of user with id " + id);
    } catch (Exception e) {
      LOGGER.error("Unable update role for user with id " + id);
    }
  }
}
