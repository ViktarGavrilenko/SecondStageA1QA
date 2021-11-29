package Utils;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.DeletePostOnWall;
import models.LikeOnPost;
import models.PostWall;

import java.net.http.HttpResponse;
import java.util.Map;

import static Utils.ApiUtils.sendGet;
import static Utils.StringUtils.buildFormDataFromMap;

public class VkApiUtils {
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    private static final String START_API_VK = CONFIG_FILE.getValue("/startApiVk").toString() + "%s?%s";

    private static final String WALL_POST = "wall.post";
    private static final String WALL_EDIT = "wall.edit";
    private static final String WALL_CREATE = "wall.createComment";
    private static final String LIKES_GET_LIST = "likes.getList";
    private static final String WALL_DELETE = "wall.delete";

    private static final String NOT_DESERIALIZE = "Could not deserialize: ";
    private static final String ERROR_DELETE = "Error while deleting record";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static PostWall writePostOnWall(Map<String, String> data) {
        String apiRequest = String.format(START_API_VK, WALL_POST, buildFormDataFromMap(data));
        HttpResponse<String> response = sendGet(apiRequest);
        try {
            return MAPPER.readValue(response.body(), PostWall.class);
        } catch (JsonProcessingException e) {
            Logger.getInstance().error(NOT_DESERIALIZE + e);
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static void editPostOnWallAndAddPhoto(Map<String, String> data) {
        String apiRequest = String.format(START_API_VK, WALL_EDIT, buildFormDataFromMap(data));
        sendGet(apiRequest);
    }

    public static void createComment(Map<String, String> data) {
        String apiRequest = String.format(START_API_VK, WALL_CREATE, buildFormDataFromMap(data));
        sendGet(apiRequest);
    }

    public static LikeOnPost isLikeFromUser(Map<String, String> data) {
        String apiRequest = String.format(START_API_VK, LIKES_GET_LIST, buildFormDataFromMap(data));
        HttpResponse<String> response = sendGet(apiRequest);
        try {
            return MAPPER.readValue(response.body(), LikeOnPost.class);
        } catch (JsonProcessingException e) {
            Logger.getInstance().error(NOT_DESERIALIZE + e);
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static void deletePostOnWall(Map<String, String> data) {
        String apiRequest = String.format(START_API_VK, WALL_DELETE, buildFormDataFromMap(data));
        HttpResponse<String> response = sendGet(apiRequest);
        try {
            if (MAPPER.readValue(response.body(), DeletePostOnWall.class).response != 1) {
                Logger.getInstance().error(ERROR_DELETE);
                throw new IllegalArgumentException(ERROR_DELETE);
            }
        } catch (JsonProcessingException e) {
            Logger.getInstance().error(NOT_DESERIALIZE + e);
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }
}