package com.homework16.filters;

import com.homework16.model.Role;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for admin filter.
 */
public class AdminFilterTest {
  private HttpServletRequest servletRequest;
  private ServletResponse servletResponse;
  private FilterChain filterChain;
  private HttpSession session;

  @Before
  public void init() {
    session = Mockito.mock(HttpSession.class);
    servletRequest = Mockito.mock(HttpServletRequest.class);
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(servletRequest.getRequestDispatcher("/access_denied.jsp")).thenReturn(requestDispatcher);
    Mockito.when(servletRequest.getSession()).thenReturn(session);
    servletResponse = Mockito.mock(ServletResponse.class);
    filterChain = Mockito.mock(FilterChain.class);
  }

  @Test
  public void testDoFilterForValidRole() throws IOException, ServletException {
    Mockito.when(session.getAttribute("role")).thenReturn(Role.ADMIN.getValue());
    new AdminFilter().doFilter(servletRequest, servletResponse, filterChain);
    Mockito.verify(session, Mockito.times(1)).getAttribute("role");
    Mockito.verify(servletRequest, Mockito.times(0))
        .getRequestDispatcher("/access_denied.jsp");
    Mockito.verify(filterChain, Mockito.times(1))
        .doFilter(servletRequest, servletResponse);
  }

  @Test
  public void testDoFilterForInValidRole() throws IOException, ServletException {
    Mockito.when(session.getAttribute("role")).thenReturn(Role.USER.getValue());
    new AdminFilter().doFilter(servletRequest, servletResponse, filterChain);
    Mockito.verify(servletRequest, Mockito.times(1))
        .getRequestDispatcher("/access_denied.jsp");
    Mockito.verify(session, Mockito.times(1)).getAttribute("role");
    Mockito.verify(filterChain, Mockito.times(0))
        .doFilter(servletRequest, servletResponse);
  }
}
