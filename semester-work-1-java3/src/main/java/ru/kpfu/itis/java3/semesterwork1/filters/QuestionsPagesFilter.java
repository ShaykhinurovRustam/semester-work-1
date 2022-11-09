package ru.kpfu.itis.java3.semesterwork1.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.kpfu.itis.java3.semesterwork1.dao.UserDao;
import ru.kpfu.itis.java3.semesterwork1.exceptions.DBException;

import java.io.IOException;

@WebFilter("/questions/*")
public class QuestionsPagesFilter extends HttpFilter {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) getServletContext().getAttribute("userDao");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpSession session = req.getSession();
        if (session.getAttribute("userId") == null) {
            req.setAttribute("errorText", "Questions is not available for unauthorized users");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
            return;
        } else {
            try {
                if (userDao.getUserById((Integer) session.getAttribute("userId")).isBanned()) {
                    req.setAttribute("errorText", "Questions is not available for banned users");
                    getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
                    return;
                }
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        }

        chain.doFilter(req, res);
    }
}
