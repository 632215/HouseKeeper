package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;
import android.util.Log;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.db.SelfCheckingBean;
import com.jinke.housekeeper.saas.report.bean.BackUpsInfo;
import com.jinke.housekeeper.saas.report.bean.RecorderBean;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.service.ReportRegisterActivityBiz;
import com.jinke.housekeeper.saas.report.service.listener.ReportRegistActivityListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */

public class ReportRegistActivityImpl implements ReportRegisterActivityBiz {
    private Context context;

    public ReportRegistActivityImpl(Context context) {
        this.context = context;
    }

    @Override
    public void upLoadData(RequestParams params, final BackUpsInfo backUpsInfo, final ReportRegistActivityListener listener) {
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
                                listener.upLoadDataResult("0");
                            }
                            Log.e("32s", "onSuccess:errcode" + jsonObject.getString("errmsg"));
                            listener.showMessage(errorMsg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.e("32s", "onFailure:" + msg + "error:" + error.getMessage());
                        saveOFFLineData(backUpsInfo, listener);
                    }
                });
    }

    private void saveOFFLineData(BackUpsInfo info, ReportRegistActivityListener listener) {
        SelfCheckingBean selfCheckingBean = new SelfCheckingBean();
        selfCheckingBean.setSessionId(MyApplicaption.getSessionId());
        selfCheckingBean.setUserId(MyApplicaption.getUserId());
        selfCheckingBean.setPictureList(info.getPictureList());
        if (info.getRecorderList().size() > 0) {
            List<String> recorderNameList = new ArrayList<>();
            List<String> recorderTimeList = new ArrayList<>();
            for (RecorderBean bean : info.getRecorderList()) {
                recorderNameList.add(bean.getFilePath());
                recorderTimeList.add(bean.getTime());
            }
            selfCheckingBean.setRecordNameList(recorderNameList);
            selfCheckingBean.setRecordTimeList(recorderTimeList);
        }
        selfCheckingBean.setDecribe(info.getText());
        selfCheckingBean.setCategory(info.getCategory());
        selfCheckingBean.setKeyPoint(info.getPointKey());
        selfCheckingBean.setStaffTime(info.getDate());
        selfCheckingBean.setInfo(info.getJson());
        if (selfCheckingBean.save()) {
            listener.showMessage(context.getString(R.string.activity_file_uploading_storage_success));
        } else {
            listener.showMessage(context.getString(R.string.activity_file_uploading_storage_failed));
        }
        listener.upLoadDataResult("1");
    }
}
