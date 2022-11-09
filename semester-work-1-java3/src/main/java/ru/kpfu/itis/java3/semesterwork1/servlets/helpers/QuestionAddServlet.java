package ru.kpfu.itis.java3.semesterwork1.servlets.helpers;

import ru.kpfu.itis.java3.semesterwork1.dao.QuestionDao;
import ru.kpfu.itis.java3.semesterwork1.exceptions.DBException;
import ru.kpfu.itis.java3.semesterwork1.validators.QuestionInputValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/questions/add_question")
public class QuestionAddServlet extends HttpServlet {
    private QuestionDao questionDao;
    private QuestionInputValidator inputValidator;

    @Override
    public void init() throws ServletException {
        questionDao = (QuestionDao) getServletContext().getAttribute("questionDao");
        inputValidator = new QuestionInputValidator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        int userId = (int) req.getSession().getAttribute("userId");
        if (!inputValidator.validate(title, description)) {
            req.setAttribute("errorText", inputValidator.getMessage());
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
            return;
        }
        try {
            questionDao.addQuestion(userId, title, description);
            resp.sendRedirect(getServletContext().getContextPath() + "/questions");
        } catch (DBException e) {
            req.setAttribute("errorText", "Something wrong with DB");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
        }
    }
}
