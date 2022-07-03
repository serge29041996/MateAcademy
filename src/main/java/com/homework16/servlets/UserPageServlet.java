package com.homework16.servlets;

import com.homework13.model.User;
import com.homework17.model.Good;
import com.homework19.dao.GoodDao;
import com.homework19.dao.GoodDaoHibernateImpl;
import com.homework20.dao.BasketDao;
import com.homework20.dao.BasketDaoHibernateImpl;
import com.homework20.model.Basket;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
  private static final Logger logger = Logger.getLogger(UserPageServlet.class);
  private static final GoodDao goodDao = new GoodDaoHibernateImpl();
  private static final BasketDao basketDao = new BasketDaoHibernateImpl();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    logger.debug("User with id " + request.getSession().getId() + " come to user page for "
        + "buying goods");
    List<Good> goodList = goodDao.getAll();
    int numberGoods = goodList.size();
    User currentUser = (User) request.getSession().getAttribute("user");
    Optional<Basket> basketUser = basketDao.getBasket(currentUser);
    if (!basketUser.isPresent()) {
      Basket basket = new Basket(currentUser);
      basketDao.save(basket);
    }
    request.setAttribute("goods", goodList);
    request.setAttribute("numberGoods", numberGoods);
    request.getRequestDispatcher("/user_page.jsp").forward(request, response);
  }
}
