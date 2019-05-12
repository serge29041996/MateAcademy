package com.homework15;

import com.homework13.dao.DuplicateUserException;
import com.homework13.dao.NoSuchUserException;
import com.homework13.model.User;
import com.homework14.dao.UserDao;
import com.homework15.servlets.UserActionServlet;
import com.homework16.model.Role;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * Tests for user action servlet.
 */
public class UserActionServletTest {
  private static final String TEST_VALUE = "1";
  private static User testUser;
  private final UserDao userDao = new UserDao();
  private HttpServletRequest request;
  private HttpServletResponse response;

  @Before
  public void init() throws IOException {
    request = Mockito.mock(HttpServletRequest.class);
    response = Mockito.mock(HttpServletResponse.class);
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(request.getRequestDispatcher("/user_form.jsp")).thenReturn(requestDispatcher);
    testUser = new User(TEST_VALUE, TEST_VALUE, "test@test.com", "user");
  }

  @After
  public void clear() {
    userDao.deleteAll();
  }

  @Test
  public void testDoGetForAddingUser() throws ServletException, IOException {
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(session.getAttribute("action")).thenReturn("add");
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(request.getAttribute("login")).thenReturn(null);
    Mockito.when(request.getAttribute("password")).thenReturn(null);
    Mockito.when(request.getAttribute("result")).thenReturn(null);
    new UserActionServlet().doGet(request, response);
    Mockito.verify(session, Mockito.times(1))
        .getAttribute("action");
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/user_form.jsp");
  }

  @Test
  public void testDoGetForUpdateUser() throws ServletException, IOException {
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(session.getAttribute("action")).thenReturn("update");
    Mockito.when(request.getSession()).thenReturn(session);
    User newUser = new User(1, TEST_VALUE, TEST_VALUE, Role.USER, TEST_VALUE);
    Mockito.when(session.getAttribute("user")).thenReturn(newUser);
    Mockito.when(request.getAttribute("login")).thenReturn(null);
    Mockito.when(request.getAttribute("password")).thenReturn(null);
    Mockito.when(request.getAttribute("mail")).thenReturn(null);
    Mockito.when(request.getAttribute("role")).thenReturn(null);
    Mockito.when(request.getAttribute("result")).thenReturn(null);
    new UserActionServlet().doGet(request, response);
    Mockito.verify(session, Mockito.times(1))
        .getAttribute("action");
    Mockito.verify(session, Mockito.times(1))
        .setAttribute("id", newUser.getId());
    Mockito.verify(request,
        Mockito.times(1)).setAttribute("login", newUser.getLogin());
    Mockito.verify(request,
        Mockito.times(1)).setAttribute("password", newUser.getPassword());
    Mockito.verify(request,
        Mockito.times(1)).setAttribute("mail", newUser.getMail());
    Mockito.verify(request,
        Mockito.times(1)).setAttribute("role", newUser.getRole().getValue());
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/user_form.jsp");
  }

