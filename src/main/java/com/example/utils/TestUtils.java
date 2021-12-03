package com.example.utils;

import aquality.selenium.core.logging.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static com.example.utils.ArithmeticUtils.getRandomNumberFromOneToMaxValue;
import static com.example.utils.ArithmeticUtils.updateTime;
import static com.example.utils.DatabaseColumnNames.*;
import static com.example.utils.DatabaseConst.SQL_EXCEPTION;
import static com.example.utils.MySqlUtils.sendSelectQuery;
import static com.example.utils.MySqlUtils.sendSqlQuery;

public class TestUtils {
    private static final int NUMBER_OF_STATUSES = 3;
    private static final String SELECT_BY_ID = "SELECT * FROM test WHERE id = %s";
    private static final String UPDATE_BY_ID =
            "UPDATE test SET status_id = %s, start_time = '%s', end_time = '%s' WHERE id = %s";

    public static void simulateTest(int testId) {
        String query = String.format(SELECT_BY_ID, testId);
        ResultSet resultSet = sendSelectQuery(query);

        int statusId = getRandomNumberFromOneToMaxValue(NUMBER_OF_STATUSES);
        Timestamp newStartTime = null;
        Timestamp newEndTime = null;

        try {
            resultSet.next();
            while (statusId == resultSet.getInt(status_id.toString())) {
                statusId = getRandomNumberFromOneToMaxValue(NUMBER_OF_STATUSES);
            }

            newStartTime = updateTime(resultSet.getTimestamp(start_time.toString()));
            newEndTime = updateTime(resultSet.getTimestamp(end_time.toString()));

            if (newStartTime.after(newEndTime)) {
                Timestamp temp = newEndTime;
                newEndTime = newStartTime;
                newStartTime = temp;
            }
        } catch (SQLException e) {
            Logger.getInstance().error(SQL_EXCEPTION + e);
        }

        query = String.format(UPDATE_BY_ID, statusId, newStartTime, newEndTime, testId);
        sendSqlQuery(query);
    }
}