package com.example.utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import java.sql.*;

public class MySqlUtils {
    protected static final ISettingsFile MYSQL_CONFIG_FILE = new JsonSettingsFile("mysqlConfig.json");
    private static final String DB_HOST = MYSQL_CONFIG_FILE.getValue("/dbHost").toString();
    private static final String DB_PORT = MYSQL_CONFIG_FILE.getValue("/dbPort").toString();
    private static final String DB_USER = MYSQL_CONFIG_FILE.getValue("/dbUser").toString();
    private static final String DB_PASS = MYSQL_CONFIG_FILE.getValue("/dbPass").toString();
    private static final String DB_NAME = MYSQL_CONFIG_FILE.getValue("/dbName").toString();

    public static Connection getDbConnection() {
        String connectionString = String.format("jdbc:mysql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection(connectionString, DB_USER, DB_PASS);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Connection failed...", e);
        }
    }

    public static void sendSqlQuery(String sqlQuery) {
        Connection connection = getDbConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet sendSelectQuery(String sqlQuery) {
        Connection connection = getDbConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Sql query failed...", e);
        }
    }
}
