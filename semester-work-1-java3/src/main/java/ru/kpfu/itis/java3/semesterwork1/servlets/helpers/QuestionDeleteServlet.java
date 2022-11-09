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

@WebServlet("/questions/question_delete")
public class QuestionDeleteServlet extends HttpServlet {
    private UserDao userDao;
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) getServletContext().getAttribute("userDao");
        questionDao = (QuestionDao) getServletContext().getAttribute("questionDao");
        answerDao = (AnswerDao) getServletContext().getAttribute("answerDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int questionId = Integer.parseInt(req.getParameter("questionId"));
        try {
            if (questionDao.getQuestionById(questionId).getUserId() != (int) req.getSession().getAttribute("userId")) {
                req.setAttribute("errorText", "You cannot operate with not your questions");
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
                return;
            }
            for (Answer answer : answerDao.getAnswersList(questionId)) {
                int likesCount = answerDao.getAnswerLikesCount(answer.getId());
                answerDao.deleteLikes(answer.getId());
                answerDao.deleteAnswer(answer.getId());
                if (answer.isBest()) {
                    userDao.updateRating(answer.getUserId(), -UserDao.BEST_ANSWER_MARKED_COST);
                }
                userDao.updateRating(answer.getUserId(), -(likesCount * UserDao.LIKE_COST));
            }
            questionDao.deleteQuestion(questionId);
            resp.sendRedirect(getServletContext().getContextPath() + "/questions");
        } catch (DBException e) {
            req.setAttribute("errorText", "Something wrong with DB");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
        }
    }
}
