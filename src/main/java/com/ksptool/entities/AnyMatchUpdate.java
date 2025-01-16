package com.ksptool.entities;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides a fluent API for performing conditional updates on the content of an Any object.
 * This class allows you to specify a field to update and a value or field to match against.
 * If the match condition is met, the specified update is applied to the content object.
 * <p>
 * 提供用于对 Any 对象的内容执行条件更新的 fluent API。此类允许您指定要更新的字段以及要匹配的值或字段。
 * 如果满足匹配条件，则将指定的更新应用于内容对象。
 */
public class AnyMatchUpdate {

    private final Any<?> any;

    private final String updateField;

    private String matchValue;

    /**
     * Constructs an AnyMatchUpdate object with the specified Any object and update field.
     * <p>
     * 使用指定的 Any 对象和更新字段构造 AnyMatchUpdate 对象。
     * @param any         The Any object whose content should be conditionally updated. 其内容应有条件更新的 Any 对象。
     * @param updateField The field to be updated. 要更新的字段。
     */
    public AnyMatchUpdate(Any<?> any,String updateField){
        this.any = any;
        this.updateField = updateField;
    }

    /**
     * Constructs an AnyMatchUpdate object with the specified Any object, update field, and match value.
     * <p>
     * 使用指定的 Any 对象、更新字段和匹配值构造 AnyMatchUpdate 对象。
     * @param any         The Any object whose content should be conditionally updated. 其内容应有条件更新的 Any 对象。
     * @param updateField The field to be updated. 要更新的字段。
     * @param matchValue  The value to match against. 要匹配的值。
     */
    public AnyMatchUpdate(Any<?> any,String updateField,String matchValue){
        this.any = any;
        this.updateField = updateField;
        this.matchValue = matchValue;
    }


    /**
     * Checks if the specified value or field equals the match value or the value of the update field in the content object.
     * If the condition is met, the specified value is assigned to the update field in the content object.
     * This method supports both List and Map content types.
     * <p>
     * 检查指定的值或字段是否等于匹配值或内容对象中更新字段的值。如果满足条件，则将指定的值赋给内容对象中的更新字段。
     * 此方法支持 List 和 Map 内容类型。
     * @param valOrField The value or field to compare. 要比较的值或字段。
     * @param append     The value to be assigned to the update field if the condition is met. 如果满足条件，要赋给更新字段的值。
     * @return The current AnyMatchUpdate object for method chaining. 当前的 AnyMatchUpdate 对象，用于方法链式调用。
     */
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

    /**
     * Returns the Any object associated with this AnyMatchUpdate object.
     * <p>
     * 返回与此 AnyMatchUpdate 对象关联的 Any 对象。
     * @return The Any object. Any 对象。
     */
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

    /**
     * Converts the content object of the associated Any object to the specified target class.
     * <p>
     * 将关联的 Any 对象的内容对象转换为指定的目標类。
     * @param target The target class to which the content object should be converted. 目标类，内容对象应转换为该类。
     * @param <R>   The type of the target class. 目标类的类型。
     * @return The converted object. 转换后的对象。
     */
    public <R> R as(Class<R> target){
        return any.as(target);
    }

    /**
     * Converts the content object of the associated Any object, which should be a List, to a List of the specified target class.
     * <p>
     * 将关联的 Any 对象的内容对象（应为 List）转换为指定目标类的 List。
     * @param target The target class to which the elements of the list should be converted. 目标类，列表的元素应转换为该类。
     * @param <R>   The type of the target class. 目标类的类型。
     * @return The converted List. 转换后的 List。
     */
    public <R> List<R> asList(Class<R> target){
        return any.asList(target);
    }


}
