package com.example;

import aquality.selenium.core.logging.Logger;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import static com.example.apiresponses.ProcessingApiResponses.getPost;
import static com.example.utils.APIUtils.sendGet;

public class PostFromGETRequestTest extends BaseTest {

    @Test(description = "Testing PostFromGETRequestTest")
    public void testPostFromGETRequest() {
        response = sendGet(String.valueOf(url.append(MAIN_URL).append(POSTS).append("/").append(POST_NINETY_NINE)));
        post = getPost(response);
        Logger.getInstance().info("Checking status code " + MAIN_URL);
        assertEquals(response.statusCode(), HttpStatus.SC_OK, "Status code is not " + HttpStatus.SC_OK);

        Logger.getInstance().info("Checking userId");
        assertEquals(post.userId, TENTH_USERID, "userId is not " + TENTH_USERID);

        Logger.getInstance().info("Checking id");
        assertEquals(post.id, POST_NINETY_NINE, "id is not " + POST_NINETY_NINE);

        Logger.getInstance().info("Checking if body value is not empty strings");
        assertFalse(post.body.isEmpty(), "Body value is empty strings");

        Logger.getInstance().info("Checking if title value is not empty strings");
        assertFalse(post.title.isEmpty(), "Title value is empty strings");
    }
}