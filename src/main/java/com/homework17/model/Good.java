package com.homework17.model;

import com.homework20.model.BasketGood;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class, which save information about goods.
 */
@Entity
@Table(name = "GOODS")
public class Good {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "NAME")
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "PRICE")
  private double price;

  @Column(name = "COUNT")
  private int count;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "good")
  private List<BasketGood> basketGoods = new ArrayList<>();

  protected Good() {
  }

  /**
   * Constructor with name, description and price of good.
   *
   * @param name name of good
   * @param description description about good
   * @param price price of good
   */
  public Good(String name, String description, double price, int count) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.count = count;
  }

  /**
   * Constructor with all parameters.
   *
   * @param id id of good
   * @param name name of good
   * @param description description about good
   * @param price price of good
   * @param count number of goods
   */
  public Good(long id, String name, String description, double price, int count) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.count = count;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public List<BasketGood> getBasketGoods() {
    return basketGoods;
  }

  public void setBasketGoods(List<BasketGood> basketGoods) {
    this.basketGoods = basketGoods;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Good good = (Good) o;

    if (Double.compare(good.price, price) != 0) {
      return false;
    }
    if (count != good.count) {
      return false;
    }
    if (!name.equals(good.name)) {
      return false;
    }
    return description.equals(good.description);
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = name.hashCode();
    result = 31 * result + description.hashCode();
    temp = Double.doubleToLongBits(price);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + count;
    return result;
  }

  @Override
  public String toString() {
    return "Good{"
        + "name='" + name + '\''
        + ", description='" + description + '\''
        + ", price=" + price
        + ", count=" + count + '}';
  }
}
