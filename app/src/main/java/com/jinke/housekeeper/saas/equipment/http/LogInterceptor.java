package com.jinke.housekeeper.saas.equipment.http;


import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.equipment.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;

/**
 * 拦截器
 */
public class LogInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Response response = chain.proceed(chain.request());
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        MyApplicaption.getInstance();
        if (1 != MyApplicaption.getInstance().getUrlType()){
            LogUtil.logw("respone: " + content);
        }
        return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, content)).build();
    }
}
