package com.homework20.dao;

import com.homework17.model.Good;
import com.homework19.utils.HibernateSessionFactoryUtil;
import com.homework20.model.Basket;
import com.homework20.model.BasketGood;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Realization dao for working with goods and baskets in database using Hibernate.
 */
public class BasketGoodDao extends GenericDaoImpl<BasketGood> {
  private static final Logger LOGGER = Logger.getLogger(BasketGoodDao.class);

  /**
   * Get all goods from basket.
   *
   * @param idBasket id of need basket
   * @return list of goods
   */
  public List<BasketGood> getAllGoodsFromBaskets(long idBasket) {
    LOGGER.debug("Try get all goods from basket with id " + idBasket);
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      Query query = session.createQuery("from BasketGood as bg where basket_id = :basket_id");
      query.setParameter("basket_id", idBasket);
      LOGGER.debug("Successful get all goods in basket " + idBasket);
      return query.getResultList();
    } catch (Exception e) {
      LOGGER.error("Unable get all goods from basket", e);
      return new ArrayList<>();
    }
  }

  public Optional<BasketGood> getBasketGood(Basket basket, Good good) {
    LOGGER.debug("Try get good with id " + good.getId() + " from basket with id " + basket.getId());
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      Query query = session.createQuery("from BasketGood as bg where basket_id = :basket_id"
          + " and good_id = :good_id");
      query.setParameter("basket_id", basket.getId());
      query.setParameter("good_id", good.getId());
      LOGGER.debug(
          "Successful get good with id " + good.getId() + " from basket with id " + basket.getId());
      List<BasketGood> basketGoods = query.getResultList();
      return Optional.of(basketGoods.get(0));
    } catch (Exception e) {
      LOGGER.error("Unable get good with id " + good.getId() + " from basket with id "
          + basket.getId(), e);
      return Optional.empty();
    }
  }

  /**
   * Delete all goods from basket.
   *
   * @param idBasket id of need basket
   */
  public void deleteAllGoodsFromBasket(long idBasket) {
    LOGGER.debug("Try delete all information about goods in baskets with id " + idBasket);
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      Query query = session.createQuery("delete from BasketGood where basket_id = :basket_id");
      query.setParameter("basket_id", idBasket);
      Transaction transaction = session.beginTransaction();
      query.executeUpdate();
      LOGGER.debug("Successfully delete all goods from basket with id " + idBasket);
      transaction.commit();
    } catch (Exception e) {
      LOGGER.error("Unable delete all goods from basket", e);
    }
  }
}
