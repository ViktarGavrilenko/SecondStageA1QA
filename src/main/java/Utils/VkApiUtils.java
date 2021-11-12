package Utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.WallPost;

import static Utils.ApiUtils.sendGet;
import static Utils.ResponsesUtils.getResponseWall;

public class VkApiUtils {
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String START_API_VK = TEST_DATA_FILE.getValue("/startApiVk").toString();
    private static final String VERSION_API_VK = TEST_DATA_FILE.getValue("/v").toString();
    private static final String TOKEN = TEST_DATA_FILE.getValue("/token").toString();

    public static WallPost writeNoteOnWall(String message) {
        StringBuilder apiRequest = new StringBuilder();
        apiRequest.append(START_API_VK).append("wall.post?message=").append(message).append("&v=").
                append(VERSION_API_VK).append("&access_token=").append(TOKEN);
        return getResponseWall(sendGet(String.valueOf(apiRequest)));
    }
}
