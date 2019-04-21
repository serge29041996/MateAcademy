package com.homework6;

/**
 * Interface for elements, which work with consumer.
 */
public interface Sendable<T> {
  String getKey();

  T getValue();
}
