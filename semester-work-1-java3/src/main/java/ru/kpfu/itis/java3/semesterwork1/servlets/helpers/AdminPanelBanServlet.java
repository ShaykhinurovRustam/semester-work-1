package ru.kpfu.itis.java3.semesterwork1.servlets.helpers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.java3.semesterwork1.dao.UserDao;
import ru.kpfu.itis.java3.semesterwork1.entity.User;
import ru.kpfu.itis.java3.semesterwork1.exceptions.DBException;

import java.io.IOException;

@WebServlet("/panel/ban")
public class AdminPanelBanServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) getServletContext().getAttribute("userDao");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = null;
        try {
            userDao.setBanStatus(Integer.parseInt(req.getParameter("id")), 1);
        } catch (DBException e) {
            req.setAttribute("errorText", "Something wrong with DB");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(getServletContext().getContextPath() + "/panel");
    }
}
