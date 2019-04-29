package com.homework14.dao;

/**
 * Exception, which throw when unable connect to database.
 */
public class ConnectionToDatabaseException extends RuntimeException {
  public ConnectionToDatabaseException(String s) {
    super(s);
  }
}
