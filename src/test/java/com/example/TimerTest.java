package com.example;

import aquality.selenium.core.logging.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TimerTest extends BaseTest {
    protected static final String START_TIME_OF_TIMER = TEST_DATA_FILE.getValue("/startTimeOfTimer").toString();

    @Test(description = "Timer test")
    public void testTimer() {
        assertTrue(formOfRegistration.isLinkDisplayed(), "UserInyerface page didn't load");

        formOfRegistration.clickStartLink();
        Logger.getInstance().info("Go to the first page");
        assertEquals(formOfRegistration.getTextWithLoginForm(), TEST_DATA_FILE.getValue("/firstPage").toString(),
                "The page with the first card did not open");

        Logger.getInstance().info("Checking that the timer starts counting from zero");
        assertEquals(formOfRegistration.getTimerValue(), START_TIME_OF_TIMER,
                "The timer did not start from zero");
    }
}