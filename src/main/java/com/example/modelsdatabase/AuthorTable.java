package com.example.modelsdatabase;

import com.example.utils.Const;

import static com.example.utils.MySqlUtils.getIdAndAddIfNot;

public class AuthorTable extends Const {
    private static final String INSERT_STR = "INSERT INTO author (%s) VALUES (%s)";
    private static final String SELECT_STR = "SELECT %s FROM author WHERE name = '%s'";

    public static int getIdAuthor(String authorName, String authorLogin, String authorEmail) {
        String columns = String.format("%s, %s, %s", COLUMN_NAME, COLUMN_LOGIN, COLUMN_EMAIL);
        String value = String.format(
                "'%s', '%s', '%s'", authorName, authorLogin, authorEmail);
        String insertStr = String.format(INSERT_STR, columns, value);
        String selectStr = String.format(SELECT_STR, COLUMN_ID, authorName);
        return getIdAndAddIfNot(insertStr, selectStr);
    }
}