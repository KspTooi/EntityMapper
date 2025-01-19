package com.ksptool.entities;

import com.ksptool.entities.model.TestStaff;
import com.ksptool.entities.model.TestStaffVo;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class AnyFunctionalTest {

    @Test
    public void funcValSet(){

        TestStaff o = new TestStaff();
        o.setStaffId(1000000L);
        o.setName("Steve");


        TestStaffVo vo = Any.of(o)
                .to(TestStaffVo.class)
                .val(TestStaffVo::setAge, 17)
                .get();

        List<TestStaffVo> list = Any.of().asList(TestStaffVo.class);

        List<Integer> collect = list.stream().map(TestStaffVo::getAge).collect(Collectors.toList());

        System.out.println(vo.getName());
    }


}
