package com.homework10;

/**
 * Class for saving information about complex number.
 */
public final class ComplexNumber {
  private final double re;
  private final double im;

  public ComplexNumber(double re, double im) {
    this.re = re;
    this.im = im;
  }

  public double getRe() {
    return re;
  }

  public double getIm() {
    return im;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    ComplexNumber castingNumber;

    if (o == null || !(o instanceof ComplexNumber)) {
      return false;
    } else {
      castingNumber = (ComplexNumber) o;
    }

    return this.re == castingNumber.re && this.im == castingNumber.im;
  }

  @Override
  public int hashCode() {
    int result = 0;
    result += Double.hashCode(this.re);
    result += Double.hashCode(this.im);
    return result;
  }

  @Override
  public String toString() {
    return "ComplexNumber{"
        + "re=" + re
        + ", im=" + im
        + '}';
  }
}
