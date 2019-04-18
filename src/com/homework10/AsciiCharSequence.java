package com.homework10;

import java.util.Arrays;

/**
 * Solution for task: https://stepik.org/lesson/14513/step/8?unit=4147.
 */
public class AsciiCharSequence implements CharSequence {
  private final byte[] sequence;

  public AsciiCharSequence(byte[] sequence) {
    this.sequence = Arrays.copyOf(sequence, sequence.length);
  }

  @Override
  public int length() {
    return sequence.length;
  }

  @Override
  public char charAt(int index) {
    for (int i = 0; i < sequence.length; i++) {
      if (i == index) {
        return (char)sequence[i];
      }
    }
    throw new IndexOutOfBoundsException();
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    if ((start < 0 && end < 0) || end > sequence.length || start > end) {
      throw new IndexOutOfBoundsException();
    }
    byte[] formatSubSequence = new byte[end - start];
    for (int i = start, j = 0; i < end; i++, j++) {
      formatSubSequence[j] = sequence[i];
    }

    return new AsciiCharSequence(formatSubSequence);
  }

  @Override
  public String toString() {
    StringBuilder formatString = new StringBuilder();
    for (int i = 0; i < sequence.length; i++) {
      formatString.append((char)sequence[i]);
    }
    return formatString.toString();
  }
}
