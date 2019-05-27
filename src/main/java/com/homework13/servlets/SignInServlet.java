package com.homework13.servlets;

import com.homework13.model.User;
import com.homework13.service.CheckData;
import com.homework16.model.Role;
import com.homework18.utils.HashUtils;
import com.homework19.dao.UserDao;
import com.homework19.dao.UserDaoHibernateImpl;
import java.io.IOException;
import java.util.Optional;
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
  private final UserDao userDao = new UserDaoHibernateImpl();
  private static final Logger LOGGER = Logger.getLogger(SignInServlet.class);

  @Override
  public void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    LOGGER.debug("User with id " + request.getSession().getId() + " enter login " + login
        + " and password " + password);
    String result = CheckData.checkUserData(login, password);
    if (result.equals("")) {
      Optional<User> gettingUserFromDb = userDao.getUserByLogin(login);
      if (gettingUserFromDb.isPresent()) {
        User user = gettingUserFromDb.get();
        if (user.getPassword().equals(HashUtils
            .getSha512SecurePassword(password, user.getSalt()))) {
          request.getSession().setAttribute("auth_user", user);
          request.getSession().setAttribute("auth_login", login);
          if (user.getRole().equals(Role.USER.getValue())) {
            LOGGER.debug("User with id " + request.getSession().getId() + "come to site as user");
            request.getSession().setAttribute("role", Role.USER.getValue());
            response.sendRedirect("/user_page");
          } else if (user.getRole().equals(Role.ADMIN.getValue())) {
            LOGGER.debug("User with id " + request.getSession().getId() + "come to site as admin");
            request.getSession().setAttribute("role", Role.ADMIN.getValue());
            response.sendRedirect("/admin_page/users");
          }
        } else {
          LOGGER.debug("User with id " + request.getSession().getId() + " enter wrong password");
          request.setAttribute("result", "неверный логин/пасс");
          setAttributeAndReturnToCurrentPage(request, response, login, password);
        }
      } else {
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
