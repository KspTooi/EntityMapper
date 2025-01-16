package com.ksptool.entities;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A generic container class that holds an object of type T and provides utility methods for manipulating and transforming the object.
 * <p>
 * 泛型容器类，用于存放 T 类型的对象，并提供用于操作和转换该对象的实用方法。
 * @param <T> The type of the object held by the container. 容器所持有的对象的类型。
 */
public class Any<T>{

    private final T content;
    private final Map<String,Object> putMap = new ConcurrentHashMap<>();
    private final Map<String,Object> getMap = new ConcurrentHashMap<>();
    private final EntityOperation eo;


    /**
     * Constructs an Any object with the specified content and uses the global EntityOperation instance.
     * <p>
     * 使用指定的内容和全局 EntityOperation 实例构造 Any 对象。
     *
     * @param t The object to be held by the container. 要由容器持有的对象。
     */
    public Any(T t){
        this.content = t;
        this.eo = Entities.getGlobalInstance();
    }

    /**
     * Constructs an Any object with the specified content and EntityOperation instance.
     * <p>
     * 使用指定的内容和 EntityOperation 实例构造 Any 对象。
     *
     * @param t  The object to be held by the container. 要由容器持有的对象。
     * @param eo The EntityOperation instance to be used for object manipulation. 用于对象操作的 EntityOperation 实例。
     */
    public Any(T t,EntityOperation eo){
        this.content = t;
        this.eo = eo;
    }

    /**
     * Creates an Any object with the specified non-null content and uses the global EntityOperation instance.
     * <p>
     * 使用指定的非空内容创建 Any 对象，并使用全局 EntityOperation 实例。
     *
     * @param t The non-null object to be held by the container. 要由容器持有的非空对象。
     * @param <T> The type of the object. 对象的类型。
     * @return The created Any object. 创建的 Any 对象。
     * @throws NullPointerException if the provided object is null. 如果提供的对象为空，则抛出 NullPointerException。
     */
    public static <T> Any<T> of(T t){
        if(t == null){
            throw new NullPointerException();
        }
        return new Any<T>(t);
    }

    /**
     * Creates an Any object with the specified content, which can be null, and uses the global EntityOperation instance.
     * <p>
     * 使用指定的内容（可以为空）创建 Any 对象，并使用全局 EntityOperation 实例。
     *
     * @param t The object to be held by the container. 要由容器持有的对象。
     * @param <T> The type of the object. 对象的类型。
     * @return The created Any object. 创建的 Any 对象。
     */
    public static <T> Any<T> ofNullable(T t){
        return new Any<>(t);
    }

    /**
     * Creates an Any object with the specified non-null content and EntityOperation instance.
     * <p>
     * 使用指定的非空内容和 EntityOperation 实例创建 Any 对象。
     *
     * @param t  The non-null object to be held by the container. 要由容器持有的非空对象。
     * @param eo The EntityOperation instance to be used for object manipulation. 用于对象操作的 EntityOperation 实例。
     * @param <T> The type of the object. 对象的类型。
     * @return The created Any object. 创建的 Any 对象。
     * @throws NullPointerException if the provided object is null. 如果提供的对象为空，则抛出 NullPointerException。
     */
    public static <T> Any<T> of(T t,EntityOperation eo){
        if(t == null){
            throw new NullPointerException();
        }
        if(eo == null){
            return new Any<T>(t);
        }
        return new Any<T>(t,eo);
    }

    /**
     * Creates an Any object holding an empty HashMap and uses the global EntityOperation instance.
     * <p>
     * 创建一个持有空 HashMap 的 Any 对象，并使用全局 EntityOperation 实例。
     * @return The created Any object. 创建的 Any 对象。
     */
    public static Any<Map<String,Object>> of(){
        return of(null);
    }

    /**
     * Creates an Any object holding an empty HashMap and the specified EntityOperation instance.
     * <p>
     * 创建一个持有空 HashMap 和指定 EntityOperation 实例的 Any 对象。
     * @param eo The EntityOperation instance to be used for object manipulation. 用于对象操作的 EntityOperation 实例。
     * @return The created Any object. 创建的 Any 对象。
     */
    public static Any<Map<String,Object>> of(EntityOperation eo){

        if(eo == null){
            return new Any<>(new HashMap<>());
        }
        return new Any<>(new HashMap<>(),eo);
    }

    /**
     * Retrieves the object held by the container.
     * <p>
     * 检索容器持有的对象。
     * @return The object held by the container. 容器持有的对象。
     */
    public T get(){
        return content;
    }

