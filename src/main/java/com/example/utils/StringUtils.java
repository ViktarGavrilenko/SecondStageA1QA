package com.example.utils;

public class StringUtils {
    public static String getFirstPartEmail(String email) {
        return email.substring(0, email.indexOf('@'));
    }

    public static String getSecondPartEmail(String email) {
        return email.substring(email.indexOf('@') + 1, email.lastIndexOf("."));
    }
}