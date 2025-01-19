package com.ksptool.entities;

import com.ksptool.entities.components.EntityMapper;
import com.ksptool.entities.components.JsonEntityMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class EntityOperation {

    private EntityMapper em;
    private JsonEntityMapper jem;

    public EntityOperation(EntityMapper entityMapper,JsonEntityMapper jsonEntityMapper){
        if(entityMapper == null){
            throw new NullPointerException("entityMapper is null");
        }
        this.em = entityMapper;
        this.jem = jsonEntityMapper;
    }
    public EntityMapper getEntityMapper() {
        return em;
    }
    public void setEntityMapper(EntityMapper m) {
        if(m != null){
            em = m;
        }
    }
    public void setJsonEntityMapper(JsonEntityMapper m) {
        if(m != null){
            jem = m;
        }
    }
    public JsonEntityMapper getJsonEntityMapper() {
        return jem;
    }

    public <T> List<T> as(List<?> source, Class<T> target){

        if(source == null){
            return new ArrayList<>();
        }

        try{
            List<T> ret = new ArrayList<T>();
            for(Object po : source){
                T vo = target.getDeclaredConstructor().newInstance();
                em.assign(po, vo);
                ret.add(vo);
            }
            return ret;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }


    public <T> T as(Object source,Class<T> target){
        try{
            T instance = target.getDeclaredConstructor().newInstance();
            if(source == null){
                return instance;
            }
            em.assign(source, instance);
            return instance;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void as(Object source, Object target){
        em.assign(source, target);
    }

    public void assign(Object source, Object target){
        em.assign(source, target);
    }

    public <T> T fromJson(String json,Class<T> target){
        try{
            return jem.fromJson(json,target);
        }catch (Exception e){
            return null;
        }
    }

    public <T> List<T> fromJsonArray(String json,Class<T> target){

        if(Strings.isBlank(json)){
            return new ArrayList<>();
        }

        try{
            return jem.fromJsonArray(json, target);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public String toJson(Object object){
        return jem.toJson(object);
    }

}
