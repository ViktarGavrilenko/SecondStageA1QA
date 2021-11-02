package com.example.pageobject;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class FormOfWindowHelp extends Form {
    private final IButton buttonHideHelpWindow = getElementFactory().getButton(By.xpath("//span[@class='discrete']/.."),
            "HideHelpWindow");

    private final ITextBox windowHelp = getElementFactory().getTextBox(By.xpath("//div[@class='help-form__container']/.."),
            "WindowHelp");

    public FormOfWindowHelp() {
        super(By.className("help-form__container"), "FormOfWindowHelp");
    }

    public void hideHelpWindow() {
        buttonHideHelpWindow.click();
    }

    public boolean isWindowHelpDisplayed() {
        return windowHelp.getAttribute("class").equals("help-form");
    }
}