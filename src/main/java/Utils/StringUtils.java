package Utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Random;

public class StringUtils {
    private static final Random RANDOM = new Random();
    private static final int NUMBER_OF_LETTERS_ALPHABET = 26;
    private static final int MAX_LENGTH = 20;

    public static String generateRandomText() {
        StringBuilder randomText = new StringBuilder();
        for (int i = 0; i < RANDOM.nextInt(MAX_LENGTH) + 1; i++) {
            if (RANDOM.nextBoolean()) {
                randomText.append((char) (RANDOM.nextInt(NUMBER_OF_LETTERS_ALPHABET) + 'a'));
            } else {
                randomText.append((char) (RANDOM.nextInt(NUMBER_OF_LETTERS_ALPHABET) + 'A'));
            }
        }
        return randomText.toString();
    }
    public static String buildFormDataFromMap(Map<String, String> data) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }
        return builder.toString();
    }
}