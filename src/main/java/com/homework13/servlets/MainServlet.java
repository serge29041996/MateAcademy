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
@WebServlet(value = "/")
public class MainServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.setContentType("text/html");
    resp.setCharacterEncoding("UTF-8");
    PrintWriter printWriter = resp.getWriter();
    printWriter.println("<h3>Выберите одно из указанных действий</h3>");
    printWriter.println("<form method=\"post\">");
    printWriter.println("<button type=\"submit\" name=\"result\" value=\"sign_in\">Войти</button>");
    printWriter.println("<button type=\"submit\" name=\"result\" value=\"sign_up\">Зарегистрироваться</button>");
    printWriter.println("</form>");
    printWriter.close();
  }

  @Override
  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter printWriter = response.getWriter();
    String result = request.getParameter("result");
    if (result.equals("sign_in")) {
      response.sendRedirect(request.getContextPath() + "/sign_in");
    } else if (result.equals("sign_up")) {
      response.sendRedirect(request.getContextPath() + "/sign_up");
    } else {
      printWriter.println("Нет действия для Вашего запроса.");
    }
  }
}
