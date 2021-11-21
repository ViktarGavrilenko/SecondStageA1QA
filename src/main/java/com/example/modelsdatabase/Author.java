package com.example.modelsdatabase;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import static com.example.utils.MySqlUtils.getIdAndAddIfNot;

public class Author extends Const {
    public int id;
    public String name;
    public String login;
    public String email;

    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String AUTHOR_LOGIN = TEST_DATA_FILE.getValue("/login").toString();
    private static final String AUTHOR_EMAIL = TEST_DATA_FILE.getValue("/email").toString();

    private static final String INSERT_STR = "INSERT INTO author (%s) VALUES (%s)";
    private static final String SELECT_STR = "SELECT %s FROM author WHERE name = '%s'";

    public static int getIdAuthor(String authorName) {
        String columns = String.format("%s, %s, %s", NAME, LOGIN, EMAIL);
        String value = String.format(
                "'%s', '%s', '%s'", authorName, AUTHOR_LOGIN, AUTHOR_EMAIL);
        String insertStr = String.format(INSERT_STR, columns, value);
        String selectStr = String.format(SELECT_STR, ID, authorName);
        return getIdAndAddIfNot(insertStr, selectStr);
    }
}

