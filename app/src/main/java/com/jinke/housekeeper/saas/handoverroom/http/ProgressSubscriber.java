package com.jinke.housekeeper.saas.handoverroom.http;

import android.content.Context;
import android.content.DialogInterface;


import com.jinke.housekeeper.saas.handoverroom.bean.HttpResult;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;
import www.jinke.com.library.utils.LogUtils;
import www.jinke.com.library.widget.ProgressDialog;

public class ProgressSubscriber<T> extends Subscriber<T>{

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private Context context;
    private ProgressDialog progressDialog;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        if (e instanceof SocketTimeoutException) {
            mSubscriberOnNextListener.onError("404", "网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            mSubscriberOnNextListener.onError("404", "网络中断，请检查您的网络状态");
        }
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onError("404", "网络中断，请检查您的网络状态");
        }
        LogUtils.i("errorMsg-----" + e.getMessage());
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        HttpResult httpResult = (HttpResult) t;
        if (mSubscriberOnNextListener != null) {
            if (httpResult.getErrcode() == 200) {
                mSubscriberOnNextListener.onNext(httpResult.getData());
            } else {
                mSubscriberOnNextListener.onError(String.valueOf(httpResult.getErrcode()), httpResult.getErrmsg());
            }
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (!ProgressSubscriber.this.isUnsubscribed()) {
                        ProgressSubscriber.this.unsubscribe();
                    }
                }
            });
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
    }

    private void dismissProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}