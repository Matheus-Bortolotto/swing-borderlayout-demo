
package com.example.app.util;

import java.util.regex.Pattern;

public final class Validation {
    private static final Pattern EMAIL =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);

    private Validation(){}

    public static String normalize(String s) {
        return s == null ? "" : s.trim().replaceAll("\\s+", " ");
    }

    public static boolean notBlank(String s) {
        return !normalize(s).isEmpty();
    }

    public static boolean isEmail(String s) {
        s = normalize(s);
        return !s.isEmpty() && EMAIL.matcher(s).matches();
    }
}
