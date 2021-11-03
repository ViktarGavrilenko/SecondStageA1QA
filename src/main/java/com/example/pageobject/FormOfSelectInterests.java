package com.example.pageobject;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ICheckBox;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static aquality.selenium.elements.ElementType.TEXTBOX;
import static com.example.utils.ArithmeticUtils.getRandomNumberFromOneToMaxValue;
import static com.example.utils.BrowserUtils.uploadFile;

public class FormOfSelectInterests extends Form {
    private final ILink uploadPhoto =
            getElementFactory().getLink(By.className("avatar-and-interests__upload-button"), "UploadPhoto");
    private final IButton buttonNextForThirdPage = getElementFactory().getButton(
            By.xpath("//button[contains(@class, 'button--stroked')]"), "NextButtonForThirdPage");

    private final By locatorOfListInterests =
            By.xpath("//div[@class='avatar-and-interests__interests-list__item']");

    private final String locatorOfCheckBoxInterests =
            "//div[@class='avatar-and-interests__interests-list__item'][%s]//label/span";

    private final String locatorOfCheckBoxTexts =
            "//div[@class='avatar-and-interests__interests-list__item'][%s]" +
                    "//span[@class='checkbox small']/following-sibling::span";

    public FormOfSelectInterests() {
        super(By.className("start__link"), "UserInyerface");
    }

    public ICheckBox getCheckBox(int index) {
        return getElementFactory().getCheckBox(By.xpath(
                String.format(locatorOfCheckBoxInterests, index)), "Interests" + index);
    }

    private String getTextWithCheckBox(int index) {
        return getElementFactory().getCheckBox(By.xpath(
                String.format(locatorOfCheckBoxTexts, index)), "Interests" + index).getText();
    }

    public void choiceInterests(int numberInterests, String textSelectAll) {
        List<Integer> interests = new ArrayList<>();
        int numberOfAllInterestsOnPage = getElementFactory().findElements(locatorOfListInterests,
                "ListOfInterests", TEXTBOX).size();
        unselectCheckbox(numberOfAllInterestsOnPage);
        int value = getRandomNumberFromOneToMaxValue(numberOfAllInterestsOnPage - 1);
        while (getTextWithCheckBox(value).equals(textSelectAll)) {
            value = getRandomNumberFromOneToMaxValue(numberOfAllInterestsOnPage - 1);
        }

        interests.add(value);

        while (interests.size() < numberInterests) {
            value = getRandomNumberFromOneToMaxValue(numberOfAllInterestsOnPage - 1);
            if (!interests.contains(value) && !getTextWithCheckBox(value).equals(textSelectAll)) {
                interests.add(value);
            }
        }

        for (int i = 0; i < numberInterests; i++) {
            getCheckBox(interests.get(i)).check();
        }
    }

    public void unselectCheckbox(int indexUnselectAll) {
        getCheckBox(indexUnselectAll).check();
    }

    public void uploadPhoto(String filePath) {
        uploadPhoto.click();
        try {
            uploadFile(filePath);
        } catch (AWTException | InterruptedException e) {
            Logger.getInstance().error("File upload error " + e);
        }
    }

    public void choiceInterestsAndUploadPhoto(int numberInterests, String filePath, String textSelectAll) {
        choiceInterests(numberInterests, textSelectAll);
        uploadPhoto(filePath);
        buttonNextForThirdPage.click();
    }
}