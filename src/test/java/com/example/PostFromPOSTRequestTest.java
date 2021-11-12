package com.example;

import aquality.selenium.core.logging.Logger;
import com.example.models.Post;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.example.apiresponses.ProcessingApiResponses.getPost;
import static com.example.utils.APIUtils.sendPost;
import static com.example.utils.JsonUtils.isFieldInJSON;
import static com.example.utils.StringUtils.generateRandomText;

public class PostFromPOSTRequestTest extends BaseTest {
    private static final String ID_FIELD_NAME = "id";
    private static final String RANDOM_TITLE = generateRandomText();
    private static final String RANDOM_BODY = generateRandomText();

    @Test(description = "Testing PostFromPOSTRequestTest")
    public void testPostFromPOSTRequest() {
        Map<Object, Object> data = new HashMap<>();
        data.put("title", RANDOM_TITLE);
        data.put("body", RANDOM_BODY);
        data.put("userId", 1);

        Post expectedPost = new Post();
        expectedPost.userId = 1;
        expectedPost.body = RANDOM_BODY;
        expectedPost.title = RANDOM_TITLE;

        response = sendPost(String.valueOf(url.append(MAIN_URL).append(POSTS)), data);
        post = getPost(response);
        Logger.getInstance().info("Checking status code");
        assertEquals(response.statusCode(), HttpStatus.SC_CREATED,
                "Status code is not " + HttpStatus.SC_CREATED);

        Logger.getInstance().info("Checking if id is present in response");
        assertTrue(isFieldInJSON(response.body(), ID_FIELD_NAME),
                ID_FIELD_NAME + " is not present in response");

        Logger.getInstance().info("Checking post data");
        assertEquals(post, expectedPost, "The posts has incorrect data");
    }
}