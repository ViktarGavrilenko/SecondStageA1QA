package com.example.pageobject;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.elements.interfaces.ICheckBox;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.util.List;

import static aquality.selenium.elements.ElementType.TEXTBOX;
import static com.example.utils.StringUtils.*;

public class FormOfRegistration extends Form {
    private final ILink startLink = getElementFactory().getLink(By.className("start__link"), "StartLink");
    private final ITextBox pageIndicator = getElementFactory().getTextBox(By.className("page-indicator"),
            "PageIndicator");
    private final ITextBox fieldForPassword =
            getElementFactory().getTextBox(By.xpath("//div[@class='login-form__field-row']/input"),
                    "FieldForPassword");
    private final ITextBox fieldOfFirstPartOFEmail =
            getElementFactory().getTextBox(By.xpath("//input[@placeholder='Your email']"),
                    "FirstPartOFEmail");
    private final ITextBox fieldOfSecondPartOFEmail =
            getElementFactory().getTextBox(By.xpath("//input[@placeholder='Domain']"),
                    "SecondPartOFEmail");
    private final ITextBox fieldOfThirdPartOfEmail =
            getElementFactory().getTextBox(By.className("dropdown__field"), "ThirdPartOFEmail");
    private final ICheckBox checkBoxAcceptTermsConditions =
            getElementFactory().getCheckBox(By.className("checkbox"), "AcceptTermsConditions");
    private final ILink buttonNextForSecondPage =
            getElementFactory().getLink(By.className("button--secondary"), "NextButton");

    private final ITextBox fieldTimer =
            getElementFactory().getTextBox(By.xpath("//div[contains(@class, 'timer--white')]"), "FieldTimer");

    private final String locatorOfDomainEmail = "//div[@class='dropdown__list-item'][%s]";

    public FormOfRegistration() {
        super(By.className("start__link"), "UserInyerface");
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

    public void cleanAndTypePassword(String password) {
        fieldForPassword.clearAndType(password);
    }

    public void cleanAndTypeFirstPartEmail(String email) {
        fieldOfFirstPartOFEmail.clearAndType(getFirstPartEmail(email));
    }

    public void cleanAndTypeSecondPartEmail(String email) {
        fieldOfSecondPartOFEmail.clearAndType(getSecondPartEmail(email));
    }

    public void clickOfThirdPartOfEmail() {
        fieldOfThirdPartOfEmail.click();
    }

    public ITextBox getDomainOfEmail(String email) {
        List<ITextBox> domains =
                getElementFactory().findElements(By.className("dropdown__list-item"), "Domains", TEXTBOX);
        for (int i = 0; i < domains.size(); i++) {
            if (domains.get(i).getText().equals(getThirdPartEmail(email))) {
                return getElementFactory().getTextBox(By.xpath
                        (String.format(locatorOfDomainEmail, i + 1)), "DomainEmail");
            }
        }
        Logger.getInstance().error("Incorrect Email");
        return null;
    }

    public void acceptTermsConditions() {
        checkBoxAcceptTermsConditions.check();
    }

    public void clickButtonNextForSecondPage() {
        buttonNextForSecondPage.click();
    }

    public String getTimerValue() {
        return fieldTimer.getText();
    }
}