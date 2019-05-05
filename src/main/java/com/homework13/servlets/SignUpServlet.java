package com.homework13.servlets;

import com.homework13.dao.DuplicateUserException;
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
import org.apache.log4j.Logger;

/**
 * Servlet for sign up of user.
 */
@WebServlet(value = "/sign_up")
public class SignUpServlet extends HttpServlet {
  private final UserDao userDao = new UserDao();
  private static final Logger LOGGER = Logger.getLogger(SignUpServlet.class);

  @Override
  public void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    String mail = request.getParameter("mail");
    LOGGER.debug("User with id " + request.getSession().getId() + " enter login " + login
        + " and password " + password);
    String result = CheckData.checkUserData(login, password, mail);
    if (result.equals("")) {
      User newUser = new User(login, password, mail);
      try {
        userDao.saveUser(newUser);
        LOGGER.debug("User with id " + request.getSession().getId() + " successful sign up");
        request.setAttribute("result", "Вы успешно зарегистрировались. "
            + "Теперь Вы можете авторизироваться.");
      } catch (DuplicateUserException e) {
        String message;
        if (e.getMessage().contains("login")) {
          message = "Пользователь с логином " + login + " уже существует.";
          LOGGER.debug("User with id" + request.getSession().getId() + " enter exist login");
        } else {
          message = "Пользователь с электронной почтой " + mail + " уже существует.";
          LOGGER.debug("User with id" + request.getSession().getId() + " enter exist mail");
        }
        request.setAttribute("result", message);
      }
    } else {
      LOGGER.debug("User with id " + request.getSession().getId()
          + " enter invalid login, password and mail");
      request.setAttribute("result", result);
    }
    request.setAttribute("login", login);
    request.setAttribute("password", password);
    request.setAttribute("mail", mail);
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/sign_up.jsp");
    requestDispatcher.forward(request, response);
  }

  @Override
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "result");
    CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "login");
    CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "password");
    LOGGER.debug("User with id " + request.getSession().getId() + " come to sign up form");
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/sign_up.jsp");
    requestDispatcher.forward(request, response);
  }
}
