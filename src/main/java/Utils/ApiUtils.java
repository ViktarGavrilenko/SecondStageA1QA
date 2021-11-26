package Utils;

import aquality.selenium.core.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ApiUtils {
    private static final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private static final String GET_REQUEST_ERROR = "GET request error ";
    private static final String POST_REQUEST_ERROR = "POST request error ";
    private static final String CONVERSION_ERROR = "Conversion error HttpEntity into JSON ";
    private static final String FILE_NOT_FOUND = "Error file not found: ";

    public static HttpResponse<String> sendGet(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .setHeader("User-Agent", "HttpClient")
                .build();

        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Logger.getInstance().error(GET_REQUEST_ERROR + e);
            throw new IllegalArgumentException(GET_REQUEST_ERROR, e);

        }
    }

    public static HttpEntity sendPost(String url, String pathFile, String nameField) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        File f = new File(pathFile);
        try {
            builder.addBinaryBody(
                    nameField,
                    new FileInputStream(f),
                    ContentType.APPLICATION_OCTET_STREAM,
                    f.getName()
            );
        } catch (FileNotFoundException e) {
            Logger.getInstance().error(FILE_NOT_FOUND + e);
            throw new IllegalArgumentException(FILE_NOT_FOUND, e);
        }

        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);

        try {
            return httpClient.execute(uploadFile).getEntity();
        } catch (IOException e) {
            Logger.getInstance().error(POST_REQUEST_ERROR + e);
            throw new IllegalArgumentException(POST_REQUEST_ERROR, e);
        }
    }

    public static String convertHttpEntityIntoJson(HttpEntity entity) {
        try {
            String res;
            InputStream inStream;
            inStream = entity.getContent();
            byte[] bytes;
            bytes = IOUtils.toByteArray(inStream);
            res = new String(bytes, StandardCharsets.UTF_8);
            inStream.close();
            return res;
        } catch (IOException e) {
            Logger.getInstance().error(CONVERSION_ERROR + e);
            throw new IllegalArgumentException(CONVERSION_ERROR, e);
        }
    }
}