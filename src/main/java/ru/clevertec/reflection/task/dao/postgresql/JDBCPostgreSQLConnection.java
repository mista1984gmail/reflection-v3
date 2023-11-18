package ru.clevertec.reflection.task.dao.postgresql;

import ru.clevertec.reflection.task.config.LoadProperties;
import ru.clevertec.reflection.task.util.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCPostgreSQLConnection {

    public static Connection getConnection() throws SQLException {
        String url = LoadProperties.getProperty()
                                   .get(Constants.URL);
        String username = LoadProperties.getProperty()
                                        .get(Constants.USERNAME);
        String password = LoadProperties.getProperty()
                                        .get(Constants.PASSWORD);
        return DriverManager.getConnection(url, username, password);
    }

}