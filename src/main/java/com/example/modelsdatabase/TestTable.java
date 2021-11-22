package com.example.modelsdatabase;

import com.example.utils.Const;

import java.sql.SQLException;
import java.sql.Timestamp;

import static com.example.utils.MySqlUtils.sendSelectQuery;
import static com.example.utils.MySqlUtils.sendSqlQuery;

public class TestTable extends Const {
    public String name;
    public int status_id;
    public String method_name;
    public int project_id;
    public int session_id;
    public Timestamp start_time;
    public Timestamp end_time;
    public String env;
    public String browser;
    public int author_id;

    private static final String INSERT_STR = "INSERT INTO  test (%s) VALUES (%s)";
    private static final String SELECT_STR = "SELECT * FROM test WHERE %s";

    private static final String SQL_QUERY_FAILED = "Sql query failed...";

    public static void addDataInTestTable(TestTable test) {
        String columns = String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                COLUMN_NAME, COLUMN_STATUS_ID, COLUMN_METHOD_NAME, COLUMN_PROJECT_ID, COLUMN_SESSION_ID,
                COLUMN_START_TIME, COLUMN_END_TIME, COLUMN_ENV, COLUMN_BROWSER, COLUMN_AUTHOR_ID);
        String values = String.format("'%s', %s, '%s', %s, %s, '%s', '%s', '%s', '%s', %s",
                test.name, test.status_id, test.method_name, test.project_id, test.session_id,
                test.start_time, test.end_time, test.env, test.browser, test.author_id);

        String query = String.format(INSERT_STR, columns, values);
        sendSqlQuery(query);
    }

    public static boolean isDataInDatabase(TestTable test){
        String partQuery = String.format("%s = '%s' AND %s = %s AND %s = '%s' AND %s = %s AND %s = %s AND %s = '%s' " +
                "AND %s = '%s' AND %s = '%s' AND %s = '%s' AND %s = %s", COLUMN_NAME, test.name, COLUMN_STATUS_ID,
                test.status_id, COLUMN_METHOD_NAME, test.method_name, COLUMN_PROJECT_ID, test.project_id,
                COLUMN_SESSION_ID, test.session_id, COLUMN_START_TIME, test.start_time, COLUMN_END_TIME, test.end_time,
                COLUMN_ENV, test.env, COLUMN_BROWSER, test.browser, COLUMN_AUTHOR_ID, test.author_id);
        String query = String.format(SELECT_STR, partQuery);

        try {
            return sendSelectQuery(query).next();
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }
}