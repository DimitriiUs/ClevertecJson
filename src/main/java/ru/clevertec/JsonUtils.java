package ru.clevertec;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class JsonUtils {

    public static String getJson(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        int length = Array.getLength(fields);

        for (int i = 0; i < length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            if (field.isSynthetic()){continue;}
            stringBuilder.append(formatString(field.getName())).append(":");

            try {
                if (field.getType().isPrimitive()) {
                    stringBuilder.append(field.get(object));
                } else if (field.getType().equals(String.class)) {
                    stringBuilder.append(formatString(field.get(object).toString()));
                } else if (field.getType().isArray()) {
                    stringBuilder.append(jsonArray(field.get(object)));
                } else {
                    stringBuilder.append(getJson(field.get(object)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (i != length - 1) {stringBuilder.append(",");}
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private static String jsonArray(Object arrObject) {
        Class<?> arrType = arrObject.getClass().getComponentType();
        int length = Array.getLength(arrObject);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for (int i = 0; i < length; i++) {
            Object item = Array.get(arrObject, i);
            if (arrType.isPrimitive()) {
                stringBuilder.append(item);
            } else if (arrType.equals(String.class)) {
                stringBuilder.append(formatString(item.toString()));
            } else {
                stringBuilder.append(getJson(item));
            }
            if (i != length - 1) {stringBuilder.append(",");}
        }

        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private static String formatString(String value) {
        return String.format("\"%s\"", value);
    }
}
