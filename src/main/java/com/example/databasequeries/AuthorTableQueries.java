package com.example.databasequeries;

import static com.example.utils.MySqlUtils.getIdAndAddIfNot;

public class AuthorTableQueries {
    private static final String INSERT_STR = "INSERT INTO author (name, login, email) VALUES ('%s', '%s', '%s')";
    private static final String SELECT_STR = "SELECT id FROM author WHERE name = '%s'";

    public static int getIdAuthor(String authorName, String authorLogin, String authorEmail) {
        String insertStr = String.format(INSERT_STR, authorName, authorLogin, authorEmail);
        String selectStr = String.format(SELECT_STR, authorName);
        return getIdAndAddIfNot(insertStr, selectStr);
    }
}