package com.example.modelsdatabase;

import com.example.utils.Const;

import static com.example.utils.MySqlUtils.getIdAndAddIfNot;

public class ProjectTable extends Const {
    private static final String INSERT_STR = "INSERT INTO  project (%s) VALUES (%s)";
    private static final String SELECT_STR = "SELECT %s FROM project WHERE name = '%s'";

    public static int getIdProject(String name) {
        String insertStr = String.format(INSERT_STR, COLUMN_NAME, name);
        String selectStr = String.format(SELECT_STR, COLUMN_ID, name);
        return getIdAndAddIfNot(insertStr, selectStr);
    }
}