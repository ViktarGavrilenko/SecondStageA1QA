package com.example.modelsdatabase;

import com.example.utils.Const;

import static com.example.utils.ArithmeticUtils.getRandomNumberFromOneToMaxValue;
import static com.example.utils.MySqlUtils.getIdAndAddIfNot;
import static com.example.utils.StringUtils.getCurrentDate;

public class SessionTable extends Const {
    private static final int MAX_BUILD_NUMBER = 20;

    private static final String INSERT_STR = "INSERT INTO session (%s) VALUES (%s)";
    private static final String SELECT_STR = "SELECT %s FROM session WHERE session_key = '%s'";

    public static int getIdSession(String session_key) {
        String columns = String.format("%s, %s, %s", COLUMN_SESSION_KEY, COLUMN_CREATED_TIME, COLUMN_BUILD_NUMBER);
        String values = String.format(
                "'%s', '%s', %s", session_key, getCurrentDate(), getRandomNumberFromOneToMaxValue(MAX_BUILD_NUMBER));
        String insertQuery = String.format(INSERT_STR, columns, values);
        String selectQuery = String.format(SELECT_STR, COLUMN_ID, session_key);
        return getIdAndAddIfNot(insertQuery, selectQuery);
    }
}