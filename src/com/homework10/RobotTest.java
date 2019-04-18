package com.homework10;

/**
 * Solution for task: https://stepik.org/lesson/12773/step/7?unit=3121.
 */
public class RobotTest {
  /**
   * Move robot to position (toX, toY).
   * @param robotConnectionManager manager, which get access to robot which need to move
   * @param toX coordination x for new position
   * @param toY coordination y for new position
   */
  public static void moveRobot(RobotConnectionManager robotConnectionManager, int toX, int toY) {
    int numberSuccessOperations = 0;
    int numberFailedOperations = 0;
    while (numberFailedOperations < 3 && numberSuccessOperations == 0) {
      try (RobotConnection rc = robotConnectionManager.getConnection()) {
        rc.moveRobotTo(toX, toY);
        numberSuccessOperations++;
      } catch (RobotConnectionException e) {
        numberFailedOperations++;
      }
    }
    if (numberFailedOperations == 3 && numberSuccessOperations == 0) {
      throw new RobotConnectionException("Подключения и отдача команд роботу не удались.");
    }
  }
}
