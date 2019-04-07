package com.homework7.factory;

import com.homework7.dao.FileHumanDao;
import com.homework7.dao.HumanDao;
import com.homework7.dao.InMemoryHumanDao;

/**
 * Realization factory for returning of human dao.
 */
public class HumanDaoFactory {
  private static final HumanDao inMemoryHumanDao = new InMemoryHumanDao();
  private static final HumanDao fileHumanDao = new FileHumanDao();
}
