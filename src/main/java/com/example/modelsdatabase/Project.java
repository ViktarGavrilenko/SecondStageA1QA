package com.example.modelsdatabase;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.utils.MySqlUtils.sendSelectQuery;
import static com.example.utils.MySqlUtils.sendSqlQuery;

public class Project {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String INSERT_STR = "INSERT INTO  project (%s) VALUES ('%s')";
    private static final String SELECT_STR = "SELECT %s FROM project WHERE name = '%s'";

    public static int getIdProject(String name) {
        String insertStr = String.format(INSERT_STR, NAME, name);
        String selectStr = String.format(SELECT_STR, ID, name);
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
            throw new IllegalArgumentException("Sql query failed...", e);
        }
    }
}
