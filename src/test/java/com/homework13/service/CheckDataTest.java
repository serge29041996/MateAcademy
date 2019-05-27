package com.homework13.service;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for check data.
 */
public class CheckDataTest {
  private static final String TEST_VALUE = "test";
  @Test
  public void checkUserDataWithNullValues() {
    String result = CheckData.checkUserData(null, null);
    Assert.assertEquals("Вы не ввели логин.Вы не ввели пароль.", result);
  }

  @Test
  public void checkUserWithEmptyLogin() {
    String result = CheckData.checkUserData("", TEST_VALUE);
    Assert.assertEquals("Вы не ввели логин.", result);
  }

  @Test
  public void checkUserWithEmptyPassword() {
    String result = CheckData.checkUserData(TEST_VALUE, "");
    Assert.assertEquals("Вы не ввели пароль.", result);
  }

  @Test
  public void checkUserWithValidData() {
    String result = CheckData.checkUserData(TEST_VALUE, TEST_VALUE);
    Assert.assertEquals("", result);
  }

  @Test
  public void checkUserWithWrongMail() {
    String result = CheckData.checkUserData(TEST_VALUE, TEST_VALUE, TEST_VALUE);
    Assert.assertEquals("Введёна неправильная электронная почта.", result);
  }

  @Test
  public void checkGoodWithInvalidPrice() {
    String result = CheckData.checkGoodData(TEST_VALUE, TEST_VALUE, "9,99", "3");
    Assert.assertEquals("Вы ввели цену, которое не является числом.", result);
  }

  @Test
  public void checkGoodWithValidData() {
    String result = CheckData.checkGoodData(TEST_VALUE, TEST_VALUE, "9.99", "3");
    Assert.assertEquals("", result);
  }
}
