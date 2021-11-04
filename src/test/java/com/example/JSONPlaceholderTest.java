package com.example;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.example.models.*;
import com.example.resourcesjson.JSONPlaceholder;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.utils.APIUtils.getJsonAnswer;
import static com.example.utils.APIUtils.getStatusCode;
import static com.example.utils.StringUtils.generateRandomText;
import static org.testng.Assert.*;

public class JSONPlaceholderTest {
    private static final int STATUS_CODE_TWO_HUNDRED = 200;
    private static final int STATUS_CODE_TWO_HUNDRED_ONE = 201;
    private static final int STATUS_CODE_FOUR_HUNDRED_FOUR = 404;
    private static final int POST_NINETY_NINE = 99;
    private static final int POST_ONE_HUNDRED_FIFTY = 150;
    private static final int FIFTH_USER = 5;
    private static final int TENTH_USERID = 10;

    private static final String ID_FIELD_NAME = "id";
    private static final String RANDOM_TITLE = generateRandomText();
    private static final String RANDOM_BODY = generateRandomText();

    private static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    private static final String FIFTH_NAME = TEST_DATA_FILE.getValue("/fifthName").toString();
    private static final String FIFTH_USERNAME = TEST_DATA_FILE.getValue("/fifthUsername").toString();
    private static final String FIFTH_EMAIL = TEST_DATA_FILE.getValue("/fifthEmail").toString();
    private static final String FIFTH_STREET = TEST_DATA_FILE.getValue("/fifthStreet").toString();
    private static final String FIFTH_SUITE = TEST_DATA_FILE.getValue("/fifthSuite").toString();
    private static final String FIFTH_CITY = TEST_DATA_FILE.getValue("/fifthCity").toString();
    private static final String FIFTH_ZIPCODE = TEST_DATA_FILE.getValue("/fifthZipcode").toString();
    private static final String FIFTH_LAT = TEST_DATA_FILE.getValue("/fifthLat").toString();
    private static final String FIFTH_LNG = TEST_DATA_FILE.getValue("/fifthLng").toString();
    private static final String FIFTH_PHONE = TEST_DATA_FILE.getValue("/fifthPhone").toString();
    private static final String FIFTH_WEBSITE = TEST_DATA_FILE.getValue("/fifthWebsite").toString();
    private static final String FIFTH_COMPANY_NAME = TEST_DATA_FILE.getValue("/fifthCompanyName").toString();
    private static final String FIFTH_CATCH_PHRASE = TEST_DATA_FILE.getValue("/fifthCatchPhrase").toString();
    private static final String FIFTH_BS = TEST_DATA_FILE.getValue("/fifthBs").toString();

