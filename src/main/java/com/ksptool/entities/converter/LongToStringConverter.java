package com.ksptool.entities.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class LongToStringConverter implements Converter<Long, String> {

    @Override
    public String convert(MappingContext<Long, String> context) {
        return context.getSource() != null ? context.getSource().toString() : null;
    }

}
