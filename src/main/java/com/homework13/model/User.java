package com.homework13.model;

import com.homework16.model.Role;

/**
 * Class for saving information about user.
 */
public class User {
  private long id;
  private String login;
  private String password;
  private Role role;

  public User(String login, String password) {
    this.login = login;
    this.password = password;
  }

  public User(long id, String login, String password) {
    this.id = id;
    this.login = login;
    this.password = password;
  }

  public User(long id, String login, String password, Role role) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.role = role;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    User user = (User) o;

    if (!login.equals(user.login)) {
      return false;
    }
    if (!password.equals(user.password)) {
      return false;
    }
    return role == user.role;
  }

  @Override
  public int hashCode() {
    int result = login.hashCode();
    result = 31 * result + password.hashCode();
    result = 31 * result + role.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
        "login='" + login + '\'' +
        ", password='" + password + '\'' +
        ", role=" + role +
        '}';
  }
}
