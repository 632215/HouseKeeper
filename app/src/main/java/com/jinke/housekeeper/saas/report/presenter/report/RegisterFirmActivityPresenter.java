package com.jinke.housekeeper.saas.report.presenter.report;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.RegisterProjectBean;
import com.jinke.housekeeper.saas.report.service.RegisterFirmActivityBiz;
import com.jinke.housekeeper.saas.report.service.RegisterFirmActivityImpl;
import com.jinke.housekeeper.view.RegisterFirmActivityView;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.http.HttpResult;
import com.jinke.housekeeper.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.http.progress.SubscriberOnNextListener;
import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/15.
 */

public class RegisterFirmActivityPresenter extends BasePresenter<RegisterFirmActivityView> {
    private Context context;
    private RegisterFirmActivityBiz biz;

    public RegisterFirmActivityPresenter(Context context) {
        this.context = context;
        biz = new RegisterFirmActivityImpl(context);
    }

    public void getXMList(SortedMap<String, String> map) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<RegisterProjectBean>() {
            @Override
            public void onNext(RegisterProjectBean info) {
                if (mView != null)
                    mView.getXMListonNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                ToastUtils.showShort(Msg);
            }
        };
        HttpMethods.getInstance().getXMListData(new ProgressSubscriber<HttpResult<RegisterProjectBean>>(onNextListener, context, false), map);
    }
}
