package pageobject;

import models.WallPost;
import org.testng.annotations.Test;

import static Utils.StringUtils.generateRandomText;
import static Utils.VkApiUtils.writeNoteOnWall;
import static org.testng.Assert.assertTrue;

public class LoginPageTest extends BaseTest {
    private static final String PHONE_OR_EMAIL = TEST_DATA_FILE.getValue("/login").toString();
    private static final String PASSWORD = TEST_DATA_FILE.getValue("/password").toString();
    protected static final String ID_USER = TEST_DATA_FILE.getValue("/idUser").toString();
    protected static final String NAME_USER = TEST_DATA_FILE.getValue("/nameUser").toString();
    private static LoginPage loginPage = new LoginPage();
    private static MyPage myPage = new MyPage();

    @Test(description = "Testing the LoginPageTest")
    public void testLoginPage() {
        assertTrue(loginPage.isFieldForPhoneOrEmail(), "Main page VK didn't load");

        loginPage.cleanAndTypePhoneOrEmail(PHONE_OR_EMAIL);
        loginPage.cleanAndTypePassword(PASSWORD);
        loginPage.clickLoginButton();
        myPage.clickLinkMyPage();
        assertTrue(loginPage.isFieldPageUserName(), "You did not go to your page");

        String message = generateRandomText();
        WallPost wallPost = writeNoteOnWall(message);
        String idWriting = wallPost.response.post_id;
        System.out.println(idWriting);

        assertTrue(myPage.isWritingOnPage(message, idWriting, ID_USER, NAME_USER), "Record id not received");
    }
}
