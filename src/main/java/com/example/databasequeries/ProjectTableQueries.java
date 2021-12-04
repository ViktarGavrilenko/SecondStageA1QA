package com.example.databasequeries;

import static com.example.utils.MySqlUtils.getIdAndAddIfNot;

public class ProjectTableQueries {
    private static final String INSERT_STR = "INSERT INTO project (name) VALUES (%s)";
    private static final String SELECT_STR = "SELECT id FROM project WHERE name = '%s'";

    public static int getIdProject(String name) {
        String insertQuery = String.format(INSERT_STR, name);
        String selectQuery = String.format(SELECT_STR, name);
        return getIdAndAddIfNot(insertQuery, selectQuery);
    }
}