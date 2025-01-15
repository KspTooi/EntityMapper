package com.ksptool.entities;

import com.ksptool.entities.model.TestStaff;
import com.ksptool.entities.model.TestStaffVo;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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


}
