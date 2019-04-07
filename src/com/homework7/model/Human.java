package com.homework7.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Class for saving information about human.
 */
public class Human implements Serializable {
  private String name;
  private String surname;
  private LocalDate birthday;

  /**
   * Constructor for all fields.
   * @param name name
   * @param surname surname
   * @param birthday birthday
   */
  public Human(String name, String surname, LocalDate birthday) {
    this.name = name;
    this.surname = surname;
    this.birthday = birthday;
  }

  @Override
  public String toString() {
    return "Human{"
        + "name='" + name + '\''
        + ", surname='" + surname + '\''
        + ", birthday=" + birthday
        + '}';
  }
}
