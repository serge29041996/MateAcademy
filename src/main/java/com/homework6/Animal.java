package com.homework6;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class for serialization and deserialization task.
 */
public class Animal implements Serializable {
  private final String name;

  public Animal(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Animal) {
      return Objects.equals(name, ((Animal) obj).name);
    }
    return false;
  }

  @Override
  public String toString() {
    return "Animal{" +
        "name='" + name + '\'' +
        '}';
  }
}
