package com.homework6;

/**
 * Class for saving information about salary.
 */
public class Salary implements Sendable<Integer> {
  private String placeWork;
  private String to;
  private int salary;

  public Salary(String placeWork, String to, int salary) {
    this.placeWork = placeWork;
    this.to = to;
    this.salary = salary;
  }

  public String getTo() {
    return to;
  }

  public int getSalary() {
    return salary;
  }

  @Override
  public String getKey() {
    return getTo();
  }

  @Override
  public Integer getValue() {
    return getSalary();
  }
}
