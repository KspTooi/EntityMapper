package com.ksptool.entities;

import com.ksptool.entities.components.DefaultEntityMapper;
import com.ksptool.entities.components.DefaultJsonEntityMapper;
import com.ksptool.entities.components.EntityMapper;
import com.ksptool.entities.components.JsonEntityMapper;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides utility methods for working with entities, including object mapping, JSON serialization and deserialization.
 * This class acts as a facade for accessing the underlying {@link EntityOperation} instance.
 * <p>
 * 提供用于处理实体的实用方法，包括对象映射、JSON 序列化和反序列化。此类充当访问底层 {@link EntityOperation} 实例的门面。
 */
public class Entities {

    private static EntityOperation eo;

    /**
     * Returns the {@link EntityMapper} used for object mapping.
     * <p>
     * 返回用于对象映射的 {@link EntityMapper}。
     * @return The {@link EntityMapper} instance. {@link EntityMapper} 实例。
     */
    public static EntityMapper getObjectMapper() {
        return getGlobalInstance().getEntityMapper();
    }

    /**
     * Sets the {@link EntityMapper} to be used for object mapping.
     * <p>
     * 设置用于对象映射的 {@link EntityMapper}。
     * @param m The {@link EntityMapper} instance to set. 要设置的 {@link EntityMapper} 实例。
     */
    public static void setObjectMapper(EntityMapper m) {
        getGlobalInstance().setEntityMapper(m);
    }

    /**
     * Sets the {@link JsonEntityMapper} to be used for JSON serialization and deserialization.
     * <p>
     * 设置用于 JSON 序列化和反序列化的 {@link JsonEntityMapper}。
     * @param m The {@link JsonEntityMapper} instance to set. 要设置的 {@link JsonEntityMapper} 实例。
     */
    public static void setJsonEntityMapper(JsonEntityMapper m) {
        getGlobalInstance().setJsonEntityMapper(m);
    }

    /**
     * Returns the {@link JsonEntityMapper} used for JSON serialization and deserialization.
     * <p>
     * 返回用于 JSON 序列化和反序列化的 {@link JsonEntityMapper}。
     * @return The {@link JsonEntityMapper} instance. {@link JsonEntityMapper} 实例。
     */
    public static JsonEntityMapper getJsonEntityMapper() {
        return getGlobalInstance().getJsonEntityMapper();
    }

    /**
     * Returns the global {@link EntityOperation} instance.
     * <p>
     * 返回全局 {@link EntityOperation} 实例。
     * @return The global {@link EntityOperation} instance. 全局 {@link EntityOperation} 实例。
     */
    public static synchronized EntityOperation getGlobalInstance(){
        if(eo == null){
            eo = new EntityOperation(new DefaultEntityMapper(),new DefaultJsonEntityMapper());
        }
        return eo;
    }

    /**
     * Converts a list of objects to a list of objects of the specified target class.
     * <p>
     * 将对象列表转换为指定目标类的对象列表。
     * @param source The source list of objects. 源对象列表。
     * @param target The target class. 目标类。
     * @param <T>   The type of the target class. 目标类的类型。
     * @return The list of converted objects. 转换后的对象列表。
     */
    public static <T> List<T> as(List<?> source,Class<T> target){

        if(source == null){
            return new ArrayList<>();
        }

        try{
            List<T> ret = new ArrayList<T>();
            for(Object po : source){
                T vo = target.getDeclaredConstructor().newInstance();
                getGlobalInstance().assign(po, vo);
                ret.add(vo);
            }
            return ret;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /**
     * Converts an object to an object of the specified target class.
     * <p>
     * 将对象转换为指定目标类的对象。
     * @param source The source object. 源对象。
     * @param target The target class. 目标类。
     * @param <T>   The type of the target class. 目标类的类型。
     * @return The converted object. 转换后的对象。
     */
    public static <T> T as(Object source,Class<T> target){

        try{

            T instance = target.getDeclaredConstructor().newInstance();

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

    /**
     * Assigns the values of the source object to the target object.
     * <p>
     * 将源对象的值赋给目标对象。
     * @param source The source object. 源对象。
     * @param target The target object. 目标对象。
     */
    public static void as(Object source, Object target){
        getGlobalInstance().assign(source, target);
    }

    /**
     * Assigns the values of the source object to the target object.
     * <p>
     * 将源对象的值赋给目标对象。
     * @param source The source object. 源对象。
     * @param target The target object. 目标对象。
     */
    public static void assign(Object source, Object target){
        getGlobalInstance().assign(source, target);
    }

    /**
     * Deserializes a JSON string to an object of the specified target class.
     * <p>
     * 将 JSON 字符串反序列化为指定目标类的对象。
     * @param json   The JSON string to deserialize. 要反序列化的 JSON 字符串。
     * @param target The target class. 目标类。
     * @param <T>    The type of the target class. 目标类的类型。
     * @return The deserialized object. 反序列化后的对象。
     */
    public static <T> T fromJson(String json,Class<T> target){
        try{
            return getGlobalInstance().fromJson(json,target);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Deserializes a JSON array string to a list of objects of the specified target class.
     * <p>
     * 将 JSON 数组字符串反序列化为指定目标类的对象列表。
     * @param json   The JSON array string to deserialize. 要反序列化的 JSON 数组字符串。
     * @param target The target class. 目标类。
     * @param <T>    The type of the target class. 目标类的类型。
     * @return The list of deserialized objects. 反序列化后的对象列表。
     */
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

    /**
     * Serializes an object to a JSON string.
     * <p>
     * 将对象序列化为 JSON 字符串。
     * @param object The object to serialize. 要序列化的对象。
     * @return The JSON string representation of the object. 对象的 JSON 字符串表示形式。
     */
    public static String toJson(Object object){
        return getGlobalInstance().toJson(object);
    }





}
