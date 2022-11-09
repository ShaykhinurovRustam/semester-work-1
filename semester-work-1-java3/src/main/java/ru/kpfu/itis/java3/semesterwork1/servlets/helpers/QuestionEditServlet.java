package ru.kpfu.itis.java3.semesterwork1.servlets.helpers;

import ru.kpfu.itis.java3.semesterwork1.dao.QuestionDao;
import ru.kpfu.itis.java3.semesterwork1.entity.Question;
import ru.kpfu.itis.java3.semesterwork1.exceptions.DBException;
import ru.kpfu.itis.java3.semesterwork1.validators.QuestionInputValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/questions/question_edit")
public class QuestionEditServlet extends HttpServlet {
    private QuestionDao questionDao;
    private QuestionInputValidator inputValidator;


    @Override
    public void init() throws ServletException {
        questionDao = (QuestionDao) getServletContext().getAttribute("questionDao");
        inputValidator = new QuestionInputValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Editing question");
        try {
             Question question = questionDao.getQuestionById(Integer.parseInt(req.getParameter("questionId")));
             req.setAttribute("question", question);
        } catch (DBException e) {
            req.setAttribute("errorText", "Something wrong with DB");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
            return;
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/questionEdit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int questionId = Integer.parseInt(req.getParameter("questionId"));
        String newTitle = req.getParameter("title");
        String newDescription = req.getParameter("description");
        try {
            if (questionDao.getQuestionById(questionId).getUserId() != (int) req.getSession().getAttribute("userId")) {
                req.setAttribute("errorText", "You cannot operate with not your questions");
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
                return;
            }
            if (!inputValidator.validate(newTitle, newDescription)) {
                req.setAttribute("errorText", inputValidator.getMessage());
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
                return;
            }
            questionDao.updateQuestion(questionId, newTitle, newDescription);
            resp.sendRedirect(getServletContext().getContextPath() + "/questions");
        } catch (DBException e) {
            req.setAttribute("errorText", "Something wrong with DB");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
        }
    }
}
