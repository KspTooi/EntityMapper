package com.ksptool.entities;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class DefaultJsonMapper implements JsonMapper {

    @Override
    public String toJson(Object obj) {

        if(obj == null){
            return null;
        }

        return JSON.toJSONString(obj);
    }

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            return JSON.parseObject(json, clazz);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> fromJsonArray(String json, Class<T> clazz) {
        try {
            return JSON.parseArray(json, clazz);
        }catch (Exception e){
            return List.of();
        }
    }

}
