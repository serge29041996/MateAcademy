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
  private static final Logger logger = Logger.getLogger(GoodDaoJdbcImpl.class);

  /**
   * Save information about good.
   * @param newGood new good
   */
  @Override
  public void save(Good newGood) {
    logger.debug("Try save good with name " + newGood.getName());
    String insertRequest = "INSERT INTO goods(name, description, price, count) VALUES(?, ?, ?, ?);";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(insertRequest)) {
      setParametersForStatement(statement, newGood);
      statement.execute();
      logger.debug("Successful save information about login with name " + newGood.getName());
    } catch (SQLException e) {
      logger.error("Cannot execute insert sql request about good with name "
          + newGood.getName(), e);
    }
  }

  /**
   * Get good by id.
   * @param id id of need good
   * @return find good
   */
  @Override
  public Optional<Good> get(long id) {
    logger.debug("Successful find good with id " + id);
    String selectRequest = "SELECT * FROM goods where id=?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectRequest)) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      return getGoodFromResultSet(resultSet);
    } catch (SQLException e) {
      logger.error("Cannot execute sql select for finding good with id " + id, e);
      return Optional.empty();
    }
  }

  /**
   * Get good by name.
   * @param name name of need good
   * @return find good
   */
  @Override
  public Optional<Good> getGood(String name) {
    logger.debug("Successful find good with name " + name);
    String selectRequest = "SELECT * FROM goods WHERE name=?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectRequest)) {
      statement.setString(1, name);
      ResultSet resultSet = statement.executeQuery();
      return getGoodFromResultSet(resultSet);
    } catch (SQLException e) {
      logger.error("Cannot execute sql select for finding good with name " + name, e);
      e.printStackTrace();
      return Optional.empty();
    }
  }

  /**
   * Get count of good.
   * @return number of goods
   */
  @Override
  public long count() {
    logger.debug("Get number of goods in website");
    String countRequest = "SELECT COUNT(*) FROM goods;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(countRequest)) {
      ResultSet resultSet = statement.executeQuery();
      resultSet.next();
      logger.debug("Successfully get number of goods in website");
      return resultSet.getLong(1);
    } catch (SQLException e) {
      logger.error("Cannot execute select sql request ", e);
      return -1;
    }
  }

  /**
   * Delete all goods from database.
   */
  @Override
  public void deleteAll() {
    logger.debug("Delete all goods");
    String deleteRequest = "DELETE FROM goods";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.execute();
      logger.debug("Successfully delete all goods");
    } catch (SQLException e) {
      logger.error("Cannot execute delete all goods sql request ", e);
    }
  }

  /**
   * Get all goods from database.
   * @return all goods
   */
  @Override
  public List<Good> getAll() {
    logger.debug("Get list of all goods");
    String getAllRequest = "SELECT * FROM goods;";
    List<Good> goods = new ArrayList<>();
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(getAllRequest)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Good newGood = readGoodFromResultSet(resultSet);
        goods.add(newGood);
      }
      logger.debug("Successfully get list with all goods");
    } catch (SQLException e) {
      logger.error("Cannot execute sql request " + getAllRequest, e);
    }
    return goods;
  }

  /**
   * Delete good with specific id.
   * @param goodForDeleting good, which should delete from database
   */
  @Override
  public void delete(Good goodForDeleting) {
    logger.debug("Delete good with id " + goodForDeleting.getId());
    String deleteRequest = "DELETE FROM goods WHERE ID=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.setLong(1, goodForDeleting.getId());
      statement.execute();
      logger.debug("Successful delete good with id " + goodForDeleting.getId());
    } catch (SQLException e) {
      logger.error("Cannot execute delete sql request ", e);
    }
  }

  /**
   * Update information about good.
   * @param newGood new good
   */
  @Override
  public void update(Good newGood) {
    logger.debug("Update good with id " + newGood.getId());
    logger.debug("Good with name " + newGood.getName() + " have not existed. Can update "
        + "information");
    String updateRequest = "UPDATE goods SET name=?, description=?, price=?, count=? WHERE id=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateRequest)) {
      setParametersForStatement(statement, newGood);
      statement.setLong(5, newGood.getId());
      statement.execute();
      logger.debug("Successful update good with id " + newGood.getId());
    } catch (SQLException e) {
      logger.error("Unable update information about good with name " + newGood.getName(), e);
      e.printStackTrace();
    }
  }

  private Optional<Good> getGoodFromResultSet(ResultSet resultSet) {
    try {
      if (resultSet.next()) {
        Good resultGood = readGoodFromResultSet(resultSet);
        return Optional.of(resultGood);
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      logger.error("Cannot read information about good ", e);
      return Optional.empty();
    }
  }

  private void setParametersForStatement(PreparedStatement statement, Good good) throws SQLException {
    statement.setString(1, good.getName());
    statement.setString(2, good.getDescription());
    statement.setDouble(3, good.getPrice());
    statement.setInt(4, good.getCount());
  }

  private Good readGoodFromResultSet(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    String name = resultSet.getString("name");
    String description = resultSet.getString("description");
    double price = resultSet.getDouble("price");
    int count = resultSet.getInt("count");
    return new Good(id, name, description, price, count);
  }
}
