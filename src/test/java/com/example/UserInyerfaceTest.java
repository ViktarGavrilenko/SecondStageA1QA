package com.example;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static com.example.utils.StringUtils.generationPassword;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserInyerfaceTest extends BaseTest {
    private static final Logger LOG = Logger.getLogger(UserInyerfaceTest.class);

    private static final String PATH_PHOTO = TEST_DATA_FILE.getValue("/photo").toString();
    private static final int NUMBER_INTERESTS =
            Integer.parseInt(CONFIG_FILE.getValue("/numberInterests").toString());
    private static final int NUMBER_INTERESTS_FOR_SELECTION =
            Integer.parseInt(TEST_DATA_FILE.getValue("/numberOfInterestsForSelection").toString());
    private static final String EMAIL = TEST_DATA_FILE.getValue("/email").toString();

    @Test(description = "Testing the UserInyerfaceForm")
    public void testUserInyerface() {
        assertTrue(userInyerface.isLinkDisplayed(), "UserInyerface page didn't load");
        userInyerface.clickStartLink();
        LOG.info("Go to the first page");
        assertEquals(userInyerface.getTextWithLoginForm(), TEST_DATA_FILE.getValue("/firstPage").toString(),
                "The page with the first card did not open");
        userInyerface.entryPasswordAndEmailAndAcceptTermsConditions(EMAIL, generationPassword(EMAIL));
        LOG.info("Go to the second page");
        assertEquals(userInyerface.getTextWithLoginForm(), TEST_DATA_FILE.getValue("/secondPage").toString(),
                "The page with the second card did not open");
        userInyerface.choiceInterestsAndUploadPhoto(NUMBER_INTERESTS_FOR_SELECTION, NUMBER_INTERESTS, PATH_PHOTO);
        LOG.info("Go to the third page");
        assertEquals(userInyerface.getTextWithLoginForm(), TEST_DATA_FILE.getValue("/thirdPage").toString(),
                "The page with the third card did not open");
    }
}