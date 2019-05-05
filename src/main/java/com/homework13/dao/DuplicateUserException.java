package com.homework13.dao;

/**
 * Exception which throw when we have in database user with same data.
 */
public class DuplicateUserException extends Exception {
  public DuplicateUserException() {
  }

  public DuplicateUserException(String message) {
    super(message);
  }
}
