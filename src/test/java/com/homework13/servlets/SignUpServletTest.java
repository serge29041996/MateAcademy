package com.homework13.servlets;

import com.homework13.model.User;
import com.homework14.dao.UserDao;
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
 * Realization tests for sign up servlet
 */
public class SignUpServletTest {
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
    Mockito.when(request.getParameter("mail")).thenReturn("test@gmail.com");
    Mockito.when(request.getRequestDispatcher("/sign_up.jsp")).thenReturn(requestDispatcher);
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
    new SignUpServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/sign_up.jsp");
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "Вы успешно зарегистрировались. Теперь Вы можете авторизироваться.");
  }

  @Test
  public void doPostForExistUser() throws Exception {
    userDao.saveUser(new User(TEST_VALUE, TEST_VALUE, TEST_VALUE));
    new SignUpServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/sign_up.jsp");
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "Пользователь с логином test уже существует.");
  }

  @Test
  public void doPostUserWithInvalidData() throws Exception {
    Mockito.when(request.getParameter("login")).thenReturn("");
    Mockito.when(request.getParameter("password")).thenReturn("pass");
    userDao.saveUser(new User(TEST_VALUE, TEST_VALUE, TEST_VALUE));
    new SignUpServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/sign_up.jsp");
    Mockito.verify(request, Mockito.times(1))
        .setAttribute("result", "Вы не ввели логин.");
  }

  @Test
  public void doGetToSignUpForm() throws Exception {
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(request.getRequestDispatcher("/sign_up.jsp")).thenReturn(requestDispatcher);
    new SignUpServlet().doGet(request, response);
    Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/sign_up.jsp");
  }
}
