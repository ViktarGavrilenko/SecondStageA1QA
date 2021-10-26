package com.example.pageobject;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IElementFactory;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class UserInyerface extends Form {

    IElementFactory elementFactory = AqualityServices.getElementFactory();
    ILink startLink = elementFactory.getLink(By.xpath("//a[@class='start__link']"), "UserInyerface");
    ITextBox pageIndicator = elementFactory.getTextBox(By.xpath("//div[@class='page-indicator']"), "PageIndicator");

    public UserInyerface() {
        super(By.id("//a[@class='start__link']"), "UserInyerface");
    }

    public String getTextWithLink() {
        return startLink.getText();
    }

    public void clickLink() {
        startLink.click();
    }

    public String getTextWithLoginForm() {
        return pageIndicator.getText();
    }
}
