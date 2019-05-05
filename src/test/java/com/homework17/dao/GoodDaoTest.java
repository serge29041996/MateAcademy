package com.homework17.dao;

import com.homework17.model.Good;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for good DAO.
 */
public class GoodDaoTest {
  private static final String TEST_VALUE = "test";
  private static final Good TEST_GOOD = new Good(TEST_VALUE, TEST_VALUE, 100.0);
  private GoodDao goodDao = new GoodDao();

  @After
  public void clear() {
    goodDao.deleteAll();
  }

  @Test
  public void testSaveGoodNonExistUser() throws DuplicateGoodException, NoSuchGoodException {
    int numberGoods = goodDao.count();
    goodDao.saveGood(TEST_GOOD);
    Good gettingGood = goodDao.getGood(TEST_GOOD.getName());
    Assert.assertEquals(numberGoods + 1, goodDao.count());
    Assert.assertEquals(TEST_GOOD, gettingGood);
  }

  @Test(expected = DuplicateGoodException.class)
  public void testSaveGoodExistUser() throws DuplicateGoodException{
    goodDao.saveGood(TEST_GOOD);
    goodDao.saveGood(TEST_GOOD);
  }

  @Test
  public void testGetByIdExistGood() throws DuplicateGoodException, NoSuchGoodException {
    goodDao.saveGood(TEST_GOOD);
    Good gettingGoodByName = goodDao.getGood(TEST_GOOD.getName());
    Good gettingGoodById = goodDao.getGood(gettingGoodByName.getId());
    Assert.assertEquals(gettingGoodByName, gettingGoodById);
  }

  @Test(expected = NoSuchGoodException.class)
  public void testGetByIdNonExistGood() throws NoSuchGoodException {
    goodDao.getGood(Long.MAX_VALUE);
  }

  @Test
  public void testGetByNameExistGood() throws DuplicateGoodException, NoSuchGoodException {
    goodDao.saveGood(TEST_GOOD);
    Good gettingGoodByName = goodDao.getGood(TEST_GOOD.getName());
    Assert.assertEquals(TEST_GOOD, gettingGoodByName);
  }

  @Test(expected = NoSuchGoodException.class)
  public void testGetByNameNonExistGood() throws NoSuchGoodException {
    goodDao.getGood(TEST_GOOD.getName());
  }

  @Test
  public void testCountFromEmptyDatabase() {
    Assert.assertEquals(0, goodDao.count());
  }

  @Test
  public void testGetAllGoodsFromEmptyDatabase() {
    Assert.assertEquals(new ArrayList<Good>(), goodDao.getAllGoods());
  }

  @Test
  public void testGetAllGoodsFromNonEmptyDatabase() throws DuplicateGoodException {
    List<Good> goodList = new ArrayList<>();
    goodList.add(TEST_GOOD);
    goodDao.saveGood(TEST_GOOD);
    Good newGood = new Good("good", "it is bad good", 50.0);
    goodList.add(newGood);
    goodDao.saveGood(newGood);
    Assert.assertEquals(goodList, goodDao.getAllGoods());
  }
}
