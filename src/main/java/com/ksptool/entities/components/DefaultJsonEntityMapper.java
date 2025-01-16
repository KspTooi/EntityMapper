package com.ksptool.entities.components;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.util.Collections;
import java.util.List;

/**
 * Default implementation of JSON entity mapper.
 * Uses Gson to serialize and deserialize objects.
 * <p>
 * JSON 实体映射器的默认实现。
 * 使用 Gson 来序列化和反序列化对象。
 */
public class DefaultJsonEntityMapper implements JsonEntityMapper {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    /**
     * Converts an object to a JSON string.
     * <p>
     * 将对象转换为 JSON 字符串。
     *
     * @param obj The object to convert.  要转换的对象
     * @return The JSON string representation of the object. 对象的 JSON 字符串表示形式
     */
    @Override
    public String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        return gson.toJson(obj);
    }

    /**
     * Converts a JSON string to an object of the specified class.
     * <p>
     * 将 JSON 字符串转换为指定类的对象。
     *
     * @param json  The JSON string to convert. 要转换的 JSON 字符串
     * @param clazz The class of the object to create. 要创建的对象的类
     * @param <T>   The type of the object to create. 要创建的对象的类型
     * @return The object created from the JSON string. 从 JSON 字符串创建的对象
     */
    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        if (isBlank(json)) {
            return null;
        }
        try {
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts a JSON array string to a list of objects of the specified class.
     * <p>
     * 将 JSON 数组字符串转换为指定类的对象列表。
     *
     * @param json  The JSON array string to convert. 要转换的 JSON 数组字符串
     * @param clazz The class of the objects in the list. 列表中对象的类
     * @param <T>   The type of the objects in the list. 列表中对象的类型
     * @return The list of objects created from the JSON array string. 从 JSON 数组字符串创建的对象列表
     */
    @Override
    public <T> List<T> fromJsonArray(String json, Class<T> clazz) {
        if (isBlank(json)) {
            return Collections.emptyList();
        }
        try {
            return gson.fromJson(json, TypeToken.getParameterized(List.class, clazz).getType());
        } catch (JsonSyntaxException e) {
            return Collections.emptyList();
        }
    }

    protected boolean isBlank(String s) {
        if (s == null || s.trim().isEmpty()) {
            return true;
        }
        return false;
    }
}