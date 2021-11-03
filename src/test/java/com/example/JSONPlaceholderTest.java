package com.example;

import com.example.jsonmodels.Post;
import com.example.jsonmodels.User;
import com.example.resourcesjson.JSONPlaceholder;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.utils.APIUtils.getJsonAnswer;
import static com.example.utils.APIUtils.getStatusCode;
import static org.testng.Assert.*;

public class JSONPlaceholderTest {
    private static final int STATUS_CODE_TWO_HUNDRED = 200;
    private static final int STATUS_CODE_TWO_HUNDRED_ONE = 201;
    private static final int STATUS_CODE_FOUR_HUNDRED_FOUR = 404;

    @Test(description = "Тест формы JSONPlaceholderTest")
    public void testJSONPlaceholder() throws Exception {
        JSONPlaceholder jsonPlaceholder = new JSONPlaceholder();
        List<Post> posts = jsonPlaceholder.getPostsFromGETRequest();
        assertEquals(getStatusCode(), STATUS_CODE_TWO_HUNDRED, "Status code is not 200");
        assertTrue(jsonPlaceholder.isListSortedById(posts), "The list is not sorted in ascending order (by id)");

        Post post = jsonPlaceholder.getPostFromGETRequest(99);
        assertEquals(getStatusCode(), STATUS_CODE_TWO_HUNDRED, "Status code is not 200");
        assertEquals(post.id, 99, "id is not 99");
        assertEquals(post.userId, 10, "userId is not 10");
        assertFalse(post.body.isEmpty(), "Body values is empty strings");
        assertFalse(post.title.isEmpty(), "Title values is empty strings");

        jsonPlaceholder.getPostFromGETRequest(150);
        assertEquals(getStatusCode(), STATUS_CODE_FOUR_HUNDRED_FOUR, "Status code is not 404");
        assertEquals(getJsonAnswer(), "{}", "Status code is not 404");

        Map<Object, Object> data = new HashMap<>();
        data.put("title", "abc");
        data.put("body", "123");
        data.put("userId", "1");

        post = jsonPlaceholder.getAnswerFromPOSTRequest(data);
        assertEquals(getStatusCode(), STATUS_CODE_TWO_HUNDRED_ONE, "Status code is not 201");
        JSONObject jsonObj = new JSONObject(getJsonAnswer());
        assertTrue(jsonObj.has("id"), "id is not present in response");
        assertEquals(post.userId, 1, "userId is not 1");
        assertEquals(post.body, "123", "The body value does not match those given in the request");
        assertEquals(post.title, "abc", "The title value does not match those given in the request");

        List<User> usersList = jsonPlaceholder.getUsersFromGETRequest();
        assertEquals(getStatusCode(), STATUS_CODE_TWO_HUNDRED, "Status code is not 200");
        assertEquals(usersList.get(4).name, "Chelsey Dietrich", "Name is not Chelsey Dietrich");
        assertEquals(usersList.get(4).username, "Kamren", "Username is not Kamren");
        assertEquals(usersList.get(4).email, "Lucio_Hettinger@annie.ca",
                "Email is not \"Lucio_Hettinger@annie.ca\"");
        assertEquals(usersList.get(4).address.street, "Skiles Walks", "Street is not \"Skiles Walks\"");
        assertEquals(usersList.get(4).address.suite, "Suite 351", "Suite is not \"Suite 351\"");
        assertEquals(usersList.get(4).address.city, "Roscoeview", "City is not Roscoeview");
        assertEquals(usersList.get(4).address.zipcode, "33263", "Zipcode is not 33263");
        assertEquals(usersList.get(4).address.geo.lat, "-31.8129", "Lat is not -31.8129");
        assertEquals(usersList.get(4).address.geo.lng, "62.5342", "Lng is not 62.5342");
        assertEquals(usersList.get(4).phone, "(254)954-1289", "Phone is not (254)954-1289");
        assertEquals(usersList.get(4).website, "demarco.info", "Website is not \"demarco.info\"");
        assertEquals(usersList.get(4).company.name, "Keebler LLC", "Name company is not \"Keebler LLC\"");
        assertEquals(usersList.get(4).company.catchPhrase, "User-centric fault-tolerant solution",
                "catchPhrase is not \"User-centric fault-tolerant solution\"");
        assertEquals(usersList.get(4).company.bs, "revolutionize end-to-end systems",
                "bs company is not \"revolutionize end-to-end systems\"");

        User user = jsonPlaceholder.getUserFromGETRequest(5);
        assertEquals(getStatusCode(), STATUS_CODE_TWO_HUNDRED, "Status code is not 200");
        assertEquals(user.name, "Chelsey Dietrich", "Name is not Chelsey Dietrich");
        assertEquals(user.username, "Kamren", "Username is not Kamren");
        assertEquals(user.email, "Lucio_Hettinger@annie.ca",
                "Email is not \"Lucio_Hettinger@annie.ca\"");
        assertEquals(user.address.street, "Skiles Walks", "Street is not \"Skiles Walks\"");
        assertEquals(user.address.suite, "Suite 351", "Suite is not \"Suite 351\"");
        assertEquals(user.address.city, "Roscoeview", "City is not Roscoeview");
        assertEquals(user.address.zipcode, "33263", "Zipcode is not 33263");
        assertEquals(user.address.geo.lat, "-31.8129", "Lat is not -31.8129");
        assertEquals(user.address.geo.lng, "62.5342", "Lng is not 62.5342");
        assertEquals(user.phone, "(254)954-1289", "Phone is not (254)954-1289");
        assertEquals(user.website, "demarco.info", "Website is not \"demarco.info\"");
        assertEquals(user.company.name, "Keebler LLC", "Name company is not \"Keebler LLC\"");
        assertEquals(user.company.catchPhrase, "User-centric fault-tolerant solution",
                "catchPhrase is not \"User-centric fault-tolerant solution\"");
        assertEquals(user.company.bs, "revolutionize end-to-end systems",
                "bs company is not \"revolutionize end-to-end systems\"");

/*
        User fifthUser = new User();
        Address fifthAddress = new Address();
        Geo fifthGeo = new Geo();
        Company fifthCompany = new Company();

        fifthUser.name = "Chelsey Dietrich";
        fifthUser.username = "Kamren";
        fifthUser.email = "Lucio_Hettinger@annie.ca";

        fifthAddress.street = "Skiles Walks";
        fifthAddress.suite = "Suite 351";
        fifthAddress.city = "Roscoeview";
        fifthAddress.zipcode = "33263";

        fifthGeo.lat = "-31.8129";
        fifthGeo.lng = "62.5342";
        fifthAddress.geo = fifthGeo;
        fifthUser.address = fifthAddress;

        fifthUser.phone = "(254)954-1289";
        fifthUser.website = "demarco.info";
        fifthCompany.name = "Keebler LLC";
        fifthCompany.catchPhrase = "User-centric fault-tolerant solution";
        fifthCompany.bs = "revolutionize end-to-end systems";

        fifthUser.company = fifthCompany;

        assertEquals(usersList.get(4), fifthUser, "The fifth user has incorrect data");
*/
    }
}

