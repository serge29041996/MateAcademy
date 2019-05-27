package com.homework20.dao;

import com.homework13.model.User;
import com.homework14.dao.DbConnector;
import com.homework14.dao.UserDaoJdbcImpl;
import com.homework19.dao.UserDao;
import com.homework20.model.Basket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

/**
 * Realization dao for working with basket in database using JDBC.
 */
public class BasketDaoJdbcImpl implements BasketDao {
  private static final Logger logger = Logger.getLogger(BasketDaoJdbcImpl.class);
  private static final UserDao userDao = new UserDaoJdbcImpl();

  @Override
  public void save(Basket newBasket) {
    logger.debug("Try save new basket for user " + newBasket.getUser().getLogin());
    String insertRequest = "INSERT INTO baskets(user_id) VALUES (?);";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(insertRequest)) {
      statement.setLong(1, newBasket.getUser().getId());
      statement.execute();
      logger.debug("Successful save information about basket for user "
          + newBasket.getUser().getLogin());
    } catch (SQLException e) {
      logger.error("Unable save new basket", e);
    }
  }

  @Override
  public Optional<Basket> get(long id) {
    logger.debug("Get basket with id " + id);
    String selectRequest = "SELECT * FROM baskets WHERE id = ?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectRequest)) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      logger.debug("Successful get basket with id " + id);
      return getBasketFromResultSet(resultSet);
    } catch (SQLException e) {
      logger.error("Unable get basket by id " + id, e);
      return Optional.empty();
    }
  }

  @Override
  public Optional<Basket> getBasket(User owner) {
    logger.debug("Get basket of user with login " + owner.getLogin());
    String selectRequest = "SELECT * FROM baskets WHERE user_id = ?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectRequest)) {
      statement.setLong(1, owner.getId());
      ResultSet resultSet = statement.executeQuery();
      logger.debug("Successful get basket of user with login " + owner.getLogin());
      return getBasketFromResultSet(resultSet);
    } catch (SQLException e) {
      logger.error("Unable get basket by owner " + owner.getLogin(), e);
      return Optional.empty();
    }
  }

  @Override
  public long count() {
    logger.debug("Get number of baskets in website");
    String countRequest = "SELECT COUNT(*) FROM baskets;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(countRequest)) {
      ResultSet resultSet = statement.executeQuery();
      resultSet.next();
      logger.debug("Successfully get number of baskets in website");
      return resultSet.getLong(1);
    } catch (SQLException e) {
      logger.error("Cannot execute select sql request ", e);
      return -1;
    }
  }

  @Override
  public List<Basket> getAll() {
    logger.debug("Get all baskets");
    String getAllRequest = "SELECT * FROM baskets;";
    List<Basket> baskets = new ArrayList<>();
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(getAllRequest)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Basket newBasket = readBasketFromResultSet(resultSet);
        baskets.add(newBasket);
      }
      logger.debug("Successfully get list with all users");
    } catch (SQLException e) {
      logger.error("Cannot execute sql request " + getAllRequest, e);
    }
    return baskets;
  }

  @Override
  public void update(Basket newObject) {
    logger.debug("Update  basket with id " + newObject.getId());
    String updateBasket = "UPDATE baskets SET user_id=? WHERE id = ?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateBasket)) {
      statement.setLong(1,newObject.getUser().getId());
      statement.setLong(2,newObject.getId());
      statement.execute();
      logger.debug("Successful update basket with id " + newObject.getId());
    } catch (SQLException e) {
      logger.debug("Cannot execute update request basket with id " + newObject.getId(), e);
    }
  }

  @Override
  public void deleteAll() {
    logger.debug("Delete all baskets");
    String deleteRequest = "DELETE FROM baskets";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(deleteRequest)) {
      preparedStatement.execute();
      logger.debug("Successfully delete all baskets");
    } catch (SQLException e) {
      logger.error("Unable delete all baskets", e);
    }
  }

  @Override
  public void delete(Basket basketForDeleting) {
    logger.debug("Delete basket with id " + basketForDeleting.getId());
    String deleteRequest = "DELETE FROM baskets WHERE id = ?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(deleteRequest)) {
      preparedStatement.setLong(1, basketForDeleting.getId());
      preparedStatement.execute();
      logger.debug("Successful delete good with id " + basketForDeleting.getId());
    } catch (SQLException e) {
      logger.error("Unable delete basket", e);
    }
  }

  private Optional<Basket> getBasketFromResultSet(ResultSet resultSet) {
    try {
      if (resultSet.next()) {
        Basket basket = readBasketFromResultSet(resultSet);
        return Optional.of(basket);
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      logger.error("Cannot read information about code ", e);
      return Optional.empty();
    }
  }

  private Basket readBasketFromResultSet(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    long idUser = resultSet.getLong("user_id");
    User gettingUser = userDao.get(idUser).get();
    return new Basket(id, gettingUser);
  }
}
