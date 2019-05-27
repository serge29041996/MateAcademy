package com;

import com.homework13.dao.UserDaoInMemoryImpl;
import com.homework13.model.User;
import com.homework14.dao.UserDaoJdbcImpl;
import com.homework16.model.Role;
import com.homework19.dao.UserDao;
import com.homework19.dao.UserDaoHibernateImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests for working with all type of dao.
 */
@RunWith(Parameterized.class)
public class UserDaoTest {
  private static final String TEST_VALUE = "test";
  private User testUser = new User(TEST_VALUE, TEST_VALUE, Role.USER.getValue(), TEST_VALUE);

  @Parameter(0)
  public UserDao userDao;

  @Parameters
  public static Collection<Object[]> initParameter() {
    Object[][] data = new Object[][]{{new UserDaoInMemoryImpl()},
        {new UserDaoJdbcImpl()},
        {new UserDaoHibernateImpl()}};
    return Arrays.asList(data);
  }

  @After
  public void clearDatabase() {
    userDao.deleteAll();
  }

  @Test
  public void testSaveNewUser() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    Assert.assertEquals(testUser, gettingUser);
    Assert.assertEquals(1, userDao.count());
  }

  @Test
  public void testSaveNewUserWithUTFCharset() {
    User user = new User("Сергей1", "123", Role.USER.getValue(), TEST_VALUE);
    userDao.save(user);
    User gettingUser = userDao.getUserByLogin("Сергей1").get();
    Assert.assertEquals(user, gettingUser);
    Assert.assertEquals(1, userDao.count());
  }

  @Test
  public void testGetByLoginExistUser() {
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    Assert.assertEquals(testUser, gettingUser);
  }

  @Test
  public void testGetByLoginNonExistUser() {
    Assert.assertEquals(Optional.empty(), userDao.getUserByLogin(TEST_VALUE));
  }

  @Test
  public void testGetByIdExistUser() {
    userDao.save(testUser);
    User gettingUserByLogin = userDao.getUserByLogin(TEST_VALUE).get();
    User gettingUserById = userDao.get(gettingUserByLogin.getId()).get();
    Assert.assertEquals(testUser, gettingUserById);
  }

  @Test
  public void testGetByIdNonExistUser() {
    Assert.assertEquals(Optional.empty(), userDao.get(100));
  }

  @Test
  public void testGetByMailExistUser() {
    userDao.save(testUser);
    User gettingUserByMail = userDao.getUserByMail(TEST_VALUE).get();
    User gettingUserById = userDao.get(gettingUserByMail.getId()).get();
    Assert.assertEquals(testUser, gettingUserById);
  }

  @Test
  public void testGetByMailNonExistUser() {
    Assert.assertEquals(Optional.empty(), userDao.getUserByMail(TEST_VALUE));
  }

  @Test
  public void testGetAllUsersFromEmptyTable() {
    Assert.assertEquals(new ArrayList<>(), userDao.getAll());
  }

  @Test
  public void testGetAllUsersFromNonEmptyTable() {
    List<User> users = new ArrayList<>();
    userDao.save(testUser);
    User gettingTestUser = userDao.getUserByLogin(testUser.getLogin()).get();
    users.add(gettingTestUser);
    User newUser = new User("1", "1", Role.USER.getValue(), "test@gmail.com");
    userDao.save(newUser);
    User gettingNewUser = userDao.getUserByLogin(newUser.getLogin()).get();
    users.add(gettingNewUser);
    Assert.assertEquals(users, userDao.getAll());
  }

  @Test
  public void testDeleteExistUser() {
    userDao.save(testUser);
    long numberUsers = userDao.count();
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    userDao.delete(gettingUser);
    Assert.assertEquals(numberUsers - 1, userDao.count());
  }

  @Test
  public void testUpdateExistUser() {
    String testLogin = "1";
    User user = new User(testLogin, "1", Role.USER.getValue(), TEST_VALUE);
    userDao.save(user);
    User gettingUser = userDao.getUserByLogin(testLogin).get();
    gettingUser.setPassword(TEST_VALUE);
    long numberUsers = userDao.count();
    userDao.update(gettingUser);
    User gettingUserAfterUpdate = userDao.getUserByLogin(testLogin).get();
    Assert.assertEquals(numberUsers, userDao.count());
    Assert.assertEquals(gettingUser, gettingUserAfterUpdate);
  }

  @Test
  public void testUpdateUserRole() {
    User userForUpdate = new User(TEST_VALUE, "1", Role.ADMIN.getValue(), TEST_VALUE);
    userDao.save(userForUpdate);
    User gettingUserForUpdate = userDao.getUserByLogin(TEST_VALUE).get();
    userDao.updateUserRole(gettingUserForUpdate.getId(), Role.USER.getValue());
    userForUpdate.setRole(Role.USER.getValue());
    User gettingUserAfterUpdateRole = userDao.getUserByLogin(TEST_VALUE).get();
    Assert.assertEquals(userForUpdate, gettingUserAfterUpdateRole);
  }
}
