package pageobject;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

import static Utils.PhotoUtils.compareImage;
import static Utils.PhotoUtils.compareImageOpenCV;
import static aquality.selenium.browser.AqualityServices.getElementFactory;

public class WallPage {
    private final ILink linkMyPage =
            getElementFactory().getLink(By.xpath("//li[@id='l_pr']"), "LinkMyPage");
    private final ILink photoAlbums = getElementFactory().getLink(
            By.xpath("//li[@id='l_ph']//a"), "LinkPhotoAlbums");
    private final IElement photoOnPage = getElementFactory().getLink(
            By.xpath("//div[@id='pv_photo']//img"), "PhotoOnPage");
    private final IButton closeButton = getElementFactory().getButton(
            By.className("pv_close_btn"), "CloseButton");

    private static final String LOCATOR_POST_ON_WALL = "//div[@id='wpt%s']//div[contains(@class,'wall_post_text')]";
    private static final String LOCATOR_POST_AUTHOR = "//div[@id='post%s']//a[@class='author']";
    private static final String LOCATOR_LINK_PHOTO_ON_WALL = "//div[@id='wpt%s']//a";

    private static final String LOCATOR_COMMENT_ON_POST = "//div[@id='replies%s']//div[contains(@id, 'post%s')]";
    private static final String LOCATOR_COMMENT_AUTHOR =
            "//div[@id='replies%s']//div[contains(@id, 'post%s')]//a[@data-from-id='%s']";
    private static final String LOCATOR_LIST_COMMENT = "//div[@id='replies%s']//a";
    private static final String LOCATOR_LIKE_BUTTON =
            "//div[contains(@class, 'like_wall%s')]//a[contains(@class, '_like')]";

    private static final String ALBUMS = "albums";
    private static final String POST_ON_WALL = "PostOnWall";

    private static final String POST_NOT_CHANGE = "Text of post has not changed ";
    private static final String PHOTO_NOT_UPLOAD = "The photo does not match the uploaded one";

    public void clickLinkMyPage() {
        linkMyPage.click();
    }

    public int getIdUser() {
        String urlAlbums = photoAlbums.getHref();
        return Integer.parseInt(urlAlbums.substring(urlAlbums.lastIndexOf(ALBUMS) + ALBUMS.length()));
    }

    public boolean isPostOnPage(String message, String idUserPost, int idUser) {
        ITextBox postOnWall = getElementFactory().getTextBox(By.xpath(
                String.format(LOCATOR_POST_ON_WALL, idUserPost)), POST_ON_WALL);
        ILink postAuthor = getElementFactory().getLink(By.xpath(
                String.format(LOCATOR_POST_AUTHOR, idUserPost)), "PostAuthor");
        return postOnWall.getText().equals(message) && postAuthor.getHref().contains(String.valueOf(idUser));
    }

    public boolean isPostUpdate(String message, String idUserPost) {
        ITextBox postOnWall = getElementFactory().getTextBox(By.xpath(
                String.format(LOCATOR_POST_ON_WALL, idUserPost)), POST_ON_WALL);
        try {
            AqualityServices.getConditionalWait().waitForTrue(() -> postOnWall.getText().equals(message),
                    "Text of post has not changed");
        } catch (TimeoutException e) {
            Logger.getInstance().error(POST_NOT_CHANGE + e);
            throw new IllegalArgumentException(POST_NOT_CHANGE, e);
        }
        return postOnWall.getText().equals(message);
    }

    public boolean isAddPhoto(String idUserPost, String pathOriginalPhoto, String pathDownloadPhoto) {
        ILink linkPhotoOnWall = getElementFactory().getLink(By.xpath(
                String.format(LOCATOR_LINK_PHOTO_ON_WALL, idUserPost)), "LinkPhoto");
        try {
            AqualityServices.getConditionalWait().waitForTrue(() -> linkPhotoOnWall.state().isDisplayed(),
                    PHOTO_NOT_UPLOAD);
            linkPhotoOnWall.click();

            try (InputStream in = new URL(photoOnPage.getAttribute("src")).openStream()) {
                Files.copy(in, Paths.get(pathDownloadPhoto));
            }

            closeButton.click();
            compareImageOpenCV(pathDownloadPhoto, pathOriginalPhoto);
            File photoDownload = new File(pathDownloadPhoto);
            File photoOriginal = new File(pathOriginalPhoto);
            return compareImage(photoDownload, photoOriginal);

        } catch (IOException | TimeoutException e) {
            Logger.getInstance().error(PHOTO_NOT_UPLOAD + e);
            throw new IllegalArgumentException(PHOTO_NOT_UPLOAD, e);
        }
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