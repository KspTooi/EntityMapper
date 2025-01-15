package com.ksptool.entities.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class IntegerToStringConverter implements Converter<Integer, String> {

    @Override
    public String convert(MappingContext<Integer, String> context) {
        return context.getSource() != null ? context.getSource().toString() : null;
    }

}
