package com.homework17.dao;

import com.homework14.dao.DbConnector;
import com.homework17.model.CodeConfirmation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.log4j.Logger;

/**
 * Class for working with code in database.
 */
public class CodeConfirmationDao {
  private static final Logger LOGGER = Logger.getLogger(CodeConfirmationDao.class);

  /**
   * Save information about code.
   * @param newCode new code
   */
  public void saveCode(CodeConfirmation newCode) {
    LOGGER.debug("Try save code for user with id " + newCode.getIdUser() + " and good with id "
        + newCode.getIdGood());
    String insertRequest = "INSERT INTO codes(user_id, good_id, code) VALUES(?, ?, ?);";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(insertRequest)) {
      statement.setLong(1, newCode.getIdUser());
      statement.setLong(2, newCode.getIdGood());
      statement.setString(3, newCode.getCode());
      statement.execute();
      LOGGER.debug("Successful save information about purchase of user with id "
          + newCode.getIdUser()
          + " of good " + newCode.getIdUser());
    } catch (SQLException e) {
      LOGGER.error("Cannot execute insert sql request about purchase ", e);
    }
  }

  /**
   * Get code with id of user and id good.
   * @param idUser id user, which want buy good
   * @param idGood id need good
   * @return find code
   */
  public Optional<CodeConfirmation> getCode(long idUser, long idGood) {
    Optional<CodeConfirmation> findCode = findCodeByUserIdAndGoodId(idUser, idGood);
    LOGGER.debug("Get code with user id " + idUser + " and good id " + idGood);
    if (!findCode.isPresent()) {
      LOGGER.debug("Successful find code with user id " + idUser + " and good id " + idGood);
    } else {
      LOGGER.debug("Code with user id " + idUser + " and good id " + idGood + " has not existed");
    }
    return findCode;
  }

  /**
   * Update information about code.
   * @param updatedCode new information of code
   */
  public void updateCode(CodeConfirmation updatedCode) {
    LOGGER.debug("Update code with user id " + updatedCode.getIdUser()
        + " and good id " + updatedCode.getIdGood());
    updateInformationAboutCode(updatedCode);
  }

  /**
   * Delete code by id.
   * @param id id code for deleting
   */
  public void deleteCode(long id) {
    LOGGER.debug("Delete code with id " + id);
    String deleteRequest = "DELETE FROM codes WHERE id=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.setLong(1, id);
      statement.execute();
      LOGGER.debug("Successful delete code with id " + id);
    } catch (SQLException e) {
      LOGGER.error("Cannot execute delete sql request ", e);
    }
  }

  /**
   * Get number of codes.
   * @return number of codes
   */
  public int count() {
    LOGGER.debug("Get number of codes in website");
    String countRequest = "SELECT COUNT(*) FROM codes;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(countRequest)) {
      ResultSet resultSet = statement.executeQuery();
      resultSet.next();
      LOGGER.debug("Successfully get number of codes in website");
      return resultSet.getInt(1);
    } catch (SQLException e) {
      LOGGER.error("Cannot execute select sql request ", e);
      return -1;
    }
  }

  /**
   * Delete all codes from database.
   */
  public void deleteAll() {
    LOGGER.debug("Delete all codes");
    String deleteRequest = "TRUNCATE codes";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteRequest)) {
      statement.execute();
      LOGGER.debug("Successfully delete all codes");
    } catch (SQLException e) {
      LOGGER.error("Cannot execute delete all codes sql request ", e);
    }
  }

  private Optional<CodeConfirmation> findCodeByUserIdAndGoodId(long userId, long goodId) {
    String selectRequest = "SELECT * FROM codes WHERE user_id=? AND good_id=?;";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectRequest)) {
      statement.setLong(1, userId);
      statement.setLong(2, goodId);
      ResultSet resultSet = statement.executeQuery();
      return getCodeFromResultSet(resultSet);
    } catch (SQLException e) {
      LOGGER.error("Cannot execute sql select for finding code with user id " + userId
          + " and good id " + goodId, e);
      return Optional.empty();
    }
  }

  private Optional<CodeConfirmation> getCodeFromResultSet(ResultSet resultSet) {
    try {
      if (resultSet.next()) {
        long id = resultSet.getLong("id");
        long idUser = resultSet.getLong("user_id");
        long idGood = resultSet.getLong("good_id");
        String code = resultSet.getString("code");
        CodeConfirmation resultCode = new CodeConfirmation(id, idUser, idGood, code);
        return Optional.of(resultCode);
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      LOGGER.error("Cannot read information about code ", e);
      return Optional.empty();
    }
  }

  private void updateInformationAboutCode(CodeConfirmation newCode) {
    LOGGER.debug("Code with user id " + newCode.getIdGood() + " and good id"
        + newCode.getIdGood() + " have not existed. Can update information");
    String updateUser = "UPDATE codes SET code=? WHERE user_id=? AND good_id=?";
    try (Connection connection = DbConnector.getConnection();
        PreparedStatement statement = connection.prepareStatement(updateUser)) {
      statement.setString(1,newCode.getCode());
      statement.setLong(2,newCode.getIdUser());
      statement.setLong(3,newCode.getIdGood());
      statement.execute();
      LOGGER.debug("Successful update code with id " + newCode.getId());
    } catch (SQLException e) {
      LOGGER.debug("Cannot execute update request code with id " + newCode.getId(), e);
    }
  }
}
