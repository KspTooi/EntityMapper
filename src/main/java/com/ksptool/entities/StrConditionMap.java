package com.ksptool.entities;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 条件选择类
 */
public class StrConditionMap<MV> {

    //用于比较的MAP
    private final Map<String, MV> target;

    //MAP中的目标字段
    private final String targetFiled;

    //比较的目标值
    private String targetVal = null;

    private StrConditionMap(Map<String, MV> t, String tf){
        this.target = t;
        this.targetFiled = tf;
    }

    public static <MV> StrConditionMap<MV> ofMap(Map<String, MV> map, String tf) {
        return new StrConditionMap<>(map, tf);
    }

    public StrConditionMap<MV> defaultVal(MV o){
        target.put(targetFiled,o);
        return this;
    }

    /**
     * 设置当前对象的比较值
     * @param target 比较目标值
     * @return
     */
    public StrConditionMap<MV> matchFor(String target){
        this.targetVal = target;
        return this;
    }

    /**
     * 比较当前的目标值是否为指定的比较值,如果是则在目标Map中加入指定对象
     * @param matchVal 比较值
     * @param appendVal 分配值
     * @return SCM
     */
    public StrConditionMap<MV> eq(String matchVal, MV appendVal){
        if(StringUtils.isBlank(targetFiled)){
            return this;
        }
        if(targetVal.equals(matchVal)){
            target.put(targetFiled,appendVal);
        }
        return this;
    }

    /**
     * 比较当前的目标值是否包含比较值,如果是则在目标Map中加入指定对象
     * @param matchVal 比较值
     * @param appendVal 分配值
     * @return SCM
     */
    public StrConditionMap<MV> contains(String matchVal, MV appendVal){
        if(StringUtils.isBlank(targetFiled)){
            return this;
        }
        if(targetVal.contains(matchVal)){
            target.put(targetFiled,appendVal);
        }
        return this;
    }



    public static void main(String[] args) {

        String jsr = "比较值1";

        Map<String,String> map = new HashMap<String, String>();

        StrConditionMap.ofMap(map, "test_key")
                .matchFor(jsr)
                .defaultVal("test_val")
                .eq("1","OK")
                .eq("比较值","SUCCESS")
        ;

        System.out.println(map);

    }


}
