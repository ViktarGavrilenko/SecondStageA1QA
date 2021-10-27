package com.example;

import aquality.selenium.browser.AqualityServices;
import com.example.pageobject.UserInyerface;
import org.testng.annotations.Test;

import java.awt.*;

import static org.testng.Assert.assertEquals;

public class UserInyerfaceTest extends BaseTest {
    private static final String PATH_PHOTO = testDataFile.getValue("/photo").toString();
    private static final int COUNT_INTERESTS = Integer.parseInt(testDataFile.getValue("/countInterests").toString());
    private static final String EMAIL = testDataFile.getValue("/email").toString();
    private static final String PASSWORD = testDataFile.getValue("/password").toString();

    @Test(description = "Тест формы UserInyerfaceForm")
    public void testJavascriptFileUpload() throws AWTException, InterruptedException {
        AqualityServices.getBrowser().goTo(DEFAULT_URL);
        UserInyerface userInyerface = new UserInyerface();
        assertEquals(userInyerface.getTextWithLink(), "HERE", "Страница UserInyerface не загрузилась");
        userInyerface.clickStartLink();
        assertEquals(userInyerface.getTextWithLoginForm(), "1 / 4", "Страница с первой карточкой не открылась");
        userInyerface.entryPasswordAndEmail(EMAIL, PASSWORD);
        assertEquals(userInyerface.getTextWithLoginForm(), "2 / 4", "Страница со второй карточкой не открылась");
        userInyerface.choiceInterestsAndUploadPhoto(COUNT_INTERESTS, PATH_PHOTO);
        assertEquals(userInyerface.getTextWithLoginForm(), "3 / 4", "Страница с третьей карточкой не открылась");
    }
}
