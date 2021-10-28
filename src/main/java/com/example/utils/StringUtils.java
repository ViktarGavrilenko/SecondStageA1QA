package com.example.utils;

import java.util.Random;

public class StringUtils {

    private static final Random RANDOM = new Random();
    private static final int NUMBER_OF_LETTERS_ALPHABET = 26;


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
                password.append(RANDOM.nextInt(9));
            } else {
                if (i == 6) {
                    password.append(email.charAt(0));
                } else {
                    if (i < 8) {
                        password.append(String.valueOf(
                                (char) (RANDOM.nextInt(NUMBER_OF_LETTERS_ALPHABET) + 'a')).toUpperCase());
                    } else {
                        password.append((char) (RANDOM.nextInt(NUMBER_OF_LETTERS_ALPHABET) + 'a'));
                    }
                }
            }
        }
        return password.toString();
    }
}