package com.example;

import aquality.selenium.core.logging.Logger;
import com.example.models.Post;
import com.example.models.User;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.apiresponses.ProcessingApiResponses.*;
import static com.example.utils.JsonUtils.getUserFromJsonFile;
import static com.example.utils.StringUtils.generateRandomText;
import static org.testng.Assert.*;

public class ApiRequestTest {
    private static final String ID_FIELD_NAME = "id";
    private static final String RANDOM_TITLE = generateRandomText();
    private static final String RANDOM_BODY = generateRandomText();

    private static final String JSON_OF_FIFTH_USER = "/fifthUser.json";

    private static final int POST_ONE_HUNDRED_FIFTY = 150;
    private static final int FIFTH_USER = 5;
    private static final int TENTH_USERID = 10;
    private static final int POST_NINETY_NINE = 99;

    @Test(description = "Testing get posts")
    public void testGetPosts() {
        List<Post> posts = getPosts(HttpStatus.SC_OK);
        Logger.getInstance().info("Checking if the list is sorted in ascending order");
        assertEquals(posts, posts.stream()
                .sorted(Comparator.comparingInt(Post::getId))
                .collect(Collectors.toList()), "The list is not sorted in ascending order (by id)");
    }

    @Test(description = "Testing get post")
    public void testGetPost() {
        Post post = getPost(HttpStatus.SC_OK, POST_NINETY_NINE);

        Logger.getInstance().info("Checking userId");
        assertEquals(post.userId, TENTH_USERID, String.format("userId is not %s", TENTH_USERID));

        Logger.getInstance().info("Checking id");
        assertEquals(post.id, POST_NINETY_NINE, String.format("id is not %s", POST_NINETY_NINE));

        Logger.getInstance().info("Checking if body value is not empty strings");
        assertFalse(post.body.isEmpty(), "Body value is empty strings");

        Logger.getInstance().info("Checking if title value is not empty strings");
        assertFalse(post.title.isEmpty(), "Title value is empty strings");
    }

    @Test(description = "Testing get post is empty")
    public void testGetPostEmpty() {
        Post post = getPost(HttpStatus.SC_NOT_FOUND, POST_ONE_HUNDRED_FIFTY);
        assertNull(post, "Response body is not empty");
    }

    @Test(description = "Testing get post from POST request")
    public void testSendPost() {
        Map<Object, Object> data = new HashMap<>();
        data.put("title", RANDOM_TITLE);
        data.put("body", RANDOM_BODY);
        data.put("userId", 1);

        Post expectedPost = new Post();
        expectedPost.userId = 1;
        expectedPost.body = RANDOM_BODY;
        expectedPost.title = RANDOM_TITLE;

        Post post = sendPost(HttpStatus.SC_CREATED, ID_FIELD_NAME, data);
        Logger.getInstance().info("Checking post data");
        assertEquals(post, expectedPost, "The posts has incorrect data");
    }

    @Test(description = "Testing get users from GET Request")
    public void testGetUsers() {
        List<User> usersList = getUsers(HttpStatus.SC_OK);
        Logger.getInstance().info(String.format("Checking user data with id %s", FIFTH_USER));
        assertEquals(usersList.get(FIFTH_USER - 1), getUserFromJsonFile(JSON_OF_FIFTH_USER),
                "The user has incorrect data");
    }

    @Test(description = "Testing get user")
    public void testGetUser() {
        User user = getUser(HttpStatus.SC_OK, FIFTH_USER);
        Logger.getInstance().info("Checking user data");
        assertEquals(user, getUserFromJsonFile(JSON_OF_FIFTH_USER), "The user has incorrect data");
    }
}