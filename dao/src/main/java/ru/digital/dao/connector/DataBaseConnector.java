package ru.digital.dao.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnector {
    private final static String URL = "jdbc:postgresql://localhost:5432/testLesson4Digit";
    private final static String USER = "postgres";
    private final static String PASSWORD = "rootroot";

    static public Connection getConnect() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
