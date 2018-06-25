package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.jinke.housekeeper.db.SelfCheckingBean;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.service.FileUploadingActivityBiz;
import com.jinke.housekeeper.saas.report.service.listener.FileUploadingActivityListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/11.
 */

public class FileUploadingActivityImpl implements FileUploadingActivityBiz {
    private Context context;

    public FileUploadingActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getUpFile(RequestParams params, final SelfCheckingBean selfCheckingBean, final FileUploadingActivityListener listener) {
        HttpUtils http = new HttpUtils(200000);
        http.send(HttpRequest.HttpMethod.POST,
                HttpMethods.BASE_URL + "Appinterface/startInspection/",
                params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String errcode;
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            String errorMsg = jsonObject.getString("errmsg");
                            errcode = jsonObject.getString("errcode");
                            if (errcode.equals("1")) {
                                listener.getUpFileonSuccess(selfCheckingBean,"0");

                            }
                            listener.getUpFileshowToast(errorMsg);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        listener.getUpFileshowonFailure(msg);
                    }
                });
    }
}
