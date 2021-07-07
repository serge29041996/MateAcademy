package com;

import com.homework17.dao.GoodDaoJdbcImpl;
import com.homework17.model.Good;
import com.homework19.dao.GoodDao;
import com.homework19.dao.GoodDaoHibernateImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for good DAO.
 */
@RunWith(Parameterized.class)
public class GoodDaoTest {
  private static final String TEST_VALUE = "test";
  private static final Good testGood = new Good(TEST_VALUE, TEST_VALUE, 100.0, 3);

  @Parameter(0)
  public GoodDao goodDao;

  @Parameters
  public static Collection<Object[]> initParameter() {
    Object[][] data = {{new GoodDaoJdbcImpl()}, {new GoodDaoHibernateImpl()}};
    return Arrays.asList(data);
  }

  @After
  public void clear() {
    goodDao.deleteAll();
  }

  @Test
  public void testSaveGoodNonExistUser() {
    long numberGoods = goodDao.count();
    goodDao.save(testGood);
    Good gettingGood = goodDao.getGood(testGood.getName()).get();
    Assert.assertEquals(numberGoods + 1, goodDao.count());
    Assert.assertEquals(testGood, gettingGood);
  }

  @Test
  public void testGetByIdExistGood() {
    goodDao.save(testGood);
    Good gettingGoodByName = goodDao.getGood(testGood.getName()).get();
    Good gettingGoodById = goodDao.get(gettingGoodByName.getId()).get();
    Assert.assertEquals(gettingGoodByName, gettingGoodById);
  }

  @Test
  public void testGetByIdNonExistGood() {
    Assert.assertEquals(Optional.empty(), goodDao.get(Long.MAX_VALUE));
  }

  @Test
  public void testGetByNameExistGood() {
    goodDao.save(testGood);
    Good gettingGoodByName = goodDao.getGood(testGood.getName()).get();
    Assert.assertEquals(testGood, gettingGoodByName);
  }

  @Test
  public void testGetByNameNonExistGood() {
    Assert.assertEquals(Optional.empty(), goodDao.getGood(testGood.getName()));
  }

  @Test
  public void testCountFromEmptyDatabase() {
    Assert.assertEquals(0, goodDao.count());
  }

  @Test
  public void testGetAllGoodsFromEmptyDatabase() {
    Assert.assertEquals(new ArrayList<Good>(), goodDao.getAll());
  }

  @Test
  public void testGetAllGoodsFromNonEmptyDatabase() {
    List<Good> goodList = new ArrayList<>();
    goodList.add(testGood);
    goodDao.save(testGood);
    Good newGood = new Good("good", "it is bad good", 50.0, 1);
    goodList.add(newGood);
    goodDao.save(newGood);
    Assert.assertEquals(goodList, goodDao.getAll());
  }

  @Test
  public void testDeleteExistGood() {
    goodDao.save(testGood);
    Good gettingGoodByName = goodDao.getGood(TEST_VALUE).get();
    long count = goodDao.count();
    goodDao.delete(gettingGoodByName);
    Assert.assertEquals(count - 1, goodDao.count());
    Assert.assertEquals(Optional.empty(), goodDao.getGood(TEST_VALUE));
  }

  @Test
  public void testUpdateGood() {
    goodDao.save(testGood);
    Good gettingGoodByName = goodDao.getGood(TEST_VALUE).get();
    long count = goodDao.count();
    gettingGoodByName.setDescription("This is amazing thing");
    goodDao.update(gettingGoodByName);
    Assert.assertEquals(count, goodDao.count());
    Assert.assertEquals(gettingGoodByName, goodDao.getGood(TEST_VALUE).get());
  }
}
