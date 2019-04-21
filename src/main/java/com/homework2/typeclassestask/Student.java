package com.homework2.typeclassestask;

import java.time.LocalDate;

/**
 * Task with using local classes.
 */
public class Student {

  private String name;
  private String surname;
  private LocalDate birthday;
  private int course;

  public Student(String name, String surname, LocalDate birthday, int course) {
    this.name = name;
    this.surname = surname;
    this.birthday = birthday;
    this.course = course;
  }

  public static void main(String[] args) {
    Student student = new Student("Serge", "Krasnikov", LocalDate.of(1998, 4, 19), 4);
    System.out.println("Student information:");
    System.out.println(student);
    System.out.println("Signature of this student");
    System.out.println(student.getSignature());
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

  public int getCourse() {
    return course;
  }

  public void setCourse(int course) {
    this.course = course;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  /**
   * Form signature of student.
   *
   * @return signature
   */
  public String getSignature() {
    class Signature {

      private String formSignature() {
        return "" + surname.charAt(0) + name.charAt(0)
            + (surname.length() > name.length()
            ? surname.charAt(surname.length() - 1) : name.charAt(name.length() - 1));
      }
    }

    return new Signature().formSignature();
  }

  @Override
  public String toString() {
    return "Student{"
        + "name='" + name + '\''
        + ", surname='" + surname + '\''
        + ", birthday=" + birthday
        + ", course=" + course
        + '}';
  }
}
