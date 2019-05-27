package com.homework20.servlets;

import com.homework13.model.User;
import com.homework17.model.Good;
import com.homework19.dao.GoodDao;
import com.homework19.dao.GoodDaoHibernateImpl;
import com.homework19.dao.UserDao;
import com.homework19.dao.UserDaoHibernateImpl;
import com.homework20.dao.BasketGoodDao;
import com.homework20.model.BasketGood;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for adding good to basket.
 */
@WebServlet(value = "/user_page/add_to_basket")
public class AddToBasketServlet extends HttpServlet {
  private static final GoodDao goodDao = new GoodDaoHibernateImpl();
  private static final UserDao userDao = new UserDaoHibernateImpl();
  private static final BasketGoodDao basketGoodDao = new BasketGoodDao();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    long goodId = Long.valueOf(req.getParameter("id"));
    Good goodForBuying = goodDao.get(goodId).get();
    User currentUser = (User) req.getSession().getAttribute("user");
    User userFromDb = userDao.get(currentUser.getId()).get();
    Optional<BasketGood> gettingGoodFromBasket = basketGoodDao.getBasketGood(userFromDb.getBasket(),
        goodForBuying);
    if (gettingGoodFromBasket.isPresent()) {
      BasketGood goodFromBasket = gettingGoodFromBasket.get();
      updateNumberOfGood(goodForBuying);
      goodFromBasket.setAmount(goodFromBasket.getAmount() + 1);
      basketGoodDao.update(goodFromBasket);
    } else {
      updateNumberOfGood(goodForBuying);
      BasketGood basketGood = new BasketGood(goodForBuying, userFromDb.getBasket(), 1);
      basketGoodDao.save(basketGood);
    }
    resp.sendRedirect("/user_page");
  }

  private void updateNumberOfGood(Good goodForBuying) {
    goodForBuying.setCount(goodForBuying.getCount() - 1);
    goodDao.update(goodForBuying);
  }
}
