package com.example;

import aquality.selenium.core.logging.Logger;
import com.example.pageobject.FormOfSelectInterests;
import com.example.steps.RegistrationFormSteps;
import org.testng.annotations.Test;

import static com.example.utils.StringUtils.generationPassword;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserInyerfaceTest extends BaseTest {
    private static final String PATH_PHOTO = TEST_DATA_FILE.getValue("/photo").toString();
    private static final int NUMBER_SELECT_ALL =
            Integer.parseInt(TEST_DATA_FILE.getValue("/numberSelectAll").toString());
    private static final int NUMBER_INTERESTS_FOR_SELECTION =
            Integer.parseInt(TEST_DATA_FILE.getValue("/numberOfInterestsForSelection").toString());
    private static final String EMAIL = TEST_DATA_FILE.getValue("/email").toString();
    private static final RegistrationFormSteps REGISTRATION_FORM_STEPS = new RegistrationFormSteps();
    private static final FormOfSelectInterests FORM_OF_SELECT_INTERESTS = new FormOfSelectInterests();

    @Test(description = "Testing the UserInyerfaceForm")
    public void testUserInyerface() {
        assertTrue(formOfRegistration.isLinkDisplayed(), "UserInyerface page didn't load");

        formOfRegistration.clickStartLink();
        Logger.getInstance().info("Go to the first page");
        assertEquals(formOfRegistration.getTextWithLoginForm(), TEST_DATA_FILE.getValue("/firstPage").toString(),
                "The page with the first card did not open");

        REGISTRATION_FORM_STEPS.entryPasswordAndEmailAndAcceptTermsConditions(EMAIL, generationPassword(EMAIL));
        Logger.getInstance().info("Go to the second page");
        assertEquals(formOfRegistration.getTextWithLoginForm(), TEST_DATA_FILE.getValue("/secondPage").toString(),
                "The page with the second card did not open");

        FORM_OF_SELECT_INTERESTS.choiceInterestsAndUploadPhoto(NUMBER_INTERESTS_FOR_SELECTION, PATH_PHOTO, NUMBER_SELECT_ALL);
        Logger.getInstance().info("Go to the third page");
        assertEquals(formOfRegistration.getTextWithLoginForm(), TEST_DATA_FILE.getValue("/thirdPage").toString(),
                "The page with the third card did not open");
    }
}