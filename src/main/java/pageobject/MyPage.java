package pageobject;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.actions.MouseActions;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;

import java.util.concurrent.TimeoutException;

import static aquality.selenium.browser.AqualityServices.getElementFactory;

public class MyPage {
    private static final ILink linkMyPage =
            getElementFactory().getLink(By.xpath("//li[@id='l_pr']"), "LinkMyPage");

    private static final String locatorPostOnWell = "//div[@id='wpt%s']/div";
    private static final String locatorPostAuthor = "//div[@id='post%s']//a[@class='author']";
    private static final String locatorLinkPhotoOnWall = "//div[@id='wpt%s']//a";
    private static final String locatorCommentOnPost = "//div[@id='replies%s']//div[contains(@id, 'post%s')]";
    private static final String locatorCommentAuthor =
            "//div[@id='replies%s']//div[contains(@id, 'post%s')]//a[@data-from-id='%s']";
    private static final String locatorListComment = "//div[@id='replies%s']//a";
    private static final String locatorLikeButton =
            "//div[contains(@class, 'like_wall%s')]//div[@class='like_btns']//div";

    public void clickLinkMyPage() {
        linkMyPage.click();
    }

    public boolean isPostOnPage(String message, String idUserPost, int idUser) {
        ITextBox postOnWell = getElementFactory().getTextBox(By.xpath(
                String.format(locatorPostOnWell, idUserPost)), "PostOnWell");
        ILink postAuthor = getElementFactory().getLink(By.xpath(
                String.format(locatorPostAuthor, idUserPost)), "PostAuthor");

        return postOnWell.getText().equals(message) && postAuthor.getHref().contains(String.valueOf(idUser));
    }

    public boolean isWritingOnPageAndIsAddPhoto(String message, String idUserPost, int idUser, int idPhoto) {
        String partOfPhoto = String.format("%s_%s", idUser, idPhoto);
        ITextBox postOnWell = getElementFactory().getTextBox(By.xpath(
                String.format(locatorPostOnWell, idUserPost)), "PostOnWell");
        ILink linkPhotoOnWall = getElementFactory().getLink(By.xpath(
                String.format(locatorLinkPhotoOnWall, idUserPost)), "LinkPhoto");
        try {
            AqualityServices.getConditionalWait().waitForTrue(() -> postOnWell.getText().equals(message),
                    "Text of post has not changed");
            String str = linkPhotoOnWall.getAttribute("href");
            AqualityServices.getConditionalWait().waitForTrue(() ->
                            linkPhotoOnWall.getAttribute("href").contains(partOfPhoto),
                    "The photo does not match the uploaded one");
        } catch (TimeoutException e) {
            throw new IllegalArgumentException(
                    "Text of post has not changed or the photo does not match the uploaded one", e);
        }

        return postOnWell.getText().equals(message) && linkPhotoOnWall.getAttribute("href").contains(partOfPhoto);
    }

    public boolean isCommentAdd(String idUserPost, int idUser) {
        ITextBox commentOnPost = getElementFactory().getTextBox(By.xpath(
                        String.format(locatorCommentOnPost, idUserPost, idUser)),
                "CommentOnPost");
        ITextBox commentAuthor = getElementFactory().getTextBox(By.xpath(
                String.format(locatorCommentAuthor,
                        idUserPost, idUser, idUser)), "AuthorComment");
        ILink listComment = getElementFactory().getLink(By.xpath(
                String.format(locatorListComment, idUserPost)), "ListComment");
        listComment.click();

        return commentAuthor.state().waitForDisplayed() && commentOnPost.state().isDisplayed();
    }

    public void likePostOnWall(String idUserPost) {
        IButton likeButton = getElementFactory().getButton(
                By.xpath(String.format(locatorLikeButton, idUserPost)), "LikeButton");

        MouseActions mouseActions = new MouseActions(likeButton, "LikeButton");
        mouseActions.click();
    }

    public boolean isPostDelete(String idUserPost) {
        ITextBox postOnWell = getElementFactory().getTextBox(By.xpath(
                String.format(locatorPostOnWell, idUserPost)), "TextOnWell");
        return postOnWell.state().waitForNotDisplayed();
    }
}