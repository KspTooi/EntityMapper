package com.ksptool.entities;

import com.ksptool.entities.model.TestStaff;
import com.ksptool.entities.model.TestStaffVo;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnyTest {


    @Test
    public void directly(){

        TestStaffVo vo = Any.of()
                .val("name", "Steve")
                .val("age", 18)
                .as(TestStaffVo.class);

        assertEquals("Steve", vo.getName());
        assertEquals(18, vo.getAge());
    }

    @Test
    public void objectAs(){

        TestStaff s = new TestStaff();
        s.setName("Steve");
        s.setAge(100);
        s.setGender(135346L);

        var vo = Any.of(s).as(TestStaffVo.class);

        assertEquals("Steve", vo.getName());
        assertEquals(100, vo.getAge());
        assertEquals(135346L, vo.getGender());
    }

    @Test
    public void objectModify(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        TestStaff s = new TestStaff();
        s.setName("Steve");
        s.setAge(100);
        s.setGender(135346L);

        TestStaffVo vo = Any.of(s)
                .val("name", "JackChen")
                .val("gender",12345L)
                .val("birthday","2014-11-11 11:11:11")
                .as(TestStaffVo.class);

        assertEquals("JackChen", vo.getName());
        assertEquals(100, vo.getAge());
        assertEquals(12345L, vo.getGender());
        assertEquals("2014-11-11 11:11:11", sdf.format(vo.getBirthday()));
    }

    @Test
    public void collectionModify(){

        List<TestStaff> list = new ArrayList<>();

        for(int i = 0; i < 32; i++){
            list.add(Any.of()
                    .val("name", "Steve")
                    .val("gender",18)
                    .as(TestStaff.class)
            );
        }

        List<TestStaffVo> ret = Any.of(list).val("name", "JackHorse").asList(TestStaffVo.class);
        assertEquals(32, ret.size());
        assertEquals("JackHorse", ret.get(0).getName());
    }

    @Test
    public void matchAndModify(){

        TestStaff s = new TestStaff();
        s.setName("Steve");
        s.setAge(100);
        s.setGender(135346L);

        TestStaffVo vo = Any.of(s)
                .matchUpdate("name")
                .eq("Steve", "Dean")
                .eq("Elon", "Jack")
                .fin()
                .as(TestStaffVo.class);

        assertEquals("Dean", vo.getName());

        var matchValue = "test";

        TestStaffVo vo1 = Any.of(s)
                .matchUpdate("name", matchValue)
                .eq("test", "testOK")
                .as(TestStaffVo.class);

        assertEquals("testOK", vo1.getName());
    }

    @Test
    public void collectionMatchAndModify(){

        List<TestStaff> list = new ArrayList<>();

        for(int i = 0; i < 32; i++){
            list.add(Any.of()
                    .val("name", "Steve"+i)
                    .val("gender",18)
                    .as(TestStaff.class)
            );
        }

        List<TestStaffVo> vos = Any.of(list)
                .matchUpdate("name")
                .eq("Steve3", "JackHorse")
                .asList(TestStaffVo.class);

        assertEquals("JackHorse", vos.get(3).getName());
    }
    



}
