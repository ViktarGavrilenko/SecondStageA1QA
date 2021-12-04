package com.example.databasequeries;

import aquality.selenium.core.logging.Logger;
import com.example.databasemodels.TestTable;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.utils.DatabaseConst.SQL_QUERY_FAILED;
import static com.example.utils.MySqlUtils.sendSelectQuery;
import static com.example.utils.MySqlUtils.sendSqlQuery;

public class TestTableQueries {
    private static final String INSERT_STR = "INSERT INTO test (name, status_id, method_name, project_id, session_id, " +
            "start_time, end_time, env, browser, author_id) VALUES ('%s', %s, '%s', %s, %s, '%s', '%s', '%s', '%s', %s)";
    private static final String SELECT_STR = "SELECT * FROM test WHERE name = '%s' AND status_id = %s AND method_name" +
            " = '%s' AND project_id = %s AND session_id = %s AND start_time = '%s' AND end_time = '%s' AND env = '%s'" +
            " AND browser = '%s' AND author_id = %s";
    private static final String SELECT_MAX_ID = "SELECT max(id) FROM test";

    public static void addDataInTestTable(TestTable test) {
        String query = String.format(INSERT_STR, test.name, test.status_id, test.method_name, test.project_id,
                test.session_id, test.start_time, test.end_time, test.env, test.browser, test.author_id);
        sendSqlQuery(query);
    }

    public static boolean isDataInDatabase(TestTable test) {
        String query = String.format(SELECT_STR, test.name, test.status_id, test.method_name, test.project_id,
                test.session_id, test.start_time, test.end_time, test.env, test.browser, test.author_id);
        try {
            return sendSelectQuery(query).next();
        } catch (SQLException e) {
            Logger.getInstance().error(SQL_QUERY_FAILED + e);
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }

    public static int getMaxIdTestTable() {
        ResultSet resultSet = sendSelectQuery(SELECT_MAX_ID);
        try {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            Logger.getInstance().error(SQL_QUERY_FAILED + e);
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }
}
