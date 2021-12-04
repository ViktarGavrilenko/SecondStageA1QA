package com.example.databasequeries;

import static com.example.utils.MySqlUtils.getIdAndAddIfNot;

public class AuthorTableQueries {
    private static final String INSERT_STR = "INSERT INTO author (name,login, email) VALUES ('%s', '%s', '%s')";
    private static final String SELECT_STR = "SELECT id FROM author WHERE name = '%s'";

    public static int getIdAuthor(String authorName, String login, String email) {
        String insertQuery = String.format(INSERT_STR, authorName, login, email);
        String selectQuery = String.format(SELECT_STR, authorName);
        return getIdAndAddIfNot(insertQuery, selectQuery);
    }
}