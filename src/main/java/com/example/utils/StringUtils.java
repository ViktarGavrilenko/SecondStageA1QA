package com.example.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
    public static String getProjectName() {
        String projectName = System.getProperty("user.dir");
        return projectName.substring(projectName.lastIndexOf('\\') + 1, projectName.length());
    }

    public static String addSlashes(String str) {
        return str.replace("'", "\\'");
    }

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format((new Date()));
    }
}