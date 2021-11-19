package com.example;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.example.pageobject.FormOfRegistration;
import org.openqa.selenium.Dimension;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static com.example.modelsdatabase.Test.addDataInTestTable;
import static com.example.utils.BrowserUtils.getComputerName;
import static com.example.utils.StringUtils.getProjectName;

public class BaseTest {
    protected static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    protected static final String DEFAULT_URL = CONFIG_FILE.getValue("/mainPage").toString();
    protected static FormOfRegistration formOfRegistration = new FormOfRegistration();
    protected final Dimension defaultSize = new Dimension(1280, 1024);

    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().goTo(DEFAULT_URL);
        getBrowser().setWindowSize(defaultSize.width, defaultSize.height);
        Logger.getInstance().info("Check if the page is loaded " + DEFAULT_URL);
    }

    @AfterMethod
    public void afterTest(ITestResult result) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String authorId = "Victor Gavrilenko";

        Map<String, String> data = new HashMap<>();
        data.put("name", result.getMethod().getDescription());
        data.put("status_id", String.valueOf(result.getStatus()));
        data.put("method_name", result.getMethod().getQualifiedName());
        data.put("project_name", getProjectName());
        data.put("session_id", "3");
        data.put("start_time", formatter.format(new Date(result.getStartMillis())));
        data.put("end_time", formatter.format(new Date(result.getEndMillis())));
        data.put("env", getComputerName());
        data.put("browser", getBrowser().getBrowserName().name());

        addDataInTestTable(data);

        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}
