package com.example.databasequeries;

import aquality.selenium.core.logging.Logger;
import com.example.databasemodels.TestTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.databasequeries.AuthorTableQueries.getIdAuthor;
import static com.example.databasequeries.ProjectTableQueries.getIdProject;
import static com.example.utils.ArithmeticUtils.getRandomNumberFromOneToMaxValue;
import static com.example.utils.DatabaseConst.*;
import static com.example.utils.MySqlUtils.sendSelectQuery;
import static com.example.utils.MySqlUtils.sendSqlQuery;
import static com.example.utils.StringUtils.addSlashes;
import static com.example.utils.StringUtils.getProjectName;

public class TestTableQueries {
    private static final String INSERT_STR = "INSERT INTO test (name, status_id, method_name, project_id, session_id, " +
            "start_time, end_time, env, browser, author_id) VALUES ('%s', %s, '%s', %s, %s, '%s', %s, '%s', '%s', %s)";
    private static final String SELECT_STR_SEARCH = "SELECT * FROM test WHERE id LIKE '%%%s%%' limit 10";
    private static final String SELECT_MAX_ID = "SELECT max(id) FROM test";
    private static final String SELECT_BY_ID = "SELECT * FROM test WHERE id = %s";
    private static final String DELETE_BY_ID = "DELETE FROM test WHERE id = %s";

    private static final int NUMBER_NINE = 9;
    private static final int NUMBER_ELEVEN = 11;

    public ArrayList<TestTable> getListWithTwoNumbersRepeating(String authorProject, String login, String email) {
        String twoNumberRepeating = String.valueOf(getRandomNumberFromOneToMaxValue(NUMBER_NINE) * NUMBER_ELEVEN);
        String query = String.format(SELECT_STR_SEARCH, twoNumberRepeating);
        ResultSet resultSet = sendSelectQuery(query);
        ArrayList<TestTable> listTests = new ArrayList<>();
        try {
            while (resultSet.next()) {
                TestTable test = new TestTable();
                test.name = resultSet.getString(COLUMN_NAME);
                test.status_id = resultSet.getInt(COLUMN_STATUS_ID);
                test.method_name = resultSet.getString(COLUMN_METHOD_NAME);
                test.project_id = getIdProject(getProjectName());
                test.session_id = resultSet.getInt(COLUMN_SESSION_ID);
                test.start_time = resultSet.getTimestamp(COLUMN_START_TIME);
                test.end_time = resultSet.getTimestamp(COLUMN_END_TIME);
                test.env = resultSet.getString(COLUMN_ENV);
                test.browser = resultSet.getString(COLUMN_BROWSER);
                test.author_id = getIdAuthor(authorProject, login, email);
                listTests.add(test);
            }
        } catch (SQLException e) {
            Logger.getInstance().error(SQL_QUERY_FAILED + e);
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
        return listTests;
    }

    public void addDataInTestTable(TestTable test) {
        String statusId = "null";
        if (test.status_id != 0) {
            statusId = String.valueOf(test.status_id);
        }

        String endTime = "null";
        if (test.end_time != null) {
            endTime = String.format("'%s'", test.end_time);
        }

        String query = String.format(INSERT_STR, addSlashes(test.name), statusId, test.method_name, test.project_id,
                test.session_id, test.start_time, endTime, test.env, test.browser, test.author_id);
        sendSqlQuery(query);
    }

    public ArrayList<Integer> copyDataWithNewProjectAndAuthor(ArrayList<TestTable> listTests) {
        ArrayList<Integer> id_tests = new ArrayList<>();
        for (TestTable listTest : listTests) {
            addDataInTestTable(listTest);
            id_tests.add(getMaxIdTestTable());
        }
        return id_tests;
    }

    public int getMaxIdTestTable() {
        ResultSet resultSet = sendSelectQuery(SELECT_MAX_ID);
        try {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            Logger.getInstance().error(SQL_QUERY_FAILED + e);
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }

    public void deleteTest(int testId) {
        String query = String.format(DELETE_BY_ID, testId);
        sendSqlQuery(query);
    }

    public boolean isDataDelete(int testId) {
        String query = String.format(SELECT_BY_ID, testId);
        ResultSet resultSet = sendSelectQuery(query);
        try {
            return !resultSet.next();
        } catch (SQLException e) {
            Logger.getInstance().error(SQL_QUERY_FAILED + e);
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }

    public TestTable getTestById(int testId) {
        TestTable test = new TestTable();
        String query = String.format(SELECT_BY_ID, testId);
        ResultSet resultSet = sendSelectQuery(query);
        try {
            if (resultSet.next()) {
                test.name = resultSet.getString(COLUMN_NAME);
                test.status_id = resultSet.getInt(COLUMN_STATUS_ID);
                test.method_name = resultSet.getString(COLUMN_METHOD_NAME);
                test.project_id = resultSet.getInt(COLUMN_PROJECT_ID);
                test.session_id = resultSet.getInt(COLUMN_SESSION_ID);
                test.start_time = resultSet.getTimestamp(COLUMN_START_TIME);
                test.end_time = resultSet.getTimestamp(COLUMN_END_TIME);
                test.env = resultSet.getString(COLUMN_ENV);
                test.browser = resultSet.getString(COLUMN_BROWSER);
                test.author_id = resultSet.getInt(COLUMN_AUTHOR_ID);
            }
            return test;
        } catch (SQLException e) {
            Logger.getInstance().error(SQL_QUERY_FAILED + e);
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }
}