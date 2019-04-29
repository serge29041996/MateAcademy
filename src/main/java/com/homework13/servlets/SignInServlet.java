package com.homework13.servlets;

import com.homework13.dao.NoSuchUserException;
import com.homework13.model.User;
import com.homework13.service.CheckData;
import com.homework14.dao.UserDao;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for sign in of user.
 */
@WebServlet(value = "/sign_in")
public class SignInServlet extends HttpServlet {
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
    if (result.equals("")) {
      try {
        User user = UserDao.getUser(login);
        if (user.getPassword().equals(password)) {
          request.setAttribute("result", "Привет " + login);
        } else {
          request.setAttribute("result", "неверный логин/пасс");
        }
      } catch (NoSuchUserException e) {
        request.setAttribute("result", "неверный логин/пасс");
      }
    } else {
      request.setAttribute("result", result);
    }
    request.setAttribute("login", login);
    request.setAttribute("password", password);
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/sign_in.jsp");
    requestDispatcher.include(request, response);
  }

  @Override
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    CheckData.checkOnNullAndSetValueForAttribute(request, "result");
    CheckData.checkOnNullAndSetValueForAttribute(request, "login");
    CheckData.checkOnNullAndSetValueForAttribute(request, "password");
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/sign_in.jsp");
    requestDispatcher.forward(request, response);
  }
}
