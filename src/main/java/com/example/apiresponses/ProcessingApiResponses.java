package com.example.apiresponses;

import com.example.models.Post;
import com.example.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;
import java.util.List;

public class ProcessingApiResponses {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static List<Post> getPosts(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), new TypeReference<List<Post>>() {
            });
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not deserialize: ", e);
        }
    }

    public static Post getPost(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), Post.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not deserialize: ", e);
        }
    }

    public static List<User> getUsers(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), new TypeReference<List<User>>() {
            });
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not deserialize: ", e);
        }
    }

    public static User getUser(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), User.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not deserialize: ", e);
        }
    }
}