    @Test(description = "Testing JSONPlaceholderTest")
    public void testJSONPlaceholder() throws Exception {

        JSONPlaceholder jsonPlaceholder = new JSONPlaceholder();
        List<Post> posts = jsonPlaceholder.getPostsFromGETRequest();
        Logger.getInstance().info("Checking status code");
        assertEquals(getStatusCode(), STATUS_CODE_TWO_HUNDRED, "Status code is not " + STATUS_CODE_TWO_HUNDRED);
        Logger.getInstance().info("Checking that the list of posts is returned in JSON format");
        assertTrue(jsonPlaceholder.isJSONValid(getJsonAnswer()), "The list of posts did not return in JSON format");
        Logger.getInstance().info("Checking if the list is sorted in ascending order");
        assertTrue(jsonPlaceholder.isListSortedById(posts), "The list is not sorted in ascending order (by id)");

        Post post = jsonPlaceholder.getPostFromGETRequest(POST_NINETY_NINE);
        Logger.getInstance().info("Checking status code");
        assertEquals(getStatusCode(), STATUS_CODE_TWO_HUNDRED, "Status code is not " + STATUS_CODE_TWO_HUNDRED);
        Logger.getInstance().info("Checking userId");
        assertEquals(post.userId, TENTH_USERID, "userId is not " + TENTH_USERID);
        Logger.getInstance().info("Checking id");
        assertEquals(post.id, POST_NINETY_NINE, "id is not " + POST_NINETY_NINE);
        Logger.getInstance().info("Checking if body value is not empty strings");
        assertFalse(post.body.isEmpty(), "Body value is empty strings");
        Logger.getInstance().info("Checking if title value is not empty strings");
        assertFalse(post.title.isEmpty(), "Title value is empty strings");

        jsonPlaceholder.getPostFromGETRequest(POST_ONE_HUNDRED_FIFTY);
        Logger.getInstance().info("Checking status code");
        assertEquals(getStatusCode(), STATUS_CODE_FOUR_HUNDRED_FOUR,
                "Status code is not " + STATUS_CODE_FOUR_HUNDRED_FOUR);
        Logger.getInstance().info("Checking status code");
        assertEquals(getJsonAnswer(), "{}", "Response body is not empty");

        Map<Object, Object> data = new HashMap<>();
        data.put("title", RANDOM_TITLE);
        data.put("body", RANDOM_BODY);
        data.put("userId", 1);

        Post expectedPost = new Post();
        expectedPost.userId = 1;
        expectedPost.body = RANDOM_BODY;
        expectedPost.title = RANDOM_TITLE;

        post = jsonPlaceholder.getPostFromPOSTRequest(data);
        Logger.getInstance().info("Checking status code");
        assertEquals(getStatusCode(), STATUS_CODE_TWO_HUNDRED_ONE,
                "Status code is not " + STATUS_CODE_TWO_HUNDRED_ONE);
        Logger.getInstance().info("Checking if id is present in response");
        assertTrue(jsonPlaceholder.isFieldInJSON(getJsonAnswer(), ID_FIELD_NAME),
                ID_FIELD_NAME + " is not present in response");
        Logger.getInstance().info("Checking post data");
        assertEquals(post, expectedPost, "The posts has incorrect data");

        User fifthUser = new User();
        Address fifthAddress = new Address();
        Geo fifthGeo = new Geo();
        Company fifthCompany = new Company();

        fifthUser.name = FIFTH_NAME;
        fifthUser.username = FIFTH_USERNAME;
        fifthUser.email = FIFTH_EMAIL;
        fifthAddress.street = FIFTH_STREET;
        fifthAddress.suite = FIFTH_SUITE;
        fifthAddress.city = FIFTH_CITY;
        fifthAddress.zipcode = FIFTH_ZIPCODE;
        fifthGeo.lat = FIFTH_LAT;
        fifthGeo.lng = FIFTH_LNG;
        fifthAddress.geo = fifthGeo;
        fifthUser.address = fifthAddress;
        fifthUser.phone = FIFTH_PHONE;
        fifthUser.website = FIFTH_WEBSITE;
        fifthCompany.name = FIFTH_COMPANY_NAME;
        fifthCompany.catchPhrase = FIFTH_CATCH_PHRASE;
        fifthCompany.bs = FIFTH_BS;
        fifthUser.company = fifthCompany;

        List<User> usersList = jsonPlaceholder.getUsersFromGETRequest();
        Logger.getInstance().info("Checking status code");
        assertEquals(getStatusCode(), STATUS_CODE_TWO_HUNDRED, "Status code is not " + STATUS_CODE_TWO_HUNDRED);
        Logger.getInstance().info("Checking that the list of users is returned in JSON format");
        assertTrue(jsonPlaceholder.isJSONValid(getJsonAnswer()), "The list of users did not return in JSON format");
        Logger.getInstance().info("Checking user data with id " + FIFTH_USER);
        assertEquals(usersList.get(FIFTH_USER - 1), fifthUser, "The user has incorrect data");

        User user = jsonPlaceholder.getUserFromGETRequest(FIFTH_USER);
        Logger.getInstance().info("Checking status code");
        assertEquals(getStatusCode(), STATUS_CODE_TWO_HUNDRED, "Status code is not " + STATUS_CODE_TWO_HUNDRED);
        Logger.getInstance().info("Checking user data");
        assertEquals(user, fifthUser, "The user has incorrect data");
    }
}