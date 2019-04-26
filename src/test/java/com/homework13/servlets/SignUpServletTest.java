package com.homework13.servlets;

import com.homework13.dao.InMemoryDatabase;
import com.homework13.model.User;
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
 * Realization tests for sign up servlet
 */
public class SignUpServletTest {
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
    InMemoryDatabase.deleteAll();
  }

  @Test
  public void doPostForNonExistUser() throws Exception {
    new SignUpServlet().doPost(request, response);
    printWriter.flush();
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Assert.assertTrue(stringWriter.toString().contains("Вы успешно зарегистрировались. Теперь Вы можете авторизироваться."));
  }

  @Test
  public void doPostForExistUser() throws Exception {
    InMemoryDatabase.saveUser(new User(TEST_VALUE, TEST_VALUE));
    new SignUpServlet().doPost(request, response);
    printWriter.flush();
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Assert.assertTrue(stringWriter.toString().contains("Пользователь с логином test уже существует."));
  }

  @Test
  public void doPostUserWithInvalidData() throws Exception {
    Mockito.when(request.getParameter("login")).thenReturn("");
    Mockito.when(request.getParameter("password")).thenReturn("pass");
    InMemoryDatabase.saveUser(new User(TEST_VALUE, TEST_VALUE));
    new SignUpServlet().doPost(request, response);
    printWriter.flush();
    Mockito.verify(request, Mockito.times(1)).getParameter("login");
    Mockito.verify(request, Mockito.times(1)).getParameter("password");
    Assert.assertTrue(stringWriter.toString().contains("Вы не ввели логин."));
  }

  @Test
  public void doGetToSignUpForm() throws Exception {
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(request.getRequestDispatcher("/sign_up.html")).thenReturn(requestDispatcher);
    new SignUpServlet().doGet(request, response);
    Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/sign_up.html");
  }
}
