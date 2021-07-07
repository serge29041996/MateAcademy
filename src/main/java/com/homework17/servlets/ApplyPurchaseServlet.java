package com.homework17.servlets;

import com.homework13.model.User;
import com.homework17.model.CodeConfirmation;
import com.homework17.service.MailConfirmation;
import com.homework19.dao.CodeConfirmationDao;
import com.homework19.dao.CodeConfirmationDaoHibernateImpl;
import com.homework19.dao.UserDao;
import com.homework19.dao.UserDaoHibernateImpl;
import com.homework20.dao.BasketDao;
import com.homework20.dao.BasketDaoHibernateImpl;
import com.homework20.dao.BasketGoodDao;
import com.homework20.model.Basket;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Servlet for applying of purchase.
 */
@WebServlet("/user_page/apply_purchase")
public class ApplyPurchaseServlet extends HttpServlet {
  private static final Logger LOGGER = Logger.getLogger(ApplyPurchaseServlet.class);
  private static final MailConfirmation mailConfirmation = new MailConfirmation();
  private static final CodeConfirmationDao codeDao = new CodeConfirmationDaoHibernateImpl();
  private static final BasketDao basketDao = new BasketDaoHibernateImpl();
  private static final BasketGoodDao basketGoodDao = new BasketGoodDao();
  private static final UserDao userDao = new UserDaoHibernateImpl();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    long idBuyingGood = Long.parseLong(request.getParameter("id"));
    LOGGER.debug("User with id " + request.getSession().getId()
        + " come to apply purchase from basket");
    User user = (User) request.getSession().getAttribute("auth_user");
    Basket basket = basketDao.getBasket(user).get();
    Optional<CodeConfirmation> findCode = codeDao.getCode(basket);
    String codeConfirmation;
    if (findCode.isPresent()) {
      CodeConfirmation code = findCode.get();
      codeConfirmation = code.getCode();
      Optional<String> generatedCode = mailConfirmation
          .generateCodeAndSendToUserMail(user.getMail(), codeConfirmation);
      checkSendAndSetAttribute(request, "result", generatedCode);
      if (generatedCode.isPresent()) {
        String generatedCodeValue = generatedCode.get();
        CodeConfirmation updatedCode = new CodeConfirmation(basket,
            generatedCodeValue);
        codeDao.update(updatedCode);
      }
    } else {
      Optional<String> generatedCode = mailConfirmation
          .generateCodeAndSendToUserMail(user.getMail());
      checkSendAndSetAttribute(request, "result", generatedCode);
      if (generatedCode.isPresent()) {
        String generatedCodeValue = generatedCode.get();
        CodeConfirmation updatedCode = new CodeConfirmation(basket,
            generatedCodeValue);
        codeDao.save(updatedCode);
      }
    }
    request.setAttribute("code", "");
    request.getRequestDispatcher("/apply_purchase.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String code = request.getParameter("code");
    User currentUser = (User) request.getSession().getAttribute("auth_user");
    Basket basket = basketDao.getBasket(currentUser).get();
    Optional<CodeConfirmation> findCode = codeDao.getCode(basket);
    if (findCode.isPresent()) {
      CodeConfirmation findCodeValue = findCode.get();
      if (findCodeValue.getCode().equals(code)) {
        request.setAttribute("result", "Покупка подтверждена");
        codeDao.delete(findCodeValue);
        basketGoodDao.deleteAllGoodsFromBasket(basket.getId());
        basketDao.delete(basket);
      } else {
        request.setAttribute("result", "Неправильный код. Введите правильный код.");
      }
    } else {
      request.setAttribute("result", "Кода для подтверждения покупки нет.");
    }
    request.setAttribute("code", code);
    request.getRequestDispatcher("/apply_purchase.jsp").forward(request, response);
  }

  private void checkSendAndSetAttribute(HttpServletRequest request, String field,
      Optional<String> sendCode) {
    if (sendCode.isPresent()) {
      LOGGER.debug("User with id " + request.getSession().getId() + " get code confirmation"
          + " for purchase.");
      request.setAttribute(field, "Сообщение с кодом подтверждения отправлено");
    } else {
      LOGGER.debug("User with id " + request.getSession().getId() + " do not get code confirmation"
          + " for purchase.");
      request.setAttribute(field, "Не удалось отправить код на почту.");
    }
  }
}
