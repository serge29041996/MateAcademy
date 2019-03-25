package com.homework1;

import java.time.LocalDate;

/**
 * Class for saving information about student.
 */
public class Student implements Cloneable {

  private String name;
  private String surname;
  private LocalDate birthday;
  private int course;
  private Department departmentForStudy;

  /**
   * Constructor for all parameters.
   *
   * @param name name of student
   * @param surname surname of student
   * @param birthday birthday of student
   * @param course current number course, where student is studying
   * @param department department, which study student
   */
  public Student(String name, String surname, LocalDate birthday, int course,
      Department department) {
    this.name = name;
    this.surname = surname;
    this.birthday = birthday;
    this.course = course;
    this.departmentForStudy = department;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public int getCourse() {
    return course;
  }

  public void setCourse(int course) {
    this.course = course;
  }

  public Department getDepartmentForStudy() {
    return departmentForStudy;
  }

  public void setDepartmentForStudy(Department departmentForStudy) {
    this.departmentForStudy = departmentForStudy;
  }

  @Override
  public String toString() {
    return "Student{"
        + "name='" + name + '\''
        + ", surname='" + surname + '\''
        + ", birthday=" + birthday
        + ", course=" + course
        + ", departmentForStudy=" + departmentForStudy
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Student student = (Student) o;

    if (course != student.course) {
      return false;
    }
    if (!name.equals(student.name)) {
      return false;
    }
    if (!surname.equals(student.surname)) {
      return false;
    }
    if (!birthday.equals(student.birthday)) {
      return false;
    }
    return departmentForStudy.equals(student.departmentForStudy);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result += surname.hashCode();
    result += birthday.hashCode();
    result += course;
    result += departmentForStudy.hashCode();
    return result;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    Student cloneStudent = (Student) super.clone();
    cloneStudent.setDepartmentForStudy((Department) cloneStudent.departmentForStudy.clone());
    return cloneStudent;
  }
}
