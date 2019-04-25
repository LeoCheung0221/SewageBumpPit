package com.yzspp.sewage.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by LeoCheung4ever on 2018/7/13.
 *
 * @See
 * @Description
 */

public class GsonConvertUtils {

    private static Gson create() {
        return GsonHolder.gson;
    }

    private static class GsonHolder {
        private static Gson gson = new GsonBuilder().serializeNulls().create();
    }

    public static <T> T fromJson(String json, Class<T> type) throws JsonIOException, JsonSyntaxException {
        T t = null;
        try {
            t = create().fromJson(json, type);
        } catch (Exception e) {
            return t;
        }
        return t;
    }

    public static <T> T fromJson(String json, Type type) {
        return create().fromJson(json, type);
    }

    public static <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return create().fromJson(reader, typeOfT);
    }

    public static <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        return create().fromJson(json, classOfT);
    }

    public static <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return create().fromJson(json, typeOfT);
    }

    public static String toJSONString(Object src) {
        return create().toJson(src);
    }

    public static String toJSONString(Object src, Type typeOfSrc) {
        return create().toJson(src, typeOfSrc);
    }

    /**
     * 单个对象间的转换=================================
     *
     * @param obj
     * @return
     */
    public static String objToJson(Object obj) {
        Gson g = new Gson();
        return g.toJson(obj);
    }

    public static Object jsonToObj(Class<?> clazz, String JsonStr) throws Exception {
        Gson g = new Gson();
        Object obj = null;
        try {
            obj = g.fromJson(JsonStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 将bean转换成Map
     *
     * @param bean
     * @return
     */
    public static HashMap<String, String> beanToMap(Object bean) {
        if (bean == null) {
            return null;
        }

        HashMap<String, String> parameters = new HashMap<>();

        if (null != parameters) {
            // 取得bean所有public 方法
            Method[] Methods = bean.getClass().getMethods();
            for (Method method : Methods) {
                if (method != null && method.getName().startsWith("get")
                        && !method.getName().startsWith("getClass")) {
                    // 得到属性的类名
                    String value = "";
                    // 得到属性值
                    try {
                        String className = method.getReturnType()
                                .getSimpleName();
                        if (className.equalsIgnoreCase("int")) {
                            int val = 0;
                            try {
                                val = (Integer) method.invoke(bean);
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            value = String.valueOf(val);
                        } else if (className.equalsIgnoreCase("String")) {
                            try {
                                value = (String) method.invoke(bean);
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        if (value != null && value != "") {
                            // 添加参数
                            // 将方法名称转化为id，去除get，将方法首字母改为小写
                            String param = method.getName().replaceFirst("get",
                                    "");
                            if (param.length() > 0) {
                                String first = String.valueOf(param.charAt(0))
                                        .toLowerCase();
                                param = first + param.substring(1);
                            }
                            parameters.put(param, value);
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return parameters;
    }

    //忽略字段id
    private Gson getSkipIdGson() {
        Gson gson = new GsonBuilder().setExclusionStrategies(
                new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        //过滤掉字段名包含"id","address"的字段
                        return f.getName().equals("id");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        // 过滤掉 类名包含 Bean的类
                        return false;
                    }
                }).create();
        return gson;
    }

    private Gson getSkipIdAndGroupIdGson() {
        Gson gson = new GsonBuilder().setExclusionStrategies(
                new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        //过滤掉字段名包含"id","address"的字段
                        return f.getName().equals("id") | f.getName().equals("groupGuid");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        // 过滤掉 类名包含 Bean的类
                        return false;
                    }
                }).create();
        return gson;
    }

}
