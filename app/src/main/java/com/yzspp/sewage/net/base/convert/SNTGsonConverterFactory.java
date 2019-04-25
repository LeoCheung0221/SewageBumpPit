package com.yzspp.sewage.net.base.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by LeoCheung4ever on 2018/8/3.
 *
 * @See
 * @Description 处理相应对象数据结构会变化的情况
 */

public class SNTGsonConverterFactory extends Converter.Factory {

    private final Gson gson;

    private SNTGsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public static SNTGsonConverterFactory create() {
        return create(new Gson());
    }

    public static SNTGsonConverterFactory create(Gson gson) {
        return new SNTGsonConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new SNTGsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new SNTGsonRequestBodyConverter<>(gson, adapter);
    }


}
