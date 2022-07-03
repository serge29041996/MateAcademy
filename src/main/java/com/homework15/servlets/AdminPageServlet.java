package com.homework15.servlets;

import com.homework13.model.User;
import com.homework16.model.Role;
import com.homework19.dao.UserDao;
import com.homework19.dao.UserDaoHibernateImpl;
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
  private final UserDao userDao = new UserDaoHibernateImpl();
  private static final Logger LOGGER = Logger.getLogger(AdminPageServlet.class);

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    List<User> userList = userDao.getAll();
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
      User needUser = userDao.get(Long.parseLong(idNeedUser)).get();
      LOGGER.debug("User with id " + request.getSession().getId()
          + " go to update form about user with id " + needUser.getId());
      request.getSession().setAttribute("user", needUser);
      request.getSession().setAttribute("action", "update");
      response.sendRedirect("/admin_page/user_action");
    } else if (result.contains("delete")) {
      String idUserForDeleting = result.split("_")[1];
      User userForDeleting = userDao.get(Long.parseLong(idUserForDeleting)).get();
      userDao.delete(userForDeleting);
      LOGGER.debug("User with id " + request.getSession().getId()
          + " delete user with id " + idUserForDeleting);
      doGet(request, response);
    } else if (result.contains("lowerRole")) {
      String idNeedUser = result.split("_")[1];
      LOGGER.debug("User with id " + request.getSession().getId()
          + " want to lower role user with id " + idNeedUser);
      userDao.updateUserRole(Long.parseLong(idNeedUser), Role.USER.getValue());
      doGet(request, response);
    } else if (result.contains("raiseRole")) {
      String idNeedUser = result.split("_")[1];
      LOGGER.debug("User with id " + request.getSession().getId()
          + " want to raise role user with id " + idNeedUser);
      userDao.updateUserRole(Long.parseLong(idNeedUser), Role.ADMIN.getValue());
      doGet(request, response);
    }
  }
}
