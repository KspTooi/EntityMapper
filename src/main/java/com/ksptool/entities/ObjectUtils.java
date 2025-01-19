package com.ksptool.entities;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


class ObjectUtils {

    // ... 其他代码 ...

    protected static Object getPropertyValue(Object object, String propertyName) {
        try {
            // 尝试使用getter方法获取属性值
            String getterMethodName = "get" + capitalize(propertyName);
            Method getterMethod = object.getClass().getMethod(getterMethodName);
            return getterMethod.invoke(object);
        } catch (NoSuchMethodException e) {
            // 如果没有getter方法，则尝试直接访问字段
            return getFieldValue(object, propertyName);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("获取属性值失败", e);
        }
    }

    protected static void setPropertyValue(Object object, String propertyName, Object propertyValue) {
        try {
            // 尝试使用setter方法设置属性值
            String setterMethodName = "set" + capitalize(propertyName);
            Method setterMethod = object.getClass().getMethod(setterMethodName, propertyValue.getClass());
            setterMethod.invoke(object, propertyValue);
        } catch (NoSuchMethodException e) {
            // 如果没有setter方法，则尝试直接访问字段
            setFieldValue(object, propertyName, propertyValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("设置属性值失败", e);
        }
    }

    // ... 其他代码 ...

    protected static Object getFieldValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("获取字段值失败", e);
        }
    }

    protected static void setFieldValue(Object object, String fieldName, Object fieldValue) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, fieldValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("设置字段值失败", e);
        }
    }

    protected static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}