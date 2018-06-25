package com.jinke.housekeeper.http;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import www.jinke.com.library.utils.LogUtils;

/**
 * 拦截器
 */
public class LogInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtils.i("request:" + request.toString());
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(request);
        long t2 = System.nanoTime();
//        LogUtils.i(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LogUtils.i("wan" + content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
