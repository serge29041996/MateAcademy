package com.homework13.servlets;

import com.homework13.dao.NoSuchUserException;
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
 * Servlet for sign in of user.
 */
@WebServlet(value = "/sign_in")
public class SignInServlet extends HttpServlet {
  private final UserDao userDao = new UserDao();
  private static final Logger LOGGER = Logger.getLogger(SignInServlet.class);

  @Override
  public void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    LOGGER.debug("User with id " + request.getSession().getId() + " enter login " + login
        + " and password " + password);
    String result = CheckData.checkUserData(login, password);
    if (result.equals("")) {
      try {
        User user = userDao.getUser(login);
        if (user.getPassword().equals(password)) {
          request.getSession().setAttribute("auth_user", user);
          request.getSession().setAttribute("auth_login", login);
          if (user.getRole() == Role.USER) {
            LOGGER.debug("User with id " + request.getSession().getId() + "come to site as user");
            request.getSession().setAttribute("role", Role.USER.getValue());
            response.sendRedirect("/user_page");
          } else if (user.getRole() == Role.ADMIN) {
            LOGGER.debug("User with id " + request.getSession().getId() + "come to site as admin");
            request.getSession().setAttribute("role", Role.ADMIN.getValue());
            response.sendRedirect("/admin_page");
          }
        } else {
          LOGGER.debug("User with id " + request.getSession().getId() + " enter wrong password");
          request.setAttribute("result", "неверный логин/пасс");
          setAttributeAndReturnToCurrentPage(request, response, login, password);
        }
      } catch (NoSuchUserException e) {
        LOGGER.debug("User with id " + request.getSession().getId()
            + " enter wrong login and password");
        request.setAttribute("result", "неверный логин/пасс");
        setAttributeAndReturnToCurrentPage(request, response, login, password);
      }
    } else {
      LOGGER.debug("User with id " + request.getSession().getId()
          + " enter invalid login and password");
      request.setAttribute("result", result);
      setAttributeAndReturnToCurrentPage(request, response, login, password);
    }
  }

  @Override
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "result");
    CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "login");
    CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "password");
    LOGGER.debug("User with id " + request.getSession().getId() + " come to sign in form");
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/sign_in.jsp");
    requestDispatcher.forward(request, response);
  }

  private void setAttributeAndReturnToCurrentPage(HttpServletRequest request,
      HttpServletResponse response, String login, String password)
      throws ServletException, IOException {
    request.setAttribute("login", login);
    request.setAttribute("password", password);
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/sign_in.jsp");
    requestDispatcher.forward(request, response);
  }
}
