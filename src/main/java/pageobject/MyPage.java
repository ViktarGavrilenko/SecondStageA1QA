package pageobject;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;

import static aquality.selenium.browser.AqualityServices.getElementFactory;

public class MyPage {
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final ILink linkMyPage =
            getElementFactory().getLink(By.xpath("//li[@id='l_pr']"), "LinkMyPage");

    public void clickLinkMyPage() {
        linkMyPage.click();
    }

    public boolean isWritingOnPage(String message, String id, String idUser, String nameUser) {
        String partOfRequest = idUser + "_" + id;
        ITextBox writingOnWell = getElementFactory().getTextBox(By.xpath(
                "//div[@id='wpt" + partOfRequest + "']/div"), "TextOnWell");
        ITextBox authorWriting = getElementFactory().getTextBox(By.xpath(
                "//div[@id='post" + partOfRequest + "']//a[@class='author']"), "TextOnWell");

        return writingOnWell.getText().equals(message) && authorWriting.getText().equals(nameUser);
    }
}
