package com.megaorders.utils;

import java.lang.reflect.Field;

public class FieldUtils {
    public static void replaceEmptyStringsWithNulls(Object obj) {
        if (obj == null) return;

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.getType() == String.class) { // Only process String fields
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if ("".equals(value)) {
                        field.set(obj, null);
                    }
                } catch (IllegalAccessException e) {
                    // Handle or rethrow based on your error handling policy
                    e.printStackTrace();
                }
            }
        }
    }
}
