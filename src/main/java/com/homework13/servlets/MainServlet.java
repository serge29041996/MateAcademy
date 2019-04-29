package com.homework13.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for manipulation with actions.
 */
@WebServlet(value = "/action")
public class MainServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter printWriter = response.getWriter();
    String result = request.getParameter("result");
    if (result.equals("sign_in")) {
      response.sendRedirect("/sign_in");
    } else if (result.equals("sign_up")) {
      response.sendRedirect("/sign_up");
    } else if (result.equals("admin_page")) {
      response.sendRedirect("/admin_page");
    }
  }
}
