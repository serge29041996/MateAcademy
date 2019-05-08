package com.homework13.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    HttpSession session = Mockito.mock(HttpSession.class);
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(session.getId()).thenReturn("test");
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
}
