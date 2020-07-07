package com.lucien.myspringboot.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author huoershuai
 * @Date 2020/5/21
 */
@AllArgsConstructor
@Getter
public enum  DemoEnum {

    DEMO_1(1, "demo1"),
    DEMO_2(2, "demo2"),
    ;

    private int code;
    private String name;

    public final static JSONArray JSON = new JSONArray();

    private static final Map<Integer,DemoEnum> MAP = new HashMap<>();

    static{
        Arrays.stream(DemoEnum.values()).forEach(e -> {
            JSONObject businessJson = new JSONObject();
            businessJson.put("code", e.getCode());
            businessJson.put("name", e.getName());
            JSON.add(businessJson);
            MAP.put(e.getCode(), e);
        });
    }

    public static DemoEnum getByCode(Integer code){
        return MAP.get(code);
    }
}
