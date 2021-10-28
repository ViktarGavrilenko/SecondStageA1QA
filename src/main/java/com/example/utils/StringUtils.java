package com.example.utils;

import java.util.Random;

public class StringUtils {

    private static Random randomChar = new Random();

    public static String getFirstPartEmail(String email) {
        return email.substring(0, email.indexOf('@'));
    }

    public static String getSecondPartEmail(String email) {
        String secondPart = getAllSecondPartEmail(email);
        return secondPart.substring(0, secondPart.indexOf("."));
    }

    public static String getThirdPartEmail(String email) {
        String secondPart = getAllSecondPartEmail(email);
        return secondPart.substring(secondPart.indexOf("."));
    }

    private static String getAllSecondPartEmail(String email) {
        return email.substring(email.indexOf('@') + 1);
    }

    public static String generationPassword(String email) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (i < 4 || i == 7) {
                password.append((int) (Math.random() * 9));
            } else {
                if (i == 6) {
                    password.append(email.charAt(0));
                } else {
                    if (i < 8) {
                        password.append(String.valueOf((char) (randomChar.nextInt(26) + 'a')).toUpperCase());
                    } else {
                        password.append((char) (randomChar.nextInt(26) + 'a'));
                    }
                }
            }
        }
        return password.toString();
    }
}