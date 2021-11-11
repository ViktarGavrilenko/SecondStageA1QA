package com.example;

import aquality.selenium.core.logging.Logger;
import com.example.models.User;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.List;

import static com.example.resourcesjson.JSONPlaceholder.getUsersFromRequest;
import static com.example.utils.APIUtils.sendGet;
import static com.example.utils.JsonUtils.getUserFromJsonFile;
import static com.example.utils.JsonUtils.isJSONValid;

public class UsersFromGETRequestTest extends BaseTest {
    @Test(description = "Testing UsersFromGETRequestTest")
    public void testUsersFromGETRequest() {

        response = sendGet(String.valueOf(url.append(MAIN_URL).append(USERS)));
        List<User> usersList = getUsersFromRequest(response);
        Logger.getInstance().info("Checking status code");
        assertEquals(response.statusCode(), HttpStatus.SC_OK, "Status code is not " + HttpStatus.SC_OK);

        Logger.getInstance().info("Checking that the list of users is returned in JSON format");
        assertTrue(isJSONValid(response.body()), "The list of users did not return in JSON format");

        Logger.getInstance().info("Checking user data with id " + FIFTH_USER);
        assertEquals(usersList.get(FIFTH_USER - 1), getUserFromJsonFile(JSON_OF_FIFTH_USER),
                "The user has incorrect data");
    }
}