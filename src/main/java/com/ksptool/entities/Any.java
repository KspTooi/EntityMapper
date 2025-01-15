package com.ksptool.entities;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Any<T>{

    private final T content;
    private final Map<String,Object> putMap = new ConcurrentHashMap<>();
    private final EntityOperation eo;

    public Any(T t){
        this.content = t;
        this.eo = Entities.getGlobalInstance();
    }

    public Any(T t,EntityOperation eo){
        this.content = t;
        this.eo = eo;
    }

    public static <T> Any<T> of(T t){
        if(t == null){
            throw new NullPointerException();
        }
        return new Any<T>(t);
    }

    public static <T> Any<T> of(T t,EntityOperation eo){
        if(t == null){
            throw new NullPointerException();
        }
        if(eo == null){
            return new Any<T>(t);
        }
        return new Any<T>(t,eo);
    }
    public static Any<Map<String,Object>> of(){
        return of(null);
    }
    public static Any<Map<String,Object>> of(EntityOperation eo){

        if(eo == null){
            return new Any<>(new HashMap<>());
        }
        return new Any<>(new HashMap<>(),eo);
    }

    public T get(){
        return content;
    }

    public Any<T> val(String k,Object v){

        try{

            putMap.put(k,v);

            if(content instanceof List){
                for(Object item : (List<?>)content){
                    eo.assign(putMap,item);
                }
                return this;
            }

            if(content instanceof Map){
                ((Map<String, Object>) content).put(k,v);
                return this;
            }

            eo.assign(putMap,content);

        }finally {
            putMap.clear();
        }

        return this;
    }

    public <R> R as(Class<R> target){
        return eo.as(this.content,target);
    }

    public <R> List<R> asList(Class<R> target){
        if(!(content instanceof List)){
            return Collections.emptyList();
        }
        return eo.as((List<?>)this.content,target);
    }

    public <TARGET> Any<TARGET> to(Class<TARGET> target){
        return Any.of(this.as(target));
    }

    public <TARGET> Any<List<TARGET>> toList(Class<TARGET> target){
        return Any.of(this.asList(target));
    }

    public AnyMatchUpdate matchUpdate(String updateField){
        return new AnyMatchUpdate(this,updateField);
    }

    public AnyMatchUpdate matchUpdate(String updateField,String matchValue){
        return new AnyMatchUpdate(this,updateField,matchValue);
    }

    public EntityOperation getEntityOperation(){
        return this.eo;
    }

}
