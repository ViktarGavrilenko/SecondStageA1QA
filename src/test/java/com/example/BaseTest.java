package com.example;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.example.modelsdatabase.TestTable;
import com.example.pageobject.FormOfRegistration;
import com.example.utils.Const;
import org.openqa.selenium.Dimension;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static com.example.modelsdatabase.AuthorTable.getIdAuthor;
import static com.example.modelsdatabase.LogTable.addLogInTestTable;
import static com.example.modelsdatabase.ProjectTable.getIdProject;
import static com.example.modelsdatabase.SessionTable.getIdSession;
import static com.example.utils.BrowserUtils.getComputerName;
import static com.example.utils.MySqlUtils.closeConnection;
import static com.example.utils.StringUtils.getLogOfTest;
import static com.example.utils.StringUtils.getProjectName;
import static org.testng.Assert.assertTrue;

public class BaseTest extends Const {
    protected static final ISettingsFile CONFIG_FILE = new JsonSettingsFile("config.json");
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    protected static final String DEFAULT_URL = CONFIG_FILE.getValue("/mainPage").toString();
    protected static final String NAME_AUTHOR_PROJECT = TEST_DATA_FILE.getValue("/name").toString();
    protected static FormOfRegistration formOfRegistration = new FormOfRegistration();

    @BeforeMethod
    protected void beforeMethod() {
        getBrowser().goTo(DEFAULT_URL);
        getBrowser().maximize();
        Logger.getInstance().info("Check if the page is loaded " + DEFAULT_URL);
    }

    @AfterMethod
    public void afterTest(ITestResult result) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TestTable test = new TestTable();

        test.name = result.getMethod().getDescription();
        test.status_id = result.getStatus();
        test.method_name = result.getMethod().getQualifiedName();
        test.project_id = getIdProject(getProjectName());
        test.session_id = getIdSession(getBrowser().getDriver().getSessionId().toString());
        test.start_time = Timestamp.valueOf(formatter.format(new Date(result.getStartMillis())));
        test.end_time = Timestamp.valueOf(formatter.format(new Date(result.getEndMillis())));
        test.env = getComputerName();
        test.browser = getBrowser().getBrowserName().name();
        test.author_id = getIdAuthor(NAME_AUTHOR_PROJECT);

        test.addDataInTestTable(test);

        addLogInTestTable(getLogOfTest(), 0, test.getMaxIdTestTable());
        if (test.status_id != 1) {
            StringWriter sw = new StringWriter();
            result.getThrowable().printStackTrace(new PrintWriter(sw));
            addLogInTestTable(sw.toString(), 1, test.getMaxIdTestTable());
        }

        assertTrue(test.isDataInDatabase(test), "The test result was not added to the database");

        closeConnection();
        if (AqualityServices.isBrowserStarted()) {
            getBrowser().quit();
        }
    }
}