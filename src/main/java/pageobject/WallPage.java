package pageobject;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;

import java.util.concurrent.TimeoutException;

import static aquality.selenium.browser.AqualityServices.getElementFactory;

public class WallPage {
    private final ILink linkMyPage =
            getElementFactory().getLink(By.xpath("//li[@id='l_pr']"), "LinkMyPage");
    private final ILink photoAlbums = getElementFactory().getLink(
            By.xpath("//li[@id='l_ph']//a"), "LinkPhotoAlbums");

    private static final String LOCATOR_POST_ON_WALL = "//div[@id='wpt%s']/div";
    private static final String LOCATOR_POST_AUTHOR = "//div[@id='post%s']//a[@class='author']";
    private static final String LOCATOR_LINK_PHOTO_ON_WALL = "//div[@id='wpt%s']//a";
    private static final String LOCATOR_COMMENT_ON_POST = "//div[@id='replies%s']//div[contains(@id, 'post%s')]";
    private static final String LOCATOR_COMMENT_AUTHOR =
            "//div[@id='replies%s']//div[contains(@id, 'post%s')]//a[@data-from-id='%s']";
    private static final String LOCATOR_LIST_COMMENT = "//div[@id='replies%s']//a";
    private static final String LOCATOR_LIKE_BUTTON =
            "//div[contains(@class, 'like_wall%s')]//a[contains(@class, '_like')]";

    private static final String PART_URL_ALBUMS = "https://vk.com/albums";
    private static final String POST_ON_WALL = "PostOnWall";

    public void clickLinkMyPage() {
        linkMyPage.click();
    }

    public int getIdUser() {
        String urlAlbums = photoAlbums.getHref();
        int lengthUrl = urlAlbums.length();
        return Integer.parseInt(urlAlbums.substring(PART_URL_ALBUMS.length(), lengthUrl));
    }

    public boolean isPostOnPage(String message, String idUserPost, int idUser) {
        ITextBox postOnWall = getElementFactory().getTextBox(By.xpath(
                String.format(LOCATOR_POST_ON_WALL, idUserPost)), POST_ON_WALL);
        ILink postAuthor = getElementFactory().getLink(By.xpath(
                String.format(LOCATOR_POST_AUTHOR, idUserPost)), "PostAuthor");
        return postOnWall.getText().equals(message) && postAuthor.getHref().contains(String.valueOf(idUser));
    }

    public boolean isPostOnPageAndIsAddPhoto(String message, String idUserPost, int idUser, int idPhoto) {
        String partOfPhoto = String.format("%s_%s", idUser, idPhoto);
        ITextBox postOnWall = getElementFactory().getTextBox(By.xpath(
                String.format(LOCATOR_POST_ON_WALL, idUserPost)), POST_ON_WALL);
        ILink linkPhotoOnWall = getElementFactory().getLink(By.xpath(
                String.format(LOCATOR_LINK_PHOTO_ON_WALL, idUserPost)), "LinkPhoto");
        try {
            AqualityServices.getConditionalWait().waitForTrue(() -> postOnWall.getText().equals(message),
                    "Text of post has not changed");
            AqualityServices.getConditionalWait().waitForTrue(() ->
                            linkPhotoOnWall.getAttribute("href").contains(partOfPhoto),
                    "The photo does not match the uploaded one");
        } catch (TimeoutException e) {
            throw new IllegalArgumentException(
                    "Text of post has not changed or the photo does not match the uploaded one", e);
        }

        return postOnWall.getText().equals(message) && linkPhotoOnWall.getAttribute("href").contains(partOfPhoto);
    }

    public boolean isCommentAdd(String idUserPost, int idUser) {
        ITextBox commentOnPost = getElementFactory().getTextBox(By.xpath(
                        String.format(LOCATOR_COMMENT_ON_POST, idUserPost, idUser)),
                "CommentOnPost");
        ITextBox commentAuthor = getElementFactory().getTextBox(By.xpath(
                String.format(LOCATOR_COMMENT_AUTHOR,
                        idUserPost, idUser, idUser)), "CommentAuthor");
        ILink listComments = getElementFactory().getLink(By.xpath(
                String.format(LOCATOR_LIST_COMMENT, idUserPost)), "ListComments");
        listComments.click();

        return commentAuthor.state().waitForDisplayed() && commentOnPost.state().isDisplayed();
    }

    public void likePostOnWall(String idUserPost) {
        IButton likeButton = getElementFactory().getButton(
                By.xpath(String.format(LOCATOR_LIKE_BUTTON, idUserPost)), "LikeButton");
        likeButton.click();
    }

    public boolean isPostDelete(String idUserPost) {
        ITextBox postOnWall = getElementFactory().getTextBox(By.xpath(
                String.format(LOCATOR_POST_ON_WALL, idUserPost)), POST_ON_WALL);
        return postOnWall.state().waitForNotDisplayed();
    }
}