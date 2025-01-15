package com.ksptool.entities;

public class Strings {

    protected static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    protected static boolean isNotBlank(String s){
        return !isBlank(s);
    }

}
