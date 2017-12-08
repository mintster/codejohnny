package com.codejohnny.core;

import com.codejohnny.containers.CodeJohnnyProperty;
import com.codejohnny.containers.CodeJohnnyTemplate;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class CodeJohnnyUtils {

    public static String pluralize(String singular) {
        String plural = singular;
        int singularLength = StringUtils.length(singular);
        if (StringUtils.right(singular, 1) == "y")
            plural = StringUtils.left(singular, singularLength - 1) + "ies";
        else
            plural = singular + "s";
        return plural;
    }

    public static String lowerPluralize(String singular) {
        return StringUtils.uncapitalize(pluralize(singular));
    }

    public static String lower(String singular) {
        return StringUtils.uncapitalize(singular);
    }

    public static Boolean getBooleanProperty(CodeJohnnyTemplate codeJohnnyTemplate, String propertyName) {
        List<CodeJohnnyProperty> properties = codeJohnnyTemplate.getProperties();
        CodeJohnnyProperty property = null;
        try {
            property = properties.stream()
                    .filter(p -> p.name.equals(propertyName))
                    .findFirst().get();
        } catch (Exception e) {
            System.out.println(String.format("Boolean Property [%s] not found in Template < properties />", propertyName.toUpperCase()));
            System.exit(0);
        }
        return (Boolean) toObject(property.getType(), property.getValue());
    }

    public static Integer getIntegerProperty(CodeJohnnyTemplate codeJohnnyTemplate, String propertyName) {
        List<CodeJohnnyProperty> properties = codeJohnnyTemplate.getProperties();
        CodeJohnnyProperty property = null;
        try {
            property = properties.stream()
                    .filter(p -> p.name.equals(propertyName))
                    .findFirst().get();
        } catch (Exception e) {
            System.out.println(String.format("Integer Property [%s] not found in Template < properties />", propertyName.toUpperCase()));
            System.exit(0);
        }
        return (Integer) toObject(property.getType(), property.getValue());
    }

    public static String getStringProperty(CodeJohnnyTemplate codeJohnnyTemplate, String propertyName) {
        List<CodeJohnnyProperty> properties = codeJohnnyTemplate.getProperties();
        CodeJohnnyProperty property = null;
        try {
            property = properties.stream()
                    .filter(p -> p.name.equals(propertyName))
                    .findFirst().get();
        } catch (Exception e) {
            System.out.println(String.format("String Property [%s] not found in Template < properties />", propertyName.toUpperCase()));
            System.exit(0);
        }
        return (String) toObject(property.getType(), property.getValue());
    }

    private static Object toObject(Class clazz, String value) {
        if (Boolean.class == clazz) return Boolean.parseBoolean(value);
        if (Byte.class == clazz) return Byte.parseByte(value);
        if (Short.class == clazz) return Short.parseShort(value);
        if (Integer.class == clazz) return Integer.parseInt(value);
        if (Long.class == clazz) return Long.parseLong(value);
        if (Float.class == clazz) return Float.parseFloat(value);
        if (Double.class == clazz) return Double.parseDouble(value);
        return value;
    }


    public static boolean isInTestingMode() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        List<StackTraceElement> list = Arrays.asList(stackTrace);
        for (StackTraceElement element : list) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }
}
