package pageobject;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;

import static aquality.selenium.browser.AqualityServices.getElementFactory;

public class LoginPage {

    private final ITextBox fieldForPhoneOrEmail =
            getElementFactory().getTextBox(By.id("index_email"), "FieldForPhoneOrEmail");
    private final ITextBox fieldForPassword =
            getElementFactory().getTextBox(By.id("index_pass"), "FieldForPassword");
    private final IButton loginButton =
            getElementFactory().getButton(By.id("index_login_button"), "LoginButton");
    private final ITextBox fieldPageUserName =
            getElementFactory().getTextBox(By.className("side_bar_nav"), "PageUserName");

    public boolean isFieldForPhoneOrEmail() {
        return fieldForPhoneOrEmail.state().isDisplayed();
    }

    public boolean isFieldPageUserName() {
        return fieldPageUserName.state().isDisplayed();
    }

    public void cleanAndTypePhoneOrEmail(String phoneOrEmail) {
        fieldForPhoneOrEmail.clearAndType(phoneOrEmail);
    }

    public void cleanAndTypePassword(String password) {
        fieldForPassword.clearAndType(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }
}