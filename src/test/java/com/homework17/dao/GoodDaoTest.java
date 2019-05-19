package com.homework17.dao;

import com.homework17.model.Good;
import com.homework19.dao.GoodDao;
import com.homework19.dao.GoodDaoHibernateImpl;
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
  private static final GoodDao GOOD_DAO = new GoodDaoHibernateImpl();

  @After
  public void clear() {
    GOOD_DAO.deleteAll();
  }

  @Test
  public void testSaveGoodNonExistUser() throws DuplicateGoodException, NoSuchGoodException {
    long numberGoods = GOOD_DAO.count();
    GOOD_DAO.saveGood(TEST_GOOD);
    Good gettingGood = GOOD_DAO.getGood(TEST_GOOD.getName());
    Assert.assertEquals(numberGoods + 1, GOOD_DAO.count());
    Assert.assertEquals(TEST_GOOD, gettingGood);
  }

  @Test(expected = DuplicateGoodException.class)
  public void testSaveGoodExistUser() throws DuplicateGoodException{
    GOOD_DAO.saveGood(TEST_GOOD);
    GOOD_DAO.saveGood(TEST_GOOD);
  }

  @Test
  public void testGetByIdExistGood() throws DuplicateGoodException, NoSuchGoodException {
    GOOD_DAO.saveGood(TEST_GOOD);
    Good gettingGoodByName = GOOD_DAO.getGood(TEST_GOOD.getName());
    Good gettingGoodById = GOOD_DAO.getGood(gettingGoodByName.getId());
    Assert.assertEquals(gettingGoodByName, gettingGoodById);
  }

  @Test(expected = NoSuchGoodException.class)
  public void testGetByIdNonExistGood() throws NoSuchGoodException {
    GOOD_DAO.getGood(Long.MAX_VALUE);
  }

  @Test
  public void testGetByNameExistGood() throws DuplicateGoodException, NoSuchGoodException {
    GOOD_DAO.saveGood(TEST_GOOD);
    Good gettingGoodByName = GOOD_DAO.getGood(TEST_GOOD.getName());
    Assert.assertEquals(TEST_GOOD, gettingGoodByName);
  }

  @Test(expected = NoSuchGoodException.class)
  public void testGetByNameNonExistGood() throws NoSuchGoodException {
    GOOD_DAO.getGood(TEST_GOOD.getName());
  }

  @Test
  public void testCountFromEmptyDatabase() {
    Assert.assertEquals(0, GOOD_DAO.count());
  }

  @Test
  public void testGetAllGoodsFromEmptyDatabase() {
    Assert.assertEquals(new ArrayList<Good>(), GOOD_DAO.getAllGoods());
  }

  @Test
  public void testGetAllGoodsFromNonEmptyDatabase() throws DuplicateGoodException {
    List<Good> goodList = new ArrayList<>();
    goodList.add(TEST_GOOD);
    GOOD_DAO.saveGood(TEST_GOOD);
    Good newGood = new Good("good", "it is bad good", 50.0);
    goodList.add(newGood);
    GOOD_DAO.saveGood(newGood);
    Assert.assertEquals(goodList, GOOD_DAO.getAllGoods());
  }
}
