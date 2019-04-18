package com.homework7.dao;

import com.homework7.di.Component;
import com.homework7.model.Human;
import java.util.ArrayList;
import java.util.List;

/**
 * Realization for working with human using list.
 */
@Component
public class InMemoryHumanDao implements HumanDao {
  private static final List<Human> inMemoryStorage = new ArrayList<>();

  @Override
  public void save(Human human) {
    inMemoryStorage.add(human);
  }

  @Override
  public List<Human> get() {
    return inMemoryStorage;
  }
}
