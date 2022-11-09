package ru.kpfu.itis.java3.semesterwork1.dao;

import ru.kpfu.itis.java3.semesterwork1.entity.Question;
import ru.kpfu.itis.java3.semesterwork1.exceptions.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao {
    private Connection conn;

    public QuestionDao(Connection connection) {
        this.conn = connection;
    }

    public void addQuestion(int userId, String title, String description) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("insert into questions (title, description, user_id) values (?, ?, ?)")) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setInt(3, userId);
            stmt.execute();
        } catch (SQLException ex) {
            throw new DBException("Cannot add question", ex);
        }
    }

    public List<Question> getQuestionsList() throws DBException {
        ArrayList<Question> list = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from questions order by id desc")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Question(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("user_id")));
            }
            return list;
        } catch (SQLException e) {
            throw new DBException("Cannot get questions list", e);
        }
    }

    public Question getQuestionById(int id) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from questions where id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Question(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("user_id"));
            }
            throw new DBException("not found question");
        } catch (SQLException e) {
            throw new DBException("Cannot get question by id", e);
        }
    }

    public void deleteQuestion(int id) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("delete from questions where id = ?")) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new DBException("Cannot delete question", e);
        }
    }

    public void updateQuestion(int id, String title, String description) throws DBException {
        if (description.isEmpty()) {
            try (PreparedStatement stmt = conn.prepareStatement("update questions set title = ? where id = ?")) {
                stmt.setString(1, title);
                stmt.setInt(2, id);
                stmt.execute();
            } catch (SQLException e) {
                throw new DBException("Cannot update question");
            }
            return;
        }
        try (PreparedStatement stmt = conn.prepareStatement("update questions set title = ?, description = ? where id = ?")) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setInt(3, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new DBException("Cannot update question");
        }
    }
}
