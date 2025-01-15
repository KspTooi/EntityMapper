package com.ksptool.entities.utils;

public class Any<T>{

    private T target;

    public Any(T t){
        this.target = t;
    }

    public static <T> Any<T> of(T t){
        return new Any<T>(t);
    }

}
