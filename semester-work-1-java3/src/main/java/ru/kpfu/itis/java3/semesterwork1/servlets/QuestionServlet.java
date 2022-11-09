package ru.kpfu.itis.java3.semesterwork1.servlets;

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
import java.util.*;

@WebServlet("/questions/question")
public class QuestionServlet extends HttpServlet {
    private AnswerDao answerDao;
    private QuestionDao questionDao;
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        answerDao = (AnswerDao) getServletContext().getAttribute("answerDao");
        questionDao = (QuestionDao) getServletContext().getAttribute("questionDao");
        userDao = (UserDao) getServletContext().getAttribute("userDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Answers");
        int questionId = Integer.parseInt(req.getParameter("id"));
        try {
            List<String> usernames = new ArrayList<>();
            List<Answer> answerList = answerDao.getAnswersList(questionId);
            String questionUsername = userDao.getUserById(questionDao.getQuestionById(questionId).getUserId()).getUsername();
            Answer bestAnswer = null;
            for (Answer answer : answerList) {
                answerDao.updateAnswerLikes(answer.getId());
                answer.setLikes(answerDao.getAnswerLikesCount(answer.getId()));
                usernames.add(userDao.getUserById(answer.getUserId()).getUsername());
                if (answer.isBest()) bestAnswer = answer;
            } answerList.sort(Comparator.comparing(Answer::getLikes, Comparator.reverseOrder()));

            req.setAttribute("questionUsername", questionUsername);
            req.setAttribute("question", questionDao.getQuestionById(questionId));
            req.setAttribute("usernames", usernames);
            req.setAttribute("answersList", answerList);
            req.setAttribute("bestAnswer", bestAnswer);
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/question.jsp").forward(req, resp);
        } catch (DBException e) {
            req.setAttribute("errorText", "Something wrong with DB");
            getServletContext().getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, resp);
        }

    }
}
