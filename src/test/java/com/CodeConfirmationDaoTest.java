package com;

import com.homework13.model.User;
import com.homework17.dao.CodeConfirmationDaoJdbcImpl;
import com.homework17.model.CodeConfirmation;
import com.homework17.util.RandomCodeGenerator;
import com.homework19.dao.CodeConfirmationDao;
import com.homework19.dao.CodeConfirmationDaoHibernateImpl;
import com.homework19.dao.UserDao;
import com.homework19.dao.UserDaoHibernateImpl;
import com.homework20.dao.BasketDao;
import com.homework20.dao.BasketDaoHibernateImpl;
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
 * Class for testing code confirmation.
 */
@RunWith(Parameterized.class)
public class CodeConfirmationDaoTest {
  private static final String TEST_VALUE = "test";
  private static final User testUser = new User(TEST_VALUE, TEST_VALUE, "user", TEST_VALUE);
  private static final UserDao userDao = new UserDaoHibernateImpl();
  private static final Basket testBasket = new Basket(testUser);
  private static final BasketDao basketDao = new BasketDaoHibernateImpl();

  @Parameter(0)
  public CodeConfirmationDao codeDao;

  @Parameters
  public static Collection<Object[]> initParameter() {
    Object[][] data = {{new CodeConfirmationDaoJdbcImpl()},
        {new CodeConfirmationDaoHibernateImpl()}};
    return Arrays.asList(data);
  }

  @After
  public void clear() {
    codeDao.deleteAll();
    basketDao.deleteAll();
    userDao.deleteAll();
  }

  @Test
  public void testSaveCode() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    basketDao.save(testBasket);
    Basket gettingBasket = basketDao.getBasket(gettingUser).get();
    long count = codeDao.count();
    CodeConfirmation codeConfirmation =
        new CodeConfirmation(gettingBasket, RandomCodeGenerator.generateFourSignCode());
    codeDao.save(codeConfirmation);
    Assert.assertEquals(count + 1, codeDao.count());
  }

  @Test
  public void testGetByIdExistCode() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    basketDao.save(testBasket);
    Basket gettingBasket = basketDao.getBasket(gettingUser).get();
    CodeConfirmation codeConfirmation =
        new CodeConfirmation(gettingBasket, RandomCodeGenerator.generateFourSignCode());
    codeDao.save(codeConfirmation);
    CodeConfirmation gettingCode = codeDao.getCode(gettingBasket).get();
    Assert.assertEquals(gettingCode, codeDao.get(gettingCode.getId()).get());
  }

  @Test
  public void testGetByIdNonExistCode() {
    Assert.assertEquals(Optional.empty(), codeDao.get(Long.MAX_VALUE));
  }

  @Test
  public void testGetByBasketExistCode() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    basketDao.save(testBasket);
    Basket gettingBasket = basketDao.getBasket(gettingUser).get();
    CodeConfirmation codeConfirmation =
        new CodeConfirmation(gettingBasket, RandomCodeGenerator.generateFourSignCode());
    codeDao.save(codeConfirmation);
    Assert.assertEquals(codeConfirmation, codeDao.getCode(gettingBasket).get());
  }

  @Test
  public void testGetByBasketNonExistCode() {
    Assert.assertEquals(Optional.empty(),
        codeDao.getCode(new Basket(new User("1", "1", "user", "1"))));
  }

  @Test
  public void testUpdateCode() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    basketDao.save(testBasket);
    Basket gettingBasket = basketDao.getBasket(gettingUser).get();
    CodeConfirmation codeConfirmation =
        new CodeConfirmation(gettingBasket, RandomCodeGenerator.generateFourSignCode());
    codeDao.save(codeConfirmation);
    long count = codeDao.count();
    CodeConfirmation gettingCode = codeDao.getCode(gettingBasket).get();
    gettingCode.setCode(RandomCodeGenerator.generateFourSignCode());
    codeDao.update(gettingCode);
    Assert.assertEquals(count, codeDao.count());
    Assert.assertEquals(gettingCode, codeDao.getCode(gettingBasket).get());
  }

  @Test
  public void testDeleteCode() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    basketDao.save(testBasket);
    Basket gettingBasket = basketDao.getBasket(gettingUser).get();
    CodeConfirmation codeConfirmation =
        new CodeConfirmation(gettingBasket, RandomCodeGenerator.generateFourSignCode());
    codeDao.save(codeConfirmation);
    long count = codeDao.count();
    CodeConfirmation gettingCode = codeDao.getCode(gettingBasket).get();
    codeDao.delete(gettingCode);
    Assert.assertEquals(count - 1, codeDao.count());
    Assert.assertEquals(Optional.empty(), codeDao.getCode(gettingBasket));
  }
}
