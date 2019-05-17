package com.homework19.dao;

import com.homework17.dao.DuplicateGoodException;
import com.homework17.dao.NoSuchGoodException;
import com.homework17.model.Good;
import com.homework18.utils.HashUtils;
import com.homework19.utils.HibernateSessionFactoryUtil;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Realization dao for working with goods in database using Hibernate.
 */
public class GoodDaoHibernateImpl implements GoodDao {
  private static final Logger LOGGER = Logger.getLogger(GoodDaoHibernateImpl.class);

  @Override
  public void saveGood(Good newGood) throws DuplicateGoodException {
    LOGGER.debug("Try save good with name " + newGood.getName());
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query query = session.createQuery("from Good where name = :name");
    query.setParameter("name", newGood.getName());
    List<Good> goodWithSameName = (List<Good>) query.getResultList();
    if (goodWithSameName.size() != 0) {
      LOGGER.debug("Exist good with name " + newGood.getName());
      throw new DuplicateGoodException();
    } else {
      Transaction transaction = session.beginTransaction();
      session.save(newGood);
      transaction.commit();
    }
    session.close();
  }

  @Override
  public Good getGood(long id) throws NoSuchGoodException {
    LOGGER.debug("Get good with id " + id);
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Good findGoodById = session.get(Good.class, id);
    if (findGoodById == null) {
      LOGGER.debug("Good with id " + id + " has not existed");
      throw new NoSuchGoodException();
    } else {
      LOGGER.debug("Successful find good with id " + id);
      return findGoodById;
    }
  }

  @Override
  public Good getGood(String name) throws NoSuchGoodException {
    LOGGER.debug("Get good with name " + name);
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query query = session.createQuery("from Good where name = :name");
    query.setParameter("name", name);
    List<Good> findGoodByName = (List<Good>) query.getResultList();
    if (findGoodByName.size() == 0) {
      LOGGER.debug("Good with name " + name + " has not existed");
      throw new NoSuchGoodException();
    } else {
      LOGGER.debug("Successful find good with name " + name);
      return findGoodByName.get(0);
    }
  }

  @Override
  public long count() {
    LOGGER.debug("Get number of goods in website");
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query query = session.createQuery("select count(*) from Good");
    LOGGER.debug("Successfully get number of goods in website");
    return (Long) query.getSingleResult();
  }

  @Override
  public void deleteAll() {
    LOGGER.debug("Delete all goods");
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query query = session.createQuery("delete from Good");
    Transaction transaction = session.beginTransaction();
    query.executeUpdate();
    LOGGER.debug("Successfully delete all goods");
    transaction.commit();
    session.close();
  }

  @Override
  public List<Good> getAllGoods() {
    LOGGER.debug("Get list of all goods");
    return (List<Good>) HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()
        .createQuery("from Good")
        .list();
  }

  @Override
  public void deleteGood(long id) {
    LOGGER.debug("Delete good with id " + id);
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query query = session.createQuery("delete from Good where id = :id");
    query.setParameter("id", id);
    Transaction transaction = session.beginTransaction();
    query.executeUpdate();
    LOGGER.debug("Successful delete good with id " + id);
    transaction.commit();
    session.close();
  }

  @Override
  public void updateGood(Good newGood) throws DuplicateGoodException {
    LOGGER.debug("Update good with id " + newGood.getId());
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query query = session.createQuery("from Good where name = :name");
    query.setParameter("name", newGood.getName());
    List<Good> findGoodWithSameName = (List<Good>) query.getResultList();
    session.close();
    if (findGoodWithSameName.size() != 0) {
      if (findGoodWithSameName.get(0).getId() == newGood.getId()) {
        updateGoodAction(newGood);
      }
    } else {
      updateGoodAction(newGood);
    }
  }

  private void updateGoodAction(Good newGood) {
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Transaction transaction = session.beginTransaction();
    session.update(newGood);
    transaction.commit();
    session.close();
  }
}
