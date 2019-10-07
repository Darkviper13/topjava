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
        try {
            if (req.getParameter("action").equals("delete")) {
                Integer id = Integer.parseInt(req.getParameter("value"));
                mealDaoInMemory.delete(id);
                log.debug("redirect to meals");
                resp.sendRedirect("meals");
                return;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        List<MealTo> mealToList = MealsUtil.createListOfMealTo(mealDaoInMemory.findAll());
        req.setAttribute("mealsFromDataBase", mealToList);
        req.setAttribute("formatter", formatter);
        log.debug("forward to meals");
        req.getServletContext().getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String requestParam = req.getParameter("action");
        log.debug("Request passed first parameter: " + requestParam);
        switch (requestParam) {

            case "edit":
                try {
                    Integer id = Integer.parseInt(req.getParameter("value"));
                    LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("datetime"));
                    String description = req.getParameter("description");
                    int calories = Integer.parseInt(req.getParameter("calories"));
                    mealDaoInMemory.update(id, new Meal(dateTime, description, calories));
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                log.debug("redirect to meals");
                resp.sendRedirect("meals");
                break;

            case "save":
                try {
                    LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("datetime"));
                    String description = req.getParameter("description");
                    int calories = Integer.parseInt(req.getParameter("calories"));
                    mealDaoInMemory.save(new Meal(dateTime, description, calories));
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                log.debug("redirect to meals");
                resp.sendRedirect("meals");
                break;

            default:
                log.debug("no parameters, redirect to meals");
                resp.sendRedirect("meals");
                break;
        }
    }
}
