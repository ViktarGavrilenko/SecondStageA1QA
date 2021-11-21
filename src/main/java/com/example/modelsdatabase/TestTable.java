package com.example.modelsdatabase;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import java.sql.*;
import java.util.ArrayList;

import static com.example.modelsdatabase.Author.getIdAuthor;
import static com.example.modelsdatabase.Project.getIdProject;
import static com.example.utils.ArithmeticUtils.getRandomNumberFromOneToMaxValue;
import static com.example.utils.ArithmeticUtils.updateTime;
import static com.example.utils.MySqlUtils.*;
import static com.example.utils.StringUtils.addSlashes;
import static com.example.utils.StringUtils.getProjectName;

public class TestTable extends Const {
    public int id;
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

    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    protected static final String NAME_AUTHOR_PROJECT = TEST_DATA_FILE.getValue("/name").toString();

    private static final int NUMBER_OF_STATUSES = 3;

    private static final int NUMBER_NINE = 9;
    private static final int NUMBER_ELEVEN = 11;
    private static final String SQL_QUERY_FAILED = "Sql query failed...";

    private static final String INSERT_STR = "INSERT INTO  test (%s) VALUES (%s)";
    private static final String SELECT_STR_SEARCH = "SELECT * FROM test WHERE id LIKE '%%%s%%' limit 10";
    private static final String SELECT_MAX_ID = "SELECT max(id) FROM test";
    private static final String SELECT_BY_ID = "SELECT * FROM test WHERE id = %s";
    private static final String DELETE_BY_ID = "DELETE FROM test WHERE id = %s";
    private static final String UPDATE_BY_ID =
            "UPDATE test SET status_id = %s, start_time = '%s', end_time = '%s' WHERE id = %s";

    public ResultSet getListWithTwoNumbersRepeating() {
        String twoNumberRepeating = String.valueOf(getRandomNumberFromOneToMaxValue(NUMBER_NINE) * NUMBER_ELEVEN);
        String query = String.format(SELECT_STR_SEARCH, twoNumberRepeating);
        return sendSelectQuery(query);
    }

    public void addDataInTestTable(TestTable test) {
        String columns = String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                NAME, STATUS_ID, METHOD_NAME, PROJECT_ID, SESSION_ID, START_TIME, END_TIME, ENV, BROWSER, AUTHOR_ID);
        String values = "?, ?, ?, ?, ?, ?, ?, ?, ?, ?";

        String query = String.format(INSERT_STR, columns, values);

        Connection connection = getDbConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, addSlashes(test.name));
            if (test.status_id == 0) {
                statement.setNull(2, Types.INTEGER);
            } else {
                statement.setInt(2, test.status_id);
            }
            statement.setString(3, test.method_name);
            statement.setInt(4, test.project_id);
            statement.setInt(5, test.session_id);
            statement.setTimestamp(6, test.start_time);
            statement.setTimestamp(7, test.end_time);
            statement.setString(8, test.env);
            statement.setString(9, test.browser);
            statement.setInt(10, test.author_id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> copyDataWithNewProjectAndAuthor(ResultSet resultSet) {
        TestTable test = new TestTable();
        ArrayList<Integer> id_tests = new ArrayList<>();
        try {
            while (resultSet.next()) {
                test.name = resultSet.getString(NAME);
                test.status_id = resultSet.getInt(STATUS_ID);
                test.method_name = resultSet.getString(METHOD_NAME);
                test.project_id = getIdProject(getProjectName());
                test.session_id = resultSet.getInt(SESSION_ID);
                test.start_time = resultSet.getTimestamp(START_TIME);
                test.end_time = resultSet.getTimestamp(END_TIME);
                test.env = resultSet.getString(ENV);
                test.browser = resultSet.getString(BROWSER);
                test.author_id = getIdAuthor(NAME_AUTHOR_PROJECT);
                addDataInTestTable(test);
                id_tests.add(getMaxIdTestTable());
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
        return id_tests;
    }

    public int getMaxIdTestTable() {
        ResultSet resultSet = sendSelectQuery(SELECT_MAX_ID);
        try {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new IllegalArgumentException(SQL_QUERY_FAILED, e);
        }
    }

    public void simulateTest(int testId) {
        String query = String.format(SELECT_BY_ID, testId);
        ResultSet resultSet = sendSelectQuery(query);

        int statusId = getRandomNumberFromOneToMaxValue(NUMBER_OF_STATUSES);
        Timestamp newStartTime = null;
        Timestamp newEndTime = null;

        try {
            resultSet.next();
            while (statusId == resultSet.getInt(STATUS_ID)) {
                statusId = getRandomNumberFromOneToMaxValue(NUMBER_OF_STATUSES);
            }

            newStartTime = updateTime(resultSet.getTimestamp(START_TIME));
            newEndTime = updateTime(resultSet.getTimestamp(END_TIME));

            if (newStartTime.after(newEndTime)) {
                Timestamp temp = newEndTime;
                newEndTime = newStartTime;
                newStartTime = temp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = String.format(UPDATE_BY_ID, statusId, newStartTime, newEndTime, testId);
        sendSqlQuery(query);
    }

    public void deleteTest(int testId) {
        String query = String.format(DELETE_BY_ID, testId);
        sendSqlQuery(query);
    }
}

