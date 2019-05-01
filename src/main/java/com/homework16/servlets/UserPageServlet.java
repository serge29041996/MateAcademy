package com.homework16.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for working with user.
 */
@WebServlet(value = "/user_page")
public class UserPageServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String userLogin = (String) request.getSession().getAttribute("auth_login");
    request.setAttribute("login", userLogin);
    request.getRequestDispatcher("/user_page.jsp").forward(request, response);
  }
}
