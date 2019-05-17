package com.homework17.servlets;

import com.homework13.model.User;
import com.homework17.model.CodeConfirmation;
import com.homework17.service.MailConfirmation;
import com.homework19.dao.CodeConfirmationDao;
import com.homework19.dao.CodeConfirmationDaoHibernateImpl;
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
  private final MailConfirmation mailConfirmation = new MailConfirmation();
  private final CodeConfirmationDao codeDao = new CodeConfirmationDaoHibernateImpl();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    long idBuyingGood = Long.parseLong(request.getParameter("id"));
    LOGGER.debug("User with id " + request.getSession().getId()
        + " come to buy good with id " + idBuyingGood);
    User user = (User) request.getSession().getAttribute("auth_user");
    Optional<CodeConfirmation> findCode = codeDao.getCode(user.getId(), idBuyingGood);
    String codeConfirmation;
    if (findCode.isPresent()) {
      CodeConfirmation code = findCode.get();
      codeConfirmation = code.getCode();
      Optional<String> generatedCode = mailConfirmation
          .generateCodeAndSendToUserMail(user.getMail(), codeConfirmation);
      checkSendAndSetAttribute(request, "result", generatedCode);
      if (generatedCode.isPresent()) {
        String generatedCodeValue = generatedCode.get();
        CodeConfirmation updatedCode = new CodeConfirmation(user.getId(), idBuyingGood,
            generatedCodeValue);
        codeDao.updateCode(updatedCode);
      }
    } else {
      Optional<String> generatedCode = mailConfirmation
          .generateCodeAndSendToUserMail(user.getMail());
      checkSendAndSetAttribute(request, "result", generatedCode);
      if (generatedCode.isPresent()) {
        String generatedCodeValue = generatedCode.get();
        CodeConfirmation updatedCode = new CodeConfirmation(user.getId(), idBuyingGood,
            generatedCodeValue);
        codeDao.saveCode(updatedCode);
      }
    }
    request.getSession().setAttribute("id_good", idBuyingGood);
    request.setAttribute("code", "");
    request.getRequestDispatcher("/apply_purchase.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String code = request.getParameter("code");
    long idGood = (Long) request.getSession().getAttribute("id_good");
    User currentUser = (User) request.getSession().getAttribute("auth_user");
    Optional<CodeConfirmation> findCode = codeDao.getCode(currentUser.getId(), idGood);
    if (findCode.isPresent()) {
      CodeConfirmation findCodeValue = findCode.get();
      if (findCodeValue.getCode().equals(code)) {
        request.setAttribute("result", "Покупка подтверждена");
        codeDao.deleteCode(findCodeValue.getId());
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
