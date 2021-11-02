package com.example.pageobject;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class FormOfCookies extends Form {
    private final IButton buttonAcceptCookies = getElementFactory().getButton(
            By.xpath("//button[contains(@class, 'button--transparent')]"), "AcceptCookies");

    private final ITextBox formCookies = getElementFactory().getTextBox(By.className("cookies"), "FormOfCookies");

    public FormOfCookies() {
        super(By.className("cookies"), "FormOfCookies");
    }

    public void clickAcceptCookies() {
        buttonAcceptCookies.click();
    }

    public boolean isCookiesFormDisplayed() {
        return formCookies.state().isDisplayed();
    }
}