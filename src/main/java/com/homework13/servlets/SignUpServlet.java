package com.homework13.servlets;

import com.homework13.dao.DuplicateUserException;
import com.homework13.dao.InMemoryDatabase;
import com.homework13.model.User;
import com.homework13.service.CheckData;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for sign up of user.
 */
@WebServlet(value = "/sign_up")
public class SignUpServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    String result = CheckData.checkUserData(login, password);
    PrintWriter printWriter = response.getWriter();
    if (result.equals("")) {
      User newUser = new User(login, password);
      try {
        InMemoryDatabase.saveUser(newUser);
        printWriter.println("Вы успешно зарегистрировались. Теперь Вы можете авторизироваться.");
      } catch (DuplicateUserException e) {
        printWriter.println("Пользователь с логином " + login + " уже существует.");
      }
    } else {
      printWriter.println(result);
    }
  }

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    PrintWriter printWriter = response.getWriter();
    printWriter.println("<h3>Введите данные для регистрации:</h3>");
    printWriter.println("<form method=\"post\"");
    printWriter.println("<label for=\"login\">Логин:</label>");
    printWriter.println("<input type=\"text\" name=\"login\" id=\"login\"/>");
    printWriter.println("<br>");
    printWriter.println("<label for=\"password\">Пароль:</label>");
    printWriter.println("<input type=\"password\" name=\"password\" id=\"password\"/>");
    printWriter.println("<br>");
    printWriter.println("<button type=\"submit\">Зарегистрироваться</button>");
    printWriter.println("</form>");
  }
}
