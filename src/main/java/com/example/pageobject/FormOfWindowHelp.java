package com.example.pageobject;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElementFactory;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class FormOfWindowHelp extends Form {
    IElementFactory elementFactory = AqualityServices.getElementFactory();

    IButton buttonHideHelpWindow = elementFactory.getButton(By.xpath("//span[@class='discrete']/.."),
            "HideHelpWindow");

    ITextBox windowHelp = elementFactory.getTextBox(By.xpath("//div[@class='help-form__container']/.."),
            "WindowHelp");

    public FormOfWindowHelp() {
        super(By.xpath("//div[@class='help-form__container']"), "FormOfWindowHelp");
    }

    public void hideHelpWindow() {
        buttonHideHelpWindow.click();
    }

    public boolean isDisplayedWindowHelp() {
        return windowHelp.getAttribute("class").equals("help-form");
    }
}