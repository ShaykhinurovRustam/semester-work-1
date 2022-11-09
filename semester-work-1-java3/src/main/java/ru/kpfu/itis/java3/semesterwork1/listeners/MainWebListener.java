package ru.kpfu.itis.java3.semesterwork1.listeners;

import ru.kpfu.itis.java3.semesterwork1.dao.AnswerDao;
import ru.kpfu.itis.java3.semesterwork1.dao.QuestionDao;
import ru.kpfu.itis.java3.semesterwork1.dao.UserDao;
import ru.kpfu.itis.java3.semesterwork1.db.DBConnectionProvider;
import ru.kpfu.itis.java3.semesterwork1.exceptions.DBException;
import ru.kpfu.itis.java3.semesterwork1.exceptions.PropertyLoadException;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Properties;

@WebListener
public class MainWebListener implements ServletContextListener {
    private String url;
    private String username;
    private String password;
    private String driver;
    private String dbName;

    private void initProperties() {
        Properties prop = new Properties();
        try (InputStreamReader reader = new InputStreamReader((getClass().getResourceAsStream("/app.properties")))) {
            prop.load(reader);
            this.url = prop.getProperty("dbUrl");
            this.username = prop.getProperty("dbUsername");
            this.password = prop.getProperty("dbPassword");
            this.driver = prop.getProperty("dbDriver");
            this.dbName = prop.getProperty("dbName");
        } catch (IOException e) {
            throw new PropertyLoadException("Cannot get configuration params", e);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initProperties();
        try {
            Connection connection = initDbConnection();
            sce.getServletContext().setAttribute("userDao", new UserDao(connection));
            sce.getServletContext().setAttribute("questionDao", new QuestionDao(connection));
            sce.getServletContext().setAttribute("answerDao", new AnswerDao(connection));

            boolean files = new File("../images").mkdirs();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

    }

    private Connection initDbConnection() throws DBException {
        try {
            Class.forName(driver);
            return DBConnectionProvider.getInstance(url, dbName, username, password);
        } catch (ClassNotFoundException e) {
            throw new DBException("Cannot init connection with db", e);
        }
    }
}
