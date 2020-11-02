package com.lucien.myspringboot.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

/**
 * @author huoershuai
 * Created on 2020-11-02
 */
public class GsonUtils {

    private static final Gson gson = new Gson();

//    static {
//        GsonBuilder builder = new GsonBuilder();
//        builder.excludeFieldsWithoutExposeAnnotation();
//        gson = builder.create();
//    }

    public static <T> String gsonToJSON(@Nullable T object) {
        if (object != null) {
            return gson.toJson(object);
        }
        return "";
    }

    public static <T> T gsonFromJSON(@Nullable String objJson, Class<T> valueType) {
        if (StringUtils.isNotBlank(objJson)) {
            return gson.fromJson(objJson, valueType);
        }
        return null;
    }

    public static <T> List<T> gsonFromJSONArray(@Nullable String objJson, Class<T> classOfT) {
        List<T> result = new ArrayList<>();
        if (StringUtils.isNotBlank(objJson)) {
            Type type = new TypeToken<ArrayList<T>>(){}.getType();
            ArrayList<LinkedTreeMap> jsonObjs = gson.fromJson(objJson, type);
            if (!CollectionUtils.isEmpty(jsonObjs)) {
                for (LinkedTreeMap jsonObj : jsonObjs) {
                    result.add(gson.fromJson(jsonObj.toString(), classOfT));
                }
            }
        }
        return result;
    }
}
