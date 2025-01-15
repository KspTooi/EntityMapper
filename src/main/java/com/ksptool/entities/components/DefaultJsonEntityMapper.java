package com.ksptool.entities.components;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.util.Collections;
import java.util.List;

public class DefaultJsonEntityMapper implements JsonEntityMapper {

    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    @Override
    public String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        return gson.toJson(obj);
    }

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