package com.example;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.example.models.Post;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.net.http.HttpResponse;

public class BaseTest extends Assert {
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    protected static final String JSON_OF_FIFTH_USER = "/fifthUser.json";

    protected static final String MAIN_URL = CONFIG_FILE.getValue("/mainPage").toString();
    protected static final String POSTS = TEST_DATA_FILE.getValue("/posts").toString();
    protected static final String USERS = TEST_DATA_FILE.getValue("/users").toString();

    protected static final int POST_ONE_HUNDRED_FIFTY = 150;
    protected static final int FIFTH_USER = 5;
    protected static final int TENTH_USERID = 10;
    protected static final int POST_NINETY_NINE = 99;

    protected static HttpResponse<String> response;
    protected static StringBuilder url = new StringBuilder();

    protected Post post = new Post();

    @BeforeMethod
    protected void beforeMethod() {
        url.setLength(0);
    }
}