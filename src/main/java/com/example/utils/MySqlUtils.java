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

    private static final String SQL_QUERY_FAILED = "Sql query failed...";

    private static Connection connection;

    private static Connection getDbConnection() {
        if (connection != null) {
            return connection;
        } else {
            String connectionString = String.format("jdbc:mysql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(connectionString, DB_USER, DB_PASS);
                return connection;
            } catch (ClassNotFoundException | SQLException e) {
                throw new IllegalArgumentException("Connection failed...", e);
            }
        }
    }

    public static void sendSqlQuery(String sqlQuery) {
        Connection connection = getDbConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet sendSelectQuery(String sqlQuery) {
        Connection connection = getDbConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            return statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }

    public static int getIdAndAddIfNot(String insertStr, String selectStr) {
        ResultSet resultSet = sendSelectQuery(selectStr);
        try {
            resultSet.next();
            if (!resultSet.isFirst()) {
                sendSqlQuery(insertStr);
            }
            resultSet = sendSelectQuery(selectStr);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}