package com.homework16.model;

/**
 * Class for saving information about role.
 */
public enum Role {
  USER("user"),
  ADMIN("admin");

  private String value;

  Role(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Role fromString(String value) {
    for (Role r : Role.values()) {
      if (r.value.equals(value)) {
        return r;
      }
    }
    throw new IllegalArgumentException("No role for value " + value);
  }

  @Override
  public String toString() {
    return "Role{"
        + "value='" + value + '\''
        + '}';
  }
}
