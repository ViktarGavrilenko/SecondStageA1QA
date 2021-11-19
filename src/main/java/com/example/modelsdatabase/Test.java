package com.example.modelsdatabase;

import java.util.Map;

import static com.example.modelsdatabase.Project.getIdProject;
import static com.example.utils.MySqlUtils.sendSqlQuery;

public class Test {
    private static final String NAME = "name";
    private static final String STATUS_ID = "status_id";
    private static final String METHOD_NAME = "method_name";
    private static final String PROJECT_ID = "project_id";
    private static final String PROJECT_NAME = "project_name";
    private static final String SESSION_ID = "session_id";
    private static final String START_TIME = "start_time";
    private static final String END_TIME = "end_time";
    private static final String ENV = "env";
    private static final String BROWSER = "browser";
    private static final String AUTHOR_ID = "NULL";

    private static final String INSERT_STR = "INSERT INTO  test (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES " +
            "('%s', %s, '%s', %s, %s, '%s', '%s', '%s', '%s')";

    public static void addDataInTestTable(Map<String, String> data) {
        String str = String.format(INSERT_STR, NAME, STATUS_ID, METHOD_NAME, PROJECT_ID,
                SESSION_ID, START_TIME, END_TIME, ENV, BROWSER,
                data.get(NAME), data.get(STATUS_ID), data.get(METHOD_NAME), getIdProject(data.get(PROJECT_NAME)),
                data.get(SESSION_ID), data.get(START_TIME), data.get(END_TIME), data.get(ENV), data.get(BROWSER));
        sendSqlQuery(str);
    }
}
