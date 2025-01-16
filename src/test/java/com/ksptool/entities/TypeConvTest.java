package com.ksptool.entities;

import com.ksptool.entities.model.TestBigDecimal;
import com.ksptool.entities.model.TestBigDecimalVo;
import com.ksptool.entities.model.TestDate;
import com.ksptool.entities.model.TestDateVo;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Date;
import static com.ksptool.entities.Entities.as;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TypeConvTest {

    @Test
    public void bigDecimal(){
        TestBigDecimal source = new TestBigDecimal();
        source.setBigDecimal(new BigDecimal("1.265445369000"));
        TestBigDecimalVo vo = as(source, TestBigDecimalVo.class);
        assertEquals("1.265445369000",vo.getBigDecimal());
        vo.setBigDecimal("1.9000");
        as(vo,source);
        assertEquals("1.9000",vo.getBigDecimal());
    }

    @Test
    public void date(){

        TestDate source = new TestDate();
        source.setDate(new Date());
        TestDateVo vo = as(source, TestDateVo.class);

        assertNotNull(vo.getDate());
        System.out.println(vo.getDate());
    }



}
