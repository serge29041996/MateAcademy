package com.homework14;

import com.homework13.dao.DuplicateUserException;
import com.homework13.dao.NoSuchUserException;
import com.homework13.model.User;
import com.homework14.dao.UserDao;
import com.homework16.model.Role;
import com.homework18.utils.HashUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for working with my sql database .
 */
public class UserDaoTest {
  private static final String TEST_VALUE = "test";
  private static User testUser;
  private final UserDao USER_DAO = new UserDao();

  @Before
  public void init() {
    testUser = new User(0, TEST_VALUE, TEST_VALUE, Role.USER, TEST_VALUE);
  }

  @After
  public void clearDatabase() {
    USER_DAO.deleteAll();
  }

  @Test
  public void testSaveNewUser() throws DuplicateUserException, NoSuchUserException {
    USER_DAO.saveUser(testUser);
    User gettingUser = USER_DAO.getUser(TEST_VALUE);
    testUser.setPassword(HashUtils.getSha512SecurePassword(testUser.getPassword(), gettingUser.getSalt()));
    testUser.setSalt(gettingUser.getSalt());
    Assert.assertEquals(testUser, gettingUser);
    Assert.assertEquals(2, USER_DAO.count());
  }

  @Test
  public void testSaveNewUserWithUTFCharset() throws DuplicateUserException, NoSuchUserException {
    User user = new User(0, "Сергей1", "123", Role.USER, TEST_VALUE);
    USER_DAO.saveUser(user);
    User gettingUser = USER_DAO.getUser("Сергей1");
    user.setPassword(HashUtils.getSha512SecurePassword(user.getPassword(), gettingUser.getSalt()));
    user.setSalt(gettingUser.getSalt());
    Assert.assertEquals(user, gettingUser);
    Assert.assertEquals(2, USER_DAO.count());
  }

  @Test(expected = DuplicateUserException.class)
  public void testSaveExistUser() throws DuplicateUserException {
    USER_DAO.saveUser(testUser);
    USER_DAO.saveUser(testUser);
  }

  @Test
  public void testGetByLoginExistUser() throws DuplicateUserException, NoSuchUserException {
    USER_DAO.saveUser(testUser);
    User gettingUser = USER_DAO.getUser(TEST_VALUE);
    testUser.setPassword(HashUtils.getSha512SecurePassword(testUser.getPassword(), gettingUser.getSalt()));
    testUser.setSalt(gettingUser.getSalt());
    Assert.assertEquals(testUser, gettingUser);
  }

  @Test(expected = NoSuchUserException.class)
  public void testGetByLoginNonExistUser() throws NoSuchUserException {
    USER_DAO.getUser(TEST_VALUE);
  }

  @Test
  public void testGetByIdExistUser() throws DuplicateUserException, NoSuchUserException {
    USER_DAO.saveUser(testUser);
    User gettingUserByLogin = USER_DAO.getUser(TEST_VALUE);
    testUser.setPassword(HashUtils.getSha512SecurePassword(testUser.getPassword(), gettingUserByLogin.getSalt()));
    testUser.setSalt(gettingUserByLogin.getSalt());
    User gettingUserById = USER_DAO.getUser(gettingUserByLogin.getId());
    Assert.assertEquals(testUser, gettingUserById);
  }

  @Test(expected = NoSuchUserException.class)
  public void testGetByIdNonExistUser() throws DuplicateUserException, NoSuchUserException {
    USER_DAO.getUser(100);
  }

  @Test
  public void testGetAllUsersFromEmptyTable() throws NoSuchUserException {
    List<User> users = new ArrayList<>();
    users.add(USER_DAO.getUser("Сергей"));
    List<User> gettingUsers = USER_DAO.getAllUsers();
    Assert.assertEquals(users, gettingUsers);
  }

