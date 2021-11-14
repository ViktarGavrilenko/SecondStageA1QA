package Utils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ApiUtils {
    private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    public static HttpResponse<String> sendGet(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .setHeader("User-Agent", "HttpClient")
                .build();

        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException("The request argument is not a request  ", e);
        }
    }

    public static HttpResponse<String> sendPost(String url, Map<Object, Object> data) {
        String boundary = "-------------oiawn4tp89n4e9p5";

        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(data))
                .uri(URI.create(url))
                .setHeader("User-Agent", "HttpClient")
                .header("Content-Type",
                        "multipart/form-data; boundary=" + boundary)
                .build();

        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException("The request argument is not a request  ", e);
        }
    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }
}
