package com.homework2.builder;

/**
 * Class for saving data about program.
 */
public class Application {

  private UserInterface userInterface;
  private boolean requireInternet;
  private String database;
  private BackEnd backEnd;

  public UserInterface getUserInterface() {
    return userInterface;
  }

  public void setUserInterface(UserInterface userInterface) {
    this.userInterface = userInterface;
  }

  public boolean isRequireInternet() {
    return requireInternet;
  }

  public void setRequireInternet(boolean requireInternet) {
    this.requireInternet = requireInternet;
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public BackEnd getBackEnd() {
    return backEnd;
  }

  public void setBackEnd(BackEnd backEnd) {
    this.backEnd = backEnd;
  }

  @Override
  public String toString() {
    return "Application{"
        + "userInterface=" + userInterface
        + ", requireInternet=" + requireInternet
        + ", database='" + database + '\''
        + ", backEnd=" + backEnd
        + '}';
  }
}
