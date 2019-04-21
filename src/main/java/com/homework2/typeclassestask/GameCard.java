package com.homework2.typeclassestask;

import java.util.Arrays;

/**
 * Task with using nested class.
 */
public class GameCard {

  protected String name;
  protected String type;
  protected String affiliation;
  protected byte[] image;

  public static void main(String[] args) {
    byte[] imageCard = new byte[]{1, -120};
    GameCard.HearthstoneGameCard hearthstoneCard =
        new GameCard.HearthstoneGameCard("Leeroy", "neutral", imageCard,
            "Charge", 5, 6, 2);
    System.out.println("Information about hearthstone card:");
    System.out.println(hearthstoneCard);
    imageCard = new byte[]{1, 4, 81, -123};
    GameCard.GwentGameCard gwentCard = new GameCard.GwentGameCard("Geralt: Witcher",
        "neutral", imageCard, "Destroy unit with number point bigger than 8",
        3);
    System.out.println("Information about gwent card:");
    System.out.println(gwentCard);
  }


  public GameCard(String name, String type, String affiliation, byte[] image) {
    this.name = name;
    this.type = type;
    this.affiliation = affiliation;
    this.image = image;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public String getAffiliation() {
    return affiliation;
  }

  public void setAffiliation(String affiliation) {
    this.affiliation = affiliation;
  }

  @Override
  public String toString() {
    return "GameCard{"
        + "name='" + name + '\''
        + ", type='" + type + '\''
        + ", affiliation='" + affiliation + '\''
        + ", image=" + Arrays.toString(image)
        + '}';
  }

  public static class HearthstoneGameCard extends GameCard {

    private String description;
    private int manaCost;
    private int numberHealth;
    private int numberAttack;

    public HearthstoneGameCard(String name, String affiliation, byte[] image,
        String description, int manaCost, int numberHealth, int numberAttack) {
      super(name, "virtual", affiliation, image);
      this.description = description;
      this.manaCost = manaCost;
      this.numberHealth = numberHealth;
      this.numberAttack = numberAttack;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public int getManaCost() {
      return manaCost;
    }

    public void setManaCost(int manaCost) {
      this.manaCost = manaCost;
    }

    public int getNumberHealth() {
      return numberHealth;
    }

    public void setNumberHealth(int numberHealth) {
      this.numberHealth = numberHealth;
    }

    public int getNumberAttack() {
      return numberAttack;
    }

    public void setNumberAttack(int numberAttack) {
      this.numberAttack = numberAttack;
    }

    @Override
    public String toString() {
      return "HearthstoneGameCard{"
          + "name='" + name + '\''
          + ", type='" + type + '\''
          + ", affiliation='" + affiliation + '\''
          + ", description='" + description + '\''
          + ", manaCost=" + manaCost
          + ", numberHealth=" + numberHealth
          + ", numberAttack=" + numberAttack
          + '}';
    }
  }

  public static class GwentGameCard extends GameCard {

    private String description;
    private int numberPoint;

    public GwentGameCard(String name, String affiliation, byte[] image,
        String description, int numberPoint) {
      super(name, "virtual", affiliation, image);
      this.description = description;
      this.numberPoint = numberPoint;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public int getNumberPoint() {
      return numberPoint;
    }

    public void setNumberPoint(int numberPoint) {
      this.numberPoint = numberPoint;
    }

    @Override
    public String toString() {
      return "GwentGameCard{"
          + "name='" + name + '\''
          + ", type='" + type + '\''
          + ", affiliation='" + affiliation + '\''
          + ", description='" + description + '\''
          + ", numberPoint=" + numberPoint
          + '}';
    }
  }
}
