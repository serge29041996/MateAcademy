package com.homework13.model;

/**
 * Class for saving information about user.
 */
public class User {
  private long id;
  private String login;
  private String password;

  public User(String login, String password) {
    this.login = login;
    this.password = password;
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
    return password.equals(user.password);
  }

  @Override
  public int hashCode() {
    int result = login.hashCode();
    result = 31 * result + password.hashCode();
    return result;
  }
}
