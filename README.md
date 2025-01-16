# EntityMapper
EntityMapper is a Java library that simplifies object mapping.  Inspired by Bean.copyProperties, it offers enhanced functionality for transferring data between objects, going beyond basic property copying to provide a more powerful and flexible solution for complex mapping scenarios.

## Features

* **Simple and Easy to Use:** Provides a concise API for convenient object mapping.
* **Flexible Configuration:** Supports various mapping methods, including direct assignment, type conversion, and custom mapping.


# GUIDE Quick Start
### 1. Add Dependency
```xml
<dependency>
  <groupId>com.ksptool</groupId>
  <artifactId>entity-mapper</artifactId>
  <version>2.0A</version> 
</dependency>
```
### 2. Entities Class: Simplified Object Mapping
The `Entities` class in EntityMapper provides a static, convenient way to perform object mapping without directly instantiating the `Any` class. This guide focuses on the `as` and `assign` methods for quick and easy object transformations.

### `as` Method
The `as` method creates a new instance of the target type and maps the source object's properties to it.
```java
TestStaff source = new TestStaff();
source.setName("test");
TestStaffVo target = Entities.as(source, TestStaffVo.class); 
assertEquals("test", target.getName()); 
```
```java
List<TestStaff> staffList = ....
List<TestStaffVo> target = Entities.as(staffList, TestStaffVo.class);
```

### `assign` Method
The assign method is an alias for the second form of the as method. It maps properties from the source object to an existing target object.
```java
TestStaff source = new TestStaff();
source.setName("test");
TestStaffVo target = new TestStaffVo();

Entities.assign(source, target); // Same as Entities.as(source, target)
assertEquals("test", target.getName());
```

# Any Class: Flexible Object Mapping
The `Any` class is the core of EntityMapper, providing a fluent and versatile API for object mapping. This guide explores its key features and demonstrates how to leverage its flexibility for various mapping scenarios.

## Creating an `Any` Instance

You can create an `Any` instance in two ways:

1. **From scratch:** Use `Any.of()` to create an empty `Any` instance and build it step by step.
2. **From an existing object:** Use `Any.of(object)` to create an `Any` instance that wraps an existing object.

## Core Mapping Methods

* **`val(String propertyName, Object value)`:** Sets the value of a property in the target object.
    -  Use this to add properties, modify existing ones, or even set values for nested properties.

* **`as(Class<T> targetClass)`:**  Transforms the current `Any` instance into an object of the specified `targetClass`.

* **`asList(Class<T> targetClass)`:**  Transforms a collection wrapped in the current `Any` instance into a list of objects of the specified `targetClass`.

## Advanced Mapping Features

* **`matchUpdate(String propertyName)`:**  Starts a conditional mapping block where you can define rules to update a property based on its current value.

* **`eq(Object oldValue, Object newValue)`:**  Within a `matchUpdate` block, this defines a rule to replace `oldValue` with `newValue` for the specified property.

* **`fin()`:**  Ends a `matchUpdate` block.

* **`to(Class<T> targetClass)`:**  Initiates an asymmetric mapping process, allowing you to map properties with different names or perform custom logic before finalizing the mapping with `get()`.

* **`get()`:**  Finalizes an asymmetric mapping process and returns the mapped object.



# EntityMapper

EntityMapper 是一个简化对象映射的 Java 库。它受到 `Bean.copyProperties` 的启发，提供了增强的功能来传输对象之间的数据，超越了基本属性复制，为复杂映射场景提供更强大、更灵活的解决方案。

## 功能特性

* **简单易用：** 提供简洁的 API，方便进行对象映射。
* **灵活配置：** 支持多种映射方法，包括直接赋值、类型转换和自定义映射。

# 快速入门指南

### 1. 添加依赖

```xml
<dependency>
  <groupId>com.ksptool</groupId>
  <artifactId>entity-mapper</artifactId>
  <version>2.0A</version> 
</dependency>
```

### 2. Entities 类：简化的对象映射
`EntityMapper` 中的 `Entities` 类提供了一种静态的、便捷的方式来执行对象映射，而无需直接实例化 `Any` 类。本指南重点介绍 `as` 和 `assign` 方法，用于快速简便的对象转换。

### `as` 方法
`as` 方法用于创建目标类型的新实例，并将一个源对象的所有属性映射至新实例。
```java
TestStaff source = new TestStaff();
source.setName("test");
TestStaffVo target = Entities.as(source, TestStaffVo.class); 
assertEquals("test", target.getName()); 
```
映射List
```java
List<TestStaff> staffList = ....
List<TestStaffVo> target = Entities.as(staffList, TestStaffVo.class);
```

### `assign` 方法
`assign` 方法是 `as` 方法的一种别名。它可以将源对象的属性映射到一个现有的目标对象。
```java
TestStaff source = new TestStaff();
source.setName("test");
TestStaffVo target = new TestStaffVo();

Entities.assign(source, target); // Same as Entities.as(source, target)
assertEquals("test", target.getName());
```

# Any 类：灵活的对象映射
`Any` 类是 `EntityMapper` 的核心，它提供了一个流畅且通用的对象映射 API。本指南探讨了它的主要功能，并演示了如何利用它的灵活性来处理各种映射场景。

## 创建 `Any` 实例

妳可以通过两种方式创建`Any`实例:

1. **从头开始:** 使用 `Any.of()` 创建一个空的 `Any` 实例, 并逐步构建它.
```java
TestStaff staff = Any.of()
        .val("id", 123456L)
        .val("name", "Steve")
        .as(TestStaff.class);
```

2. **从现有对象:** 使用 `Any.of(object)` 创建一个包装现有对象的 `Any` 实例
```java
TestStaff staff = new TestStaff();
staff.setStaffId(123456L);
staff.setName("Steve");

TestStaffVo staffVo = Any.of(staff)
        .to(TestStaffVo.class)
        .val("id", staff.getStaffId())
        .get();

System.out.println(staffVo.getId());
```

## 核心映射方法

* **`val(String propertyName, Object value)`:** 设置目标对象中属性的值。
  -  使用此方法添加属性、修改现有属性，甚至可以设置嵌套属性的值。

* **`as(Class<T> targetClass)`:**  将当前的`Any` 实例转换为指定 `targetClass`的对象.

* **`asList(Class<T> targetClass)`:**   将当前 `Any` 实例中包装的集合转换为指定 `targetClass`的对象列表

## 高级映射功能

* **`matchUpdate(String propertyName)`:**  启动一个条件映射块，您可以在其中定义基于当前值更新属性的规则。

* **`eq(Object oldValue, Object newValue)`:**  在 `matchUpdate` 块内, 这定义了一个将指定属性的 `oldValue` 替换为 `newValue` 的规则.

* **`fin()`:**  结束 `matchUpdate` 块.

* **`to(Class<T> targetClass)`:**  启动非对称映射过程，允许您映射具有不同名称的属性，或使用 `get()` 完成映射之前执行自定义逻辑。

* **`get()`:**  完成非对称映射过程并返回映射后的对象。


高级映射功能
```java
List<TestStaff> list = new ArrayList<>();

for(int i = 0; i < 32; i++){
    list.add(Any.of()
            .val("name", "Steve"+i)
            .val("gender",18)
            .as(TestStaff.class)
    );
}
//以上代码用于创建一个包含Staff对象的集合

//使用Any将集合中name字段为Steve3的对象修改为JackHorse
List<TestStaffVo> vos = Any.of(list)
        .matchUpdate("name")
        .eq("Steve3", "JackHorse")
        .asList(TestStaffVo.class);

assertEquals("JackHorse", vos.get(3).getName());
```

