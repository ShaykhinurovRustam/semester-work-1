package ru.kpfu.itis.java3.semesterwork1.servlets;

import ru.kpfu.itis.java3.semesterwork1.dao.UserDao;
import ru.kpfu.itis.java3.semesterwork1.entity.User;
import ru.kpfu.itis.java3.semesterwork1.exceptions.DBException;
import ru.kpfu.itis.java3.semesterwork1.validators.RegistrationInputValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private RegistrationInputValidator registrationInputValidator;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        registrationInputValidator = new RegistrationInputValidator();
        userDao = (UserDao) getServletContext().getAttribute("userDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Registration");
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (!registrationInputValidator.validate(username, password)) {
            req.setAttribute("errorText", registrationInputValidator.getMessage());
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
            return;
        }
        try {
            if (userDao.containsUser(username)) {
                req.setAttribute("errorText", "username must be unique");
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
                return;
            }
            userDao.addUser(username, password);
            User user = userDao.getUserByUsername(username);
            req.getSession().setAttribute("userId", user.getId());
            req.getSession().setAttribute("name", user.getUsername());
            req.getSession().setAttribute("role", user.getRole());
            resp.sendRedirect(getServletContext().getContextPath() + "/profile");
        } catch (DBException e) {
            System.out.println(e.getMessage());
            req.setAttribute("errorText", "Something wrong with DB");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
        }
    }
}
