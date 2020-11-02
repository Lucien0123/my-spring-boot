package com.lucien.myspringboot.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lucien.myspringboot.util.GsonUtils;

import lombok.Data;

/**
 * @author huoershuai
 * Created on 2020-11-02
 */
@Data
public class GsonTestModel {

    /**
     * 不参与gson的序列化与反序列化
     */
    @Expose(serialize = false, deserialize = false)
    private long id;

    /**
     * 设置别名
     * String类似默认值为null
     */
    @SerializedName(value = "name", alternate = "fullName")
    private String name;

    /**
     * int 类型默认值为0
     */
    private int age;


    private boolean isMan;

    private SubGsonModel ad;

    @Data
    static class SubGsonModel {
        private String province;
        private int adCode;
    }

    public static void main(String[] args) {
        GsonTestModel model = new GsonTestModel();
        model.setName("huoershuai");
        model.setAge(20);
        model.setMan(true);
        SubGsonModel subModel = new SubGsonModel();
        subModel.setProvince("shanxi");
        subModel.setAdCode(5);
        SubGsonModel subModel2 = new SubGsonModel();
        subModel2.setProvince("beijing");
        subModel2.setAdCode(10);
        model.setAd(subModel);
        List<SubGsonModel> subGsonModelList = new ArrayList<>();
        subGsonModelList.add(subModel);
        subGsonModelList.add(subModel2);

        
        String listString = GsonUtils.gsonToJSON(subGsonModelList);
        System.out.println(listString);
        List<SubGsonModel> ccc = GsonUtils.gsonFromJSONArray(listString, SubGsonModel.class);
        System.out.println(ccc.get(0).getClass().getName());

    }
}
