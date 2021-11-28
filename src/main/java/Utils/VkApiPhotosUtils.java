package Utils;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Photo;
import models.UploadPhoto;
import models.UploadServer;
import org.apache.hc.core5.http.HttpEntity;

import java.net.http.HttpResponse;
import java.util.Map;

import static Utils.ApiUtils.*;
import static Utils.StringUtils.buildFormDataFromMap;

public class VkApiPhotosUtils {
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    private static final String START_API_VK = CONFIG_FILE.getValue("/startApiVk").toString() + "%s?%s";
    private static final String WALL_UPLOAD_SERVER = "photos.getWallUploadServer";
    private static final String SAVE_WALL_PHOTO = "photos.saveWallPhoto";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String NOT_DESERIALIZE = "Could not deserialize: ";

    public static UploadServer getUrlWallUploadServer(Map<String, String> data) {
        String apiRequest = String.format(START_API_VK, WALL_UPLOAD_SERVER, buildFormDataFromMap(data));
        HttpResponse<String> response = sendGet(apiRequest);
        try {
            return MAPPER.readValue(response.body(), UploadServer.class);
        } catch (JsonProcessingException e) {
            Logger.getInstance().error(NOT_DESERIALIZE + e);
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static Photo savePhoto(int idUser, String pathFile, Map<String, String> data, String nameFieldPhoto) {
        UploadPhoto uploadPhoto = getUploadPhoto(pathFile, data, nameFieldPhoto);
        data.put("user_id", String.valueOf(idUser));
        data.put("photo", uploadPhoto.photo);
        data.put("server", String.valueOf(uploadPhoto.server));
        data.put("hash", uploadPhoto.hash);

        String apiRequest = String.format(START_API_VK, SAVE_WALL_PHOTO, buildFormDataFromMap(data));
        HttpResponse<String> response = sendGet(apiRequest);
        try {
            return MAPPER.readValue(response.body(), Photo.class);
        } catch (JsonProcessingException e) {
            Logger.getInstance().error(NOT_DESERIALIZE + e);
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }

    public static UploadPhoto getUploadPhoto(String pathFile, Map<String, String> data, String nameFieldPhoto) {
        HttpEntity entity = sendPost(getUrlWallUploadServer(data).response.upload_url, pathFile, nameFieldPhoto);
        String res = convertHttpEntityIntoJson(entity);
        try {
            return MAPPER.readValue(res, UploadPhoto.class);
        } catch (JsonProcessingException e) {
            Logger.getInstance().error(NOT_DESERIALIZE + e);
            throw new IllegalArgumentException(NOT_DESERIALIZE, e);
        }
    }
}
