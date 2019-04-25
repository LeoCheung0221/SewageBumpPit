package com.yzspp.sewage.net.base.convert;

import android.os.Looper;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.vondear.rxui.view.dialog.RxDialog;
import com.yzspp.sewage.net.base.ResponseBean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import timber.log.Timber;

import static okhttp3.internal.Util.UTF_8;

/**
 * Created by LeoCheung4ever on 2018/8/3.
 *
 * @See
 * @Description
 */

public class SNTGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    SNTGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string(); //把response body转为string

        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;

        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        assert charset != null;
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }

    public static String toURLEncoded(String url) {
        url = url.replaceAll("%(?![0-9a-fA-F]{2})", "%");
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public <T> Class getTClass(T t) {
        Type[] params = t.getClass().getGenericInterfaces();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            finalNeedType = type;
        }
        final Class clazz = this.getClass(finalNeedType, 0);
        return clazz;
    }

    public Class getClass(Type type, int i) {
        if (type instanceof ParameterizedType) {
            return getGenericClass((ParameterizedType) type, i);
        } else if (type instanceof TypeVariable) {
            return (Class) getClass(((TypeVariable) type).getBounds()[0], 0);
        } else {
            return (Class) type;
        }
    }

    public Class getGenericClass(ParameterizedType parameterizedType, int i) {
        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        if (genericClass instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) genericClass).getRawType();
        } else if (genericClass instanceof GenericArrayType) {
            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
        } else if (genericClass instanceof TypeVariable) {
            return (Class) getClass(((TypeVariable) genericClass).getBounds()[0], 0);
        } else {
            return (Class) genericClass;
        }
    }

}
