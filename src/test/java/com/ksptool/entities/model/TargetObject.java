package com.ksptool.entities.model;

public class TargetObject {
    private String name;
    private int age;

    public TargetObject() {
        // Empty constructor for TargetObject
    }

    public TargetObject(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
} 