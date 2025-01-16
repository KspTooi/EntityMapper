package com.ksptool.entities;


import com.ksptool.entities.components.DefaultEntityMapper;
import com.ksptool.entities.components.DefaultJsonEntityMapper;
import com.ksptool.entities.components.EntityMapper;
import com.ksptool.entities.components.JsonEntityMapper;
import com.ksptool.entities.model.TestJsonTarget;
import com.ksptool.entities.model.TestStaff;
import com.ksptool.entities.model.TestStaffVo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionTest {

    @Test
    void tGetObjectMapper() {
        assertNotNull(Entities.getObjectMapper());
    }

    @Test
    void tSetObjectMapper() {
        EntityMapper originalMapper = Entities.getObjectMapper();
        DefaultEntityMapper newMapper = new DefaultEntityMapper();
        Entities.setObjectMapper(newMapper);
        assertEquals(newMapper, Entities.getObjectMapper());
        assertNotEquals(originalMapper, Entities.getObjectMapper());
    }

    @Test
    void testSetJsonEntityMapper() {
        JsonEntityMapper originalMapper = Entities.getJsonEntityMapper();
        DefaultJsonEntityMapper newMapper = new DefaultJsonEntityMapper();
        Entities.setJsonEntityMapper(newMapper);
        assertEquals(newMapper, Entities.getJsonEntityMapper());
        assertNotEquals(originalMapper, Entities.getJsonEntityMapper());
    }

    @Test
    void testGetJsonEntityMapper() {
        assertNotNull(Entities.getJsonEntityMapper());
    }

    @Test
    void testGetGlobalInstance() {
        assertNotNull(Entities.getGlobalInstance());
    }

    @Test
    void testAs_list() {

        List<TestStaff> po = new ArrayList<>();
        po.add(new TestStaff());
        po.get(0).setName("Steve");
        po.get(0).setGender(36L);
        po.get(0).setAge(18);

        List<TestStaffVo> targetList = Entities.as(po, TestStaffVo.class);
        assertEquals(1, targetList.size());
        assertEquals("Steve", targetList.get(0).getName());
        assertEquals(36L, targetList.get(0).getGender());
        assertEquals(18, targetList.get(0).getAge());
    }

    @Test
    void testAs_object() {

        TestStaff source = new TestStaff();
        source.setName("test");

        TestStaffVo target = Entities.as(source, TestStaffVo.class);
        assertEquals("test", target.getName());
    }

    @Test
    void testAs_assign() {

        TestStaff source = new TestStaff();
        source.setName("test");
        TestStaffVo target = new TestStaffVo();

        Entities.as(source, target);
        assertEquals("test", target.getName());
    }

    @Test
    void testAssign() {

        TestStaff source = new TestStaff();
        source.setName("test");
        TestStaffVo target = new TestStaffVo();

        Entities.assign(source, target);
        assertEquals("test", target.getName());
    }

    @Test
    void testFromJson() {

        String json = "{\"name\":\"test\"}";
        TestStaff target = Entities.fromJson(json, TestStaff.class);
        assertEquals("test", target.getName());
    }

    @Test
    void testFromJson_null() {
        class Target {
            public String name;
        }

        String json = null;
        Target target = Entities.fromJson(json, Target.class);
        assertNull(target);
    }

    @Test
    void testFromJsonArray() {

        String json = "[{\"name\":\"test1\"},{\"name\":\"test2\"}]";
        List<TestJsonTarget> targetList = Entities.fromJsonArray(json, TestJsonTarget.class);
        assertEquals(2, targetList.size());
        assertEquals("test1", targetList.get(0).getName());
        assertEquals("test2", targetList.get(1).getName());
    }

    @Test
    void testFromJsonArray_empty() {
        class Target {
            public String name;
        }

        String json = "";
        List<Target> targetList = Entities.fromJsonArray(json, Target.class);
        assertEquals(0, targetList.size());
    }

    @Test
    void testToJson() {

        TestStaff target = new TestStaff();
        target.setName("test");
        String json = Entities.toJson(target);
        assertEquals("{\"name\":\"test\"}", json);
    }

}
