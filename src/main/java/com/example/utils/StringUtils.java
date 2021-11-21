package com.example.utils;

public class StringUtils {
    public static String getProjectName() {
        String projectName = System.getProperty("user.dir");
        return projectName.substring(projectName.lastIndexOf('\\') + 1, projectName.length());
    }

    public static String addSlashes(String str) {
        return str.replace("'", "\\'");
    }
}