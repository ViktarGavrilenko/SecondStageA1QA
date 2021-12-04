package com.example.databasequeries;

import static com.example.utils.ArithmeticUtils.getRandomNumberFromOneToMaxValue;
import static com.example.utils.MySqlUtils.getIdAndAddIfNot;
import static com.example.utils.StringUtils.getCurrentDate;

public class SessionTableQueries {
    private static final String INSERT_STR = "INSERT INTO session (session_key, created_time, build_number) " +
            "VALUES ('%s', '%s', %s)";
    private static final String SELECT_STR = "SELECT id FROM session WHERE session_key = '%s'";
    private static final int MAX_BUILD_NUMBER = 20;

    public static int getIdSession(String session_key) {
        String insertQuery = String.format
                (INSERT_STR, session_key, getCurrentDate(), getRandomNumberFromOneToMaxValue(MAX_BUILD_NUMBER));
        String selectQuery = String.format(SELECT_STR, session_key);
        return getIdAndAddIfNot(insertQuery, selectQuery);
    }
}