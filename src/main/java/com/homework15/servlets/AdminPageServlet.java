package com.homework15.servlets;

import com.homework13.dao.NoSuchUserException;
import com.homework13.model.User;
import com.homework14.dao.UserDao;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Servlet for admin action.
 */
@WebServlet(value = "/admin_page/users")
public class AdminPageServlet extends HttpServlet {
  private final UserDao userDao = new UserDao();
  private static final Logger LOGGER = Logger.getLogger(AdminPageServlet.class);

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    List<User> userList = userDao.getAllUsers();
    request.setAttribute("numberUsers", userList.size());
    request.setAttribute("users", userList);
    LOGGER.debug("User with id " + request.getSession().getId() + " come to admin users page");
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin_users_page.jsp");
    requestDispatcher.forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String result = request.getParameter("result");
    if (result.equals("addUser")) {
      request.getSession().setAttribute("action", "add");
      LOGGER.debug("User with id " + request.getSession().getId() + " go to add user form");
      response.sendRedirect("/admin_page/user_action");
    } else if (result.contains("update")) {
      String idNeedUser = result.split("_")[1];
      try {
        User needUser = userDao.getUser(Long.parseLong(idNeedUser));
        LOGGER.debug("User with id " + request.getSession().getId()
            + " go to update form about user with id " + needUser.getId());
        request.getSession().setAttribute("user", needUser);
        request.getSession().setAttribute("action", "update");
      } catch (NoSuchUserException e) {
        LOGGER.debug("User with id " + request.getSession().getId()
            + " go to add user form, because user with id " + idNeedUser + " was not find");
        request.getSession().setAttribute("action", "add");
      }
      response.sendRedirect("/admin_page/user_action");
    } else if (result.contains("delete")) {
      String idUserForDeleting = result.split("_")[1];
      userDao.deleteUser(Long.parseLong(idUserForDeleting));
      LOGGER.debug("User with id " + request.getSession().getId()
          + " delete user with id " + idUserForDeleting);
      doGet(request, response);
    }
  }
}
