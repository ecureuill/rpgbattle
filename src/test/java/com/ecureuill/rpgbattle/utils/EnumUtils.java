package com.ecureuill.rpgbattle.utils;

import java.util.Random;

public class EnumUtils {
    public static <T extends Enum<?>> T getRandomEnumValue(Class<T> enumClass) {
        T[] enumValues = enumClass.getEnumConstants();
        int randomIndex = new Random().nextInt(enumValues.length);
        return enumValues[randomIndex];
    }

}