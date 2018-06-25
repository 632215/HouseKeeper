package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.db.SelfCheckingBean;
import com.jinke.housekeeper.saas.report.service.FileUploadingActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.FileUploadingActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.FileUploadingActivityListener;
import com.jinke.housekeeper.saas.report.view.FileUploadingActivityView;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by Administrator on 2017/9/11.
 */

public class FileUploadingActivityPresenter extends BasePresenter<FileUploadingActivityView> implements FileUploadingActivityListener {
    private FileUploadingActivityBiz biz;
    private Context context;

    public FileUploadingActivityPresenter(Context context) {
        this.context = context;
        biz =new FileUploadingActivityImpl(context);
    }

    public void getUpFile(RequestParams params, SelfCheckingBean selfCheckingBean) {
        mView.showLoading();
        biz.getUpFile(params,selfCheckingBean,this);
    }

    @Override
    public void getUpFileonSuccess(SelfCheckingBean selfCheckingBean, String s) {
        mView.hideLoading();
        mView.getUpFileonSuccess(selfCheckingBean,s);
    }

    @Override
    public void getUpFileshowToast(String errorMsg) {
        mView.hideLoading();
        mView.getUpFileshowToast(errorMsg);
    }

    @Override
    public void getUpFileshowonFailure(String msg) {
        mView.hideLoading();
        mView.getUpFileshowonFailure(msg);
    }
}
