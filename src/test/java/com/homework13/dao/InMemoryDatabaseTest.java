package com.homework13.dao;

import com.homework13.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for in-memory database.
 */
public class InMemoryDatabaseTest {
  private static final String TEST_VALUE = "test";
  private static final User TEST_USER = new User(TEST_VALUE, TEST_VALUE);

  @After
  public void clearDB() {
    InMemoryDatabase.deleteAll();
  }

  @Test
  public void saveNewUserToDB() throws DuplicateUserException, NoSuchUserException {
    InMemoryDatabase.saveUser(TEST_USER);
    User gettingUser = InMemoryDatabase.getUser(TEST_VALUE);
    Assert.assertEquals(TEST_USER, gettingUser);
    Assert.assertEquals(1, InMemoryDatabase.count());
  }

  @Test(expected = DuplicateUserException.class)
  public void saveExistUserToDB() throws DuplicateUserException {
    InMemoryDatabase.saveUser(TEST_USER);
    InMemoryDatabase.saveUser(TEST_USER);
  }

  @Test(expected = NoSuchUserException.class)
  public void getNonExistUser() throws NoSuchUserException {
    InMemoryDatabase.getUser(TEST_USER.getLogin());
  }

  @Test
  public void getExistUser() throws DuplicateUserException, NoSuchUserException {
    InMemoryDatabase.saveUser(TEST_USER);
    User gettingUser = InMemoryDatabase.getUser(TEST_VALUE);
    Assert.assertEquals(TEST_USER, gettingUser);
  }
}
