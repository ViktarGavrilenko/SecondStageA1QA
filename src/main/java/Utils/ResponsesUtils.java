package Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;

import java.net.http.HttpResponse;

public class ResponsesUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static PostWall getResponseWall(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), PostWall.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not deserialize: ", e);
        }
    }

    public static UploadServer getResponseUploadServer(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), UploadServer.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not deserialize: ", e);
        }
    }

    public static UploadPhoto getResponseUploadPhoto(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), UploadPhoto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not deserialize: ", e);
        }
    }

    public static CommentOnPostOnWall getResponseCommentOnPage(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), CommentOnPostOnWall.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not deserialize: ", e);
        }
    }

    public static LikeOnPost getResponseLikeOnComment(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), LikeOnPost.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not deserialize: ", e);
        }
    }

    public static DeletePostOnWall getResponseDeletePostOnWall(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), DeletePostOnWall.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not deserialize: ", e);
        }
    }
}
