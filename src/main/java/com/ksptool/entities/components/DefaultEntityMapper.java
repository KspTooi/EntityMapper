package com.ksptool.entities.components;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class DefaultEntityMapper implements EntityMapper {

    private static final ModelMapper mMapper = new ModelMapper();

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DefaultEntityMapper() {
        init();
    }

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

    @Override
    public void assign(Object source, Object target) {
        mMapper.map(source, target);
    }

    @Override
    public void assign(Object source, Object target, Map<String, String> map) {

    }

}
