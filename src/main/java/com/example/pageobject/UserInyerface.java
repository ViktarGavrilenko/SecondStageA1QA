package com.example.pageobject;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.*;
import aquality.selenium.forms.Form;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static aquality.selenium.elements.ElementType.TEXTBOX;
import static com.example.utils.ArithmeticUtils.getRandomNumberFromOneToMaxValue;
import static com.example.utils.BrowserUtils.uploadFile;
import static com.example.utils.StringUtils.*;

public class UserInyerface extends Form {

    private static final Logger LOG = Logger.getLogger(UserInyerface.class);
    IElementFactory elementFactory = AqualityServices.getElementFactory();

    ILink startLink = elementFactory.getLink(By.xpath("//a[@class='start__link']"), "StartLink");
    ITextBox pageIndicator = elementFactory.getTextBox(By.xpath("//div[@class='page-indicator']"),
            "PageIndicator");
    ITextBox fieldForPassword =
            elementFactory.getTextBox(By.xpath("//div[@class='login-form__field-row']/input"),
                    "FieldForPassword");
    ITextBox fieldOfFirstPartOFEmail =
            elementFactory.getTextBox(By.xpath("//div[contains(@class, 'align--gutter-sm')]/div[1]/input"),
                    "FirstPartOFEmail");
    ITextBox fieldOfSecondPartOFEmail =
            elementFactory.getTextBox(By.xpath("//div[contains(@class, 'align--gutter-sm')]/div[3]/input"),
                    "SecondPartOFEmail");
    ITextBox fieldOfThirdPartOFEmail =
            elementFactory.getTextBox(By.xpath("//div[@class='dropdown__field']"), "ThirdPartOFEmail");
    ICheckBox checkBoxAcceptTermsConditions =
            elementFactory.getCheckBox(By.xpath("//span[@class='checkbox']"), "AcceptTermsConditions");
    ILink buttonNextForSecondPage =
            elementFactory.getLink(By.xpath("//a[@class='button--secondary']"), "NextButton");
    ILink uploadPhoto =
            elementFactory.getLink(By.xpath("//a[@class='avatar-and-interests__upload-button']"), "UploadPhoto");
    IButton buttonNextForThirdPage = elementFactory.getButton(By.xpath("//button[contains(@class, 'button--stroked')]"),
            "NextButtonForThirdPage");
    ITextBox fieldTimer =
            elementFactory.getTextBox(By.xpath("//div[contains(@class, 'timer--white')]"), "FieldTimer");

    public UserInyerface() {
        super(By.xpath("//a[@class='start__link']"), "UserInyerface");
    }

    public boolean isLinkDisplayed() {
        return startLink.state().isDisplayed();
    }

    public void clickStartLink() {
        startLink.click();
    }

    public String getTextWithLoginForm() {
        return pageIndicator.getText();
    }

    public void entryPasswordAndEmailAndAcceptTermsConditions(String email, String password) {
        fieldForPassword.clearAndType(password);
        fieldOfFirstPartOFEmail.clearAndType(getFirstPartEmail(email));
        fieldOfSecondPartOFEmail.clearAndType(getSecondPartEmail(email));
        fieldOfThirdPartOFEmail.click();
        getDomainOfEmail(email).click();
        checkBoxAcceptTermsConditions.check();
        buttonNextForSecondPage.click();
    }

    public ICheckBox getCheckBox(int index) {
        return elementFactory.getCheckBox(By.xpath(String.format
                        ("//div[@class='avatar-and-interests__interests-list__item'][%s]//label/span", index)),
                "Interests");
    }

    public void choiceInterests(int numberInterests, int maxNumberInterests) {
        unselectCheckbox(maxNumberInterests);
        List<Integer> interests = new ArrayList<>();
        interests.add(getRandomNumberFromOneToMaxValue(maxNumberInterests));
        while (interests.size() < numberInterests) {
            int index = getRandomNumberFromOneToMaxValue(maxNumberInterests);
            if (!interests.contains(index)) {
                interests.add(index);
            }
        }
        for (int i = 0; i < numberInterests; i++) {
            getCheckBox(interests.get(i)).check();
        }
    }

    public void unselectCheckbox(int maxNumberInterests) {
        for (int i = 1; i <= maxNumberInterests; i++) {
            getCheckBox(i).check();
        }
    }

    public void uploadPhoto(String filePath) {
        uploadPhoto.click();
        try {
            uploadFile(filePath);
        } catch (AWTException | InterruptedException e) {
            LOG.error("File upload error " + e);
        }
    }

    public void choiceInterestsAndUploadPhoto(int numberInterests, int maxNumberInterests, String filePath) {
        choiceInterests(numberInterests, maxNumberInterests);
        uploadPhoto(filePath);
        buttonNextForThirdPage.click();
    }

    public String getStartTimeOfTimer() {
        return fieldTimer.getText();
    }

    public ITextBox getDomainOfEmail(String email) {
        List<ITextBox> domains =
                elementFactory.findElements(By.xpath("//div[@class='dropdown__list-item']"), "Domains", TEXTBOX);
        for (int i = 0; i < domains.size(); i++) {
            if (domains.get(i).getText().equals(getThirdPartEmail(email))) {
                return elementFactory.getTextBox(By.xpath
                        (String.format("//div[@class='dropdown__list-item'][%s]", i + 1)), "DomainEmail");
            }
        }
        LOG.error("Incorrect Email");
        return null;
    }
}