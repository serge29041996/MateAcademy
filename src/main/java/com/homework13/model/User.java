package com.homework13.model;

import com.homework16.model.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class for saving information about user.
 */
@Entity
@Table(name = "USERS")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(name = "LOGIN")
  private String login;
  @Column(name = "PASSWORD")
  private String password;
  @Column(name = "ROLE")
  private String role;
  @Column(name = "MAIL")
  private String mail;
  @Column(name = "SALT")
  private String salt;

  protected User() {}

  /**
   * Constructor for login, password and mail.
   * @param login login of user
   * @param password password user
   * @param mail mail of user
   */
  public User(String login, String password, String mail) {
    this.login = login;
    this.password = password;
    this.mail = mail;
    this.role = Role.USER.getValue();
  }

  /**
   * Constructor for login, password, mail and role.
   * @param login login of user
   * @param password password user
   * @param mail mail of user
   * @param role role of user
   */
  public User(String login, String password, String mail, String role) {
    this.login = login;
    this.password = password;
    this.mail = mail;
    this.role = role;
  }

  /**
   * Constructor for login, password, mail, role and salt.
   * @param login login of user
   * @param password password user
   * @param mail mail of user
   * @param role role of user
   */
  public User(String login, String password, String role, String mail, String salt) {
    this.login = login;
    this.password = password;
    this.role = role;
    this.mail = mail;
    this.salt = salt;
  }

  /**
   * Constructor for id, login, password, role and mail parameters.
   * @param id id of user
   * @param login login of user
   * @param password password user
   * @param role role of user
   * @param mail mail of user
   */
  public User(long id, String login, String password, String role, String mail) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.role = role;
    this.mail = mail;
  }

  /**
   * Constructor for id, login, password, role and mail parameters.
   * @param id id of user
   * @param login login of user
   * @param password password user
   * @param role role of user
   * @param mail mail of user
   */
  public User(long id, String login, String password, String role, String mail, String salt) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.role = role;
    this.mail = mail;
    this.salt = salt;
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

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
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
    if (!role.equals(user.role)) {
      return false;
    }
    if (!mail.equals(user.mail)) {
      return false;
    }
    return salt != null ? salt.equals(user.salt) : user.salt == null;
  }

  @Override
  public int hashCode() {
    int result = login.hashCode();
    result = 31 * result + password.hashCode();
    result = 31 * result + role.hashCode();
    result = 31 * result + mail.hashCode();
    result = 31 * result + (salt != null ? salt.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "User{"
        + "login='" + login + '\''
        + ", password='" + password + '\''
        + ", role=" + role
        + ", mail='" + mail + '\''
        + ", salt='" + salt + '\''
        + '}';
  }
}