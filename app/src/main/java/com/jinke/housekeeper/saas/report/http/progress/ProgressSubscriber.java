package com.jinke.housekeeper.saas.report.http.progress;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jinke.housekeeper.saas.report.http.HttpResult;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;
import www.jinke.com.library.utils.LogUtils;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by liukun on 16/3/10.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;
    private Context context;
    private boolean isShow = true;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context, boolean isShow) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        this.isShow = isShow;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog() {
        if (isShow) {
            if (mProgressDialogHandler != null) {
                mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
            }
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            LogUtils.i("error:" + e.getMessage());
        }
        dismissProgressDialog();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     * @param t 创建Subscriber时的泛型类型--
     */
    @Override
    public void onNext(T t) {
        HttpResult httpResult = (HttpResult) t;
        if (mSubscriberOnNextListener != null) {
            if (httpResult.getErrcode() == 1 ) {
                mSubscriberOnNextListener.onNext(httpResult.getData());
            } else {
                mSubscriberOnNextListener.onError(httpResult.getErrcode() + "", httpResult.getErrmsg());
            }
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}