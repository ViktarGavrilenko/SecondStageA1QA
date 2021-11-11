package com.example;

import aquality.selenium.core.logging.Logger;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static com.example.utils.APIUtils.sendGet;

public class PostFromGETRequestMissingTest extends BaseTest {

    @Test(description = "Testing PostFromGETRequestMissingTest")
    public void testPostFromGETRequestMissing() {
        response = sendGet(String.valueOf(url.append(MAIN_URL).append(POSTS).append("/").append(POST_ONE_HUNDRED_FIFTY)));
        Logger.getInstance().info("Checking status code");
        assertEquals(response.statusCode(), HttpStatus.SC_NOT_FOUND,
                "Status code is not " + HttpStatus.SC_NOT_FOUND);

        Logger.getInstance().info("Checking status code");
        assertEquals(response.body(), "{}", "Response body is not empty");
    }
}