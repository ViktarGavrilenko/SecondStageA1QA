package com.example.databasequeries;

import static com.example.utils.MySqlUtils.getIdAndAddIfNot;

public class ProjectTableQueries {
    private static final String INSERT_STR = "INSERT INTO  project (name) VALUES (%s)";
    private static final String SELECT_STR = "SELECT id FROM project WHERE name = '%s'";

    public static int getIdProject(String name) {
        String insertStr = String.format(INSERT_STR, name);
        String selectStr = String.format(SELECT_STR, name);
        return getIdAndAddIfNot(insertStr, selectStr);
    }
}