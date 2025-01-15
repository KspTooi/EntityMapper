package com.ksptool.entities;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnyMatchUpdate {

    private final Any<?> any;

    private final String updateField;

    private String matchValue;

    public AnyMatchUpdate(Any<?> any,String updateField){
        this.any = any;
        this.updateField = updateField;
    }

    public AnyMatchUpdate(Any<?> any,String updateField,String matchValue){
        this.any = any;
        this.updateField = updateField;
        this.matchValue = matchValue;
    }


    public AnyMatchUpdate eq(String valOrField,Object append){

        //使用外部比较值
        if(matchValue != null){

            if(any.get() instanceof List){
                for (Object item : (List<?>) any.get()){
                    if(valOrField.equals(matchValue)){
                        Map<String,Object> assignMap = new HashMap<>();
                        assignMap.put(updateField,append);
                        Entities.assign(assignMap,item);
                    }
                }
            }

            if(valOrField.equals(matchValue)){
                any.val(updateField,append);
            }

        }

        //没有外部比较值 使用any自身的属性进行比较
        if(matchValue == null){

            if(any.get() instanceof List){

                for (Object item : (List<?>) any.get()){

                    String fieldVal = getFieldAsString(item,updateField);

                    if(fieldVal == null){
                        return this;
                    }

                    if(fieldVal.equals(valOrField)){
                        any.val(updateField,append);
                    }
                }

            }

            if(any.get() instanceof Map){
                Object mapVal = ((Map<?, ?>) any.get()).get(updateField);
                if(mapVal == null){
                    return this;
                }
                if(mapVal.toString().equals(valOrField)){
                    any.val(updateField,append);
                }
            }

            String fieldVal = getFieldAsString(updateField);

            if(fieldVal == null){
                return this;
            }

            if(fieldVal.equals(valOrField)){
                any.val(updateField,append);
            }
            return this;
        }

        return this;
    }



    public Any<?> fin(){
        return this.any;
    }


    private String getFieldAsString(String fieldName){
        return getFieldAsString(this.any.get(),fieldName);
    }

    private String getFieldAsString(Object item,String fieldName){
        try{
            Field field = item.getClass().getDeclaredField(updateField);
            field.setAccessible(true);
            return field.get(item).toString();
        }catch (Exception e){
            return null;
        }
    }

    public <R> R as(Class<R> target){
        return any.as(target);
    }

    public <R> List<R> asList(Class<R> target){
        return any.asList(target);
    }


}
