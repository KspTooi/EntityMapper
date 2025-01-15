package com.ksptool.entities;

import java.util.List;

public interface JsonMapper {

    public String toJson(Object obj);

    public <T> T fromJson(String json, Class<T> clazz);

    public <T> List<T> fromJsonArray(String json, Class<T> clazz);
    
}
