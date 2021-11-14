package pageobject;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.PostWall;
import models.UploadPhoto;
import org.openqa.selenium.Dimension;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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
    protected static final String ID_USER = TEST_DATA_FILE.getValue("/idUser").toString();
    protected static final String NAME_USER = TEST_DATA_FILE.getValue("/nameUser").toString();
    protected static final String ID_PHOTO = TEST_DATA_FILE.getValue("/idPhoto").toString();
    private static LoginPage loginPage = new LoginPage();
    private static MyPage myPage = new MyPage();

    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().goTo(DEFAULT_URL);
        getBrowser().setWindowSize(defaultSize.width, defaultSize.height);
        Logger.getInstance().info("Check if the page is loaded " + DEFAULT_URL);
    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }

    @Test(description = "Testing the LoginPageTest")
    public void testVkApi() {
        assertTrue(loginPage.isFieldForPhoneOrEmail(), "Main page VK didn't load");

        loginPage.cleanAndTypePhoneOrEmail(PHONE_OR_EMAIL);
        loginPage.cleanAndTypePassword(PASSWORD);
        loginPage.clickLoginButton();
        myPage.clickLinkMyPage();
        assertTrue(loginPage.isFieldPageUserName(), "You did not go to your page");

        Map<Object, Object> data = new HashMap<>();
        data.put("photo", Paths.get(PATH_PHOTO));
        UploadPhoto up = getUploadPhoto(ID_USER, data);

        String message = generateRandomText();
        PostWall postWall = writePostOnWall(message);
        String idPost = postWall.response.post_id;
        assertTrue(myPage.isPostOnPage(message, idPost, ID_USER, NAME_USER), "Record id not received");

        String newMessage = generateRandomText();
        editPostOnPageAndAddPhoto(newMessage, idPost, ID_USER, ID_PHOTO);

/*
        assertTrue(myPage.isWritingOnPageAndAddPhoto(newMessage, idWriting, ID_USER, ID_PHOTO),
                "The record has not changed or the photo does not match the uploaded one");
*/
        String comment = generateRandomText();
        createComment(comment, idPost, ID_USER);
        assertTrue(myPage.isCommentAdd(idPost, ID_USER), "Comment on the post on the wall was not found");
        myPage.likePostOnWall(idPost, ID_USER);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(isLikeFromUser(ID_USER, idPost).response.items.stream().anyMatch(ID_USER::equals),
                "There is no like from the desired user");
        assertEquals(deletePostOnWall(ID_USER, idPost).response, 1, "Post not deleted");
    }
}
