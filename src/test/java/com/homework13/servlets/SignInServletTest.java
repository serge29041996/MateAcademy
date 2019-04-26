package com.homework13.servlets;

import com.homework13.model.User;
import com.homework14.dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Realization tests for sign in servlet.
 */
public class SignInServletTest {
  private static final String TEST_VALUE = "test";
  private HttpServletRequest request;
  private HttpServletResponse response;
  private StringWriter stringWriter;
  private PrintWriter printWriter;

  @Before
  public void init() throws IOException {
    request = Mockito.mock(HttpServletRequest.class);
    response = Mockito.mock(HttpServletResponse.class);
    Mockito.when(request.getParameter("login")).thenReturn(TEST_VALUE);
    Mockito.when(request.getParameter("password")).thenReturn(TEST_VALUE);
    stringWriter = new StringWriter();
    printWriter = new PrintWriter(stringWriter);
    Mockito.when(response.getWriter()).thenReturn(printWriter);
    UserDao.deleteAll();
  }

  @Test
  public void doPostForNonExistUser() throws Exception {
    new SignInServlet().doPost(request, response);
    printWriter.flush();
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Assert.assertTrue(stringWriter.toString().contains("неверный логин/пасс"));
  }

  @Test
  public void doPostForExistUser() throws Exception {
    UserDao.saveUser(new User(TEST_VALUE, TEST_VALUE));
    new SignInServlet().doPost(request, response);
    printWriter.flush();
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Assert.assertTrue(stringWriter.toString().contains("Привет test"));
  }

  @Test
  public void doPostUserWithInvalidPassword() throws Exception {
    Mockito.when(request.getParameter("password")).thenReturn("pass");
    UserDao.saveUser(new User(TEST_VALUE, TEST_VALUE));
    new SignInServlet().doPost(request, response);
    printWriter.flush();
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Assert.assertTrue(stringWriter.toString().contains("неверный логин/пасс"));
  }

  @Test
  public void doPostUserWithInvalidData() throws Exception {
    Mockito.when(request.getParameter("login")).thenReturn("");
    Mockito.when(request.getParameter("password")).thenReturn("pass");
    UserDao.saveUser(new User(TEST_VALUE, TEST_VALUE));
    new SignInServlet().doPost(request, response);
    printWriter.flush();
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Assert.assertTrue(stringWriter.toString().contains("Вы не ввели логин."));
  }

  @Test
  public void doGetToSignInForm() throws Exception {
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(request.getRequestDispatcher("/sign_in.html")).thenReturn(requestDispatcher);
    new SignInServlet().doGet(request, response);
    Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/sign_in.html");
  }
}
