package com.homework16.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test for user page servlet.
 */
public class UserPageServletTest {
  @Test
  public void testDoGetWithAuthLogin() throws ServletException, IOException {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(request.getRequestDispatcher("/user_page.jsp")).thenReturn(requestDispatcher);
    Mockito.when(session.getAttribute("auth_login")).thenReturn("test");
    new UserPageServlet().doGet(request, response);
    Mockito.verify(request, Mockito.times(1)).setAttribute("login", "test");
    Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/user_page.jsp");
    Mockito.verify(session, Mockito.times(1)).getAttribute("auth_login");
  }
}
