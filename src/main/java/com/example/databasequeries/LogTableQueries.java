package com.example.databasequeries;

import static com.example.utils.MySqlUtils.sendSqlQuery;
import static com.example.utils.StringUtils.addSlashes;

public class LogTableQueries {
    private static final String INSERT_STR = "INSERT INTO log (content, is_exception, test_id) VALUES ('%s', %s, %s)";

    public static void addLogInTestTable(String textLog, int isException, int testId) {
        String insertQuery = String.format(INSERT_STR, addSlashes(textLog), isException, testId);
        sendSqlQuery(insertQuery);
    }
}