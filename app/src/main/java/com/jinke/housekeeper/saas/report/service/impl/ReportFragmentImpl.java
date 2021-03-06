package com.jinke.housekeeper.saas.report.service.impl;

import android.content.Context;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.service.ReportFragmentBiz;
import com.jinke.housekeeper.saas.report.service.listener.ReportFragmentListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import static com.tangxiaolv.telegramgallery.Utils.AndroidUtilities.showToast;

/**
 * Created by Administrator on 2017/9/19.
 */

public class ReportFragmentImpl implements ReportFragmentBiz {
    private Context mContext;

    public ReportFragmentImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getUpFile(RequestParams params, final ReportFragmentListener listener) {
        HttpUtils http = new HttpUtils(200000);
        http.send(HttpRequest.HttpMethod.POST,
                HttpMethods.BASE_URL + "Appinterface/processFollow/",
                params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        try {
                            String code;
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            String errorMsg = jsonObject.getString("errmsg");
                            code = jsonObject.getString("errcode");
                            if (code.equals("1")) {
                                listener.getUpFileonSuccess();
                            } else {
                                listener.getFileUponFailure(errorMsg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        listener.getFileUponFailure(mContext.getResources().getString(R.string.fragment_report_commit_failue));
                    }
                });
    }
}
