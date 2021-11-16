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

    private void cleanAndTypePhoneOrEmail(String phoneOrEmail) {
        fieldForPhoneOrEmail.clearAndType(phoneOrEmail);
    }

    private void cleanAndTypePassword(String password) {
        fieldForPassword.clearAndTypeSecret(password);
    }

    private void clickLoginButton() {
        loginButton.click();
    }

    public void logToVkPage(String phoneOrEmail, String password) {
        cleanAndTypePhoneOrEmail(phoneOrEmail);
        cleanAndTypePassword(password);
        clickLoginButton();
    }
}