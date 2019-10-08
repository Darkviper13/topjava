package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.CrudDao;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private CrudDao<Meal> mealDaoInMemory;

    @Override
    public void init() throws ServletException {
        log.info("Initialization MealServlet");
        mealDaoInMemory = new MealDaoInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("delete".equals(req.getParameter("action"))) {
            Integer id = Integer.parseInt(req.getParameter("value"));
            mealDaoInMemory.delete(id);
            log.debug("redirect to meals");
            resp.sendRedirect("meals");
        } else {
            List<MealTo> mealToList = MealsUtil.getFiltered(mealDaoInMemory.findAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.DEFAULT_CALORIES_PER_DAY);
            req.setAttribute("mealsFromDataBase", mealToList);
            req.setAttribute("formatter", formatter);
            log.debug("forward to meals");
            req.getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String requestParam = req.getParameter("action");
        log.debug("Request passed first parameter: " + requestParam);
        Meal meal = parseRequest(req);
        if (meal != null) {
            switch (requestParam) {
                case "edit":
                    try {
                        Integer id = Integer.parseInt(req.getParameter("value"));
                        meal.setId(id);
                        mealDaoInMemory.update(meal);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    break;

                case "save":
                    try {
                        mealDaoInMemory.save(meal);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                    break;

                default:
                    log.debug("no parameters");
                    break;
            }
        }
        log.debug("redirect to meals");
        resp.sendRedirect("meals");
    }

    private Meal parseRequest(HttpServletRequest req) {
        try {
            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("datetime"));
            String description = req.getParameter("description");
            int calories = Integer.parseInt(req.getParameter("calories"));
            return new Meal(dateTime, description, calories);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}