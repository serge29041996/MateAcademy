package com.homework17.dao;

import com.homework14.dao.DbConnector;
import com.homework17.model.CodeConfirmation;
import com.homework19.dao.CodeConfirmationDao;
import com.homework20.dao.BasketDao;
import com.homework20.dao.BasketDaoJdbcImpl;
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
 * Realization dao for working with code in database using JDBC.
 */
public class CodeConfirmationDaoJdbcImpl implements CodeConfirmationDao {
  private static final Logger logger = Logger.getLogger(CodeConfirmationDaoJdbcImpl.class);
  private static final BasketDao basketDao = new BasketDaoJdbcImpl();

  /**
   * Save information about code.
   *
   * @param newCode new code
   */
  @Override
  public void save(CodeConfirmation newCode) {
    logger.debug("Try save code for basket with id " + newCode.getBasket());
    String insertRequest = "INSERT INTO codes(basket_id, code) VALUES(?, ?);";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(insertRequest)) {
      statement.setLong(1, newCode.getBasket().getId());
      statement.setString(2, newCode.getCode());
      statement.execute();
      logger.debug("Successful save save code for basket with id " + newCode.getBasket());
    } catch (SQLException e) {
      logger.error("Cannot execute insert sql request about purchase ", e);
    }
  }

  /**
   * Get code by id.
   *
   * @param id id of need code
   * @return find code
   */
  @Override
  public Optional<CodeConfirmation> get(long id) {
    String selectRequest = "SELECT * FROM codes WHERE id=?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectRequest)) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      logger.debug("Successful find code with id " + id);
      return getCodeFromResultSet(resultSet);
    } catch (SQLException e) {
      logger.error("Cannot execute sql select for finding code with id "
          + id, e);
      return Optional.empty();
    }
  }

  /**
   * Get code by basket.
   *
   * @param basket basket with goods
   * @return find code
   */
  @Override
  public Optional<CodeConfirmation> getCode(Basket basket) {
    String selectRequest = "SELECT * FROM codes WHERE basket_id=?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectRequest)) {
      statement.setLong(1, basket.getId());
      ResultSet resultSet = statement.executeQuery();
      logger.debug("Successful find code for basket with id " + basket.getId());
      return getCodeFromResultSet(resultSet);
    } catch (SQLException e) {
      logger.error("Cannot execute sql select for finding code of basket with id "
          + basket.getId(), e);
      return Optional.empty();
    }
  }

  /**
   * Update information about code.
   *
   * @param updatedCode new information of code
   */
  @Override
  public void update(CodeConfirmation updatedCode) {
    logger.debug("Update code for basket with id " + updatedCode.getBasket().getId());
    String updateCode = "UPDATE codes SET code=? WHERE basket_id = ?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateCode)) {
      statement.setString(1, updatedCode.getCode());
      statement.setLong(2, updatedCode.getBasket().getId());
      statement.execute();
      logger.debug("Successful update code with id " + updatedCode.getId());
    } catch (SQLException e) {
      logger.debug("Cannot execute update request code with id " + updatedCode.getId(), e);
    }
  }

  /**
   * Delete code by id.
   *
   * @param codeConfirmationForDeleting code for deleting
   */
  @Override
  public void delete(CodeConfirmation codeConfirmationForDeleting) {
    logger.debug("Delete code with id " + codeConfirmationForDeleting.getId());
    String deleteRequest = "DELETE FROM codes WHERE id=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.setLong(1, codeConfirmationForDeleting.getId());
      statement.execute();
      logger.debug("Successful delete code with id " + codeConfirmationForDeleting.getId());
    } catch (SQLException e) {
      logger.error("Cannot execute delete sql request ", e);
    }
  }

  /**
   * Get number of codes.
   *
   * @return number of codes
   */
  @Override
  public long count() {
    logger.debug("Get number of codes in website");
    String countRequest = "SELECT COUNT(*) FROM codes;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(countRequest)) {
      ResultSet resultSet = statement.executeQuery();
      resultSet.next();
      logger.debug("Successfully get number of codes in website");
      return resultSet.getLong(1);
    } catch (SQLException e) {
      logger.error("Cannot execute select sql request ", e);
      return -1;
    }
  }

  /**
   * Delete all codes from database.
   */
  @Override
  public void deleteAll() {
    logger.debug("Delete all codes");
    String deleteRequest = "DELETE FROM codes";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.execute();
      logger.debug("Successfully delete all codes");
    } catch (SQLException e) {
      logger.error("Cannot execute delete all codes sql request ", e);
    }
  }

  @Override
  public List<CodeConfirmation> getAll() {
    logger.debug("Get all codes");
    String getAllRequest = "SELECT * FROM codes;";
    List<CodeConfirmation> codes = new ArrayList<>();
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(getAllRequest)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        CodeConfirmation newCodeConfirmation = readCodeFromResultSet(resultSet);
        codes.add(newCodeConfirmation);
      }
      logger.debug("Successfully get list with all users");
    } catch (SQLException e) {
      logger.error("Cannot execute sql request " + getAllRequest, e);
    }
    return codes;
  }

  private Optional<CodeConfirmation> getCodeFromResultSet(ResultSet resultSet) {
    try {
      if (resultSet.next()) {
        CodeConfirmation resultCode = readCodeFromResultSet(resultSet);
        return Optional.of(resultCode);
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      logger.error("Cannot read information about code ", e);
      return Optional.empty();
    }
  }

  private CodeConfirmation readCodeFromResultSet(ResultSet resultSet) throws SQLException {
    long id = resultSet.getLong("id");
    long idBasket = resultSet.getLong("basket_id");
    Basket basket = basketDao.get(idBasket).get();
    String code = resultSet.getString("code");
    return new CodeConfirmation(id, basket, code);
  }
}
