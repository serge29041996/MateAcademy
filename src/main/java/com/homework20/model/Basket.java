package com.homework20.model;

import com.homework13.model.User;
import com.homework17.model.CodeConfirmation;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Class for saving information about basket.
 */
@Entity
@Table(name = "BASKETS")
public class Basket {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToOne(optional = false)
  @JoinColumn(name = "USER_ID", unique = true, nullable = false, updatable = false)
  private User user;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "basket")
  private List<BasketGood> basketGoods = new ArrayList<>();

  @OneToOne(mappedBy = "basket")
  private CodeConfirmation codeConfirmation;

  protected Basket() {
  }

  public Basket(User user) {
    this.user = user;
  }

  public Basket(long id, User user) {
    this.id = id;
    this.user = user;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<BasketGood> getBasketGoods() {
    return basketGoods;
  }

  public void setBasketGoods(List<BasketGood> basketGoods) {
    this.basketGoods = basketGoods;
  }

  public CodeConfirmation getCodeConfirmation() {
    return codeConfirmation;
  }

  public void setCodeConfirmation(CodeConfirmation codeConfirmation) {
    this.codeConfirmation = codeConfirmation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Basket basket = (Basket) o;

    return user.equals(basket.user);
  }

  @Override
  public int hashCode() {
    return user.hashCode();
  }

  @Override
  public String toString() {
    return "Basket{"
        + "id=" + id
        + ", user=" + user
        + '}';
  }
}
