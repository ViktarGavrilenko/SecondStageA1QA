package com.example.modelsdatabase;

import com.example.utils.Const;

import static com.example.utils.MySqlUtils.sendSqlQuery;
import static com.example.utils.StringUtils.addSlashes;

public class LogTable extends Const {
    private static final String INSERT_STR = "INSERT INTO log (%s) VALUES (%s)";

    public static void addLog(String textLog, int isException, int testId) {
        String columns = String.format("%s, %s, %s", COLUMN_CONTENT, COLUMN_IS_EXCEPTION, COLUMN_TEST_ID);
        String values = String.format(
                "'%s', %s, %s", addSlashes(textLog), isException, testId);
        String insertQuery = String.format(INSERT_STR, columns, values);
        sendSqlQuery(insertQuery);
    }
}

