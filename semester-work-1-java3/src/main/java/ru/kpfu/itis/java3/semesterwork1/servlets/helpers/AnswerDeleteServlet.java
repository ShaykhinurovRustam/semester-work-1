package ru.kpfu.itis.java3.semesterwork1.servlets.helpers;

import ru.kpfu.itis.java3.semesterwork1.dao.AnswerDao;
import ru.kpfu.itis.java3.semesterwork1.dao.UserDao;
import ru.kpfu.itis.java3.semesterwork1.entity.Answer;
import ru.kpfu.itis.java3.semesterwork1.exceptions.DBException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/questions/answer_delete")
public class AnswerDeleteServlet extends HttpServlet {
    private AnswerDao answerDao;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        answerDao = (AnswerDao) getServletContext().getAttribute("answerDao");
        userDao = (UserDao) getServletContext().getAttribute("userDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int answerId = Integer.parseInt(req.getParameter("answerId"));
        try {
            Answer answer = answerDao.getAnswerById(answerId);
            if (answer.getUserId() != (int) req.getSession().getAttribute("userId")) {
                req.setAttribute("errorText", "You cannot operate with not your answers");
                getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
                return;
            }
            int likesCount = answerDao.getAnswerLikesCount(answerId);
            answerDao.deleteLikes(answerId);
            answerDao.deleteAnswer(answerId);
            if (answer.isBest()) {
                userDao.updateRating(answer.getUserId(), -UserDao.BEST_ANSWER_MARKED_COST);
            }
            userDao.updateRating(answer.getUserId(), -(likesCount * UserDao.LIKE_COST));
            resp.sendRedirect(getServletContext().getContextPath() + "/questions/question?id=" + answer.getQuestion());
        } catch (DBException e) {
            req.setAttribute("errorText", "Something wrong with DB");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
        }
    }
}
