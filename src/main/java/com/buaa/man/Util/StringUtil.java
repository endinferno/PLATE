package com.buaa.man.Util;

public final class StringUtil {
    private StringUtil() {
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || 0 == str.trim().length();
    }
}
