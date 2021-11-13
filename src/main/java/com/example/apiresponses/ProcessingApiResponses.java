package com.example.apiresponses;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.example.models.Post;
import com.example.models.User;
import com.example.utils.APIUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static com.example.utils.APIUtils.sendGet;
import static com.example.utils.JsonUtils.*;

public class ProcessingApiResponses {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");

    private static final String MAIN_URL = CONFIG_FILE.getValue("/mainPage").toString();
    private static final String POSTS = TEST_DATA_FILE.getValue("/posts").toString();
    private static final String USERS = TEST_DATA_FILE.getValue("/users").toString();

    private static final String NOT_DESERIALIZE = "Could not deserialize: ";

    public static List<Post> getPosts(int statusCode) {
        StringBuilder url = new StringBuilder();
        HttpResponse<String> response = sendGet(String.valueOf(url.append(MAIN_URL).append(POSTS)));
        isStatusCodeCorrect(statusCode, response.statusCode());

        if (!isJSONValid(response.body())) {
            throw new IllegalArgumentException("The list of posts did not return in JSON format");
        }

        try {
            return MAPPER.readValue(response.body(), new TypeReference<List<Post>>() {
            });
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static Post getPost(int statusCode, int numberPost) {
        StringBuilder url = new StringBuilder();
        HttpResponse<String> response =
                sendGet(String.valueOf(url.append(MAIN_URL).append(POSTS).append("/").append(numberPost)));
        isStatusCodeCorrect(statusCode, response.statusCode());

        if (response.body().equals("{}")) {
            return null;
        }

        try {
            return MAPPER.readValue(response.body(), Post.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static Post sendPost(int statusCode, String fieldName, Map<Object, Object> data) {
        StringBuilder url = new StringBuilder();
        HttpResponse<String> response = APIUtils.sendPost(String.valueOf(url.append(MAIN_URL).append(POSTS)), data);
        isStatusCodeCorrect(statusCode, response.statusCode());

        if (!isFieldInJSON(response.body(), fieldName)) {
            throw new IllegalArgumentException(String.format("%s is not present in response", fieldName));
        }

        try {
            return MAPPER.readValue(response.body(), Post.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static List<User> getUsers(int statusCode) {
        StringBuilder url = new StringBuilder();
        HttpResponse<String> response = sendGet(String.valueOf(url.append(MAIN_URL).append(USERS)));
        isStatusCodeCorrect(statusCode, response.statusCode());

        if (!isJSONValid(response.body())) {
            throw new IllegalArgumentException("The list of users did not return in JSON format");
        }

        try {
            return MAPPER.readValue(response.body(), new TypeReference<List<User>>() {
            });
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static User getUser(int statusCode, int numberUser) {
        StringBuilder url = new StringBuilder();
        HttpResponse<String> response =
                sendGet(String.valueOf(url.append(MAIN_URL).append(USERS).append("/").append(numberUser)));
        isStatusCodeCorrect(statusCode, response.statusCode());

        try {
            return MAPPER.readValue(response.body(), User.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }
}