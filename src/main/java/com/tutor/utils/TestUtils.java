package com.tutor.utils;

public class TestUtils {

    public static String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("[\\s,]", "").trim();
    }
}
