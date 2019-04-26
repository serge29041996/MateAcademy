package com.homework13.servlets;

import com.homework13.dao.InMemoryDatabase;
import com.homework13.dao.NoSuchUserException;
import com.homework13.model.User;
import com.homework13.service.CheckData;
import java.io.IOException;
import java.io.PrintWriter;
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
    PrintWriter printWriter = response.getWriter();
    if (result.equals("")) {
      try {
        User user = InMemoryDatabase.getUser(login);
        if (user.getPassword().equals(password)) {
          printWriter.println("Привет " + login);
        } else {
          printWriter.println("неверный логин/пасс");
        }
      } catch (NoSuchUserException e) {
        printWriter.println("неверный логин/пасс");
      }
    } else {
      printWriter.println(result);
    }
  }

  @Override
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/sign_in.html");
    requestDispatcher.forward(request, response);
  }
}
