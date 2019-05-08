package com.homework15.servlets;

import com.homework13.dao.DuplicateUserException;
import com.homework13.model.User;
import com.homework13.service.CheckData;
import com.homework14.dao.UserDao;
import com.homework16.model.Role;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Class for handle add and update operation for user.
 */
@WebServlet(value = "/admin_page/user_action")
public class UserActionServlet extends HttpServlet {
  private final UserDao userDao = new UserDao();
  private static final Logger LOGGER = Logger.getLogger(UserActionServlet.class);

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "result");
    String action = (String) request.getSession().getAttribute("action");
    if (action.equals("add")) {
      LOGGER.debug("User with id " + request.getSession().getId() + " come for adding user");
      CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "login");
      CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "password");
      CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "mail");
      CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "role");
    } else {
      User userForUpdate = (User) request.getSession().getAttribute("user");
      LOGGER.debug("User with id " + request.getSession().getId() + " come for update information "
          + "about user with id " + userForUpdate.getId());
      CheckData.checkOnNullAndSetValueForAttribute(request, "login", userForUpdate.getLogin());
      CheckData.checkOnNullAndSetValueForAttribute(request,
          "password", userForUpdate.getPassword());
      CheckData.checkOnNullAndSetValueForAttribute(request, "mail", userForUpdate.getMail());
      CheckData.checkOnNullAndSetValueForAttribute(request, "role",
          userForUpdate.getRole().getValue());
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
    String mail = request.getParameter("mail");
    String role = request.getParameter("role");
    String action = request.getParameter("option");
    if (action.equals("return")) {
      LOGGER.debug("User with id " + request.getSession().getId() + " return to admin users page");
      response.sendRedirect("/admin_page/users");
    } else {
      actionWithFormData(action, login, password, mail, role, request);
      request.setAttribute("login", login);
      request.setAttribute("password", password);
      request.setAttribute("mail", mail);
      request.setAttribute("role", role);
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user_form.jsp");
      requestDispatcher.forward(request, response);
    }
  }

  private void actionWithFormData(String action, String login, String password,
      String mail, String role, HttpServletRequest request) {
    if (action.equals("add")) {
      actionForAddUser(login, password, mail, role, request);
    } else {
      actionForUpdateUser(login, password, mail, role, request);
    }
  }

  private void actionForAddUser(String login, String password, String mail,
      String role, HttpServletRequest request) {
    String result = CheckData.checkUserData(login, password, mail, role);
    if (result.equals("")) {
      User newUser = new User(login, password, mail, role);
      try {
        userDao.saveUser(newUser);
        LOGGER.debug("User with id " + request.getSession().getId()
            + " saved information about user with login " + login);
        request.setAttribute("result", "Информация про пользователя успешно добавлена.");
      } catch (DuplicateUserException e) {
        String message;
        if (e.getMessage().contains("login")) {
          LOGGER.debug("User with id " + request.getSession().getId()
              + " cannot saved information about user with login " + login
              + " because user with same login already exist");
          message = "Пользователь с логином " + login + " уже существует.";
        } else {
          LOGGER.debug("User with id " + request.getSession().getId()
              + " cannot saved information about user with mail " + mail
              + " because user with same mail already exist");
          message = "Пользователь с электронной почтой " + mail + " уже существует.";
        }
        request.setAttribute("result", message);
      }
    } else {
      LOGGER.debug("User with id " + request.getSession().getId()
          + "write invalid information about user");
      request.setAttribute("result", result);
    }
  }

  private void actionForUpdateUser(String login, String password, String mail, String role,
      HttpServletRequest request) {
    User oldDataUser = (User) request.getSession().getAttribute("user");
    User newDataUser = new User((Long) request.getSession().getAttribute("id"), login, password,
        Role.fromString(role), mail);
    if (oldDataUser.equals(newDataUser)) {
      LOGGER.debug("User with id " + request.getSession().getId()
          + " update information about user with login " + login + " without change");
      request.setAttribute("result", "Данные про пользователя обновлены.");
    } else {
      String result = CheckData.checkUserData(login, password, mail, role);
      if (result.equals("")) {
        try {
          userDao.updateUser(newDataUser);
          LOGGER.debug("User with id " + request.getSession().getId()
              + " update information about user with login " + login);
          request.setAttribute("result", "Данные про пользователя обновлены.");
        } catch (DuplicateUserException e) {
          String message;
          if (e.getMessage().contains("login")) {
            LOGGER.debug("User with id " + request.getSession().getId()
                + " try update user with login " + login + " which already exist in database");
            message = "Пользователь с логином " + login + " уже существует";
          } else {
            LOGGER.debug("User with id " + request.getSession().getId()
                + " try update user with mail " + mail + " which already exist in database");
            message = "Пользователь с электронной почтой " + mail + " уже существует";
          }
          request.setAttribute("result", message);
        }
      } else {
        request.setAttribute("result", result);
        LOGGER.debug("User with id " + request.getSession().getId()
            + " write invalid information about user");
      }
    }
  }
}
