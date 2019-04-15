package com.homework10;

/**
 * Exception which throw when has errors with work of robot connection or connection manager.
 */
public class RobotConnectionException extends RuntimeException {

  public RobotConnectionException(String message) {
    super(message);

  }

  public RobotConnectionException(String message, Throwable cause) {
    super(message, cause);
  }
}
