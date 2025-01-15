package com.ksptool.entities;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;

import javax.validation.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
//implements ApplicationContextAware
public class Entities extends BeanUtils {

    private static ApplicationContext ctx;
    //private static Validator jsr303;

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator jsr303 = factory.getValidator();

    private static ObjectMapper mapper = new DefaultObjectMapper();
    private static JsonMapper jsonMapper = new DefaultJsonMapper();

    public static ObjectMapper getObjectMapper() {
        if(mapper == null){
            mapper = new DefaultObjectMapper();
        }
        return mapper;
    }
    public static void setObjectMapper(ObjectMapper m) {
        if(m != null){
            mapper = m;
        }
    }
    public static void setJsonMapper(JsonMapper m) {
        if(m != null){
            jsonMapper = m;
        }
    }
    public static JsonMapper getJsonMapper() {
        if(jsonMapper == null){
            jsonMapper = new DefaultJsonMapper();
        }
        return jsonMapper;
    }

    public static void valid(Object object){

        // 校验对象，获取校验结果
        Set<ConstraintViolation<Object>> violations = jsr303.validate(object);

        // 如果校验不通过，抛出异常
        if (!violations.isEmpty()) {
            StringBuilder message = new StringBuilder();
            message.append("\r\n");
            for (ConstraintViolation<Object> violation : violations) {
                message.append("字段校验失败:").append(violation.getPropertyPath()).append(";\r\n");
            }
            throw new ConstraintViolationException("Validation failed: " + message.toString(), violations);
        }
    }


    public static <T> List<T> as(List<?> source,Class<T> target){

        if(source == null){
            return new ArrayList<>();
        }

        try{

            var ret = new ArrayList<T>();

            for(var po : source){
                var vo = target.getDeclaredConstructor().newInstance();
                mapper.assign(po, vo);
                ret.add(vo);
            }

            return ret;

        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }


    public static <T> List<T> as(Collection<?> source, Class<T> target,FieldMap fieldMap){


        return null;
    }




    public static <T> T as(Object source,Class<T> target){

        try{

            var instance = target.getDeclaredConstructor().newInstance();

            if(source == null){
                return instance;
            }

            mapper.assign(source, instance);
            return instance;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static void as(Object source, Object target){
        mapper.assign(source, target);
    }

    public static void assign(Object source, Object target){
        mapper.assign(source, target);
    }

    public static <T> T fromJson(String json,Class<T> target){
        try{
            return jsonMapper.fromJson(json,target);
        }catch (Exception e){
            return null;
        }
    }

    public static <T> List<T> fromJsonArray(String json,Class<T> target){

        if(StringUtils.isBlank(json)){
            return new ArrayList<>();
        }

        try{
            return jsonMapper.fromJsonArray(json, target);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public static String toJson(Object object){
        return jsonMapper.toJson(object);
    }



    //@Override
    //public void setApplicationContext(ApplicationContext ctx) throws BeansException {
    //    Entities.ctx = ctx;
    //    Entities.jsr303 = ctx.getBean(Validator.class);
    //}

}
