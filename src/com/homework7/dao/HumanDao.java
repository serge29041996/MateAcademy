package com.homework7.dao;

import com.homework7.model.Human;

/**
 * Interface for working with dao for human.
 */
public interface HumanDao {

  void save(Human human);

  Human get();
}
