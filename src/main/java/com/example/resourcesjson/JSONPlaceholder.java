package com.example.resourcesjson;

import com.example.models.Post;
import com.example.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;
import java.util.List;

public class JSONPlaceholder {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static List<Post> getPostsFromRequest(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), new TypeReference<List<Post>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Post getPostFromRequest(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), Post.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> getUsersFromRequest(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), new TypeReference<List<User>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User getUserFromRequest(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}