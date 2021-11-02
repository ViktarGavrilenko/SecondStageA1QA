package com.example;

import aquality.selenium.core.logging.Logger;
import com.example.pageobject.FormOfCookies;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CookiesTest extends BaseTest {

    @Test(description = "Test of accept cookies ")
    public void testCookies() {
        FormOfCookies formOfCookies = new FormOfCookies();
        assertTrue(formOfRegistration.isLinkDisplayed(), "UserInyerface page didn't load");

        formOfRegistration.clickStartLink();
        Logger.getInstance().info("Go to the first page");
        assertEquals(formOfRegistration.getTextWithLoginForm(), TEST_DATA_FILE.getValue("/firstPage").toString(),
                "The page with the first card did not open");

        Logger.getInstance().info("Checking if the cookie form is hidden");
        formOfCookies.clickAcceptCookies();
        assertFalse(formOfCookies.isCookiesFormDisplayed(), "Cookies form is not hidden");
    }
}