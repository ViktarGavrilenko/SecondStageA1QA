package com.example;

import com.example.pageobject.FormOfCookies;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CookiesTest extends BaseTest {
    private static final Logger LOG = Logger.getLogger(CookiesTest.class);

    @Test(description = "Test of accept cookies ")
    public void testCookies() {
        FormOfCookies formOfCookies = new FormOfCookies();
        assertTrue(userInyerface.isLinkDisplayed(), "UserInyerface page didn't load");
        userInyerface.clickStartLink();
        LOG.info("Go to the first page");
        assertEquals(userInyerface.getTextWithLoginForm(), TEST_DATA_FILE.getValue("/firstPage").toString(),
                "The page with the first card did not open");
        LOG.info("Checking if the cookie form is hidden");
        assertFalse(formOfCookies.isFormDisappearedAfterClickingAcceptCookies(), "Cookies form is not hidden");
    }
}