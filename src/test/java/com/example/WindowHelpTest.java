package com.example;

import aquality.selenium.core.logging.Logger;
import com.example.pageobject.FormOfWindowHelp;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class WindowHelpTest extends BaseTest {

    @Test(description = "Testing the \"Help\" window")
    public void testOfWindowHelp() {
        FormOfWindowHelp formOfWindowHelp = new FormOfWindowHelp();
        assertTrue(formOfRegistration.isLinkDisplayed(), "UserInyerface page didn't load");

        formOfRegistration.clickStartLink();
        Logger.getInstance().info("Go to the first page");
        assertEquals(formOfRegistration.getTextWithLoginForm(), TEST_DATA_FILE.getValue("/firstPage").toString(),
                "The page with the first card did not open");

        formOfWindowHelp.hideHelpWindow();
        Logger.getInstance().info("Checking if the \"Help\" window is hidden");
        assertFalse(formOfWindowHelp.isWindowHelpDisplayed(), "The \"Help\" window is not hidden");
    }
}