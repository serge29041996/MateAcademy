package com.homework19.dao;

import com.homework17.model.CodeConfirmation;
import java.util.Optional;

/**
 * Interface for working with code confirmation in database.
 */
public interface CodeConfirmationDao {
  void saveCode(CodeConfirmation newCodeConfirmation);

  Optional<CodeConfirmation> getCode(long idUser, long idGood);

  void updateCode(CodeConfirmation newCodeConfirmation);

  void deleteCode(long id);

  long count();

  void deleteAll();
}
