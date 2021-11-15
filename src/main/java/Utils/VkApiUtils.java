package Utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static Utils.ApiUtils.sendGet;
import static Utils.ApiUtils.sendPostNew;
import static Utils.ResponsesUtils.*;

public class VkApiUtils {
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String START_API_VK = TEST_DATA_FILE.getValue("/startApiVk").toString();
    private static final String VERSION_API_VK = TEST_DATA_FILE.getValue("/v").toString();
    private static final String TOKEN = TEST_DATA_FILE.getValue("/token").toString();
    private static final String VERSION_AND_TOKEN = String.format("&v=%s&access_token=%s", VERSION_API_VK, TOKEN);

    private static final String WALL_POST = "%swall.post?message=%s%s";
    private static final String WALL_EDIT = "%swall.edit?message=%s&owner_id=%s&post_id=%s&attachments=photo%s_%s%s";
    private static final String WALL_CREATE = "%swall.createComment?owner_id=%s&post_id=%s&message=%s%s";
    private static final String LIKES_GET_LIST = "%slikes.getList?type=post&owner_id=%s&item_id=%s%s";
    private static final String WALL_DELETE = "%swall.delete?owner_id=%s&post_id=%s%s";
    private static final String WALL_UPLOAD_SERVER = "%sphotos.getWallUploadServer?%s";
    private static final String SAVE_WALL_PHOTO = "%sphotos.saveWallPhoto?user_id=%s&photo=%s&server=%s&hash=%s%s";


    public static PostWall writePostOnWall(String message) {
        String apiRequest = String.format(WALL_POST, START_API_VK, message, VERSION_AND_TOKEN);
        return getResponseWall(sendGet(apiRequest));
    }

    public static UploadServer getWallUploadServer() {
        String apiRequest = String.format(WALL_UPLOAD_SERVER, START_API_VK, VERSION_AND_TOKEN);
        return getResponseUploadServer(sendGet(apiRequest));
    }

    public static UploadPhoto getUploadPhoto(String pathFile) {
        return getResponseUploadPhoto(sendPostNew(getWallUploadServer().response.upload_url, pathFile));
    }

    public static Photo savePhoto(int idUser, String photo, int server, String hash) {
        String apiRequest = String.format(SAVE_WALL_PHOTO, START_API_VK, idUser,
                URLEncoder.encode(photo, StandardCharsets.UTF_8), server, hash, VERSION_AND_TOKEN);
        return getResponseSavePhoto(sendGet(apiRequest));
    }

    public static PostWall editPostOnPageAndAddPhoto(String message, int idPost, int idUser, int idPhoto) {
        String apiRequest =
                String.format(WALL_EDIT, START_API_VK, message, idUser, idPost, idUser, idPhoto, VERSION_AND_TOKEN);
        return getResponseWall(sendGet(apiRequest));
    }

    public static CommentOnPostOnWall createComment(String comment, int idUser, int idPost) {
        String apiRequest = String.format(WALL_CREATE, START_API_VK, idUser, idPost, comment, VERSION_AND_TOKEN);
        return getResponseCommentOnPage(sendGet(apiRequest));
    }

    public static LikeOnPost isLikeFromUser(int idUser, int idPost) {
        String apiRequest = String.format(LIKES_GET_LIST, START_API_VK, idUser, idPost, VERSION_AND_TOKEN);
        return getResponseLikeOnComment(sendGet(apiRequest));
    }

    public static DeletePostOnWall deletePostOnWall(int idUser, int idPost) {
        String apiRequest = String.format(WALL_DELETE, START_API_VK, idUser, idPost, VERSION_AND_TOKEN);
        return getResponseDeletePostOnWall(sendGet(apiRequest));
    }
}