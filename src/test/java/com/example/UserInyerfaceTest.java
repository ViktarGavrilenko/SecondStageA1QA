package com.example;

import aquality.selenium.browser.AqualityServices;
import com.example.pageobject.UserInyerface;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class UserInyerfaceTest extends BaseTest {

    @Test(description = "Тест формы UserInyerfaceForm")
    public void testJavascriptFileUpload() {
        AqualityServices.getBrowser().goTo(DEFAULT_URL);
        UserInyerface userInyerface = new UserInyerface();
        assertEquals(userInyerface.getTextWithLink(), "HERE", "Страница UserInyerface не загрузилась");
        userInyerface.clickLink();
        assertEquals(userInyerface.getTextWithLoginForm(), "1 / 4", "Страница с первой карточкой не открылась");
    }
}
