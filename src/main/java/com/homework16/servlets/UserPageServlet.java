package com.homework16.servlets;

import com.homework17.dao.GoodDao;
import com.homework17.model.Good;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Servlet for working with user.
 */
@WebServlet(value = "/user_page")
public class UserPageServlet extends HttpServlet {
  private static final Logger LOGGER = Logger.getLogger(UserPageServlet.class);
  private final GoodDao goodDao = new GoodDao();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    LOGGER.debug("User with id " + request.getSession().getId() + " come to user page for "
        + "buying goods");
    List<Good> goodList = goodDao.getAllGoods();
    int numberGoods = goodList.size();
    request.setAttribute("goods", goodList);
    request.setAttribute("numberGoods", numberGoods);
    request.getRequestDispatcher("/user_page.jsp").forward(request, response);
  }
}
