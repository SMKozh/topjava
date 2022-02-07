package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    private static final String SERVLET = "meals";
    private MealDao mealDAO;

    @Override
    public void init() throws ServletException {
        mealDAO = new MealDaoInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in doGET");

        String forward = "";
        String action = request.getParameter("action");
        if (action == null) {
            action = "listmeal";
        }

        switch (action) {
            case "insert":
                log.debug("insert new meal");
                forward = INSERT_OR_EDIT;
                break;
            case "edit":
                log.debug("edit meal with id " + getMealId(request));
                request.setAttribute("meal", mealDAO.
                        read(getMealId(request)));
                forward = INSERT_OR_EDIT;
                break;
            case "delete":
                log.debug("delete meal with id " + getMealId(request));
                mealDAO.delete(getMealId(request));
                response.sendRedirect(SERVLET);
                return;
            case "listmeal":
                log.debug("get meals list");
                forward = LIST_MEAL;
                List<MealTo> mealTos = MealsUtil.filteredByStreams(mealDAO.readAll(), LocalTime.MIN,
                        LocalTime.MAX, CALORIES_PER_DAY);
                request.setAttribute("meals", mealTos);
                break;
            default:
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in doPOST");

        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("datetime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String mealId = request.getParameter("mealid");
        if (mealId == null || mealId.isEmpty()) {
            log.debug("insert new meal");
            mealDAO.create(meal);
        } else {
            log.debug("edit meal with id " + getMealId(request));
            meal.setId(getMealId(request));
            mealDAO.update(meal);
        }
        response.sendRedirect(SERVLET);
    }

    private int getMealId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("mealid"));
    }
}
