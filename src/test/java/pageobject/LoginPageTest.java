package pageobject;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.Photo;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;

import java.util.concurrent.TimeoutException;

import static Utils.StringUtils.generateRandomText;
import static Utils.VkApiUtils.*;
import static aquality.selenium.browser.AqualityServices.getBrowser;
import static org.testng.Assert.assertTrue;

public class LoginPageTest {
    private static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String DEFAULT_URL = CONFIG_FILE.getValue("/mainPage").toString();
    private static final String PHONE_OR_EMAIL = TEST_DATA_FILE.getValue("/login").toString();
    private static final String PASSWORD = TEST_DATA_FILE.getValue("/password").toString();
    private static final String PATH_PHOTO = System.getProperty("user.dir") +
            TEST_DATA_FILE.getValue("/photo").toString();
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

        Logger.getInstance().info("Go to \"My Page\"");
        WALL_PAGE.clickLinkMyPage();

        Logger.getInstance().info
                ("Create a post with randomly generated text on the wall and get the post id from the response");
        String message = generateRandomText();
        int idPost = writePostOnWall(message).response.post_id;

        Logger.getInstance().info
                ("Checking if a post appeared on the wall with the correct text from the correct user");
        int idUser = WALL_PAGE.getIdUser();
        String idUserPost = String.format("%s_%s", idUser, idPost);
        assertTrue(WALL_PAGE.isPostOnPage(message, idUserPost, idUser), "Record id not received");

        Logger.getInstance().info("Changing the text and add a picture to the post");
        String newMessage = generateRandomText();
        Photo photo = savePhoto(idUser, PATH_PHOTO);
        editPostOnPageAndAddPhoto(newMessage, idPost, photo.response.get(0).owner_id, photo.response.get(0).id);

        Logger.getInstance().info("Checking that the message text has changed and the uploaded picture has been added");
        assertTrue(WALL_PAGE.isWritingOnPageAndIsAddPhoto(newMessage, idUserPost, idUser, photo.response.get(0).id),
                "The record has not changed or the photo does not match the uploaded one");

        Logger.getInstance().info("Adding a comment to a post with random text");
        String comment = generateRandomText();
        createComment(comment, idUser, idPost);

        Logger.getInstance().info("Checking that a comment from the correct user has been added to the desired post");
        assertTrue(WALL_PAGE.isCommentAdd(idUserPost, idUser), "Comment on the post on the wall was not found");

        Logger.getInstance().info("Leave a like to the post");
        WALL_PAGE.likePostOnWall(idUserPost);

        Logger.getInstance().info("Checking that the post has a like from the correct user");
        try {
            AqualityServices.getConditionalWait().waitForTrue(() ->
                            isLikeFromUser(idUser, idPost).response.items.stream().anyMatch(n -> n == idUser),
                    "There is no like from the desired user");
        } catch (TimeoutException e) {
            throw new IllegalArgumentException(
                    "There is no like from the desired user", e);
        }

        Logger.getInstance().info("Delete the created entry");
        deletePostOnWall(idUser, idPost);

        Logger.getInstance().info("Checking that the entry has been deleted");
        assertTrue(WALL_PAGE.isPostDelete(idUserPost), "Post not deleted");

        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}