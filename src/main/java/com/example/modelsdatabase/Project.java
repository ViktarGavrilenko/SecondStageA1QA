package com.example.modelsdatabase;

import static com.example.utils.MySqlUtils.getIdAndAddIfNot;

public class Project {
    private static final String ID = "id";
    private static final String NAME = "name";

    private static final String INSERT_STR = "INSERT INTO  project (%s) VALUES (%s)";
    private static final String SELECT_STR = "SELECT %s FROM project WHERE name = '%s'";

    public static int getIdProject(String name) {
        String insertStr = String.format(INSERT_STR, NAME, name);
        String selectStr = String.format(SELECT_STR, ID, name);
        return getIdAndAddIfNot(insertStr, selectStr);
    }
}
