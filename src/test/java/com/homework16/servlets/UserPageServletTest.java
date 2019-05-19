package com.homework16.servlets;

import com.homework17.dao.DuplicateGoodException;
import com.homework17.model.Good;
import com.homework19.dao.GoodDao;
import com.homework19.dao.GoodDaoHibernateImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test for user page servlet.
 */
public class UserPageServletTest {
  private final GoodDao goodDao = new GoodDaoHibernateImpl();
  private HttpServletRequest request;
  private HttpServletResponse response;

  @Before
  public void init() {
    request = Mockito.mock(HttpServletRequest.class);
    response = Mockito.mock(HttpServletResponse.class);
    HttpSession session = Mockito.mock(HttpSession.class);
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(request.getSession()).thenReturn(session);
    Mockito.when(request.getRequestDispatcher("/user_page.jsp")).thenReturn(requestDispatcher);
    Mockito.when(session.getId()).thenReturn("test");
  }

  @After
  public void clear() {
    goodDao.deleteAll();
  }

  @Test
  public void testDoGetWithEmptyDatabase() throws ServletException, IOException {
    new UserPageServlet().doGet(request, response);
    Mockito.verify(request, Mockito.times(1)).setAttribute("goods", new ArrayList<Good>());
    Mockito.verify(request, Mockito.times(1)).setAttribute("numberGoods",0);
    Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/user_page.jsp");
  }

  @Test
  public void testDoGetWithNonEmptyDatabase() throws ServletException, IOException, DuplicateGoodException {
    List<Good> goodList = new ArrayList<>();
    Good newGood1 = new Good("1", "1", 100.0);
    goodList.add(newGood1);
    goodDao.saveGood(newGood1);
    Good newGood2 = new Good("2", "2", 200.0);
    goodList.add(newGood2);
    goodDao.saveGood(newGood2);
    new UserPageServlet().doGet(request, response);
    Mockito.verify(request, Mockito.times(1)).setAttribute("goods", goodList);
    Mockito.verify(request, Mockito.times(1)).setAttribute("numberGoods",goodList.size());
    Mockito.verify(request, Mockito.times(1)).getRequestDispatcher("/user_page.jsp");
  }
}
