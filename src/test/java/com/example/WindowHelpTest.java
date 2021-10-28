package com.example;

import com.example.pageobject.FormOfWindowHelp;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class WindowHelpTest extends BaseTest {
    private static final Logger LOG = Logger.getLogger(WindowHelpTest.class);

    @Test(description = "Testing the \"Help\" window")
    public void testOfWindowHelp() {
        FormOfWindowHelp formOfWindowHelp = new FormOfWindowHelp();
        assertTrue(userInyerface.isLinkDisplayed(), "UserInyerface page didn't load");
        userInyerface.clickStartLink();
        LOG.info("Go to the first page");
        assertEquals(userInyerface.getTextWithLoginForm(), TEST_DATA_FILE.getValue("/firstPage").toString(),
                "The page with the first card did not open");
        formOfWindowHelp.hideHelpWindow();
        LOG.info("Checking if the \"Help\" window is hidden");
        assertFalse(formOfWindowHelp.isDisplayedWindowHelp(), "The \"Help\" window is not hidden");
    }
}