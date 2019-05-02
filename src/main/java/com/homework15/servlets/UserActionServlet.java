package com.homework15.servlets;

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

/**
 * Class for handle add and update operation for user.
 */
@WebServlet(value = "/admin_page/user_action")
public class UserActionServlet extends HttpServlet {
  private final UserDao userDao = new UserDao();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "result");
    String action = (String) request.getSession().getAttribute("action");
    if (action.equals("add")) {
      CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "login");
      CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "password");
    } else {
      User userForUpdate = (User) request.getSession().getAttribute("user");
      request.getSession().setAttribute("old_login", userForUpdate.getLogin());
      CheckData.checkOnNullAndSetValueForAttribute(request, "login", userForUpdate.getLogin());
      CheckData.checkOnNullAndSetValueForAttribute(request, "password",
          userForUpdate.getPassword());
      request.getSession().setAttribute("old_password", userForUpdate.getPassword());
      request.getSession().setAttribute("id", userForUpdate.getId());
    }
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user_form.jsp");
    requestDispatcher.forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    String action = request.getParameter("option");
    if (action.equals("return")) {
      response.sendRedirect("/admin_page");
    } else {
      actionWithFormData(action, login, password, request);
      request.setAttribute("login", login);
      request.setAttribute("password", password);
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user_form.jsp");
      requestDispatcher.forward(request, response);
    }
  }

  private void actionWithFormData(String action, String login, String password,
      HttpServletRequest request) {
    if (action.equals("add")) {
      actionForAddUser(login, password, request);
    } else {
      actionForUpdateUser(login, password, request);
    }
  }

  private void actionForAddUser(String login, String password, HttpServletRequest request) {
    String result = CheckData.checkUserData(login, password);
    if (result.equals("")) {
      User newUser = new User(login, password);
      try {
        userDao.saveUser(newUser);
        request.setAttribute("result", "Информация про пользователя успешно добавлена.");
      } catch (DuplicateUserException e) {
        request.setAttribute("result", "Пользователь с логином " + login + " уже существует.");
      }
    } else {
      request.setAttribute("result", result);
    }
  }

  private void actionForUpdateUser(String login, String password, HttpServletRequest request) {
    User oldDataUser = new User((String) request.getSession().getAttribute("old_login"),
        (String) request.getSession().getAttribute("old_password"));
    User newDataUser = new User((Long) request.getSession().getAttribute("id"), login, password);
    if (oldDataUser.equals(newDataUser)) {
      request.setAttribute("result", "Данные про пользователя обновлены.");
    } else {
      String result = CheckData.checkUserData(login, password);
      if (result.equals("")) {
        userDao.updateUser(newDataUser);
        request.setAttribute("result", "Данные про пользователе обновлены.");
      } else {
        request.setAttribute("result", result);
      }
    }
  }
}
