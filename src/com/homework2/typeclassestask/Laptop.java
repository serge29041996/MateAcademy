package com.homework2.typeclassestask;

/**
 * Task with using inner class.
 */
public class Laptop {

  private String name;
  private Body body;
  private String battery;

  public static void main(String[] args) {
    Laptop laptop = new Laptop("Lenovo A3", "Lenovo L85");
    Body laptopBody = laptop.getBody();
    System.out.println("Body of laptop:");
    System.out.println(laptopBody);
  }

  public Laptop(String name, String battery) {
    this.name = name;
    this.battery = battery;
    this.body = new Body("black", "metal", 300, 200, 28);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Body getBody() {
    return body;
  }

  public void setBody(Body body) {
    this.body = body;
  }

  public String getBattery() {
    return battery;
  }

  public void setBattery(String battery) {
    this.battery = battery;
  }

  public class Body {

    private String color;
    private String material;
    private int length;
    private int width;
    private int height;

    public Body(String color, String material, int length, int width, int height) {
      this.color = color;
      this.material = material;
      this.width = width;
      this.length = length;
      this.height = height;
    }

    public String getColor() {
      return color;
    }

    public void setColor(String color) {
      this.color = color;
    }

    public String getMaterial() {
      return material;
    }

    public void setMaterial(String material) {
      this.material = material;
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

    public int getHeight() {
      return height;
    }

    public void setHeight(int height) {
      this.height = height;
    }

    @Override
    public String toString() {
      return "Body{" +
          "color='" + color + '\'' +
          ", material='" + material + '\'' +
          ", width=" + width +
          ", length=" + length +
          ", height=" + height +
          '}';
    }
  }
}
