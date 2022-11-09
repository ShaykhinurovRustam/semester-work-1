package ru.kpfu.itis.java3.semesterwork1.dao;

import ru.kpfu.itis.java3.semesterwork1.entity.Answer;
import ru.kpfu.itis.java3.semesterwork1.exceptions.DBException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerDao {
    private Connection conn;

    public AnswerDao(Connection conn) {
        this.conn = conn;
    }

    public void addAnswer(int questionId, int userId, String text) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("insert into answers (text, question, user_id) values (?, ?, ?)")) {
            stmt.setString(1, text);
            stmt.setInt(2, questionId);
            stmt.setInt(3, userId);
            stmt.execute();
        } catch (SQLException ex) {
            throw new DBException("Cannot add answer", ex);
        }
    }

    public List<Answer> getAnswersList(int questionId) throws DBException {
        ArrayList<Answer> list = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("select * from answers where is_best = true union all (select * from answers where question = ? except select * from answers where is_best = true order by likes desc)")) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(
                        new Answer(
                                rs.getInt("id"),
                                rs.getString("text"),
                                rs.getInt("question"),
                                rs.getInt("user_id"),
                                rs.getInt("likes"),
                                rs.getBoolean("is_best")
                        )
                );
            }
            return list;
        } catch (SQLException e) {
            throw new DBException("Cannot get answers list", e);
        }
    }

    public boolean like(int userId, int answerId) throws DBException {
        try {
            PreparedStatement stmt = conn.prepareStatement("select * from likes where user_id = ? and answer_id = ?");
            stmt.setInt(1, userId);
            stmt.setInt(2, answerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stmt = conn.prepareStatement("delete from likes where user_id = ? and answer_id = ?");
                stmt.setInt(1, userId);
                stmt.setInt(2, answerId);
                stmt.execute();
                return false;
            }
            stmt = conn.prepareStatement("insert into likes (user_id, answer_id) values (?, ?)");
            stmt.setInt(1, userId);
            stmt.setInt(2, answerId);
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            throw new DBException("Cannot set like", ex);
        }
    }

    public void updateAnswerLikes(int answerId) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("update answers set likes = (select count(*) from likes where answer_id = ?) where id = ?");) {
            stmt.setInt(1, answerId);
            stmt.setInt(2, answerId);
            stmt.execute();
        } catch (SQLException ex) {
            throw new DBException("Cannot update likes in answer", ex);
        }
    }

    public Answer getAnswerById(int id) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("select * from answers where id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Answer(rs.getInt("id"), rs.getString("text"), rs.getInt("question"), rs.getInt("user_id"), rs.getInt("likes"), rs.getBoolean("is_best"));
            }
            throw new DBException("Answer not find");
        } catch (SQLException ex) {
            throw new DBException("Cannot get answer", ex);
        }
    }

    public void deleteLikes(int answerId) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("delete from likes where answer_id = ?;")) {
            stmt.setInt(1, answerId);
            stmt.execute();
        } catch (SQLException e) {
            throw new DBException("Cannot delete answer likes", e);
        }
    }

    public void deleteAnswer(int id) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("delete from answers where id = ?");) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new DBException("Cannot delete answer", e);
        }
    }

    public int getAnswerLikesCount(int id) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("select count(*) from likes where answer_id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
            throw new DBException("Cannot get likes from answer");
        } catch (SQLException e) {
            throw new DBException("Cannot get likes from answer", e);
        }
    }

    public void updateAnswer(int id, String newText) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("update answers set text = ? where id = ?")) {
            stmt.setString(1, newText);
            stmt.setInt(2, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new DBException("Cannot update answer", e);
        }
    }

    public Answer getBestAnswer(int questionId) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("select * from answers where question = ? and is_best = true")) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Answer(rs.getInt("id"), rs.getString("text"), rs.getInt("question"), rs.getInt("user_id"), rs.getInt("likes"), rs.getBoolean("is_best"));
            }
            throw new DBException("Cannot get best answer");
        } catch (SQLException e) {
            throw new DBException("Cannot get best answer", e);
        }
    }

    public void setBestAnswer(int answerId) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("update answers set is_best = true where id = ?")) {
            stmt.setInt(1, answerId);
            stmt.execute();
        } catch (SQLException e) {
            throw new DBException("Cannot set best answer", e);
        }
    }

    public boolean haveBestAnswer(int questionId) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("select * from answers where question = ? and is_best = true")) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DBException("Cannot get best answer", e);
        }
    }

    public void unmarkBestAnswer(int questionId) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("update answers set is_best = false where is_best = true and question = ?")) {
            stmt.setInt(1, questionId);
            stmt.execute();
        } catch (SQLException e) {
            throw new DBException("Cannot get best answer", e);
        }
    }
}
