package com.homework19.dao;

import com.homework17.model.Good;
import com.homework19.utils.HibernateSessionFactoryUtil;
import com.homework20.dao.GenericDao;
import com.homework20.dao.GenericDaoImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Realization dao for working with goods in database using Hibernate.
 */
public class GoodDaoHibernateImpl extends GenericDaoImpl<Good> implements GoodDao {
  private static final Logger logger = Logger.getLogger(GoodDaoHibernateImpl.class);

  @Override
  public Optional<Good> getGood(String name) {
    logger.debug("Get good with name " + name);
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      Query query = session.createQuery("from Good where name = :name");
      query.setParameter("name", name);
      List<Good> findGoodByName = (List<Good>) query.getResultList();
      return Optional.ofNullable(findGoodByName.get(0));
    } catch (Exception e) {
      logger.error("Unable get good with name " + name, e);
      return Optional.empty();
    }
  }
}
