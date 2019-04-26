package com.homework13.servlets;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test for main servlet.
 */
public class MainServletTest {
  private HttpServletRequest request;
  private HttpServletResponse response;

  @Before
  public void init() {
    request = Mockito.mock(HttpServletRequest.class);
    response = Mockito.mock(HttpServletResponse.class);
  }

  @Test
  public void doPostToSignIn() throws Exception {
    Mockito.when(request.getParameter("result")).thenReturn("sign_in");
    new MainServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1)).getParameter("result");
    Mockito.verify(response, Mockito.times(1)).sendRedirect("/sign_in");
  }

  @Test
  public void doPostToSignUp() throws Exception {
    Mockito.when(request.getParameter("result")).thenReturn("sign_up");
    new MainServlet().doPost(request, response);
    Mockito.verify(request, Mockito.times(1)).getParameter("result");
    Mockito.verify(response, Mockito.times(1)).sendRedirect("/sign_up");
  }

  @Test
  public void doPostToIllegalOperation() throws Exception {
    Mockito.when(request.getParameter("result")).thenReturn("test");
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    Mockito.when(response.getWriter()).thenReturn(printWriter);
    new MainServlet().doPost(request, response);
    printWriter.flush();
    Mockito.verify(request, Mockito.times(1)).getParameter("result");
    Mockito.verify(response, Mockito.times(0)).sendRedirect("/sign_up");
    Mockito.verify(response, Mockito.times(0)).sendRedirect("/sign_in");
    Assert.assertTrue(stringWriter.toString().contains("Нет действия для Вашего запроса."));
  }
}
