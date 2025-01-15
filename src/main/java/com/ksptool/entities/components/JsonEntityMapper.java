package com.ksptool.entities.components;

import java.util.List;

public interface JsonEntityMapper {
    public String toJson(Object obj);

    public <T> T fromJson(String json, Class<T> clazz);

    public <T> List<T> fromJsonArray(String json, Class<T> clazz);
}
