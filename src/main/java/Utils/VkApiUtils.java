package Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.WallPost;

import java.net.http.HttpResponse;

public class VkApiUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static WallPost getResponseWall(HttpResponse<String> response) {
        try {
            return MAPPER.readValue(response.body(), WallPost.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Could not deserialize: ", e);
        }
    }
}
