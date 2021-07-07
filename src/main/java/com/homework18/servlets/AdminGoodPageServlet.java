package com.homework18.servlets;

import com.homework17.model.Good;
import com.homework19.dao.GoodDao;
import com.homework19.dao.GoodDaoHibernateImpl;
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
 * Servlet for working with goods.
 */
@WebServlet("/admin_page/goods")
public class AdminGoodPageServlet extends HttpServlet {
  private final GoodDao goodDao = new GoodDaoHibernateImpl();
  private static final Logger LOGGER = Logger.getLogger(AdminGoodPageServlet.class);

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    List<Good> goodList = goodDao.getAll();
    request.setAttribute("numberGoods", goodList.size());
    request.setAttribute("goods", goodList);
    LOGGER.debug("User with id " + request.getSession().getId() + " come to admin goods page");
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin_goods_page.jsp");
    requestDispatcher.forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String result = request.getParameter("result");
    if (result.equals("addGood")) {
      request.getSession().setAttribute("action", "add");
      LOGGER.debug("User with id " + request.getSession().getId() + " go to add good form");
      response.sendRedirect("/admin_page/good_action");
    } else if (result.contains("update")) {
      String idNeedGood = result.split("_")[1];
      Good needGood = goodDao.get(Long.parseLong(idNeedGood)).get();
      LOGGER.debug("User with id " + request.getSession().getId()
          + " go to update form about good with id " + needGood.getId());
      request.getSession().setAttribute("good", needGood);
      request.getSession().setAttribute("action", "update");
      response.sendRedirect("/admin_page/good_action");
    } else if (result.contains("delete")) {
      String idGoodForDeleting = result.split("_")[1];
      Good goodForDeleting = goodDao.get(Long.valueOf(idGoodForDeleting)).get();
      goodDao.delete(goodForDeleting);
      LOGGER.debug("User with id " + request.getSession().getId()
          + " delete good with id " + idGoodForDeleting);
      doGet(request, response);
    }
  }
}
