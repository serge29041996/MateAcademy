package com.homework6;

/**
 * Realization generic.
 */
public class Pair<V, E> {
  private V first;
  private E second;

  private Pair(V first, E second) {
    this.first = first;
    this.second = second;
  }

  public static <V,E> Pair of(V firstValue, E secondValue) {
    return new Pair<>(firstValue, secondValue);
  }

  public V getFirst() {
    return first;
  }

  public E getSecond() {
    return second;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Pair<?, ?> pair = (Pair<?, ?>) o;

    if (first != null ? !first.equals(pair.first) : pair.first != null) {
      return false;
    }
    return second != null ? second.equals(pair.second) : pair.second == null;
  }

  @Override
  public int hashCode() {
    int result = first != null ? first.hashCode() : 0;
    result = 31 * result + (second != null ? second.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Pair{" +
        "first=" + first +
        ", second=" + second +
        '}';
  }
}
