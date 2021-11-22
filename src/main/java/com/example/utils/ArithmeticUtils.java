package com.example.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;

import static com.example.utils.StringUtils.getCurrentDate;

public class ArithmeticUtils {
    private static Random random = new Random();
    private static final int HOUR_OF_DAY = 24;
    private static final int SIXTY = 60;

    public static int getRandomNumberFromOneToMaxValue(int maxValue) {
        return random.nextInt(maxValue) + 1;
    }

    public static Timestamp updateTime(Timestamp time) {
        if (time == null) {
            time = Timestamp.valueOf(getCurrentDate());
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.HOUR_OF_DAY, getRandomNumberFromOneToMaxValue(HOUR_OF_DAY));
        cal.add(Calendar.MINUTE, getRandomNumberFromOneToMaxValue(SIXTY));
        cal.add(Calendar.SECOND, getRandomNumberFromOneToMaxValue(SIXTY));
        time.setTime(cal.getTime().getTime());
        return time;
    }
}