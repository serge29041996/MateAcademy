package com.homework18.servlets;

import com.homework13.service.CheckData;
import com.homework17.dao.DuplicateGoodException;
import com.homework17.dao.GoodDao;
import com.homework17.model.Good;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Servlet for adding and updating information about goods.
 */
@WebServlet("/admin_page/good_action")
public class GoodActionServlet extends HttpServlet {
  private final GoodDao goodDao = new GoodDao();
  private static final Logger LOGGER = Logger.getLogger(GoodActionServlet.class);

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "result");
    String action = (String) request.getSession().getAttribute("action");
    if (action.equals("add")) {
      LOGGER.debug("User with id " + request.getSession().getId() + " come for adding good");
      CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "name");
      CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "description");
      CheckData.checkOnNullAndSetDefaultValueForAttribute(request, "price");
    } else {
      Good goodForUpdate = (Good) request.getSession().getAttribute("good");
      LOGGER.debug("User with id " + request.getSession().getId() + " come for update information "
          + "about good with id " + goodForUpdate.getId());
      CheckData.checkOnNullAndSetValueForAttribute(request, "name", goodForUpdate.getName());
      CheckData.checkOnNullAndSetValueForAttribute(request,
          "description", goodForUpdate.getDescription());
      CheckData.checkOnNullAndSetValueForAttribute(request, "price",
          String.valueOf(goodForUpdate.getPrice()));
      request.getSession().setAttribute("id", goodForUpdate.getId());
    }
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/good_form.jsp");
    requestDispatcher.forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String name = request.getParameter("name");
    String description = request.getParameter("description");
    String price = request.getParameter("price");
    String action = request.getParameter("option");
    if (action.equals("return")) {
      LOGGER.debug("User with id " + request.getSession().getId() + " return to admin goods page");
      response.sendRedirect("/admin_page/goods");
    } else {
      actionWithFormData(action, name, description, price, request);
      request.setAttribute("name", name);
      request.setAttribute("description", description);
      request.setAttribute("price", price);
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("/good_form.jsp");
      requestDispatcher.forward(request, response);
    }
  }

  private void actionWithFormData(String action, String name, String description,
      String price, HttpServletRequest request) {
    if (action.equals("add")) {
      actionForAddGood(name, description, price, request);
    } else {
      actionForUpdateGood(name, description, price, request);
    }
  }

  private void actionForAddGood(String name, String description, String price,
      HttpServletRequest request) {
    String result = CheckData.checkGoodData(name, description, price);
    if (result.equals("")) {
      Good newGood = new Good(name, description, Double.parseDouble(price));
      try {
        goodDao.saveGood(newGood);
        LOGGER.debug("User with id " + request.getSession().getId()
            + " saved information about good with name " + name);
        request.setAttribute("result", "Информация про товар успешно добавлена.");
      } catch (DuplicateGoodException e) {
        LOGGER.debug("User with id " + request.getSession().getId()
            + " cannot saved information about good with name " + name
            + " because good with same name already exist");
        request.setAttribute("result", "Товар с названием " + name + " уже существует.");
      }
    } else {
      LOGGER.debug("User with id " + request.getSession().getId()
          + "write invalid information about good");
      request.setAttribute("result", result);
    }
  }

  private void actionForUpdateGood(String name, String description, String price,
      HttpServletRequest request) {
    String result = CheckData.checkGoodData(name, description, price);
    if (result.equals("")) {
      Good oldDataGood = (Good) request.getSession().getAttribute("good");
      Good newDataGood = new Good((Long) request.getSession().getAttribute("id"), name, description,
          Double.parseDouble(price));
      if (oldDataGood.equals(newDataGood)) {
        LOGGER.debug("User with id " + request.getSession().getId()
            + " update information about good with name " + name + " without change");
        request.setAttribute("result", "Данные про товар обновлены.");
      } else {
        try {
          goodDao.updateGood(newDataGood);
          LOGGER.debug("User with id " + request.getSession().getId()
              + " update information about good with name " + name);
          request.setAttribute("result", "Данные про товар обновлены.");
        } catch (DuplicateGoodException e) {
          LOGGER.debug("User with id " + request.getSession().getId()
              + " try update good with name " + name + " which already exist in database");
          request.setAttribute("result", "Товар с названием " + name + " уже существует");
        }
      }
    } else {
      request.setAttribute("result", result);
      LOGGER.debug("User with id " + request.getSession().getId()
          + " write invalid information about good");
    }
  }
}
