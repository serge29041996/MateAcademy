package com.homework20.dao;

import com.homework13.model.User;
import com.homework20.model.Basket;
import java.util.Optional;

/**
 * Interface working with basket in database.
 */
public interface BasketDao extends GenericDao<Basket> {

  Optional<Basket> getBasket(User owner);

}