  @Test
  public void testGetAllUsersFromNonEmptyTable() throws DuplicateUserException, NoSuchUserException {
    List<User> users = new ArrayList<>();
    users.add(USER_DAO.getUser("Сергей"));
    USER_DAO.saveUser(testUser);
    User gettingTestUser = USER_DAO.getUser(testUser.getLogin());
    testUser.setPassword(HashUtils.getSha512SecurePassword(testUser.getPassword(), gettingTestUser.getSalt()));
    testUser.setSalt(gettingTestUser.getSalt());
    users.add(testUser);
    User newUser = new User(1,"1", "1", Role.USER, "test@gmail.com");
    USER_DAO.saveUser(newUser);
    User gettingNewUser = USER_DAO.getUser(newUser.getLogin());
    newUser.setPassword(HashUtils.getSha512SecurePassword(newUser.getPassword(), gettingNewUser.getSalt()));
    newUser.setSalt(gettingNewUser.getSalt());
    users.add(newUser);
    List<User> gettingUsers = USER_DAO.getAllUsers();
    Assert.assertEquals(users, gettingUsers);
  }

  @Test
  public void testDeleteExistUser() throws DuplicateUserException, NoSuchUserException {
    USER_DAO.saveUser(testUser);
    int numberUsers = USER_DAO.count();
    User gettingUser = USER_DAO.getUser(TEST_VALUE);
    USER_DAO.deleteUser(gettingUser.getId());
    Assert.assertEquals(numberUsers - 1, USER_DAO.count());
  }

  @Test
  public void testUpdateExistUser() throws DuplicateUserException, NoSuchUserException {
    String testLogin = "1";
    User userForUpdate = new User(0, testLogin, "1", Role.USER, TEST_VALUE);
    USER_DAO.saveUser(userForUpdate);
    User gettingUser = USER_DAO.getUser(testLogin);
    userForUpdate.setId(gettingUser.getId());
    userForUpdate.setPassword(TEST_VALUE);
    userForUpdate.setSalt(gettingUser.getSalt());
    int numberUsers = USER_DAO.count();
    USER_DAO.updateUser(userForUpdate);
    User gettingUserAfterUpdate = USER_DAO.getUser(testLogin);
    Assert.assertEquals(numberUsers, USER_DAO.count());
    Assert.assertEquals(userForUpdate, gettingUserAfterUpdate);
  }

  @Test(expected = DuplicateUserException.class)
  public void testUpdateExistUserWithExistLogin() throws DuplicateUserException, NoSuchUserException {
    String testLogin = "1";
    User userForUpdate = new User(0, testLogin, "1", Role.USER, TEST_VALUE);
    USER_DAO.saveUser(userForUpdate);
    User secondUser = new User(1, "2", "2", Role.USER, "test@gmail.com");
    USER_DAO.saveUser(secondUser);
    User gettingUser = USER_DAO.getUser(testLogin);
    userForUpdate.setId(gettingUser.getId());
    userForUpdate.setLogin("2");
    USER_DAO.updateUser(userForUpdate);
  }

  @Test
  public void testUpdateUserRole() throws DuplicateUserException, NoSuchUserException {
    User userForUpdate = new User(0, TEST_VALUE, "1", Role.USER, TEST_VALUE);
    USER_DAO.saveUser(userForUpdate);
    User gettingUserForUpdate = USER_DAO.getUser(TEST_VALUE);
    userForUpdate.setPassword(HashUtils.getSha512SecurePassword(userForUpdate.getPassword(), gettingUserForUpdate.getSalt()));
    userForUpdate.setSalt(gettingUserForUpdate.getSalt());
    USER_DAO.updateUserRole(gettingUserForUpdate.getId(), Role.ADMIN.getValue());
    userForUpdate.setRole(Role.ADMIN);
    User gettingUserAfterUpdateRole = USER_DAO.getUser(TEST_VALUE);
    Assert.assertEquals(userForUpdate, gettingUserAfterUpdateRole);
  }
}
