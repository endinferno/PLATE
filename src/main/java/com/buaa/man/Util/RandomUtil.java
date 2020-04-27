package com.buaa.man.Util;

import java.util.Random;

public final class RandomUtil {
    private RandomUtil() {
    }

    public static String getCode() {
        Random r = new Random();
        return  "" + ((10000000 + r.nextInt(10000000)) % 900000 + 100000);
    }
}
