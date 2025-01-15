package com.ksptool.entities.components;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;

public class DefaultEntityMapper implements EntityMapper {


    private static final ModelMapper mMapper = new ModelMapper();

    public DefaultEntityMapper() {
        init();
    }

    public void init(){
        var toStringConverter = new Converter<Integer, String>() {
            public String convert(MappingContext<Integer, String> context) {
                return context.getSource() != null ? context.getSource().toString() : null;
            }
        };

        var longToStringConverter = new Converter<Long, String>() {
            public String convert(MappingContext<Long, String> context) {
                return context.getSource() != null ? context.getSource().toString() : null;
            }
        };

        mMapper.addConverter(toStringConverter);
        mMapper.addConverter(longToStringConverter);
        mMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public void assign(Object source, Object target) {
        mMapper.map(source, target);
    }

}
