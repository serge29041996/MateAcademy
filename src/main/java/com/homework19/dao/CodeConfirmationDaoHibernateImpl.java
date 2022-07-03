package com.homework19.dao;

import com.homework17.model.CodeConfirmation;
import com.homework19.utils.HibernateSessionFactoryUtil;
import com.homework20.dao.GenericDaoImpl;
import com.homework20.model.Basket;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Realization dao for working with codes confirmation in database using Hibernate.
 */
public class CodeConfirmationDaoHibernateImpl extends GenericDaoImpl<CodeConfirmation>
    implements CodeConfirmationDao {
  private static final Logger LOGGER = Logger.getLogger(CodeConfirmationDaoHibernateImpl.class);

  @Override
  public Optional<CodeConfirmation> getCode(Basket basket) {
    LOGGER.debug("Get code for basket with id " + basket.getId());
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      Query query = session.createQuery("from CodeConfirmation where basket_id = :basket_id");
      query.setParameter("basket_id", basket.getId());
      List<CodeConfirmation> codes = (List<CodeConfirmation>) query.getResultList();
      if (codes.size() == 0) {
        LOGGER.debug("Code for basket with id " + basket.getId() + " has not existed");
        return Optional.empty();
      } else {
        LOGGER.debug("Successful find code for basket with id " + basket.getId());
        return Optional.of(codes.get(0));
      }
    } catch (Exception e) {
      LOGGER.error("Unable get code for basket with id " + basket.getId(), e);
      return Optional.empty();
    }
  }
}
