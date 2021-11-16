package Utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.hc.core5.http.HttpEntity;

import java.io.InputStream;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static Utils.ApiUtils.sendGet;
import static Utils.ApiUtils.sendPost;

public class VkApiUtils {
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String START_API_VK = TEST_DATA_FILE.getValue("/startApiVk").toString();
    private static final String VERSION_API_VK = TEST_DATA_FILE.getValue("/v").toString();
    private static final String TOKEN = TEST_DATA_FILE.getValue("/token").toString();
    private static final String NAME_FIELD_PHOTO = "photo";

    private static final String VERSION_AND_TOKEN = String.format("&v=%s&access_token=%s", VERSION_API_VK, TOKEN);
    private static final String WALL_POST = "%swall.post?message=%s%s";
    private static final String WALL_EDIT = "%swall.edit?message=%s&owner_id=%s&post_id=%s&attachments=photo%s_%s%s";
    private static final String WALL_CREATE = "%swall.createComment?owner_id=%s&post_id=%s&message=%s%s";
    private static final String LIKES_GET_LIST = "%slikes.getList?type=post&owner_id=%s&item_id=%s%s";
    private static final String WALL_DELETE = "%swall.delete?owner_id=%s&post_id=%s%s";
    private static final String WALL_UPLOAD_SERVER = "%sphotos.getWallUploadServer?%s";
    private static final String SAVE_WALL_PHOTO = "%sphotos.saveWallPhoto?user_id=%s&photo=%s&server=%s&hash=%s%s";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String NOT_DESERIALIZE = "Could not deserialize: ";


    public static PostWall writePostOnWall(String message) {
        String apiRequest = String.format(WALL_POST, START_API_VK, message, VERSION_AND_TOKEN);
        HttpResponse<String> response = sendGet(apiRequest);
        try {
            return MAPPER.readValue(response.body(), PostWall.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static UploadServer getWallUploadServer() {
        String apiRequest = String.format(WALL_UPLOAD_SERVER, START_API_VK, VERSION_AND_TOKEN);
        HttpResponse<String> response = sendGet(apiRequest);
        try {
            return MAPPER.readValue(response.body(), UploadServer.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static UploadPhoto getUploadPhoto(String pathFile) {
        HttpEntity entity = sendPost(getWallUploadServer().response.upload_url, pathFile, NAME_FIELD_PHOTO);

        try {
            String res;
            InputStream inStream;
            inStream = entity.getContent();
            byte[] bytes;
            bytes = IOUtils.toByteArray(inStream);
            res = new String(bytes, StandardCharsets.UTF_8);
            inStream.close();

            return MAPPER.readValue(res, UploadPhoto.class);
        } catch (Exception e) {
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static Photo savePhoto(int idUser, String pathFile) {
        UploadPhoto uploadPhoto = getUploadPhoto(pathFile);
        String apiRequest = String.format(
                SAVE_WALL_PHOTO, START_API_VK, idUser, URLEncoder.encode(uploadPhoto.photo, StandardCharsets.UTF_8),
                uploadPhoto.server, uploadPhoto.hash, VERSION_AND_TOKEN);
        HttpResponse<String> response = sendGet(apiRequest);
        try {
            return MAPPER.readValue(response.body(), Photo.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static void editPostOnPageAndAddPhoto(String message, int idPost, int idUser, int idPhoto) {
        String apiRequest =
                String.format(WALL_EDIT, START_API_VK, message, idUser, idPost, idUser, idPhoto, VERSION_AND_TOKEN);
        sendGet(apiRequest);
    }

    public static void createComment(String comment, int idUser, int idPost) {
        String apiRequest = String.format(WALL_CREATE, START_API_VK, idUser, idPost, comment, VERSION_AND_TOKEN);
        sendGet(apiRequest);
    }

    public static LikeOnPost isLikeFromUser(int idUser, int idPost) {
        String apiRequest = String.format(LIKES_GET_LIST, START_API_VK, idUser, idPost, VERSION_AND_TOKEN);
        HttpResponse<String> response = sendGet(apiRequest);
        try {
            return MAPPER.readValue(response.body(), LikeOnPost.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static void deletePostOnWall(int idUser, int idPost) {
        String apiRequest = String.format(WALL_DELETE, START_API_VK, idUser, idPost, VERSION_AND_TOKEN);
        HttpResponse<String> response = sendGet(apiRequest);
        try {
            if (MAPPER.readValue(response.body(), DeletePostOnWall.class).response != 1) {
                throw new IllegalArgumentException("Error while deleting record");
            }
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }
}