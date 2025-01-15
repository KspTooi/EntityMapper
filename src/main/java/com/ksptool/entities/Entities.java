package com.ksptool.entities;

import com.ksptool.entities.components.DefaultEntityMapper;
import com.ksptool.entities.components.DefaultJsonEntityMapper;
import com.ksptool.entities.components.EntityMapper;
import com.ksptool.entities.components.JsonEntityMapper;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Entities {

    private static EntityOperation eo;

    public static EntityMapper getObjectMapper() {
        return getGlobalInstance().getObjectMapper();
    }
    public static void setObjectMapper(EntityMapper m) {
        getGlobalInstance().setObjectMapper(m);
    }
    public static void setJsonEntityMapper(JsonEntityMapper m) {
        getGlobalInstance().setJem(m);
    }
    public static JsonEntityMapper getJsonEntityMapper() {
        return getGlobalInstance().getJsonEntityMapper();
    }

    public static synchronized EntityOperation getGlobalInstance(){
        if(eo == null){
            eo = new EntityOperation(new DefaultEntityMapper(),new DefaultJsonEntityMapper());
        }
        return eo;
    }

    public static <T> List<T> as(List<?> source,Class<T> target){

        if(source == null){
            return new ArrayList<>();
        }

        try{
            var ret = new ArrayList<T>();
            for(var po : source){
                var vo = target.getDeclaredConstructor().newInstance();
                getGlobalInstance().assign(po, vo);
                ret.add(vo);
            }
            return ret;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public static <T> T as(Object source,Class<T> target){

        try{

            var instance = target.getDeclaredConstructor().newInstance();

            if(source == null){
                return instance;
            }

            getGlobalInstance().assign(source, instance);
            return instance;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static void as(Object source, Object target){
        getGlobalInstance().assign(source, target);
    }

    public static void assign(Object source, Object target){
        getGlobalInstance().assign(source, target);
    }

    public static <T> T fromJson(String json,Class<T> target){
        try{
            return getGlobalInstance().fromJson(json,target);
        }catch (Exception e){
            return null;
        }
    }

    public static <T> List<T> fromJsonArray(String json,Class<T> target){

        if(Strings.isBlank(json)){
            return new ArrayList<>();
        }

        try{
            return getGlobalInstance().fromJsonArray(json, target);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public static String toJson(Object object){
        return getGlobalInstance().toJson(object);
    }





}
