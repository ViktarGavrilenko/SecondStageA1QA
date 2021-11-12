package pageobject;

import models.WallPost;
import org.testng.annotations.Test;

import static Utils.ApiUtils.sendGet;
import static Utils.VkApiUtils.getResponseWall;
import static org.testng.Assert.assertTrue;

public class LoginPageTest extends BaseTest {
    private static final String PHONE_OR_EMAIL = TEST_DATA_FILE.getValue("/login").toString();
    private static final String PASSWORD = TEST_DATA_FILE.getValue("/password").toString();
    private static final String WALL_POST = TEST_DATA_FILE.getValue("/wallPost").toString();
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
        WallPost wallPost = getResponseWall(sendGet(WALL_POST));
        assertTrue(wallPost.response.post_id > 0, "Record id not received");
    }
}
