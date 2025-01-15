package com.ksptool.entities.components;

import java.util.Map;

public interface EntityMapper {
    public void assign(Object source, Object target);

    public void assign(Object source, Object target, Map<String,String> map);
}
