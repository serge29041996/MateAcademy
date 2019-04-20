package com.homework2;

import java.time.LocalDate;

/**
 * Solution for task 2.4.
 */
public final class BankCard {

  private final String numberCard;
  private final String cvv2code;
  private final byte[] image;
  private final LocalDate expiryDate;

  public BankCard(String numberCard, String cvv2code, byte[] image, LocalDate expiryDate) {
    this.numberCard = numberCard;
    this.cvv2code = cvv2code;
    this.image = image;
    this.expiryDate = expiryDate;
  }

  public String getNumberCard() {
    return numberCard;
  }

  public String getCvv2code() {
    return cvv2code;
  }

  public byte[] getImage() {
    byte[] copyImage = new byte[image.length];
    System.arraycopy(image, 0, copyImage, 0, image.length);
    return copyImage;
  }

  public LocalDate getExpiryDate() {
    return LocalDate.of(expiryDate.getYear(), expiryDate.getMonth(), expiryDate.getDayOfMonth());
  }
}
