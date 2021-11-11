package com.example.utils;

import com.example.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {
    public static boolean isFieldInJSON(String jsonAnswer, String nameField) {
        JSONObject jsonObj = new JSONObject(jsonAnswer);
        return jsonObj.has(nameField);
    }

    public static boolean isJSONValid(String jsonAnswer) {
        try {
            new JSONObject(jsonAnswer);
        } catch (JSONException ex) {
            try {
                new JSONArray(jsonAnswer);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static User getUserFromJsonFile(String jsonFile) {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = JsonUtils.class.getResourceAsStream(jsonFile);
        try {
            return mapper.readValue(is, User.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not deserialize: ", e);
        }
    }
}