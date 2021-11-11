package com.example;

import aquality.selenium.core.logging.Logger;
import com.example.models.Post;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.List;

import static com.example.resourcesjson.JSONPlaceholder.getPostsFromRequest;
import static com.example.utils.APIUtils.sendGet;
import static com.example.utils.CollectionUtils.isListSortedById;
import static com.example.utils.JsonUtils.isJSONValid;

public class PostsFromGETRequestTest extends BaseTest {
    @Test(description = "Testing PostsFromGETRequestTest")
    public void testPostsFromGETRequest() {
        response = sendGet(String.valueOf(url.append(MAIN_URL).append(POSTS)));
        List<Post> posts = getPostsFromRequest(response);
        Logger.getInstance().info("Checking status code");
        assertEquals(response.statusCode(), HttpStatus.SC_OK, "Status code is not " + HttpStatus.SC_OK);

        Logger.getInstance().info("Checking that the list of posts is returned in JSON format");
        assertTrue(isJSONValid(response.body()), "The list of posts did not return in JSON format");

        Logger.getInstance().info("Checking if the list is sorted in ascending order");
        assertTrue(isListSortedById(posts), "The list is not sorted in ascending order (by id)");
    }
}