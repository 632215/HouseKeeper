package com.jinke.housekeeper.presenter;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.OpenIdBean;
import com.jinke.housekeeper.saas.report.bean.SessionBean;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.service.WorkBenchFragmentBiz;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.service.impl.WorkBenchFragmentImpl;
import com.jinke.housekeeper.service.listener.WorkBenchFragmentListener;
import com.jinke.housekeeper.view.WorkBenchFragmentView;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/6.
 */

public class WorkBenchFragmentPresenter extends BasePresenter<WorkBenchFragmentView> implements WorkBenchFragmentListener {
    private Context mContext;
    private WorkBenchFragmentBiz mBiz;

    public WorkBenchFragmentPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new WorkBenchFragmentImpl(mContext);
    }


    /**
     * 巡更
     * @param map
     */
    public void getMapPoint(SortedMap<String, String> map) {
        if (mView != null)
            mBiz.getMapPoint(map, this);
    }

    @Override
    public void getMapPointNext(OpenIdBean info) {
        if (mView != null)
            mView.getMapPointNext(info);
    }

    @Override
    public void getMapPointError(String code, String msg) {
        if (mView != null)
            mView.getMapPointError(code, msg);
    }

    public void getToken(SortedMap<String, String> map) {
        //获取品质巡检openId
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<SessionBean>() {
            @Override
            public void onNext(SessionBean info) {
                SharedPreferencesUtils.init(mContext)
                        .put("quality_sessionId", info.getSessionId())
                        .put("staffName", info.getStaffName());
                mView.onQualityInspect(info);

            }

            @Override
            public void onError(String Code, String Msg) {
                ToastUtils.showShort(Msg);
            }
        };
        HttpMethods.getInstance().getToken(new ProgressSubscriber<HttpResult<SessionBean>>(onNextListener, mContext, true), map, MyApplicaption.createSign(map));

    }
}
