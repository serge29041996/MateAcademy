package com.homework14;

import com.homework13.dao.DuplicateUserException;
import com.homework13.dao.NoSuchUserException;
import com.homework13.model.User;
import com.homework14.dao.UserDao;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for working with my sql database .
 */
public class UserDaoTest {
  private static final String TEST_VALUE = "test";
  private static final User TEST_USER = new User(TEST_VALUE, TEST_VALUE);
  private final UserDao userDao = new UserDao();

  @Before
  public void clear() {
    userDao.deleteAll();
  }

  @Test
  public void testSaveNewUser() throws DuplicateUserException, NoSuchUserException {
    userDao.saveUser(TEST_USER);
    User gettingUser = userDao.getUser(TEST_VALUE);
    Assert.assertEquals(TEST_USER, gettingUser);
    Assert.assertEquals(1, userDao.count());
  }

  @Test
  public void testSaveNewUserWithUTFCharset() throws DuplicateUserException, NoSuchUserException {
    User user = new User("Сергей", "123");
    userDao.saveUser(user);
    User gettingUser = userDao.getUser("Сергей");
    Assert.assertEquals(user, gettingUser);
    Assert.assertEquals(1, userDao.count());
  }

  @Test(expected = DuplicateUserException.class)
  public void testSaveExistUser() throws DuplicateUserException {
    userDao.saveUser(TEST_USER);
    userDao.saveUser(TEST_USER);
  }

  @Test
  public void testGetByLoginExistUser() throws DuplicateUserException, NoSuchUserException {
    userDao.saveUser(TEST_USER);
    User gettingUser = userDao.getUser(TEST_VALUE);
    Assert.assertEquals(TEST_USER, gettingUser);
  }

  @Test(expected = NoSuchUserException.class)
  public void testGetByLoginNonExistUser() throws NoSuchUserException {
    userDao.getUser(TEST_VALUE);
  }

  @Test
  public void testGetByIdExistUser() throws DuplicateUserException, NoSuchUserException {
    userDao.saveUser(TEST_USER);
    User gettingUserByLogin = userDao.getUser(TEST_VALUE);
    User gettingUserById = userDao.getUser(gettingUserByLogin.getId());
    Assert.assertEquals(TEST_USER, gettingUserById);
  }

  @Test(expected = NoSuchUserException.class)
  public void testGetByIdNonExistUser() throws DuplicateUserException, NoSuchUserException {
    userDao.getUser(100);
  }

  @Test
  public void testGetAllUsersFromEmptyTable() {
    List<User> users = new ArrayList<>();
    List<User> gettingUsers = userDao.getAllUsers();
    Assert.assertEquals(users, gettingUsers);
  }

  @Test
  public void testGetAllUsersFromNonEmptyTable() throws DuplicateUserException {
    List<User> users = new ArrayList<>();
    userDao.saveUser(TEST_USER);
    users.add(TEST_USER);
    User newUser = new User("1", "1");
    userDao.saveUser(newUser);
    users.add(newUser);
    List<User> gettingUsers = userDao.getAllUsers();
    Assert.assertEquals(users, gettingUsers);
  }

  @Test
  public void testDeleteExistUser() throws DuplicateUserException, NoSuchUserException {
    userDao.saveUser(TEST_USER);
    int numberUsers = userDao.count();
    User gettingUser = userDao.getUser(TEST_VALUE);
    userDao.deleteUser(gettingUser.getId());
    Assert.assertEquals(numberUsers - 1, userDao.count());
  }

  @Test
  public void testUpdateExistUser() throws DuplicateUserException, NoSuchUserException {
    String testLogin = "1";
    User userForUpdate = new User(testLogin, "1");
    userDao.saveUser(userForUpdate);
    User gettingUser = userDao.getUser(testLogin);
    userForUpdate.setId(gettingUser.getId());
    userForUpdate.setPassword(TEST_VALUE);
    int numberUsers = userDao.count();
    userDao.updateUser(userForUpdate);
    User gettingUserAfterUpdate = userDao.getUser(testLogin);
    Assert.assertEquals(numberUsers, userDao.count());
    Assert.assertEquals(userForUpdate, gettingUserAfterUpdate);
  }
}
