package com.buaa.man.Util;

import java.util.List;

public final class ArrayUtil {

    private ArrayUtil() {
    }

    public static boolean isEmptyOrNull(List list) {
        return null == list || list.isEmpty();
    }
}
