package com.example.pageobject;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.*;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.utils.ArithmeticUtils.getRandomNumberFromOneToTwentyOne;
import static com.example.utils.BrowserUtils.uploadFile;
import static com.example.utils.StringUtils.getFirstPartEmail;
import static com.example.utils.StringUtils.getSecondPartEmail;

public class UserInyerface extends Form {

    IElementFactory elementFactory = AqualityServices.getElementFactory();
    ILink startLink = elementFactory.getLink(By.xpath("//a[@class='start__link']"), "UserInyerface");
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
    ITextBox domainOfEmail =
            elementFactory.getTextBox(By.xpath("//div[@class='dropdown__list-item'][1]"), "DomainOfEmail");
    ICheckBox checkBoxAcceptTermsConditions =
            elementFactory.getCheckBox(By.xpath("//span[@class='checkbox']"), "AcceptTermsConditions");
    ILink buttonNextForSecondPage =
            elementFactory.getLink(By.xpath("//a[@class='button--secondary']"), "NextButton");
    ILink uploadPhoto =
            elementFactory.getLink(By.xpath("//a[@class='avatar-and-interests__upload-button']"), "UploadPhoto");
    IButton buttonNextForThirdPage = elementFactory.getButton(By.xpath("//button[contains(@class, 'button--stroked')]"),
            "NextButtonForThirdPage");

    public UserInyerface() {
        super(By.id("//a[@class='start__link']"), "UserInyerface");
    }

    public String getTextWithLink() {
        return startLink.getText();
    }

    public void clickStartLink() {
        startLink.click();
    }

    public String getTextWithLoginForm() {
        return pageIndicator.getText();
    }

    public void entryPasswordAndEmail(String email, String password) {
        fieldForPassword.clearAndType(password);
        fieldOfFirstPartOFEmail.clearAndType(getFirstPartEmail(email));
        fieldOfSecondPartOFEmail.clearAndType(getSecondPartEmail(email));
        fieldOfThirdPartOFEmail.click();
        domainOfEmail.click();
        checkBoxAcceptTermsConditions.check();
        buttonNextForSecondPage.click();
    }

    public ICheckBox getCheckBox(int index) {
        return elementFactory.getCheckBox(By.xpath(String.format
                        ("//div[@class='avatar-and-interests__interests-list__item'][%s]//label/span", index)),
                "Interests");
    }

    public void choiceInterests(int countInterests) {
        unselectCheckbox();
        List<Integer> interests = new ArrayList<>();
        interests.add(getRandomNumberFromOneToTwentyOne());
        while (interests.size() < countInterests) {
            int index = getRandomNumberFromOneToTwentyOne();
            if (!interests.contains(index)) {
                interests.add(index);
            }
        }
        for (int i = 0; i < countInterests; i++) {
            getCheckBox(interests.get(i)).check();
        }
    }

    public void unselectCheckbox() {
        for (int i = 1; i < 22; i++) {
            getCheckBox(i).check();
        }
    }

    public void uploadPhoto(String filePath) throws AWTException, InterruptedException {
        uploadPhoto.click();
        uploadFile(filePath);
    }

    public void choiceInterestsAndUploadPhoto(int countInterests, String filePath) throws AWTException, InterruptedException {
        choiceInterests(countInterests);
        uploadPhoto(filePath);
        buttonNextForThirdPage.click();
    }
}