  @Test
  public void testDoPostForReturningToAdminPage() throws ServletException, IOException {
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getParameter("option")).thenReturn("return");
    Mockito.when(request.getParameter("login")).thenReturn(TEST_VALUE);
    Mockito.when(request.getParameter("password")).thenReturn(TEST_VALUE);
    Mockito.when(request.getSession()).thenReturn(session);
    new UserActionServlet().doPost(request, response);
    Mockito.verify(response, Mockito.times(1))
        .sendRedirect("/admin_page/users");
  }

  @Test
  public void testDoPostForAddNewUser() throws ServletException, IOException {
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getParameter("option")).thenReturn("add");
    Mockito.when(request.getParameter("login")).thenReturn(TEST_VALUE);
    Mockito.when(request.getParameter("password")).thenReturn(TEST_VALUE);
    Mockito.when(request.getParameter("mail")).thenReturn("test@gmail.com");
    Mockito.when(request.getParameter("role")).thenReturn("user");
    Mockito.when(request.getSession()).thenReturn(session);
    new UserActionServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "Информация про пользователя успешно добавлена.");
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/user_form.jsp");
  }

  @Test
  public void testDoPostForAddExistUser() throws ServletException, IOException, DuplicateUserException {
    userDao.saveUser(testUser);
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getParameter("option")).thenReturn("add");
    Mockito.when(request.getParameter("login")).thenReturn(TEST_VALUE);
    Mockito.when(request.getParameter("password")).thenReturn(TEST_VALUE);
    Mockito.when(request.getParameter("mail")).thenReturn("test@gmail.com");
    Mockito.when(request.getParameter("role")).thenReturn("user");
    Mockito.when(request.getSession()).thenReturn(session);
    new UserActionServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "Пользователь с логином 1 уже существует.");
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/user_form.jsp");
  }

  @Test
  public void testDoPostForAddUserWithoutPassword() throws ServletException, IOException,
      DuplicateUserException {
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getParameter("option")).thenReturn("add");
    Mockito.when(request.getParameter("login")).thenReturn(TEST_VALUE);
    Mockito.when(request.getParameter("password")).thenReturn(null);
    Mockito.when(request.getParameter("mail")).thenReturn("test@gmail.com");
    Mockito.when(request.getParameter("role")).thenReturn("user");
    Mockito.when(request.getSession()).thenReturn(session);
    new UserActionServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "Вы не ввели пароль.");
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/user_form.jsp");
  }

  @Test
  public void testDoPostForUpdateUserWithoutChange() throws ServletException, IOException,
      DuplicateUserException, NoSuchUserException {
    userDao.saveUser(testUser);
    User gettingUser = userDao.getUser(TEST_VALUE);
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getParameter("option")).thenReturn("update");
    Mockito.when(session.getAttribute("id")).thenReturn(gettingUser.getId());
    Mockito.when(session.getAttribute("user")).thenReturn(gettingUser);
    Mockito.when(request.getParameter("login")).thenReturn(TEST_VALUE);
    Mockito.when(request.getParameter("password")).thenReturn(TEST_VALUE);
    Mockito.when(request.getParameter("mail")).thenReturn("test@test.com");
    Mockito.when(request.getParameter("role")).thenReturn("user");
    Mockito.when(request.getSession()).thenReturn(session);
    new UserActionServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "Данные про пользователя обновлены.");
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/user_form.jsp");
  }

  @Test
  public void testDoPostForUpdateUserWithoutPassword() throws ServletException, IOException,
      DuplicateUserException, NoSuchUserException {
    userDao.saveUser(testUser);
    User gettingUser = userDao.getUser(TEST_VALUE);
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getParameter("option")).thenReturn("update");
    Mockito.when(session.getAttribute("id")).thenReturn(gettingUser.getId());
    Mockito.when(session.getAttribute("user")).thenReturn(gettingUser);
    Mockito.when(request.getParameter("login")).thenReturn(TEST_VALUE);
    Mockito.when(request.getParameter("password")).thenReturn(null);
    Mockito.when(request.getParameter("mail")).thenReturn("test@gmail.com");
    Mockito.when(request.getParameter("role")).thenReturn("user");
    Mockito.when(request.getSession()).thenReturn(session);
    new UserActionServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "Вы не ввели пароль.");
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/user_form.jsp");
  }

  @Test
  public void testDoPostForUpdateUserWithNewValues() throws ServletException, IOException,
      DuplicateUserException, NoSuchUserException {
    userDao.saveUser(testUser);
    User gettingUser = userDao.getUser(TEST_VALUE);
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getParameter("option")).thenReturn("update");
    Mockito.when(session.getAttribute("id")).thenReturn(gettingUser.getId());
    Mockito.when(session.getAttribute("user")).thenReturn(gettingUser);
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(request.getParameter("login")).thenReturn("2");
    Mockito.when(request.getParameter("password")).thenReturn("2");
    Mockito.when(request.getParameter("mail")).thenReturn("test@gmail.com");
    Mockito.when(request.getParameter("role")).thenReturn("user");
    new UserActionServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "Данные про пользователя обновлены.");
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/user_form.jsp");
  }

  @Test
  public void testDoPostForUpdateUserWithExistLogin() throws ServletException, IOException,
      DuplicateUserException, NoSuchUserException {
    String testValue = "test";
    userDao.saveUser(testUser);
    User gettingUser = userDao.getUser(testUser.getLogin());
    User newUser = new User(testValue, testValue, "test@gmail.com", "user");
    userDao.saveUser(newUser);
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getParameter("option")).thenReturn("update");
    Mockito.when(session.getAttribute("id")).thenReturn(gettingUser.getId());
    Mockito.when(session.getAttribute("user")).thenReturn(gettingUser);
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(request.getParameter("login")).thenReturn("test");
    Mockito.when(request.getParameter("password")).thenReturn("2");
    Mockito.when(request.getParameter("mail")).thenReturn("test@test.com");
    Mockito.when(request.getParameter("role")).thenReturn("user");
    new UserActionServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "Пользователь с логином " + testValue + " уже существует");
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/user_form.jsp");
  }

  @Test
  public void testDoPostForUpdateUserWithExistMail() throws ServletException, IOException,
      DuplicateUserException, NoSuchUserException {
    String testMail = "test@gmail.com";
    userDao.saveUser(testUser);
    User gettingUser = userDao.getUser(testUser.getLogin());
    User newUser = new User("test", "test", testMail, "user");
    userDao.saveUser(newUser);
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getParameter("option")).thenReturn("update");
    Mockito.when(session.getAttribute("id")).thenReturn(gettingUser.getId());
    Mockito.when(session.getAttribute("user")).thenReturn(gettingUser);
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(request.getParameter("login")).thenReturn("2");
    Mockito.when(request.getParameter("password")).thenReturn("2");
    Mockito.when(request.getParameter("mail")).thenReturn(testMail);
    Mockito.when(request.getParameter("role")).thenReturn("user");
    new UserActionServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "Пользователь с электронной почтой " + testMail + " уже существует");
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/user_form.jsp");
  }
}
