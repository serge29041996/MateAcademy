package com.homework10;

/**
 * Interface for robot connection.
 */
public interface RobotConnection extends AutoCloseable {
  void moveRobotTo(int x, int y);

  @Override
  void close();
}
