package com.example.modelsdatabase;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.example.utils.Const;

import static com.example.utils.MySqlUtils.getIdAndAddIfNot;

public class AuthorTable extends Const {
    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String LOGIN_AUTHOR = TEST_DATA_FILE.getValue("/login").toString();
    private static final String EMAIL_AUTHOR = TEST_DATA_FILE.getValue("/email").toString();

    private static final String INSERT_STR = "INSERT INTO author (%s) VALUES (%s)";
    private static final String SELECT_STR = "SELECT %s FROM author WHERE name = '%s'";

    public static int getIdAuthor(String authorName) {
        String columns = String.format("%s, %s, %s", COLUMN_NAME, COLUMN_LOGIN, COLUMN_EMAIL);
        String values = String.format("'%s', '%s', '%s'", authorName, LOGIN_AUTHOR, EMAIL_AUTHOR);
        String insertQuery = String.format(INSERT_STR, columns, values);
        String selectQuery = String.format(SELECT_STR, COLUMN_ID, authorName);
        return getIdAndAddIfNot(insertQuery, selectQuery);
    }
}