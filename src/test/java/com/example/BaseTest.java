package com.example;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.example.pageobject.FormOfRegistration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public class BaseTest {
    protected static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    protected static final String DEFAULT_URL = CONFIG_FILE.getValue("/mainPage").toString();
    protected static FormOfRegistration formOfRegistration = new FormOfRegistration();

    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().goTo(DEFAULT_URL);
        getBrowser().maximize();
        Logger.getInstance().info("Check if the page is loaded " + DEFAULT_URL);
    }

    @AfterMethod
    public void afterTest() {
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}