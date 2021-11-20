package com.example.modelsdatabase;

import static com.example.utils.ArithmeticUtils.getRandomNumberFromOneToMaxValue;
import static com.example.utils.MySqlUtils.getIdAndAddIfNot;
import static com.example.utils.StringUtils.getCurrentDate;

public class Session {
    private static final String ID = "id";
    private static final String SESSION_KEY = "session_key";
    private static final String CREATED_TIME = "created_time";
    private static final String BUILD_NUMBER = "build_number";

    private static final int MAX_BUILD_NUMBER = 20;

    private static final String INSERT_STR = "INSERT INTO session (%s) VALUES (%s)";
    private static final String SELECT_STR = "SELECT %s FROM session WHERE session_key = '%s'";

    public static int getIdSession(String session_key) {
        String columns = String.format("%s, %s, %s", SESSION_KEY, CREATED_TIME, BUILD_NUMBER);
        String value = String.format(
                "'%s', '%s', %s", session_key, getCurrentDate(), getRandomNumberFromOneToMaxValue(MAX_BUILD_NUMBER));
        String insertStr = String.format(INSERT_STR, columns, value);
        String selectStr = String.format(SELECT_STR, ID, session_key);
        return getIdAndAddIfNot(insertStr, selectStr);
    }
}