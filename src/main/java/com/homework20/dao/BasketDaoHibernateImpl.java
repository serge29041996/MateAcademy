package com.homework20.dao;

import com.homework13.model.User;
import com.homework19.utils.HibernateSessionFactoryUtil;
import com.homework20.model.Basket;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * Realization dao for working with baskets in database using Hibernate.
 */
public class BasketDaoHibernateImpl extends GenericDaoImpl<Basket> implements BasketDao {
  private static final Logger LOGGER = LogManager.getLogger(BasketDaoHibernateImpl.class);

  /**
   * Get basket by owner.
   *
   * @param owner owner of the basket
   * @return basket of owner
   */
  @Override
  public Optional<Basket> getBasket(User owner) {
    LOGGER.debug("Get basket of user with login " + owner.getLogin());
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      Query query = session.createQuery("from Basket where user_id = :user_id");
      query.setParameter("user_id", owner.getId());
      List<Basket> basketList = query.getResultList();
      if (basketList.size() == 0) {
        return Optional.empty();
      } else {
        return Optional.of(basketList.get(0));
      }
    } catch (Exception e) {
      LOGGER.error("Unable get basket by owner " + owner.getLogin(), e);
      return Optional.empty();
    }
  }
}
