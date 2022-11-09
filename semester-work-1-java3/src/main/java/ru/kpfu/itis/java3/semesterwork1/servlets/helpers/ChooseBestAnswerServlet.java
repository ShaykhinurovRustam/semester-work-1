package ru.kpfu.itis.java3.semesterwork1.servlets.helpers;

import ru.kpfu.itis.java3.semesterwork1.dao.AnswerDao;
import ru.kpfu.itis.java3.semesterwork1.dao.QuestionDao;
import ru.kpfu.itis.java3.semesterwork1.dao.UserDao;
import ru.kpfu.itis.java3.semesterwork1.entity.Answer;
import ru.kpfu.itis.java3.semesterwork1.exceptions.DBException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/questions/choose_best")
public class ChooseBestAnswerServlet extends HttpServlet {
    private AnswerDao answerDao;
    private UserDao userDao;
    private QuestionDao questionDao;

    @Override
    public void init() throws ServletException {
        answerDao = (AnswerDao) getServletContext().getAttribute("answerDao");
        userDao = (UserDao) getServletContext().getAttribute("userDao");
        questionDao = (QuestionDao) getServletContext().getAttribute("questionDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "choosing best answer");
        try {
            int questionId = Integer.parseInt(req.getParameter("questionId"));
            if (questionDao.getQuestionById(questionId).getUserId() != (int) req.getSession().getAttribute("userId")) {
                req.setAttribute("errorText", "You cannot operate with not your questions");
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
                return;
            }
            req.setAttribute("answersList", answerDao.getAnswersList(questionId));
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/chooseBestAnswer.jsp").forward(req, resp);
        } catch (DBException e) {
            req.setAttribute("errorText", e.getMessage());
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("bestAnswer") == null) {
            req.setAttribute("errorText", "Best answer not chosen");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
            return;
        }
        int newBestAnswerId = Integer.parseInt(req.getParameter("bestAnswer"));
        int questionId = Integer.parseInt(req.getParameter("questionId"));
        try {
            if (answerDao.haveBestAnswer(questionId)) {
                Answer bestAnswer = answerDao.getBestAnswer(questionId);
                userDao.updateRating(bestAnswer.getUserId(), -UserDao.BEST_ANSWER_MARKED_COST);
                answerDao.unmarkBestAnswer(questionId);
            }
            answerDao.setBestAnswer(newBestAnswerId);
            Answer newBestAnswer = answerDao.getAnswerById(newBestAnswerId);
            userDao.updateRating(newBestAnswer.getUserId(), +UserDao.BEST_ANSWER_MARKED_COST);
            resp.sendRedirect(getServletContext().getContextPath() + "/questions/question?id=" + questionId);
        } catch (DBException e) {
            req.setAttribute("errorText", "Something wrong with DB");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
        }
    }
}
