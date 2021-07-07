package com.homework20.dao;

import java.util.List;
import java.util.Optional;

/**
 * Generic dao for all existence dao.
 */
public interface GenericDao<T> {
  void save(T newObject);

  Optional<T> get(long id);

  long count();

  void deleteAll();

  List<T> getAll();

  void delete(T objectForDeleting);

  void update(T newObject);
}
