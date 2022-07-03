package com.homework15;

import com.homework13.model.User;
import com.homework15.servlets.AdminPageServlet;
import com.homework16.model.Role;
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
  private User testUser = new User(TEST_VALUE, TEST_VALUE, Role.USER.getValue(), TEST_VALUE);
  private static final UserDao userDao = new UserDaoHibernateImpl();
  private HttpServletRequest request;
  private HttpServletResponse response;

  @Before
  public void init() throws IOException {
    request = Mockito.mock(HttpServletRequest.class);
    response = Mockito.mock(HttpServletResponse.class);
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(request.getRequestDispatcher("/admin_users_page.jsp"))
        .thenReturn(requestDispatcher);
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(session.getId()).thenReturn("test");
  }

  @After
  public void clear() {
    userDao.deleteAll();
  }

  @Test
  public void testDoGetWithNoUser() throws ServletException, IOException {
    new AdminPageServlet().doGet(request, response);
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("numberUsers", 0);
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("users", new ArrayList<>());
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/admin_users_page.jsp");
  }

  @Test
  public void testDoGetWithTwoUsers()
      throws ServletException, IOException {
    List<User> userList = new ArrayList<>();
    User newUser1 = new User(TEST_VALUE, TEST_VALUE, Role.USER.getValue(), TEST_VALUE);
    userDao.save(newUser1);
    User gettingFirstUser = userDao.getUserByLogin(TEST_VALUE).get();
    userList.add(gettingFirstUser);
    User newUser2 = new User("2", "2", Role.USER.getValue(), "2");
    userDao.save(newUser2);
    User gettingSecondUser = userDao.getUserByLogin("2").get();
    userList.add(gettingSecondUser);
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
      throws ServletException, IOException {
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);
    userDao.save(testUser);
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
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
  public void testDoPostForDeleteExistUser()
      throws ServletException, IOException {
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);
    userDao.save(testUser);
    long numberUsers = userDao.count();
    User gettingUser = userDao.getUserByLogin(TEST_VALUE).get();
    long id = gettingUser.getId();
    Mockito.when(request.getParameter("result")).thenReturn("delete_" + id);
    new AdminPageServlet().doPost(request, response);
    Assert.assertEquals(numberUsers - 1, userDao.count());
  }

  @Test
  public void testLowerRoleForUser()
      throws ServletException, IOException {
    testUser.setRole(Role.ADMIN.getValue());
    userDao.save(testUser);
    User gettingTestUser = userDao.getUserByLogin(testUser.getLogin()).get();
    long id = gettingTestUser.getId();
    Mockito.when(request.getParameter("result")).thenReturn("lowerRole_" + id);
    new AdminPageServlet().doPost(request, response);
    User gettingTestUserAfterUpdateRole = userDao.get(id).get();
    testUser.setRole(Role.USER.getValue());
    Assert.assertEquals(Role.USER.getValue(), gettingTestUserAfterUpdateRole.getRole());
    Assert.assertEquals(testUser, gettingTestUserAfterUpdateRole);
  }

  @Test
  public void testRaiseRoleUser()
      throws ServletException, IOException {
    userDao.save(testUser);
    User gettingTestUser = userDao.getUserByLogin(testUser.getLogin()).get();
    long id = gettingTestUser.getId();
    Mockito.when(request.getParameter("result")).thenReturn("raiseRole_" + id);
    new AdminPageServlet().doPost(request, response);
    User gettingTestUserAfterUpdateRole = userDao.get(id).get();
    testUser.setRole(Role.ADMIN.getValue());
    Assert.assertEquals(Role.ADMIN.getValue(), gettingTestUserAfterUpdateRole.getRole());
    Assert.assertEquals(testUser, gettingTestUserAfterUpdateRole);
    userDao.delete(gettingTestUserAfterUpdateRole);
  }
}
