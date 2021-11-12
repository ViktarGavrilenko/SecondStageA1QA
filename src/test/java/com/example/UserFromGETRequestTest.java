package com.example;

import aquality.selenium.core.logging.Logger;
import com.example.models.User;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static com.example.apiresponses.ProcessingApiResponses.getUser;
import static com.example.utils.APIUtils.sendGet;
import static com.example.utils.JsonUtils.getUserFromJsonFile;

public class UserFromGETRequestTest extends BaseTest {

    @Test(description = "Testing JSONPlaceholderTest")
    public void testJSONPlaceholder() {
        response = sendGet(String.valueOf(url.append(MAIN_URL).append(USERS).append("/").append(FIFTH_USER)));
        User user = getUser(response);
        Logger.getInstance().info("Checking status code");
        assertEquals(response.statusCode(), HttpStatus.SC_OK, "Status code is not " + HttpStatus.SC_OK);

        Logger.getInstance().info("Checking user data");
        assertEquals(user, getUserFromJsonFile(JSON_OF_FIFTH_USER), "The user has incorrect data");
    }
}