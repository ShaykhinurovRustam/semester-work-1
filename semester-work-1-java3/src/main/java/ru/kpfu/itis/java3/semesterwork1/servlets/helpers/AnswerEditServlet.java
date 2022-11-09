package ru.kpfu.itis.java3.semesterwork1.servlets.helpers;

import ru.kpfu.itis.java3.semesterwork1.dao.AnswerDao;
import ru.kpfu.itis.java3.semesterwork1.entity.Answer;
import ru.kpfu.itis.java3.semesterwork1.exceptions.DBException;
import ru.kpfu.itis.java3.semesterwork1.validators.AnswerInputValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/questions/answer_edit")
public class AnswerEditServlet extends HttpServlet {
    private AnswerDao answerDao;
    private AnswerInputValidator inputValidator;

    @Override
    public void init() throws ServletException {
        answerDao = (AnswerDao) getServletContext().getAttribute("answerDao");
        inputValidator = new AnswerInputValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "editing answer");
        try {
            req.setAttribute("answer", answerDao.getAnswerById(Integer.parseInt(req.getParameter("answerId"))));
        } catch (DBException e) {
            req.setAttribute("errorText", "Something wrong with DB");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
            return;
        }
        getServletContext().getRequestDispatcher("/WEB-INF/jsp/answerEdit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int answerId = Integer.parseInt(req.getParameter("answerId"));
        String text = req.getParameter("newText");
        try {
            Answer answer = answerDao.getAnswerById(answerId);
            if (answer.getUserId() != (int) req.getSession().getAttribute("userId")) {
                req.setAttribute("errorText", "You cannot operate with not your answers");
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
                return;
            }
            if (!inputValidator.validate(text)) {
                req.setAttribute("errorText", inputValidator.getMessage());
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
                return;
            }
            int questionId = answerDao.getAnswerById(answerId).getQuestion();
            answerDao.updateAnswer(answerId, text);
            resp.sendRedirect(getServletContext().getContextPath() + "/questions/question?id=" + questionId);
        } catch (DBException e) {
            req.setAttribute("errorText", "Something wrong with DB");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
        }
    }
}
