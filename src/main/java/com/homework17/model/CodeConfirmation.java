package com.homework17.model;

/**
 * Class for saving code for confirmation selling good to user.
 */
public class CodeConfirmation {
  private long id;
  private long idUser;
  private long idGood;
  private String code;

  public CodeConfirmation(long idUser, long idGood, String code) {
    this.idUser = idUser;
    this.idGood = idGood;
    this.code = code;
  }

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