    /**
     * Assigns the specified value to the specified key in the internal map and updates the content object if it is a List or Map.
     * If the content object is neither a List nor a Map, the internal map is assigned to the content object using the EntityOperation instance.
     * <p>
     * 将指定的值赋给内部映射中指定的键，并在内容对象为 List 或 Map 时更新内容对象。
     * 如果内容对象既不是 List 也不是 Map，则使用 EntityOperation 实例将内部映射赋给内容对象。
     * @param k The key to which the value is assigned.  分配值的键。
     * @param v The value to be assigned. 要分配的值。
     * @return The current Any object. 当前的 Any 对象。
     */
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

    /**
     * Converts the content object to the specified target class.
     * <p>
     * 将内容对象转换为指定的目標类。
     * @param target The target class to which the content object should be converted. 目标类，内容对象应转换为该类。
     * @param <R>   The type of the target class. 目标类的类型。
     * @return The converted object. 转换后的对象。
     */
    public <R> R as(Class<R> target){
        return eo.as(this.content,target);
    }

    /**
     * Converts the content object, which should be a List, to a List of the specified target class.
     * <p>
     * 将内容对象（应为 List）转换为指定目标类的 List。
     * @param target The target class to which the elements of the list should be converted. 目标类，列表的元素应转换为该类。
     * @param <R>   The type of the target class. 目标类的类型。
     * @return The converted List. 转换后的 List。
     */
    public <R> List<R> asList(Class<R> target){
        if(!(content instanceof List)){
            return Collections.emptyList();
        }
        return eo.as((List<?>)this.content,target);
    }

    /**
     * Converts the content object to the specified target class and wraps it in a new Any object.
     * <p>
     * 将内容对象转换为指定的目標类，并将其包装在一个新的 Any 对象中。
     * @param target The target class to which the content object should be converted. 目标类，内容对象应转换为该类。
     * @param <TARGET> The type of the target class. 目标类的类型。
     * @return The new Any object holding the converted object. 持有转换后对象的新 Any 对象。
     */
    public <TARGET> Any<TARGET> to(Class<TARGET> target){
        return Any.of(this.as(target));
    }

    /**
     * Converts the content object, which should be a List, to a List of the specified target class and wraps it in a new Any object.
     * <p>
     * 将内容对象（应为 List）转换为指定目标类的 List，并将其包装在一个新的 Any 对象中。
     * @param target The target class to which the elements of the list should be converted. 目标类，列表的元素应转换为该类。
     * @param <TARGET> The type of the target class. 目标类的类型。
     * @return The new Any object holding the converted List. 持有转换后 List 的新 Any 对象。
     */
    public <TARGET> Any<List<TARGET>> toList(Class<TARGET> target){
        return Any.of(this.asList(target));
    }

    /**
     * Creates an AnyMatchUpdate object for performing conditional updates on the content object.
     * <p>
     * 创建一个 AnyMatchUpdate 对象，用于对内容对象执行条件更新。
     * @param updateField The field to be updated. 要更新的字段。
     * @return The created AnyMatchUpdate object. 创建的 AnyMatchUpdate 对象。
     */
    public AnyMatchUpdate matchUpdate(String updateField){
        return new AnyMatchUpdate(this,updateField);
    }

    /**
     * Creates an AnyMatchUpdate object for performing conditional updates on the content object with a specified match value.
     * <p>
     * 创建一个 AnyMatchUpdate 对象，用于使用指定的匹配值对内容对象执行条件更新。
     * @param updateField The field to be updated. 要更新的字段。
     * @param matchValue  The value to match against. 要匹配的值。
     * @return The created AnyMatchUpdate object. 创建的 AnyMatchUpdate 对象。
     */
    public AnyMatchUpdate matchUpdate(String updateField,String matchValue){
        return new AnyMatchUpdate(this,updateField,matchValue);
    }


    /**
     * Returns the EntityOperation instance used by this Any object.
     * <p>
     * 返回此 Any 对象使用的 EntityOperation 实例。
     * @return The EntityOperation instance. EntityOperation 实例。
     */
    public EntityOperation getEntityOperation(){
        return this.eo;
    }

    /**
     * Checks if the content object is null.
     * <p>
     * 检查内容对象是否为空。
     * @return True if the content object is null, false otherwise. 如果内容对象为空，则返回 true，否则返回 false。
     */
    public boolean isNull(){
        return this.content==null;
    }

    public Any<T> assign(Object object){

        if(object == null){
            return this;
        }

        eo.assign(object,content);
        return this;
    }

    public String get(String field){
        putMap.clear();
        eo.assign(content,putMap);
        Object value = putMap.get(field);
        if(value == null){
            return null;
        }
        return value.toString();
    }

    public <RETURN> RETURN get(String field,Class<RETURN> tClass){
        putMap.clear();
        eo.assign(content,putMap);
        Object value = putMap.get(field);
        if(value == null){
            return null;
        }
        if (tClass.isInstance(value)) {
            return (RETURN)value;
        } else {
            return null;
        }
    }


}
