package com.example.utils;

import java.util.Random;

public class ArithmeticUtils {
    private static final int SELECT_ALL = 18;
    private static Random random = new Random();
    public static int getRandomNumberFromOneToMaxValue(int maxValue) {
        int value = random.nextInt(maxValue) + 1;
        while (value == SELECT_ALL) {
            value = random.nextInt(maxValue) + 1;
        }
        return value;
    }
}