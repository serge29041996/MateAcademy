package com.homework13.servlets;

import com.homework13.model.User;
import com.homework14.dao.UserDao;
import com.homework16.model.Role;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Realization tests for sign in servlet.
 */
public class SignInServletTest {
  private static final String TEST_VALUE = "test";
  private final UserDao userDao = new UserDao();
  private HttpServletRequest request;
  private HttpServletResponse response;

  @Before
  public void init() throws IOException {
    request = Mockito.mock(HttpServletRequest.class);
    response = Mockito.mock(HttpServletResponse.class);
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(request.getParameter("login")).thenReturn(TEST_VALUE);
    Mockito.when(request.getParameter("password")).thenReturn(TEST_VALUE);
    Mockito.when(request.getRequestDispatcher("/sign_in.jsp")).thenReturn(requestDispatcher);
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(session.getId()).thenReturn("test");
  }

  @After
  public void clear() {
    userDao.deleteAll();
  }

  @Test
  public void doPostForNonExistUser() throws Exception {
    new SignInServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/sign_in.jsp");
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "неверный логин/пасс");
  }

  @Test
  public void doPostForExistUser() throws Exception {
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);
    userDao.saveUser(new User(TEST_VALUE, TEST_VALUE, TEST_VALUE));
    new SignInServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Mockito.verify(response, Mockito.times(1)).sendRedirect("/user_page");
    Mockito.verify(session, Mockito.times(1)).setAttribute("auth_login", TEST_VALUE);
    Mockito.verify(session, Mockito.times(1)).setAttribute("role", Role.USER.getValue());
  }

  @Test
  public void doPostForExistAdmin() throws Exception {
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(request.getParameter("login")).thenReturn("Сергей");
    Mockito.when(request.getParameter("password")).thenReturn("123456");
    new SignInServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Mockito.verify(response, Mockito.times(1)).sendRedirect("/admin_page/users");
    Mockito.verify(session, Mockito.times(1)).setAttribute("auth_login", "Сергей");
    Mockito.verify(session, Mockito.times(1)).setAttribute("role", Role.ADMIN.getValue());
  }

  @Test
  public void doPostUserWithInvalidPassword() throws Exception {
    Mockito.when(request.getParameter("password")).thenReturn("pass");
    userDao.saveUser(new User(TEST_VALUE, TEST_VALUE, TEST_VALUE));
    new SignInServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/sign_in.jsp");
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "неверный логин/пасс");
  }

  @Test
  public void doPostUserWithInvalidData() throws Exception {
    Mockito.when(request.getParameter("login")).thenReturn("");
    Mockito.when(request.getParameter("password")).thenReturn("pass");
    userDao.saveUser(new User(TEST_VALUE, TEST_VALUE, TEST_VALUE));
    new SignInServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/sign_in.jsp");
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "Вы не ввели логин.");
  }

  @Test
  public void doGetToSignInForm() throws Exception {
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(request.getRequestDispatcher("/sign_in.jsp")).thenReturn(requestDispatcher);
    new SignInServlet().doGet(request, response);
    Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/sign_in.jsp");
  }
}

