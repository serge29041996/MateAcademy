package com.homework19.dao;

import com.homework17.model.CodeConfirmation;
import com.homework20.dao.GenericDao;
import com.homework20.model.Basket;
import java.util.Optional;

/**
 * Interface for working with code confirmation in database.
 */
public interface CodeConfirmationDao extends GenericDao<CodeConfirmation> {

  Optional<CodeConfirmation> getCode(Basket basket);

}
