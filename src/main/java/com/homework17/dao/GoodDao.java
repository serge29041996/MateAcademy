package com.homework17.dao;

import com.homework14.dao.DbConnector;
import com.homework17.model.Good;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

/**
 * Class for working with goods in database.
 */
public class GoodDao {
  private static final Logger LOGGER = Logger.getLogger(GoodDao.class);

  /**
   * Save information about good.
   * @param newGood new good
   * @throws DuplicateGoodException when find exist good
   */
  public void saveGood(Good newGood) throws DuplicateGoodException {
    LOGGER.debug("Try save good with name " + newGood.getName());
    if (findGoodByName(newGood.getName()).isPresent()) {
      LOGGER.debug("Exist good with name " + newGood.getName());
      throw new DuplicateGoodException();
    } else {
      String insertRequest = "INSERT INTO goods(name, description, price) VALUES(?, ?, ?);";
      try (Connection connection = DbConnector.getConnection();
          PreparedStatement statement = connection.prepareStatement(insertRequest)) {
        statement.setString(1, newGood.getName());
        statement.setString(2, newGood.getDescription());
        statement.setDouble(3, newGood.getPrice());
        statement.execute();
        LOGGER.debug("Successful save information about login with name " + newGood.getName());
      } catch (SQLException e) {
        LOGGER.error("Cannot execute insert sql request about good with name " + newGood.getName(), e);
      }
    }
  }

  /**
   * Get good by id.
   * @param id id of need good
   * @return find good
   * @throws NoSuchGoodException when good has not found
   */
  public Good getGood(long id) throws NoSuchGoodException {
    Optional<Good> findGood = findGoodById(id);
    LOGGER.debug("Get good with id " + id);
    if (!findGood.isPresent()) {
      LOGGER.debug("Successful find good with id " + id);
      throw new NoSuchGoodException();
    } else {
      LOGGER.debug("Good with id " + id + " has not existed");
      return findGood.get();
    }
  }

  /**
   * Get good by name.
   * @param name name of need good
   * @return find good
   * @throws NoSuchGoodException when good has not found
   */
  public Good getGood(String name) throws NoSuchGoodException {
    Optional<Good> findGood = findGoodByName(name);
    LOGGER.debug("Get good with name " + name);
    if (!findGood.isPresent()) {
      LOGGER.debug("Successful find good with name " + name);
      throw new NoSuchGoodException();
    } else {
      LOGGER.debug("Good with name " + name + " has not existed");
      return findGood.get();
    }
  }

  /**
   * Get count of good.
   * @return number of goods
   */
  public int count() {
    LOGGER.debug("Get number of goods in website");
    String countRequest = "SELECT COUNT(*) FROM goods;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(countRequest)) {
      ResultSet resultSet = statement.executeQuery();
      resultSet.next();
      LOGGER.debug("Successfully get number of goods in website");
      return resultSet.getInt(1);
    } catch (SQLException e) {
      LOGGER.error("Cannot execute select sql request ", e);
      return -1;
    }
  }

  /**
   * Delete all goods from database.
   */
  public void deleteAll() {
    LOGGER.debug("Delete all goods");
    String deleteRequest = "TRUNCATE goods";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.execute();
      LOGGER.debug("Successfully delete all goods");
    } catch (SQLException e) {
      LOGGER.error("Cannot execute delete all goods sql request ", e);
    }
  }

  /**
   * Get all goods from database.
   * @return all goods
   */
  public List<Good> getAllGoods() {
    LOGGER.debug("Get list of all goods");
    String getAllRequest = "SELECT * FROM goods;";
    List<Good> goods = new ArrayList<>();
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(getAllRequest)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        double price = resultSet.getDouble("price");
        Good newGood = new Good(id, name, description, price);
        goods.add(newGood);
      }
      LOGGER.debug("Successfully get list with all goods");
    } catch (SQLException e) {
      LOGGER.error("Cannot execute sql request " + getAllRequest, e);
    }
    return goods;
  }

  private Optional<Good> findGoodByName(String name) {
    String selectRequest = "SELECT * FROM goods WHERE name=?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectRequest)) {
      statement.setString(1, name);
      ResultSet resultSet = statement.executeQuery();
      return getGoodFromResultSet(resultSet);
    } catch (SQLException e) {
      LOGGER.error("Cannot execute sql select for finding good with name " + name, e);
      e.printStackTrace();
      return Optional.empty();
    }
  }

  private Optional<Good> findGoodById(long needId) {
    String selectRequest = "SELECT * FROM goods where id=?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectRequest)) {
      statement.setLong(1, needId);
      ResultSet resultSet = statement.executeQuery();
      return getGoodFromResultSet(resultSet);
    } catch (SQLException e) {
      LOGGER.error("Cannot execute sql select for finding good with id " + needId, e);
      return Optional.empty();
    }
  }

  private Optional<Good> getGoodFromResultSet(ResultSet resultSet) {
    try {
      if (resultSet.next()) {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        double price = resultSet.getDouble("price");
        Good resultGood = new Good(id, name, description, price);
        return Optional.of(resultGood);
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      LOGGER.error("Cannot read information about good ", e);
      return Optional.empty();
    }
  }
}
