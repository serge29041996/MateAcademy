package com.homework17.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class for saving code for confirmation selling good to user.
 */
@Entity
@Table(name = "CODES")
public class CodeConfirmation {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "USER_ID")
  private long idUser;

  @Column(name = "GOOD_ID")
  private long idGood;

  @Column(name = "CODE")
  private String code;

  protected CodeConfirmation() {
  }

  /**
   * Constructor with id user, id good and code for confirmation purchase.
   * @param idUser id of user, which want to buy a good
   * @param idGood id of good, which user want to buy
   * @param code code for confirmation purchase
   */
  public CodeConfirmation(long idUser, long idGood, String code) {
    this.idUser = idUser;
    this.idGood = idGood;
    this.code = code;
  }

  /**
   * Constructor for all parameters.
   * @param id id of code confirmation
   * @param idUser id of user, which want to buy a good
   * @param idGood id of good, which user want to buy
   * @param code code for confirmation purchase
   */
  public CodeConfirmation(long id, long idUser, long idGood, String code) {
    this.id = id;
    this.idUser = idUser;
    this.idGood = idGood;
    this.code = code;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getIdUser() {
    return idUser;
  }

  public void setIdUser(long idUser) {
    this.idUser = idUser;
  }

  public long getIdGood() {
    return idGood;
  }

  public void setIdGood(long idGood) {
    this.idGood = idGood;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CodeConfirmation that = (CodeConfirmation) o;

    if (idUser != that.idUser) {
      return false;
    }
    if (idGood != that.idGood) {
      return false;
    }
    return code.equals(that.code);
  }

  @Override
  public int hashCode() {
    int result = (int) (idUser ^ (idUser >>> 32));
    result = 31 * result + (int) (idGood ^ (idGood >>> 32));
    result = 31 * result + code.hashCode();
    return result;
  }
}
