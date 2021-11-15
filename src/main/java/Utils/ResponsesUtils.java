package Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.hc.core5.http.HttpEntity;

import java.io.InputStream;
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

    public static UploadPhoto getResponseUploadPhoto(HttpEntity entity) {
        String res = null;
        try {
            InputStream instream = null;
            instream = entity.getContent();

            byte[] bytes = new byte[0];

            bytes = IOUtils.toByteArray(instream);

            res = new String(bytes, "UTF-8");

            instream.close();

            return MAPPER.readValue(res, UploadPhoto.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not deserialize: ", e);
        }
    }

    public static Photo getResponseSavePhoto(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), Photo.class);
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