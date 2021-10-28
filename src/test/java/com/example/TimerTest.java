package com.example;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TimerTest extends BaseTest {
    private static final Logger LOG = Logger.getLogger(TimerTest.class);
    protected static final String START_TIME_OF_TIMER = TEST_DATA_FILE.getValue("/startTimeOfTimer").toString();

    @Test(description = "Timer test")
    public void testTimer() {
        assertTrue(userInyerface.isLinkDisplayed(), "UserInyerface page didn't load");
        userInyerface.clickStartLink();
        LOG.info("Go to the first page");
        assertEquals(userInyerface.getTextWithLoginForm(), TEST_DATA_FILE.getValue("/firstPage").toString(),
                "The page with the first card did not open");
        LOG.info("Checking that the timer starts counting from zero");
        assertEquals(userInyerface.getStartTimeOfTimer(), START_TIME_OF_TIMER,
                "The timer did not start from zero");
    }
}