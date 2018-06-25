package com.jinke.housekeeper.saas.report.presenter.report;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.MailListBean;
import com.jinke.housekeeper.saas.report.bean.HouseMsgBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.saas.report.view.repot.IRoomCheckView;

import java.util.SortedMap;

/**
 * Created by root on 18-4-23.
 */

public class RoomCheckPresent extends BasePresenter<IRoomCheckView> {
    Context mContext;

    public RoomCheckPresent(Context mContext) {
        this.mContext = mContext;
    }

    public void getHouseMsg(SortedMap<String, String> map) {
        if (mView != null) {
            SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<HouseMsgBean>() {
                @Override
                public void onNext(HouseMsgBean info) {
                    mView.onHouseMsgBean(info);
                }

                @Override
                public void onError(String Code, String Msg) {
                    ToastUtils.showLongToast(Msg);
                }
            };
            HttpMethods.getInstance().getHouseMsg(new ProgressSubscriber<HttpResult<HouseMsgBean>>(onNextListener, mContext, false), map, ReportConfig.createSign(map));
        }
    }
}
