package com.homework2.builder;

/**
 * Realization user interface for application.
 */
public class UserInterface {

  private int width;
  private int length;
  private String backgroundColor;
  private String typeUserInterface;

  /**
   * Constructor for user interface with all parameters.
   *
   * @param width width of the window
   * @param length length of the window
   * @param backgroundColor background color
   * @param typeUserInterface type of interface (forms or web pages or console)
   */
  public UserInterface(int width, int length, String backgroundColor,
      String typeUserInterface) {
    this.width = width;
    this.length = length;
    this.backgroundColor = backgroundColor;
    this.typeUserInterface = typeUserInterface;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public String getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(String backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public String getTypeUserInterface() {
    return typeUserInterface;
  }

  public void setTypeUserInterface(String typeUserInterface) {
    this.typeUserInterface = typeUserInterface;
  }

  @Override
  public String toString() {
    return "UserInterface{"
        + "width=" + width
        + ", length=" + length
        + ", backgroundColor='" + backgroundColor + '\''
        + ", typeUserInterface='" + typeUserInterface + '\''
        + '}';
  }
}
