package com.homework13.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Servlet for manipulation with actions.
 */
@WebServlet(value = "/action")
public class MainServlet extends HttpServlet {
  private static final Logger LOGGER = Logger.getLogger(MainServlet.class);

  @Override
  public void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    LOGGER.debug("User with id " + request.getSession().getId() + "come to website.");
    String result = request.getParameter("result");
    if (result.equals("sign_in")) {
      LOGGER.debug("User with id " + request.getSession().getId() + "come to sign in.");
      response.sendRedirect("/sign_in");
    } else if (result.equals("sign_up")) {
      LOGGER.debug("User with id " + request.getSession().getId() + "come to sign up.");
      response.sendRedirect("/sign_up");
    }
  }
}
