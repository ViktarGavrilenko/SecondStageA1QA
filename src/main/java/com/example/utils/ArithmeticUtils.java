package com.example.utils;

import java.util.Random;

public class ArithmeticUtils {
    private static Random random = new Random();

    public static int getRandomNumberFromOneToMaxValue(int maxValue) {
        return random.nextInt(maxValue) + 1;
    }
}