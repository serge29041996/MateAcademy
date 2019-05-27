package com.homework20.dao;

import com.homework13.model.User;
import com.homework17.model.Good;
import com.homework19.dao.GoodDao;
import com.homework19.dao.GoodDaoHibernateImpl;
import com.homework19.dao.UserDao;
import com.homework19.dao.UserDaoHibernateImpl;
import com.homework20.model.Basket;
import com.homework20.model.BasketGood;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for basket and good dao.
 */
public class BasketGoodDaoTest {
  private static final String TEST_VALUE = "test";
  private final User testUser = new User(TEST_VALUE, TEST_VALUE, "user", TEST_VALUE);
  private final Good testGood = new Good(TEST_VALUE, TEST_VALUE, 100.0, 3);
  private final UserDao userDao = new UserDaoHibernateImpl();
  private final BasketDaoHibernateImpl basketDao = new BasketDaoHibernateImpl();
  private final GoodDao goodDao = new GoodDaoHibernateImpl();
  private final BasketGoodDao basketGoodDao = new BasketGoodDao();

  @After
  public void clear() {
    basketGoodDao.deleteAll();
    goodDao.deleteAll();
    basketDao.deleteAll();
    userDao.deleteAll();
  }

  @Test
  public void testSaveInformationAboutGoodAndBasket() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    Basket newBasket = new Basket(gettingUser);
    basketDao.save(newBasket);
    Basket gettingBasket = basketDao.getBasket(gettingUser).get();
    goodDao.save(testGood);
    Good gettingGood = goodDao.getGood(TEST_VALUE).get();
    BasketGood basketGood = new BasketGood(gettingGood, gettingBasket, 1);
    basketGoodDao.save(basketGood);
    List<BasketGood> goods = basketGoodDao.getAllGoodsFromBaskets(gettingBasket.getId());
    Assert.assertEquals(Arrays.asList(basketGood), goods);
  }

  @Test
  public void testGetNotExistBasketGood() {
    Assert.assertEquals(Optional.empty(), basketGoodDao.get(1));
  }

  @Test
  public void testGetExistBasketGood() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    Basket newBasket = new Basket(gettingUser);
    basketDao.save(newBasket);
    Basket gettingBasket = basketDao.getBasket(gettingUser).get();
    goodDao.save(testGood);
    Good gettingGood = goodDao.getGood(TEST_VALUE).get();
    BasketGood basketGood = new BasketGood(gettingGood, gettingBasket, 1);
    basketGoodDao.save(basketGood);
    List<BasketGood> goods = basketGoodDao.getAllGoodsFromBaskets(gettingBasket.getId());
    Optional<BasketGood> getBasketGood = basketGoodDao.get(goods.get(0).getId());
    Assert.assertEquals(goods.get(0), getBasketGood.get());
  }

  @Test
  public void testDeleteAllGoodsFromBasket() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    Basket newBasket = new Basket(gettingUser);
    basketDao.save(newBasket);
    Basket gettingBasket = basketDao.getBasket(gettingUser).get();
    goodDao.save(testGood);
    Good gettingGood = goodDao.getGood(TEST_VALUE).get();
    BasketGood basketGood = new BasketGood(gettingGood, gettingBasket, 1);
    basketGoodDao.save(basketGood);
    basketGoodDao.deleteAllGoodsFromBasket(gettingBasket.getId());
    List<BasketGood> goods = basketGoodDao.getAllGoodsFromBaskets(gettingBasket.getId());
    Assert.assertEquals(new ArrayList<BasketGood>(), goods);
  }

  @Test
  public void testDeleteGoodFromBasket() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    Basket newBasket = new Basket(gettingUser);
    basketDao.save(newBasket);
    Basket gettingBasket = basketDao.getBasket(gettingUser).get();
    goodDao.save(testGood);
    Good gettingGood = goodDao.getGood(TEST_VALUE).get();
    BasketGood basketGood = new BasketGood(gettingGood, gettingBasket, 1);
    basketGoodDao.save(basketGood);
    List<BasketGood> goods = basketGoodDao.getAllGoodsFromBaskets(gettingBasket.getId());
    basketGoodDao.delete(goods.get(0));
    Optional<BasketGood> getBasketGood = basketGoodDao.get(goods.get(0).getId());
    Assert.assertEquals(Optional.empty(), getBasketGood);
  }

  @Test
  public void testUpdateBasketGood() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    Basket newBasket = new Basket(gettingUser);
    basketDao.save(newBasket);
    Basket gettingBasket = basketDao.getBasket(gettingUser).get();
    goodDao.save(testGood);
    Good gettingGood = goodDao.getGood(TEST_VALUE).get();
    BasketGood basketGood = new BasketGood(gettingGood, gettingBasket, 2);
    basketGoodDao.save(basketGood);
    List<BasketGood> goods = basketGoodDao.getAllGoodsFromBaskets(gettingBasket.getId());
    BasketGood gettingBasketGood = basketGoodDao.get(goods.get(0).getId()).get();
    gettingBasketGood.setAmount(3);
    basketGoodDao.update(gettingBasketGood);
    Assert.assertEquals(gettingBasketGood,
        basketGoodDao.get(gettingBasketGood.getId()).get());
  }
}
