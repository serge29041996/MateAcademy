package com.homework20.model;

import com.homework17.model.Good;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class for saving information about goods in the baskets.
 */
@Entity
@Table(name = "BASKET_GOOD")
public class BasketGood {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "GOOD_ID", nullable = false)
  private Good good;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "BASKET_ID", nullable = false)
  private Basket basket;

  @Column(name = "AMOUNT")
  private int amount;

  protected BasketGood() {}

  public BasketGood(Good good, Basket basket, int amount) {
    this.good = good;
    this.basket = basket;
    this.amount = amount;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Good getGood() {
    return good;
  }

  public void setGood(Good good) {
    this.good = good;
  }

  public Basket getBasket() {
    return basket;
  }

  public void setBasket(Basket basket) {
    this.basket = basket;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BasketGood that = (BasketGood) o;

    if (amount != that.amount) {
      return false;
    }
    if (!good.equals(that.good)) {
      return false;
    }
    return basket.equals(that.basket);
  }

  @Override
  public int hashCode() {
    int result = good.hashCode();
    result = 31 * result + basket.hashCode();
    result = 31 * result + amount;
    return result;
  }

  @Override
  public String toString() {
    return "BasketGood{"
        + "id=" + id
        + ", good=" + good
        + ", basket=" + basket
        + ", amount=" + amount + '}';
  }
}
