package com.homework17.servlets;

import com.homework13.model.User;
import com.homework14.dao.UserDaoJdbcImpl;
import com.homework16.model.Role;
import com.homework17.model.CodeConfirmation;
import com.homework17.util.RandomCodeGenerator;
import com.homework19.dao.CodeConfirmationDao;
import com.homework19.dao.CodeConfirmationDaoHibernateImpl;
import com.homework20.dao.BasketDao;
import com.homework20.dao.BasketDaoHibernateImpl;
import com.homework20.model.Basket;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for servlet, which using for applying purchase.
 */
public class ApplyPurchaseServletTest {
  private HttpServletRequest request;
  private HttpServletResponse response;
  private HttpSession session;
  private static final String TEST_VALUE = "test";
  private User testUser;
  private final UserDaoJdbcImpl USER_DAO = new UserDaoJdbcImpl();
  private final BasketDao basketDao = new BasketDaoHibernateImpl();
  private final CodeConfirmationDao CODE_DAO = new CodeConfirmationDaoHibernateImpl();

  @Before
  public void init() {
    testUser = new User(TEST_VALUE, TEST_VALUE, Role.USER.getValue(), "sergey290496@gmail.com");
    request = Mockito.mock(HttpServletRequest.class);
    response = Mockito.mock(HttpServletResponse.class);
    session = Mockito.mock(HttpSession.class);
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(request.getRequestDispatcher("/apply_purchase.jsp"))
        .thenReturn(requestDispatcher);
  }

  @After
  public void clear() {
    USER_DAO.deleteAll();
    basketDao.deleteAll();
    CODE_DAO.deleteAll();
  }

  @Test
  public void testDoGetForExistCode() throws Exception {
    USER_DAO.save(testUser);
    User gettingUser = USER_DAO.getUserByLogin(testUser.getLogin()).get();
    Basket basket = new Basket(gettingUser);
    basketDao.save(basket);
    Basket gettingBasket = basketDao.getBasket(gettingUser).get();
    String generatedCode = RandomCodeGenerator.generateFourSignCode();
    CodeConfirmation codeConfirmation = new CodeConfirmation(gettingBasket, generatedCode);
    CODE_DAO.save(codeConfirmation);
    Mockito.when(session.getAttribute("auth_user")).thenReturn(gettingUser);
    new ApplyPurchaseServlet().doGet(request, response);
    Mockito.verify(request, Mockito.times(1)).setAttribute("result",
        "Сообщение с кодом подтверждения отправлено");
    Mockito.verify(request, Mockito.times(1)).setAttribute("code",
        "");
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/apply_purchase.jsp");
  }
}
