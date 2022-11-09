package ru.kpfu.itis.java3.semesterwork1.dao;

import ru.kpfu.itis.java3.semesterwork1.entity.User;
import ru.kpfu.itis.java3.semesterwork1.exceptions.DBException;
import ru.kpfu.itis.java3.semesterwork1.utils.PasswordHashGenerator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public static int LIKE_COST = 1;
    public static int BEST_ANSWER_MARKED_COST = 10;
    private Connection conn;
    private PasswordHashGenerator hashProcessor;

    public UserDao(Connection conn) {
        this.conn = conn;
        hashProcessor = new PasswordHashGenerator();
    }

    public List<User> getUsersList() throws DBException {
        ArrayList<User> list = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from users")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("rating"),
                        rs.getString("role"),
                        rs.getInt("is_banned")));
            }
            return list;
        } catch (SQLException e) {
            throw new DBException("Cannot get users list", e);
        }
    }

    public List<User> getSortedUsersList() throws DBException {
        ArrayList<User> list = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from users order by rating desc")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("rating"),
                        rs.getString("role"),
                        rs.getInt("is_banned")));
            }
            return list;
        } catch (SQLException e) {
            throw new DBException("Cannot get users list", e);
        }
    }

    public User getUserByUsername(String username) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            stmt.setString(1, username);
            System.out.println(username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("rating"),
                        rs.getString("role"),
                        rs.getInt("is_banned"));
            }
            throw new DBException("not found user");
        } catch (SQLException exception) {
            throw new DBException("Cannot get user by username", exception);
        }
    }

    public User getUserById(int id) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("rating"),
                        rs.getString("role"),
                        rs.getInt("is_banned"));
            }
            throw new DBException("not found user");
        } catch (SQLException exception) {
            throw new DBException("Cannot get user by id", exception);
        }
    }

    public void setRating(int userId, int rating) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("update users set rating = ? where id = ?")) {
            stmt.setInt(1, rating);
            stmt.setInt(2, userId);
            stmt.execute();
        } catch (SQLException exception) {
            throw new DBException("Cannot set user rating", exception);
        }
    }

    public void setBanStatus(int userId, int ban_status) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("update users set is_banned = ? where id = ?")) {
            stmt.setInt(1, ban_status);
            stmt.setInt(2, userId);
            stmt.execute();
        } catch (SQLException exception) {
            throw new DBException("Cannot set user ban status", exception);
        }
    }

    public void addUser(String username, String password) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("insert into users (username, password) values (?, ?);")) {
            stmt.setString(1, username);
            stmt.setString(2, hashProcessor.generateHashedPassword(password));
            stmt.execute();
        } catch (SQLException exception) {
            throw new DBException("Cannot add user", exception);
        }
    }

    public void updateRating(int userId, int value) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("update users set rating = rating + ? where id = ?")) {
            stmt.setInt(1, value);
            stmt.setInt(2, userId);
            stmt.execute();
        } catch (SQLException ex) {
            throw new DBException("Cannot update user rating", ex);
        }
    }

    public void updateAvatar(String url, int id) {
        System.out.println(url);
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new URL(url).openStream());
            File file = new File("../images/" + id + ".png");
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean containsUser(String username) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("select * from users where username = ?")) {
            stmt.setString(1, username);
            return stmt.executeQuery().next();
        } catch (SQLException ex) {
            throw new DBException("Cannot check user exist", ex);
        }
    }

    public boolean checkAuth(String username, String password) throws DBException {
        try (PreparedStatement stmt = conn.prepareStatement("select * from users where username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return (rs.getString("password").equals(hashProcessor.generateHashedPassword(password)));
            }
            throw new DBException("Cannot check user password");
        } catch (SQLException ex) {
            throw new DBException("Cannot check user password", ex);
        }
    }

}
