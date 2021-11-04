package com.example.resourcesjson;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.example.models.Post;
import com.example.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import static com.example.utils.APIUtils.*;

public class JSONPlaceholder {
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String URL = CONFIG_FILE.getValue("/mainPage").toString();
    private static final String POSTS = TEST_DATA_FILE.getValue("/posts").toString();
    private static final String USERS = TEST_DATA_FILE.getValue("/users").toString();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public List<Post> getPostsFromGETRequest() throws Exception {
        sendGet(URL + POSTS);
        return MAPPER.readValue(getJsonAnswer(), new TypeReference<List<Post>>() {
        });
    }

    public boolean isListSortedById(List<Post> posts) {
        int index = posts.get(0).id;
        for (int i = 1; i < posts.size(); i++) {
            if (index > posts.get(i).id) {
                return false;
            } else {
                index = posts.get(i).id;
            }
        }
        return true;
    }

    public Post getPostFromGETRequest(int numberPost) throws Exception {
        sendGet(URL + POSTS + "/" + numberPost);
        return MAPPER.readValue(getJsonAnswer(), Post.class);
    }

    public Post getPostFromPOSTRequest(Map<Object, Object> data) throws Exception {
        sendPost(URL + POSTS, data);
        return MAPPER.readValue(getJsonAnswer(), Post.class);
    }

    public List<User> getUsersFromGETRequest() throws Exception {
        sendGet(URL + USERS);
        return MAPPER.readValue(getJsonAnswer(), new TypeReference<List<User>>() {
        });
    }

    public User getUserFromGETRequest(int numberUser) throws Exception {
        sendGet(URL + USERS + "/" + numberUser);
        return MAPPER.readValue(getJsonAnswer(), User.class);
    }

    public boolean isFieldInJSON(String jsonAnswer, String nameField) {
        JSONObject jsonObj = new JSONObject(jsonAnswer);
        return jsonObj.has(nameField);
    }

    public boolean isJSONValid(String jsonAnswer) {
        try {
            new JSONObject(jsonAnswer);
        } catch (JSONException ex) {
            try {
                new JSONArray(jsonAnswer);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}