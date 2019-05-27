package com.homework19.dao;

import com.homework17.model.Good;
import com.homework20.dao.GenericDao;
import java.util.Optional;

/**
 * Interface for working with goods in database.
 */
public interface GoodDao extends GenericDao<Good> {

  Optional<Good> getGood(String name);
}
