package com.homework17.dao;

import com.homework14.dao.DbConnector;
import com.homework17.model.Good;
import com.homework19.dao.GoodDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

/**
 * Realization dao for working with goods in database.
 */
public class GoodDaoJdbcImpl implements GoodDao {
  private static final Logger LOGGER = Logger.getLogger(GoodDaoJdbcImpl.class);

  /**
   * Save information about good.
   * @param newGood new good
   * @throws DuplicateGoodException when find exist good
   */
  @Override
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
        LOGGER.error("Cannot execute insert sql request about good with name "
            + newGood.getName(), e);
      }
    }
  }

  /**
   * Get good by id.
   * @param id id of need good
   * @return find good
   * @throws NoSuchGoodException when good has not found
   */
  @Override
  public Good getGood(long id) throws NoSuchGoodException {
    Optional<Good> findGood = findGoodById(id);
    LOGGER.debug("Get good with id " + id);
    if (!findGood.isPresent()) {
      LOGGER.debug("Good with id " + id + " has not existed");
      throw new NoSuchGoodException();
    } else {
      LOGGER.debug("Successful find good with id " + id);
      return findGood.get();
    }
  }

  /**
   * Get good by name.
   * @param name name of need good
   * @return find good
   * @throws NoSuchGoodException when good has not found
   */
  @Override
  public Good getGood(String name) throws NoSuchGoodException {
    Optional<Good> findGood = findGoodByName(name);
    LOGGER.debug("Get good with name " + name);
    if (!findGood.isPresent()) {
      LOGGER.debug("Good with name " + name + " has not existed");
      throw new NoSuchGoodException();
    } else {
      LOGGER.debug("Successful find good with name " + name);
      return findGood.get();
    }
  }

  /**
   * Get count of good.
   * @return number of goods
   */
  @Override
  public long count() {
    LOGGER.debug("Get number of goods in website");
    String countRequest = "SELECT COUNT(*) FROM goods;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(countRequest)) {
      ResultSet resultSet = statement.executeQuery();
      resultSet.next();
      LOGGER.debug("Successfully get number of goods in website");
      return resultSet.getLong(1);
    } catch (SQLException e) {
      LOGGER.error("Cannot execute select sql request ", e);
      return -1;
    }
  }

  /**
   * Delete all goods from database.
   */
  @Override
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
  @Override
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

  /**
   * Delete good with specific id.
   * @param id id of good for deleting
   */
  @Override
  public void deleteGood(long id) {
    LOGGER.debug("Delete good with id " + id);
    String deleteRequest = "DELETE FROM goods WHERE ID=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.setLong(1, id);
      statement.execute();
      LOGGER.debug("Successful delete good with id " + id);
    } catch (SQLException e) {
      LOGGER.error("Cannot execute delete sql request ", e);
    }
  }

  /**
   * Update information about good.
   * @param newGood new good
   * @throws DuplicateGoodException if good with same name already exist
   */
  @Override
  public void updateGood(Good newGood) throws DuplicateGoodException {
    LOGGER.debug("Update good with id " + newGood.getId());
    Optional<Good> findGoodWithSameName = findGoodByName(newGood.getName());
    if (findGoodWithSameName.isPresent()) {
      Good findGoodWithSameNameValue = findGoodWithSameName.get();
      if (findGoodWithSameNameValue.getId() == newGood.getId()) {
        updateInformationAboutGood(newGood);
      } else {
        LOGGER.debug("Good with name " + newGood.getName() + " already exist in database.");
        throw new DuplicateGoodException();
      }
    } else {
      updateInformationAboutGood(newGood);
    }
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

  private void updateInformationAboutGood(Good newGood) {
    LOGGER.debug("Good with name " + newGood.getName() + " have not existed. Can update "
        + "information");
    String updateRequest = "UPDATE goods SET name=?, description=?, price=? WHERE id=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateRequest)) {
      statement.setString(1, newGood.getName());
      statement.setString(2, newGood.getDescription());
      statement.setDouble(3, newGood.getPrice());
      statement.setLong(4, newGood.getId());
      statement.execute();
      LOGGER.debug("Successful update good with id " + newGood.getId());
    } catch (SQLException e) {
      LOGGER.error("Unable update information about good with name " + newGood.getName(), e);
      e.printStackTrace();
    }
  }
}
