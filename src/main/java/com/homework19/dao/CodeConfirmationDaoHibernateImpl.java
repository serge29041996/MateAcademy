package com.homework19.dao;

import com.homework17.model.CodeConfirmation;
import com.homework19.utils.HibernateSessionFactoryUtil;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Realization dao for working with codes confirmation in database using Hibernate.
 */
public class CodeConfirmationDaoHibernateImpl implements CodeConfirmationDao {
  private static final Logger LOGGER = Logger.getLogger(CodeConfirmationDaoHibernateImpl.class);

  @Override
  public void saveCode(CodeConfirmation newCode) {
    LOGGER.debug("Try save code for user with id " + newCode.getIdUser() + " and good with id "
        + newCode.getIdGood());
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Transaction transaction = session.beginTransaction();
    session.save(newCode);
    LOGGER.debug("Successful save information about purchase of user with id "
        + newCode.getIdUser()
        + " of good " + newCode.getIdUser());
    transaction.commit();
    session.close();
  }

  @Override
  public Optional<CodeConfirmation> getCode(long idUser, long idGood) {
    LOGGER.debug("Get code with user id " + idUser + " and good id " + idGood);
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query query = session.createQuery("from CodeConfirmation where user_id = :user_id "
        + "and good_id = :good_id");
    query.setParameter("user_id", idUser);
    query.setParameter("good_id", idGood);
    List<CodeConfirmation> codes = (List<CodeConfirmation>) query.getResultList();
    if (codes.size() == 0) {
      LOGGER.debug("Code with user id " + idUser + " and good id " + idGood + " has not existed");
      return Optional.empty();
    } else {
      LOGGER.debug("Successful find code with user id " + idUser + " and good id " + idGood);
      return Optional.of(codes.get(0));
    }
  }

  @Override
  public void updateCode(CodeConfirmation newCodeConfirmation) {
    LOGGER.debug("Update code with user id " + newCodeConfirmation.getIdUser()
        + " and good id " + newCodeConfirmation.getIdGood());
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Transaction transaction = session.beginTransaction();
    session.update(newCodeConfirmation);
    LOGGER.debug("Successful update code with id " + newCodeConfirmation.getId());
    transaction.commit();
    session.close();
  }

  @Override
  public void deleteCode(long id) {
    LOGGER.debug("Delete code with id " + id);
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query query = session.createQuery("delete from CodeConfirmation where id = :id");
    query.setParameter("id", id);
    Transaction transaction = session.beginTransaction();
    query.executeUpdate();
    LOGGER.debug("Successful delete code with id " + id);
    transaction.commit();
    session.close();
  }

  @Override
  public long count() {
    LOGGER.debug("Get number of codes in website");
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query query = session.createQuery("select count(*) from CodeConfirmation");
    LOGGER.debug("Successfully get number of codes in website");
    return (Long) query.getSingleResult();
  }

  @Override
  public void deleteAll() {
    LOGGER.debug("Delete all codes");
    Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession();
    Query query = session.createQuery("delete from CodeConfirmation");
    Transaction transaction = session.beginTransaction();
    query.executeUpdate();
    LOGGER.debug("Successfully delete all codes");
    transaction.commit();
    session.close();
  }
}
