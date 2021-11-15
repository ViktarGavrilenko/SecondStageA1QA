package pageobject;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.Photo;
import models.UploadPhoto;
import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;

import static Utils.StringUtils.generateRandomText;
import static Utils.VkApiUtils.*;
import static aquality.selenium.browser.AqualityServices.getBrowser;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginPageTest {
    protected static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    protected static final String DEFAULT_URL = CONFIG_FILE.getValue("/mainPage").toString();
    private static final String PATH_PHOTO = System.getProperty("user.dir") +
            TEST_DATA_FILE.getValue("/photo").toString();

    protected final Dimension defaultSize = new Dimension(1280, 1024);

    private static final String PHONE_OR_EMAIL = TEST_DATA_FILE.getValue("/login").toString();
    private static final String PASSWORD = TEST_DATA_FILE.getValue("/password").toString();
    protected static final int ID_USER = Integer.parseInt(TEST_DATA_FILE.getValue("/idUser").toString());
    protected static final String ID_PHOTO = TEST_DATA_FILE.getValue("/idPhoto").toString();
    private static LoginPage loginPage = new LoginPage();
    private static MyPage myPage = new MyPage();

    @Test(description = "Testing the LoginPageTest")
    public void testVkApi() {
        getBrowser().goTo(DEFAULT_URL);
        getBrowser().setWindowSize(defaultSize.width, defaultSize.height);
        Logger.getInstance().info("Check if the page is loaded " + DEFAULT_URL);
        assertTrue(loginPage.isFieldForPhoneOrEmail(), "Main page VK didn't load");

        loginPage.logToVkPage(PHONE_OR_EMAIL, PASSWORD);

        myPage.clickLinkMyPage();
        assertTrue(loginPage.isFieldPageUserName(), "You did not go to your page");

        String message = generateRandomText();
        int idPost = writePostOnWall(message).response.post_id;
        String idUserPost = String.format("%s_%s", ID_USER, idPost);

        assertTrue(myPage.isPostOnPage(message, idUserPost, ID_USER), "Record id not received");

        UploadPhoto uploadPhoto = getUploadPhoto(PATH_PHOTO);

        Photo photo = savePhoto(ID_USER, uploadPhoto.photo, uploadPhoto.server, uploadPhoto.hash);

        String newMessage = generateRandomText();
        editPostOnPageAndAddPhoto(newMessage, idPost, photo.response.get(0).owner_id, photo.response.get(0).id);

        assertTrue(myPage.isWritingOnPageAndIsAddPhoto(newMessage, idUserPost, ID_USER, photo.response.get(0).id),
                "The record has not changed or the photo does not match the uploaded one");

        String comment = generateRandomText();
        createComment(comment, ID_USER, idPost);

        assertTrue(myPage.isCommentAdd(idUserPost, ID_USER), "Comment on the post on the wall was not found");

        myPage.likePostOnWall(idUserPost);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(isLikeFromUser(ID_USER, idPost).response.items.stream().anyMatch(n -> n == ID_USER),
                "There is no like from the desired user");

        assertEquals(deletePostOnWall(ID_USER, idPost).response, 1, "Post not deleted");

        assertTrue(myPage.isPostDelete(idUserPost), "Post not deleted");

        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}