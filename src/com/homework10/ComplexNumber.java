package com.homework10;

/**
 * Class for saving information about complex number.
 */
public final class ComplexNumber {
  private final double real;
  private final double imaginary;

  public ComplexNumber(double real, double imaginary) {
    this.real = real;
    this.imaginary = imaginary;
  }

  public double getReal() {
    return real;
  }

  public double getImaginary() {
    return imaginary;
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

    return this.real == castingNumber.real && this.imaginary == castingNumber.imaginary;
  }

  @Override
  public int hashCode() {
    int result = 0;
    result += Double.hashCode(this.real);
    result += Double.hashCode(this.imaginary);
    return result;
  }

  @Override
  public String toString() {
    return "ComplexNumber{"
        + "re=" + real
        + ", im=" + imaginary
        + '}';
  }
}
