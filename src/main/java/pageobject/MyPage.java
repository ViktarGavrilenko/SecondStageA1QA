package pageobject;

import aquality.selenium.elements.interfaces.ILink;
import org.openqa.selenium.By;

import static aquality.selenium.browser.AqualityServices.getElementFactory;

public class MyPage {
    private static final ILink linkMyPage =
            getElementFactory().getLink(By.xpath("//li[@id='l_pr']"), "LinkMyPage");

    public void clickLinkMyPage() {
        linkMyPage.click();
    }
}
