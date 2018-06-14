package com.disarch.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Map;

public class GsonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(GsonUtils.class);

    public static Gson gson = null;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.setPrettyPrinting().serializeNulls().create();
    }

    public static Map<String, String> fromJson(String json) {
        if (json == null || "".equals(json.trim()))
            return null;

        Map<String, String> map = null;
        try {
            map = fromJson(json, Map.class);
        } catch (Exception e) {
            LOGGER.error("Json string is :" + json, e);
        }
        return map;
    }

    public static <T> T fromJson(String json, Type type) {
        if (json == null || "".equals(json.trim()))
            return null;

        T data = null;
        try {
            data = GsonUtils.gson.fromJson(json, type);
        } catch (Exception e) {
            LOGGER.error("Json string is :" + json, e);
        }
        return data;
    }

    public static String toJson(Object src) throws JsonSyntaxException {
        return GsonUtils.gson.toJson(src);
    }

    public static void main(String[] args) {
        String content="{\"a\":1,\"b\":2}";
        Map<String,String> map= GsonUtils.<Map<String,String>>fromJson(content,Map.class);
        System.out.println(map);
    }
}
