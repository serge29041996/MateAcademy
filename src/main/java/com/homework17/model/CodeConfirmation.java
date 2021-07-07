package com.homework17.model;

import com.homework20.model.Basket;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

  @OneToOne
  @JoinColumn(name = "BASKET_ID")
  private Basket basket;

  @Column(name = "CODE")
  private String code;

  protected CodeConfirmation() {
  }

  /**
   * Constructor with id user, id good and code for confirmation purchase.
   *
   * @param basket basket with goods
   * @param code code for confirmation purchase
   */
  public CodeConfirmation(Basket basket, String code) {
    this.basket = basket;
    this.code = code;
  }

  /**
   * Constructor for all parameters.
   *
   * @param id id of code confirmation
   * @param basket basket with goods
   * @param code code for confirmation purchase
   */
  public CodeConfirmation(long id, Basket basket, String code) {
    this(basket, code);
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Basket getBasket() {
    return basket;
  }

  public void setBasket(Basket basket) {
    this.basket = basket;
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

    return code.equals(that.code);
  }

  @Override
  public int hashCode() {
    return code.hashCode();
  }
}
