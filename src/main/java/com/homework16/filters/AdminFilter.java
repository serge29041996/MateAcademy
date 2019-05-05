package com.homework16.filters;

import com.homework16.model.Role;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 * Filter for prevent go to admin page and do admin action.
 */
@WebFilter("/admin_page/*")
public class AdminFilter implements Filter {
  private static final Logger LOGGER = Logger.getLogger(AdminFilter.class);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    String role = (String) request.getSession().getAttribute("role");
    LOGGER.debug("User with id " + request.getSession().getId() + " try come to admin page");
    if (role == null || !role.equals(Role.ADMIN.getValue())) {
      LOGGER.debug("User with id " + request.getSession().getId() + " is not admin, access denied");
      request.getRequestDispatcher("/access_denied.jsp").forward(servletRequest, servletResponse);
    } else {
      LOGGER.debug("User with id " + request.getSession().getId() + " is an admin");
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }

  @Override
  public void destroy() {

  }
}
