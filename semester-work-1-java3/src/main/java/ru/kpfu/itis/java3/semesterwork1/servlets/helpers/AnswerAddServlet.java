package ru.kpfu.itis.java3.semesterwork1.servlets.helpers;

import ru.kpfu.itis.java3.semesterwork1.dao.AnswerDao;
import ru.kpfu.itis.java3.semesterwork1.exceptions.DBException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/questions/add_answer")
public class AnswerAddServlet extends HttpServlet {
    private AnswerDao answerDao;

    @Override
    public void init() throws ServletException {
        answerDao = (AnswerDao) getServletContext().getAttribute("answerDao");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int questionId = Integer.parseInt(req.getParameter("questionId"));
        int userId = Integer.parseInt(req.getParameter("userId"));
        String text = req.getParameter("text");
        if (text.isEmpty()) {
            req.setAttribute("errorText", "Empty text field");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
            return;
        }
        try {
            answerDao.addAnswer(questionId, userId, text);
        } catch (DBException e) {
            req.setAttribute("errorText", "Something wrong with DB");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(getServletContext().getContextPath() + "/questions/question?id=" + questionId);
    }
}
