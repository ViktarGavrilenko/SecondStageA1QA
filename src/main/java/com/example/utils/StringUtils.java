package com.example.utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringUtils {
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    protected static final String LOGGER_FILE = System.getProperty("user.dir") + TEST_DATA_FILE.getValue("/logger").toString();
    private static final Random RANDOM = new Random();
    private static final int NUMBER_OF_LETTERS_ALPHABET = 26;
    private static final int NUMBER_OF_DIGITS_IN_PASSWORD = 4;
    private static final int NUMBER_OF_CAPITAL_LETTERS_IN_PASSWORD = 3;
    private static final int NUMBER_OF_LOWERCASE_LETTERS_IN_PASSWORD = 2;
    private static final String START_STR_OF_LOGGER = "Got browser profile options from settings file:";


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
        StringBuilder dataForPassword = new StringBuilder();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < NUMBER_OF_DIGITS_IN_PASSWORD; i++) {
            dataForPassword.append(RANDOM.nextInt(9));
        }

        for (int i = 0; i < NUMBER_OF_LOWERCASE_LETTERS_IN_PASSWORD; i++) {
            dataForPassword.append((char) (RANDOM.nextInt(NUMBER_OF_LETTERS_ALPHABET) + 'a'));
        }

        for (int i = 0; i < NUMBER_OF_CAPITAL_LETTERS_IN_PASSWORD; i++) {
            dataForPassword.append(String.valueOf((char) (RANDOM.nextInt(NUMBER_OF_LETTERS_ALPHABET) + 'a')).toUpperCase());
        }

        dataForPassword.append(email.charAt(0));

        int randomSymbol;
        int passwordLength = dataForPassword.length();

        for (int i = 0; i < passwordLength; i++) {
            randomSymbol = RANDOM.nextInt(dataForPassword.length());
            password.append(dataForPassword.charAt(randomSymbol));
            dataForPassword.deleteCharAt(randomSymbol);
        }

        return password.toString();
    }

    public static String getProjectName() {
        String projectName = System.getProperty("user.dir");
        return projectName.substring(projectName.lastIndexOf('\\') + 1, projectName.length());
    }

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format((new Date()));
    }

    public static String getLogOfTest() {
        StringBuilder logText = new StringBuilder();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(LOGGER_FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    logText.append(line).append("\n");
                    if (line.contains(START_STR_OF_LOGGER)) {
                        logText.setLength(0);
                        logText.append(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(logText);
    }

    public static String addSlashes(String str) {
        return str.replace("'", "\\'");
    }
}
