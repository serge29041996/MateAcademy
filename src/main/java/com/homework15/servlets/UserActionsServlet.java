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
@WebServlet(value = "/user_action")
public class UserActionsServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    CheckData.checkOnNullAndSetValueForAttribute(request, "result");
    String action = (String) request.getAttribute("action");
    if (action.equals("add")) {
      CheckData.checkOnNullAndSetValueForAttribute(request, "login");
      CheckData.checkOnNullAndSetValueForAttribute(request, "password");
    } else {
      User userForUpdate = (User) request.getAttribute("user");
      request.setAttribute("login", userForUpdate.getLogin());
      request.setAttribute("password", userForUpdate.getPassword());
      request.setAttribute("id", userForUpdate.getId());
    }
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user_form.jsp");
    requestDispatcher.forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    String action = (String) request.getAttribute("action");
    if (action == null) {
      response.sendRedirect("/admin_page");
    } else {
      if (action.equals("add")) {
        String result = CheckData.checkUserData(login, password);
        if (result.equals("")) {
          User newUser = new User(login, password);
          try {
            UserDao.saveUser(newUser);
            request.setAttribute("result", "Информация про пользователя успешно добавлена.");
          } catch (DuplicateUserException e) {
            request.setAttribute("result", "Пользователь с логином " + login + " уже существует.");
          }
        } else {
          request.setAttribute("result", result);
        }
      } else {
        User oldDataUser = new User((String) request.getAttribute("login"),
            (String) request.getAttribute("password"));
        User newDataUser = new User(login, password);
        newDataUser.setId((Long) request.getAttribute("id"));
        if (oldDataUser.equals(newDataUser)) {
          request.setAttribute("result", "Данные про пользователя обновлены.");
        } else {
          String result = CheckData.checkUserData(login, password);
          if (result.equals("")) {
            UserDao.updateUser(newDataUser);
            request.setAttribute("result", "Данные о пользователе обновлены.");
          } else {
            request.setAttribute("result", result);
          }
        }
      }
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user_form.jsp");
      requestDispatcher.include(request, response);
    }

  }
}
