package com.jinke.housekeeper.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.LoginInfo;
import com.jinke.housekeeper.bean.OpenIdBean;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.bean.SessionBean;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.service.biz.LoginActivityBiz;
import com.jinke.housekeeper.service.impl.LoginActivityImpl;
import com.jinke.housekeeper.service.listener.LoginActivityListener;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.view.LoginActivityView;

import java.util.SortedMap;
import java.util.TreeMap;

import www.jinke.com.library.utils.NetWorksUtils;

/**
 * Created by Administrator on 2017/9/15.
 */

public class LoginActivityPresenter extends BasePresenter<LoginActivityView> implements LoginActivityListener {
    private Context context;
    private LoginActivityBiz biz;

    public LoginActivityPresenter(Context context) {
        this.context = context;
        biz = new LoginActivityImpl(context);
    }

    public void getUserLoginData(SortedMap<String, String> map) {
        biz.getUserLoginData(map, this);
    }


    @Override
    public void getUserLoginDataonError(String code, String msg) {
        if (mView != null)
            mView.getUserLoginDataonError(code, msg);
    }

    @Override
    public void getUserLoginDataonNext(LoginInfo info) {
        if (mView != null)
            mView.getUserLoginDataonNext(info);
    }

    public void doNext(Activity activity, int requestCode, int[] grantResults) {
        switch (requestCode) {
            case 2:
                if (grantResults.length == 0) {
                    return;
                }

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    ToastUtils.showShort(activity.getResources().getString(R.string.activity_login_recorder_permit));
                    activity.finish();
                }
                break;
        }
    }


    public void getAppOauth(String appKey) {
        if (NetWorksUtils.isConnected(context)) {
            SortedMap<String, String> map = new TreeMap<>();
            map.put("userId", MyApplicaption.getUserId());
            map.put("sessionId", MyApplicaption.getSessionId());
            map.put("appKey", appKey);
           getMapPoint(map);
        } else {
            new AlertDialog.Builder(context).setTitle("提示")//设置对话框标题
                    .setMessage("网络请求失败，请检查网络再试")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }


    public void getMapPoint(SortedMap<String, String> map) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<OpenIdBean>() {
            @Override
            public void onNext(OpenIdBean info) {
                UserInfo userInfo = CommonlyUtils.getUserInfo(context);
                SortedMap<String, String> map = new TreeMap<>();
                map.put("openId", info.getOpenId());
                map.put("projectId", userInfo.getLeftOrgId());
               getToken(map);

            }

            @Override
            public void onError(String Code, String Msg) {
            }
        };
        HttpMethods.getInstance().platformOauth(new ProgressSubscriber<HttpResult<OpenIdBean>>(onNextListener, context, true), map, MyApplicaption.createSign(map));
    }


    //获取报事报修token
    public void getToken(SortedMap<String, String> map) {
        //获取品质巡检openId
        com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener onNextListener = new com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener<SessionBean>() {
            @Override
            public void onNext(SessionBean info) {
                SharedPreferencesUtils.init(context)
                        .put("quality_sessionId", info.getSessionId())
                        .put("staffName", info.getStaffName());
            }

            @Override
            public void onError(String Code, String Msg) {
                ToastUtils.showShort(Msg);
            }
        };
        com.jinke.housekeeper.saas.report.http.HttpMethods.getInstance().getToken(new com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber<com.jinke.housekeeper.saas.report.http.HttpResult<SessionBean>>(onNextListener, context, true), map, MyApplicaption.createSign(map));

    }
}
