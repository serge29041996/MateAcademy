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

/**
 * Servlet for admin action.
 */
@WebServlet(value = "/admin_page")
public class AdminServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    List<User> userList = UserDao.getAllUsers();
    request.setAttribute("numberUsers", userList.size());
    request.setAttribute("users", userList);
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin.jsp");
    requestDispatcher.forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String result = request.getParameter("result");
    if (result.equals("addUser")) {
      request.setAttribute("action", "add");
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user_action");
      requestDispatcher.forward(request, response);
    } else if (result.contains("update")) {
      String idNeedUser = result.split("_")[1];
      try {
        User needUser = UserDao.getUser(Long.parseLong(idNeedUser));
        request.setAttribute("user", needUser);
        request.setAttribute("action", "update");
      } catch (NoSuchUserException e) {
        request.setAttribute("action", "add");
      }
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user_action");
      requestDispatcher.forward(request, response);
    } else if (result.contains("delete")) {
      String idUserForDeleting = result.split("_")[1];
      UserDao.deleteUser(Long.parseLong(idUserForDeleting));
      doGet(request, response);
    }
  }
}
