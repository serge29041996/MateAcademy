package com.homework7.dao;

import com.homework7.model.Human;
import java.util.ArrayList;
import java.util.List;

/**
 * Realization for working with human using list.
 */
public class InMemoryHumanDao implements HumanDao {
  private static final List<Human> inMemoryStorage = new ArrayList<>();

  @Override
  public void save(Human human) {
    inMemoryStorage.add(human);
  }

  @Override
  public Human get() {
    return inMemoryStorage.get(0);
  }
}
