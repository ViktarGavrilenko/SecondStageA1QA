package pageobject;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.ResponsePhoto;
import org.openqa.selenium.Dimension;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static Utils.StringUtils.generateRandomText;
import static Utils.VkApiPhotosUtils.savePhoto;
import static Utils.VkApiUtils.*;
import static aquality.selenium.browser.AqualityServices.getBrowser;
import static org.testng.Assert.assertTrue;

public class LoginPageTest {
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String DEFAULT_URL = CONFIG_FILE.getValue("/mainPage").toString();
    private static final String PHONE_OR_EMAIL = CONFIG_FILE.getValue("/login").toString();
    private static final String PASSWORD = CONFIG_FILE.getValue("/password").toString();
    private static final String TOKEN = TEST_DATA_FILE.getValue("/token").toString();
    private static final String VERSION_API_VK = TEST_DATA_FILE.getValue("/v").toString();

    private static final String PATH_PHOTO = System.getProperty("user.dir") +
            TEST_DATA_FILE.getValue("/photo").toString();
    private static final String PATH_PHOTO_DOWNLOAD = System.getProperty("user.dir") +
            TEST_DATA_FILE.getValue("/photoDownload").toString();

    private static final String NAME_FIELD_PHOTO = "photo";
    private static final String VERSION = "v";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String MESSAGE = "message";
    private static final String OWNER_ID = "owner_id";
    private static final String POST_ID = "post_id";
    private static final String ATTACHMENTS = "attachments";
    private static final String ITEM_ID = "item_id";

    private final Dimension defaultSize = new Dimension(1280, 1024);

    private static final LoginPage LOGIN_PAGE = new LoginPage();
    private static final WallPage WALL_PAGE = new WallPage();

    @Test(description = "Testing the LoginPageTest")
    public void testVkApi() {
        Logger.getInstance().info("Go to the site " + DEFAULT_URL);
        getBrowser().goTo(DEFAULT_URL);
        getBrowser().setWindowSize(defaultSize.width, defaultSize.height);
        Logger.getInstance().info("Check if the page is loaded " + DEFAULT_URL);

        Logger.getInstance().info("Log in");
        LOGIN_PAGE.logToVkPage(PHONE_OR_EMAIL, PASSWORD);

        Logger.getInstance().info("Go to 'My Page'");
        WALL_PAGE.clickLinkMyPage();

        Logger.getInstance().info
                ("Create a post with randomly generated text on the wall and get the post id from the response");
        String message = generateRandomText();

        Map<String, String> data = new HashMap<>();
        data.put(MESSAGE, message);
        data.put(VERSION, VERSION_API_VK);
        data.put(ACCESS_TOKEN, TOKEN);

        int idPost = writePostOnWall(data).response.post_id;

        Logger.getInstance().info
                ("Checking if a post appeared on the wall with the correct text from the correct user");
        int idUser = WALL_PAGE.getIdUser();
        String idUserPost = String.format("%s_%s", idUser, idPost);
        assertTrue(WALL_PAGE.isPostOnPage(message, idUserPost, idUser), "Record id not received");

        Logger.getInstance().info("Changing the text and add a picture to the post");
        String newMessage = generateRandomText();

        data.clear();
        data.put(VERSION, VERSION_API_VK);
        data.put(ACCESS_TOKEN, TOKEN);

        ResponsePhoto photo = savePhoto(idUser, PATH_PHOTO, data, NAME_FIELD_PHOTO).response.get(0);
        data.clear();
        data.put(VERSION, VERSION_API_VK);
        data.put(ACCESS_TOKEN, TOKEN);
        data.put(MESSAGE, newMessage);
        data.put(OWNER_ID, String.valueOf(photo.owner_id));
        data.put(POST_ID, String.valueOf(idPost));
        data.put(ATTACHMENTS, NAME_FIELD_PHOTO + photo.owner_id + "_" + photo.id);
        editPostOnWallAndAddPhoto(data);

        Logger.getInstance().info("Checking that the message text has changed and the uploaded picture has been added");
        assertTrue(WALL_PAGE.isPostUpdate(newMessage, idUserPost) &&
                        WALL_PAGE.isAddPhoto(idUserPost, PATH_PHOTO, PATH_PHOTO_DOWNLOAD),
                "The record has not changed or the photo does not match the uploaded one");

        Logger.getInstance().info("Adding a comment to a post with random text");
        String comment = generateRandomText();
        data.clear();
        data.put(VERSION, VERSION_API_VK);
        data.put(ACCESS_TOKEN, TOKEN);
        data.put(MESSAGE, comment);
        data.put(OWNER_ID, String.valueOf(idUser));
        data.put(POST_ID, String.valueOf(idPost));
        createComment(data);

        Logger.getInstance().info("Checking that a comment from the correct user has been added to the desired post");
        assertTrue(WALL_PAGE.isCommentAdd(idUserPost, idUser), "Comment on the post on the wall was not found");

        Logger.getInstance().info("Leave a like to the post");
        WALL_PAGE.likePostOnWall(idUserPost);

        Logger.getInstance().info("Checking that the post has a like from the correct user");
        data.clear();
        data.put(VERSION, VERSION_API_VK);
        data.put(ACCESS_TOKEN, TOKEN);
        data.put("type", "post");
        data.put(OWNER_ID, String.valueOf(idUser));
        data.put(ITEM_ID, String.valueOf(idPost));

        try {
            AqualityServices.getConditionalWait().waitForTrue(() ->
                            isLikeFromUser(data).response.items.stream().anyMatch(n -> n == idUser),
                    "There is no like from the desired user");
        } catch (TimeoutException e) {
            throw new IllegalArgumentException(
                    "There is no like from the desired user", e);
        }

        Logger.getInstance().info("Delete the created entry");
        data.clear();
        data.put(VERSION, VERSION_API_VK);
        data.put(ACCESS_TOKEN, TOKEN);
        data.put(OWNER_ID, String.valueOf(idUser));
        data.put(POST_ID, String.valueOf(idPost));
        deletePostOnWall(data);

        Logger.getInstance().info("Checking that the entry has been deleted");
        assertTrue(WALL_PAGE.isPostDelete(idUserPost), "Post not deleted");
/*
        File photoDownload = new File(PATH_PHOTO_DOWNLOAD);
        File photoOriginal = new File(PATH_PHOTO);
        assertTrue(compareImage(photoDownload, photoOriginal), "ФОТКИ НЕ ОДИНАКОВЫЕ");
*/
    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}