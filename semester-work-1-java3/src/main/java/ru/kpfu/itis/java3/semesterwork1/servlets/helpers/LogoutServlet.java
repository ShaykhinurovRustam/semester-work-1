package ru.kpfu.itis.java3.semesterwork1.servlets.helpers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("userId");
        req.getSession().removeAttribute("role");
        req.getSession().removeAttribute("name");
        resp.sendRedirect(getServletContext().getContextPath() + "/");
    }
}
