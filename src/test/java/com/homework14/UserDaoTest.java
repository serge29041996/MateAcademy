package com.homework14;

import com.homework13.dao.DuplicateUserException;
import com.homework13.dao.NoSuchUserException;
import com.homework13.model.User;
import com.homework14.dao.UserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for working with my sql database .
 */
public class UserDaoTest {
  private static final String TEST_VALUE = "test";
  private static final User TEST_USER = new User(TEST_VALUE, TEST_VALUE);

  @Before
  public void clear() {
    UserDao.deleteAll();
  }

  @Test
  public void testSaveNewUser() throws DuplicateUserException, NoSuchUserException {
    UserDao.saveUser(TEST_USER);
    User gettingUser = UserDao.getUser(TEST_VALUE);
    Assert.assertEquals(TEST_USER, gettingUser);
    Assert.assertEquals(1, UserDao.count());
  }

  @Test
  public void testSaveNewUserWithUTFCharset() throws DuplicateUserException, NoSuchUserException {
    User user = new User("Сергей", "123");
    UserDao.saveUser(user);
    User gettingUser = UserDao.getUser("Сергей");
    Assert.assertEquals(user, gettingUser);
    Assert.assertEquals(1, UserDao.count());
  }

  @Test(expected = DuplicateUserException.class)
  public void testSaveExistUser() throws DuplicateUserException {
    UserDao.saveUser(TEST_USER);
    UserDao.saveUser(TEST_USER);
  }

  @Test
  public void testGetExistUser() throws DuplicateUserException, NoSuchUserException {
    UserDao.saveUser(TEST_USER);
    User gettingUser = UserDao.getUser(TEST_VALUE);
    Assert.assertEquals(TEST_USER, gettingUser);
  }

  @Test(expected = NoSuchUserException.class)
  public void testGetNonExistUser() throws NoSuchUserException {
    UserDao.getUser(TEST_VALUE);
  }
}
