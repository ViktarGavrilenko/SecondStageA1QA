package pageobject;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.actions.MouseActions;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;

import java.time.Duration;

import static aquality.selenium.browser.AqualityServices.getElementFactory;

public class MyPage {
    private static final ILink linkMyPage =
            getElementFactory().getLink(By.xpath("//li[@id='l_pr']"), "LinkMyPage");

    private static final String locatorPostOnWell = "//div[@id='wpt%s']/div";
    private static final String locatorPostAuthor = "//div[@id='post%s']//a[@class='author']";
    private static final String locatorLinkPhotoOnWall = "//div[@id='wpt%s']//a";
    private static final String locatorCommentOnPost = "//div[@id='replies%s']//div[contains(@id, 'post%s')]";
    private static final String locatorCommentAuthor = "//div[@id='replies%s']//div[contains(@id, 'post%s')]//a[@data-from-id='%s']";
    private static final String locatorListComment = "//div[@id='replies%s']//a";
    private static final String locatorLikeButton = "//div[contains(@class, 'like_wall%s')]//div[@class='like_btns']//div";

    public void clickLinkMyPage() {
        linkMyPage.click();
    }

    public boolean isPostOnPage(String message, String idPost, String idUser, String nameUser) {
        String partOfRequest = String.format("%s_%s", idUser, idPost);
        ITextBox postOnWell = getElementFactory().getTextBox(By.xpath(
                String.format(locatorPostOnWell, partOfRequest)), "TextOnWell");
        ITextBox postAuthor = getElementFactory().getTextBox(By.xpath(
                String.format(locatorPostAuthor, partOfRequest)), "TextOnWell");

        return postOnWell.getText().equals(message) && postAuthor.getText().equals(nameUser);
    }

    public boolean isWritingOnPageAndAddPhoto(String message, String idPost, String idUser, String idPhoto) {
        String partOfRequest = String.format("%s_%s", idUser, idPost);
        String partOfPhoto = String.format("%s_%s", idUser, idPhoto);
        ITextBox postOnWell = getElementFactory().getTextBox(By.xpath(
                String.format(locatorPostOnWell, partOfRequest)), "TextOnWell");
        ILink linkPhotoOnWall = getElementFactory().getLink(By.xpath(
                String.format(locatorLinkPhotoOnWall, partOfRequest)), "LinkPhoto");
        //return postOnWell.getText().equals(message) && linkPhotoOnWall.getAttribute("href").contains("partOfPhoto");
        AqualityServices.getConditionalWait().waitFor(() -> linkPhotoOnWall.getAttribute("href").contains("partOfPhoto"),
                Duration.ofSeconds(5), Duration.ofMillis(50), "File should be downloaded");
        return linkPhotoOnWall.getAttribute("href").contains("partOfPhoto");
    }

    public boolean isCommentAdd(String idPost, String idUser) {
        String partOfRequest = String.format("%s_%s", idUser, idPost);

        ITextBox commentOnPost = getElementFactory().getTextBox(By.xpath(
                        String.format(locatorCommentOnPost, partOfRequest, idUser)),
                "CommentOnPost");
        ITextBox commentAuthor = getElementFactory().getTextBox(By.xpath(
                String.format(locatorCommentAuthor,
                        partOfRequest, idUser, idUser)), "AuthorComment");
        ILink listComment = getElementFactory().getLink(By.xpath(
                String.format(locatorListComment, partOfRequest)), "ListComment");
        listComment.click();

        return commentAuthor.state().waitForDisplayed() && commentOnPost.state().isDisplayed();
    }

    public void likePostOnWall(String idPost, String idUser) {
        String partOfRequest = String.format("%s_%s", idUser, idPost);

        IButton likeButton = getElementFactory().getButton(
                By.xpath(String.format(locatorLikeButton, partOfRequest)), "LikeButton");

        MouseActions mouseActions = new MouseActions(likeButton, "LikeButton");
        mouseActions.click();
    }
}
