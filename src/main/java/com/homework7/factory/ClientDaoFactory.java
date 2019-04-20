package com.homework7.factory;

import com.homework7.dao.ClientDao;
import com.homework7.dao.FileClientDao;
import com.homework7.dao.InMemoryClientDao;
import com.homework7.service.PropertyLoader;

import java.io.IOException;

public class ClientDaoFactory {
  private static final ClientDao inMemoryClientDao = new InMemoryClientDao();
  private static final ClientDao fileClientDao = new FileClientDao();

  /**
   * Get need client dao (I don't use this method in my realization, because
   * I get object using field).
   * @param isFileDao is file dao has component annotation
   * @param isInMemoryDao is in-memory dao has component annotation
   * @return need client dao
   * @throws ComponentNotFoundException if need dao object is not exist or file dao has not
   *         component annotation.
   */
  public static ClientDao getClientDao(boolean isFileDao, boolean isInMemoryDao) {
    try {
      String dbName = PropertyLoader.getProperty("db.name");
      if (dbName.equals("memory") && isInMemoryDao) {
        return inMemoryClientDao;
      }
    } catch (IOException e) {
      System.out.println("Нет доступа к файлу с свойствами.");
    }
    if (isFileDao) {
      return fileClientDao;
    } else {
      throw new ComponentNotFoundException();
    }
  }
}
