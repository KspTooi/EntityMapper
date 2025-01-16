package com.ksptool.entities.components;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Default implementation of entity mapper.
 * Uses ModelMapper to copy object properties and defines some custom type converters.
 * <p>
 * 默认的实体映射器实现。
 * 使用ModelMapper进行对象属性的拷贝，并自定义了一些类型转换器。
 */
public class DefaultEntityMapper implements EntityMapper {

    private static final ModelMapper mMapper = new ModelMapper();

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor, initializes the configuration of ModelMapper.
     * <p>
     * 构造函数，初始化ModelMapper的配置。
     */
    public DefaultEntityMapper() {
        init();
    }

    /**
     * Initializes ModelMapper, adds custom type converters and sets matching strategy.
     * <p>
     * 初始化ModelMapper，添加自定义的类型转换器并设置匹配策略。
     */
    public void init(){

        Converter<Integer, String> toStringConverter = new Converter<Integer, String>() {
            public String convert(MappingContext<Integer, String> context) {
                return context.getSource() != null ? context.getSource().toString() : null;
            }
        };

        Converter<Long, String> longToStringConverter = new Converter<Long, String>() {
            public String convert(MappingContext<Long, String> context) {
                return context.getSource() != null ? context.getSource().toString() : null;
            }
        };

        Converter<String, Date> strToDateConverter = new Converter<String, Date>() {
            public Date convert(MappingContext<String, Date> context) {
                try {
                    return context.getSource() != null ? sdf.parse(context.getSource()) : null;
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Converter<Date, String> dateToStrConverter = new Converter<Date, String>() {
            public String convert(MappingContext<Date, String> context) {
                return context.getSource() != null ? sdf.format(context.getSource()) : null;
            }
        };

        mMapper.addConverter(toStringConverter);
        mMapper.addConverter(longToStringConverter);
        mMapper.addConverter(strToDateConverter);
        mMapper.addConverter(dateToStrConverter);
        mMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Assigns the value of the source object to the target object.
     * <p>
     * 将源对象的值赋给目标对象。
     *
     * @param source Source object 源对象
     * @param target Target object 目标对象
     */
    @Override
    public void assign(Object source, Object target) {
        mMapper.map(source, target);
    }

    /**
     * Assigns the value of the source object to the target object, using the specified mapping relationship.
     * <p>
     * 将源对象的值赋给目标对象，并使用指定的映射关系。
     *
     * @param source Source object 源对象
     * @param target Target object 目标对象
     * @param map    Property mapping relationship 属性映射关系
     */
    @Override
    public void assign(Object source, Object target, Map<String, String> map) {

    }

}
