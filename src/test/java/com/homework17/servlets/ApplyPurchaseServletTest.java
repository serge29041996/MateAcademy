package com.homework17.servlets;

import com.homework13.model.User;
import com.homework14.dao.UserDaoJdbcImpl;
import com.homework16.model.Role;
import com.homework17.model.CodeConfirmation;
import com.homework17.model.Good;
import com.homework17.util.RandomCodeGenerator;
import com.homework19.dao.CodeConfirmationDao;
import com.homework19.dao.CodeConfirmationDaoHibernateImpl;
import com.homework19.dao.GoodDao;
import com.homework19.dao.GoodDaoHibernateImpl;
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
  private Good testGood = new Good(TEST_VALUE, TEST_VALUE, 10.0);
  private final GoodDao GOOD_DAO = new GoodDaoHibernateImpl();
  private final CodeConfirmationDao CODE_DAO = new CodeConfirmationDaoHibernateImpl();

  @Before
  public void init() {
    testUser = new User(TEST_VALUE, TEST_VALUE, "sergey290496@gmail.com",
        Role.USER.getValue());
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
    GOOD_DAO.deleteAll();
    CODE_DAO.deleteAll();
  }

  @Test
  public void testDoGetForExistCode() throws Exception {
    USER_DAO.saveUser(testUser);
    User gettingUser = USER_DAO.getUser(testUser.getLogin());
    GOOD_DAO.saveGood(testGood);
    Good gettingGood = GOOD_DAO.getGood(testGood.getName());
    String generatedCode = RandomCodeGenerator.generateFourSignCode();
    CodeConfirmation codeConfirmation = new CodeConfirmation(gettingUser.getId(),
        gettingGood.getId(), generatedCode);
    CODE_DAO.saveCode(codeConfirmation);
    Mockito.when(request.getParameter("id")).thenReturn(String.valueOf(gettingGood.getId()));
    Mockito.when(session.getAttribute("auth_user")).thenReturn(gettingUser);
    new ApplyPurchaseServlet().doGet(request, response);
    Mockito.verify(request, Mockito.times(1)).setAttribute("result",
        "Сообщение с кодом подтверждения отправлено");
    Mockito.verify(request, Mockito.times(1)).setAttribute("code",
        "");
    Mockito.verify(request, Mockito.times(1))
        .getRequestDispatcher("/apply_purchase.jsp");
    Mockito.verify(session, Mockito.times(1))
        .setAttribute("id_good", gettingGood.getId());
  }
}
