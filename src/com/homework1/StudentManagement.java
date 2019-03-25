package com.homework1;

import java.time.LocalDate;

/**
 * Solution for task 1.7.
 */
public class StudentManagement {

  /**
   * Showing using deep cloning.
   *
   * @param args arguments of command lines
   * @throws CloneNotSupportedException if object used clone method without implementing Cloneable
   */
  public static void main(String[] args) throws CloneNotSupportedException {
    System.out.println("Test of deep copy:");
    Department departmentIt = new Department(1, "Department of IT");
    Student serge = new Student("Serge", "Krasnikov", LocalDate.of(2000, 12, 1), 1, departmentIt);
    Student cloneSerge = (Student) serge.clone();
    System.out.println("First student:");
    System.out.println(serge);
    System.out.println("Second student:");
    System.out.println(cloneSerge);
    departmentIt.setName("Department of ecology");
    System.out.println("After change name of department: ");
    System.out.println("First student:");
    System.out.println(serge);
    System.out.println("Second student:");
    System.out.println(cloneSerge);
  }
}
