package com.example.steps;

import com.example.pageobject.FormOfRegistration;

public class RegistrationFormSteps {

    private final FormOfRegistration formOfRegistration = new FormOfRegistration();

    public void entryPasswordAndEmailAndAcceptTermsConditions(String email, String password) {
        formOfRegistration.cleanAndTypePassword(password);
        formOfRegistration.cleanAndTypeFirstPartEmail(email);
        formOfRegistration.cleanAndTypeSecondPartEmail(email);
        formOfRegistration.clickOfThirdPartOfEmail();
        formOfRegistration.getDomainOfEmail(email).click();
        formOfRegistration.acceptTermsConditions();
        formOfRegistration.clickButtonNextForSecondPage();
    }
}
