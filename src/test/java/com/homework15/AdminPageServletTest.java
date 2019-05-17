package com.homework15;

import com.homework13.dao.DuplicateUserException;
import com.homework13.dao.NoSuchUserException;
import com.homework13.model.User;
import com.homework15.servlets.AdminPageServlet;
import com.homework16.model.Role;
import com.homework18.utils.HashUtils;
import com.homework19.dao.UserDao;
import com.homework19.dao.UserDaoHibernateImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * Tests for admin page servlet.
 */
public class AdminPageServletTest {
  private static final String TEST_VALUE = "1";
  private User testUser;
  private static final UserDao USER_DAO = new UserDaoHibernateImpl();
  private HttpServletRequest request;
  private HttpServletResponse response;

  @Before
  public void init() throws IOException {
    request = Mockito.mock(HttpServletRequest.class);
    response = Mockito.mock(HttpServletResponse.class);
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(request.getRequestDispatcher("/admin_users_page.jsp")).thenReturn(requestDispatcher);
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(session.getId()).thenReturn("test");
    testUser = new User(1, TEST_VALUE, TEST_VALUE, Role.USER.getValue(), TEST_VALUE);
  }

  @After
  public void clear() {
    USER_DAO.deleteAll();
  }

  @Test
  public void testDoGetWithNoUser() throws ServletException, IOException, NoSuchUserException {
    List<User> users = new ArrayList<>();
    users.add(USER_DAO.getUser("Сергей"));
    new AdminPageServlet().doGet(request, response);
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("numberUsers", 1);
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("users", users);
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/admin_users_page.jsp");
  }

  @Test
  public void testDoGetWithTwoUsers()
      throws ServletException, IOException, DuplicateUserException, NoSuchUserException {
    List<User> userList = new ArrayList<>();
    userList.add(USER_DAO.getUser("Сергей"));
    User newUser1 = new User(0, TEST_VALUE, TEST_VALUE, Role.USER.getValue(), TEST_VALUE);
    USER_DAO.saveUser(newUser1);
    User gettingNewUser1 = USER_DAO.getUser(TEST_VALUE);
    newUser1.setPassword(HashUtils.getSha512SecurePassword(TEST_VALUE, gettingNewUser1.getSalt()));
    newUser1.setSalt(gettingNewUser1.getSalt());
    userList.add(newUser1);
    User newUser2 = new User(1,"2", "2", Role.USER.getValue(), "2");
    USER_DAO.saveUser(newUser2);
    User gettingNewUser2 = USER_DAO.getUser("2");
    newUser2.setPassword(HashUtils.getSha512SecurePassword("2", gettingNewUser2.getSalt()));
    newUser2.setSalt(gettingNewUser2.getSalt());
    userList.add(newUser2);
    new AdminPageServlet().doGet(request, response);
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("numberUsers", userList.size());
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("users", userList);
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/admin_users_page.jsp");
  }

  @Test
  public void testDoPostForAddUser() throws ServletException, IOException {
    Mockito.when(request.getParameter("result")).thenReturn("addUser");
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);
    new AdminPageServlet().doPost(request, response);
    Mockito.verify(session, Mockito.times(1))
        .setAttribute("action", "add");
    Mockito.verify(response, Mockito.times(1))
        .sendRedirect("/admin_page/user_action");
  }

  @Test
  public void testDoPostForUpdateExistUser()
      throws ServletException, IOException, DuplicateUserException, NoSuchUserException {
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);
    USER_DAO.saveUser(testUser);
    User gettingUser = USER_DAO.getUser(TEST_VALUE);
    long id = gettingUser.getId();
    Mockito.when(request.getParameter("result")).thenReturn("update_" + id);
    new AdminPageServlet().doPost(request, response);
    Mockito.verify(session, Mockito.times(1))
        .setAttribute("action", "update");
    Mockito.verify(session, Mockito.times(1))
        .setAttribute("user", gettingUser);
    Mockito.verify(response, Mockito.times(1))
        .sendRedirect("/admin_page/user_action");
  }

  @Test
  public void testDoPostForUpdateNonUser()
      throws ServletException, IOException, DuplicateUserException, NoSuchUserException {
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(request.getParameter("result")).thenReturn("update_" + 100);
    new AdminPageServlet().doPost(request, response);
    Mockito.verify(session, Mockito.times(1))
        .setAttribute("action", "add");
    Mockito.verify(response, Mockito.times(1))
        .sendRedirect("/admin_page/user_action");
  }

  @Test
  public void testDoPostForDeleteExistUser()
      throws ServletException, IOException, DuplicateUserException, NoSuchUserException {
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);
    USER_DAO.saveUser(testUser);
    long numberUsers = USER_DAO.count();
    User gettingUser = USER_DAO.getUser(TEST_VALUE);
    long id = gettingUser.getId();
    Mockito.when(request.getParameter("result")).thenReturn("delete_" + id);
    new AdminPageServlet().doPost(request, response);
    Assert.assertEquals(numberUsers - 1, USER_DAO.count());
  }

  @Test
  public void testLowerRoleForUser()
      throws DuplicateUserException, NoSuchUserException, ServletException, IOException {
    testUser.setRole(Role.ADMIN.getValue());
    USER_DAO.saveUser(testUser);
    User gettingTestUser = USER_DAO.getUser(testUser.getLogin());
    testUser.setPassword(HashUtils.getSha512SecurePassword(testUser.getPassword(), gettingTestUser.getSalt()));
    testUser.setSalt(gettingTestUser.getSalt());
    long id = gettingTestUser.getId();
    Mockito.when(request.getParameter("result")).thenReturn("lowerRole_" + id);
    new AdminPageServlet().doPost(request, response);
    User gettingTestUserAfterUpdateRole = USER_DAO.getUser(id);
    testUser.setRole(Role.USER.getValue());
    Assert.assertEquals(Role.USER.getValue(), gettingTestUserAfterUpdateRole.getRole());
    Assert.assertEquals(testUser, gettingTestUserAfterUpdateRole);
  }

  @Test
  public void testRaiseRoleUser()
      throws DuplicateUserException, NoSuchUserException, ServletException, IOException {
    USER_DAO.saveUser(testUser);
    User gettingTestUser = USER_DAO.getUser(testUser.getLogin());
    testUser.setPassword(HashUtils.getSha512SecurePassword(testUser.getPassword(), gettingTestUser.getSalt()));
    testUser.setSalt(gettingTestUser.getSalt());
    long id = gettingTestUser.getId();
    Mockito.when(request.getParameter("result")).thenReturn("raiseRole_" + id);
    new AdminPageServlet().doPost(request, response);
    User gettingTestUserAfterUpdateRole = USER_DAO.getUser(id);
    testUser.setRole(Role.ADMIN.getValue());
    Assert.assertEquals(Role.ADMIN.getValue(), gettingTestUserAfterUpdateRole.getRole());
    Assert.assertEquals(testUser, gettingTestUserAfterUpdateRole);
    USER_DAO.deleteUser(id);
  }
}
