package com.homework19.dao;

import com.homework17.dao.DuplicateGoodException;
import com.homework17.dao.NoSuchGoodException;
import com.homework17.model.Good;
import java.util.List;

/**
 * Interface for working with goods in database.
 */
public interface GoodDao {
  void saveGood(Good newGood) throws DuplicateGoodException;

  Good getGood(long id) throws NoSuchGoodException;

  Good getGood(String name) throws NoSuchGoodException;

  long count();

  void deleteAll();

  List<Good> getAllGoods();

  void deleteGood(long id);

  void updateGood(Good newGood) throws DuplicateGoodException;
}
