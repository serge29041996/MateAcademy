package com.homework13.servlets;

import com.homework13.dao.DuplicateUserException;
import com.homework13.model.User;
import com.homework13.service.CheckData;
import com.homework14.dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
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
  @Override
  public void doPost(HttpServletRequest request,
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
        UserDao.saveUser(newUser);
        printWriter.println("Вы успешно зарегистрировались. Теперь Вы можете авторизироваться.");
      } catch (DuplicateUserException e) {
        printWriter.println("Пользователь с логином " + login + " уже существует.");
      }
    } else {
      printWriter.println(result);
    }
  }

  @Override
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/sign_up.html");
    requestDispatcher.forward(request, response);
  }
}
