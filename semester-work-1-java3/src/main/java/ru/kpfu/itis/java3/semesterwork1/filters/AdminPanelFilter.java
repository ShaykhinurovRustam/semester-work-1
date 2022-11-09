package ru.kpfu.itis.java3.semesterwork1.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/panel")
public class AdminPanelFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpSession session = req.getSession();
        if (session.getAttribute("userId") == null) {
            String errorText = "Not access to not guests (please authorize)";
            req.setAttribute("errorText", errorText);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
            return;
        }
        if (!session.getAttribute("role").equals("admin")) {
            String errorText = "Not access to your access level";
            req.setAttribute("errorText", errorText);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
            return;
        }

        chain.doFilter(req, res);
    }
}
