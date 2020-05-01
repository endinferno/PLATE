package com.buaa.man.Dao;

import java.util.HashMap;
import java.util.Map;

public enum Type {
    /**
     * 一对一
     */
    ONE_TO_ONE(1),
    /**
     * 一对多
     */
    ONE_TO_MANY(2),
    /**
     * 多对一
     */
    MANY_TO_ONE(3),
    /**
     * 多对多
     */
    MANY_TO_MANY(4);

    private static final Map<Integer, Type> MAP;

    static {
        MAP = new HashMap<>();
        MAP.put(1, Type.ONE_TO_ONE);
        MAP.put(2, Type.ONE_TO_MANY);
        MAP.put(3, Type.MANY_TO_ONE);
        MAP.put(4, Type.MANY_TO_MANY);
    }

    int value;

    Type(int value) {
        this.value = value;
    }

    public Type getType(int value) {
        return MAP.get(value);
    }
}
