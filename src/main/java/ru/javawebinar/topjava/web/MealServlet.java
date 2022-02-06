package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOImpl;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEAL = "/meals.jsp";
    private final MealDAO mealDAO;

    public MealServlet() {
        super();
        mealDAO = new MealDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to GET mealservelt");

        String forward = "";
        String action = request.getParameter("action").toLowerCase();

        switch (action) {
            case "delete":
                int idToDelete = Integer.parseInt(request.getParameter("mealId"));
                mealDAO.delete(idToDelete);
            case "listmeal":
                forward = LIST_MEAL;
                List<MealTo> mealTos = MealsUtil.filteredByStreams(mealDAO.readAll(), LocalTime.of(0, 0),
                        LocalTime.of(23, 59,59), CALORIES_PER_DAY);
                request.setAttribute("meals", mealTos);
                break;
            case "insert":
                forward = INSERT_OR_EDIT;
                break;
            case "edit":
                int idToEdit = Integer.parseInt(request.getParameter("mealId"));
                request.setAttribute("meal", mealDAO.read(idToEdit));
                forward = INSERT_OR_EDIT;
                break;
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to POST mealservlet");

        request.setCharacterEncoding("UTF-8");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("datetime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        Meal meal = new Meal(dateTime, description, calories);
        String mealId = request.getParameter("mealid");
        if (mealId == null || mealId.isEmpty()) {
            mealDAO.create(meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            mealDAO.update(meal);
        }
        List<MealTo> mealTos = MealsUtil.filteredByStreams(mealDAO.readAll(), LocalTime.of(0, 0),
                LocalTime.of(23, 59,59), CALORIES_PER_DAY);
        request.setAttribute("meals", mealTos);
        request.getRequestDispatcher(LIST_MEAL).forward(request, response);
    }
}
