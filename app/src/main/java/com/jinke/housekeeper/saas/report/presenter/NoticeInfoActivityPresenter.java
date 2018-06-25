package com.jinke.housekeeper.saas.report.presenter;

import android.content.Context;

import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.service.NoticeInfoActivityBiz;
import com.jinke.housekeeper.saas.report.service.impl.NoticeInfoActivityImpl;
import com.jinke.housekeeper.saas.report.service.listener.NoticeInfoActivityListener;
import com.jinke.housekeeper.saas.report.view.NoticeInfoActivityView;

/**
 * Created by Administrator on 2017/9/11.
 */

public class NoticeInfoActivityPresenter extends BasePresenter<NoticeInfoActivityView> implements NoticeInfoActivityListener {
    private Context context;
    private NoticeInfoActivityBiz biz;
    public NoticeInfoActivityPresenter(Context context) {
        this.context = context;
        biz= new NoticeInfoActivityImpl(context);
    }
}
