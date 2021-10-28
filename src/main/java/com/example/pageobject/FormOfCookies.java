package com.example.pageobject;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElementFactory;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class FormOfCookies extends Form {

    IElementFactory elementFactory = AqualityServices.getElementFactory();

    IButton buttonAcceptCookies = elementFactory.getButton(By.xpath("//button[contains(@class, 'button--transparent')]"),
            "AcceptCookies");

    ITextBox formCookies = elementFactory.getTextBox(By.xpath("//div[@class='cookies']"), "FormOfCookies");

    public FormOfCookies() {
        super(By.xpath("//div[@class='cookies']"), "FormOfCookies");
    }

    public boolean isFormDisappearedAfterClickingAcceptCookies() {
        buttonAcceptCookies.click();
        return formCookies.state().isDisplayed();
    }
}