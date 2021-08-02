package io.github.palexdev.raw4j.utils;

import java.util.Random;

public class StringUtils {

    private StringUtils() {}

    public static String randomString(int length) {
        Random random = new Random();
        return random.ints(48, 122 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Replaces the last occurrence of the given string with a new string.
     *
     * @param string      The string to modify
     * @param substring   The last occurrence to find
     * @param replacement The replacement
     * @return The modified string
     */
    public static String replaceLast(String string, String substring, String replacement) {
        int index = string.lastIndexOf(substring);
        if (index == -1)
            return string;
        return string.substring(0, index) + replacement
                + string.substring(index + substring.length());
    }
}
