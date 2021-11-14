package Utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.*;

import java.util.Map;

import static Utils.ApiUtils.sendGet;
import static Utils.ApiUtils.sendPost;
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
    private static final String WALL_UPLOAD_SERVER = "%sphotos.getWallUploadServer?group_id=%s%s";


    public static PostWall writePostOnWall(String message) {
        String apiRequest = String.format(WALL_POST, START_API_VK, message, VERSION_AND_TOKEN);
        return getResponseWall(sendGet(apiRequest));
    }

    public static UploadServer getWallUploadServer(String idUser) {
        String apiRequest = String.format(WALL_UPLOAD_SERVER, START_API_VK, idUser, VERSION_AND_TOKEN);
        return getResponseUploadServer(sendGet(apiRequest));
    }

    public static UploadPhoto getUploadPhoto(String idUser, Map<Object, Object> data) {
        return getResponseUploadPhoto(sendPost(getWallUploadServer(idUser).response.upload_url, data));
    }

    public static PostWall editPostOnPageAndAddPhoto(String message, String idPost, String idUser, String idPhoto) {
        String apiRequest =
                String.format(WALL_EDIT, START_API_VK, message, idUser, idPost, idUser, idPhoto, VERSION_AND_TOKEN);
        return getResponseWall(sendGet(apiRequest));
    }

    public static CommentOnPostOnWall createComment(String comment, String idPost, String idUser) {
        String apiRequest = String.format(WALL_CREATE, START_API_VK, idUser, idPost, comment, VERSION_AND_TOKEN);
        return getResponseCommentOnPage(sendGet(apiRequest));
    }

    public static LikeOnPost isLikeFromUser(String idUser, String idPost) {
        String apiRequest = String.format(LIKES_GET_LIST, START_API_VK, idUser, idPost, VERSION_AND_TOKEN);
        return getResponseLikeOnComment(sendGet(apiRequest));
    }

    public static DeletePostOnWall deletePostOnWall(String idUser, String idPost) {
        String apiRequest = String.format(WALL_DELETE, START_API_VK, idUser, idPost, VERSION_AND_TOKEN);
        return getResponseDeletePostOnWall(sendGet(apiRequest));
    }
}
