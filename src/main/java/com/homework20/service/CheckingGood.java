package com.homework20.service;

import com.homework17.model.Good;
import com.homework19.dao.GoodDao;
import java.util.Optional;

/**
 * Service for checking existence good in database.
 */
public class CheckingGood {
  private CheckingGood() {}

  /**
   * Check exist good in database for saving.
   * @param goodDao dao for working with database
   * @param goodForChecking good for checking
   * @return result of checking
   */
  public static String checkExistenceGoodWithSameNameForSave(GoodDao goodDao,
      Good goodForChecking) {
    StringBuilder resultMessage = new StringBuilder();
    Optional<Good> goodWithSameName = goodDao.getGood(goodForChecking.getName());
    goodWithSameName.ifPresent(good -> addMessage(resultMessage, goodForChecking.getName()));
    return resultMessage.toString();
  }

  /**
   * Check exist good in database for update.
   * @param goodDao dao for working with database
   * @param goodForChecking good for checking
   * @return result of checking
   */
  public static String checkExistenceGoodWithSameNameForUpdate(GoodDao goodDao,
      Good goodForChecking) {
    StringBuilder resultMessage = new StringBuilder();
    Optional<Good> goodWithSameNameFromDb = goodDao.getGood(goodForChecking.getName());
    if (goodWithSameNameFromDb.isPresent()) {
      Good goodWithSameName = goodWithSameNameFromDb.get();
      if (goodWithSameName.getId() != goodForChecking.getId()) {
        addMessage(resultMessage, goodForChecking.getName());
      }
    }
    return resultMessage.toString();
  }

  private static void addMessage(StringBuilder resultMessage, String name) {
    resultMessage.append("Товар с названием ");
    resultMessage.append(name);
    resultMessage.append(" уже существует.");
  }
}
