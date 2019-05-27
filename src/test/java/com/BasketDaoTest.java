package com;

import com.homework13.model.User;
import com.homework19.dao.UserDao;
import com.homework19.dao.UserDaoHibernateImpl;
import com.homework20.dao.BasketDao;
import com.homework20.dao.BasketDaoHibernateImpl;
import com.homework20.dao.BasketDaoJdbcImpl;
import com.homework20.model.Basket;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for basket dao.
 */
@RunWith(Parameterized.class)
public class BasketDaoTest {
  private static final String TEST_VALUE = "test";
  private final User testUser = new User(TEST_VALUE, TEST_VALUE, "user", TEST_VALUE);
  private final UserDao userDao = new UserDaoHibernateImpl();

  @Parameter(0)
  public BasketDao basketDao = new BasketDaoHibernateImpl();

  @Parameters
  public static Collection<Object[]> initParameter() {
    Object[][] data = {{new BasketDaoJdbcImpl()}, {new BasketDaoHibernateImpl()}};
    return Arrays.asList(data);
  }

  @After
  public void clear() {
    basketDao.deleteAll();
    userDao.deleteAll();
  }

  @Test
  public void testSaveBasket() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    Basket newBasket = new Basket(gettingUser);
    basketDao.save(newBasket);
    User gettingUserAfterSaving = userDao.getUserByLogin(TEST_VALUE).get();
    Assert.assertEquals(newBasket, gettingUserAfterSaving.getBasket());
  }

  @Test
  public void testGetBasketByIdForExistBasket() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    Basket newBasket = new Basket(gettingUser);
    basketDao.save(newBasket);
    User gettingUserAfterSaving = userDao.getUserByLogin(TEST_VALUE).get();
    long idNeedBasket = gettingUserAfterSaving.getBasket().getId();
    Assert.assertEquals(newBasket, basketDao.get(idNeedBasket).get());
  }

  @Test
  public void testGetBasketByIdForNonExistBasket() {
    Assert.assertEquals(Optional.empty(), basketDao.get(1));
  }

  @Test
  public void testGetBasketByOwnerForExistBasket() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    Basket newBasket = new Basket(gettingUser);
    basketDao.save(newBasket);
    Assert.assertEquals(newBasket, basketDao.getBasket(gettingUser).get());
  }

  @Test
  public void testGetBasketByOwnerForNonExistBasket() {
    Assert.assertEquals(Optional.empty(), basketDao.getBasket(testUser));
  }

  @Test
  public void testDeleteBasket() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    Basket newBasket = new Basket(gettingUser);
    basketDao.save(newBasket);
    User gettingUserAfterSaving = userDao.getUserByLogin(TEST_VALUE).get();
    long idNeedBasket = gettingUserAfterSaving.getBasket().getId();
    basketDao.delete(gettingUserAfterSaving.getBasket());
    Assert.assertEquals(Optional.empty(), basketDao.get(idNeedBasket));
  }
}
