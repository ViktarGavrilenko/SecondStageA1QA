package com.example.utils;

public class ArithmeticUtils {
    public static int getRandomNumberFromOneToMaxValue(int maxValue) {
        return (int) (Math.random() * maxValue) + 1;
    }
}