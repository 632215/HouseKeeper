package com.jinke.housekeeper.saas.handoverroom.precenter;

import android.content.Context;

import com.jinke.housekeeper.saas.handoverroom.base.BasePresenter;
import com.jinke.housekeeper.saas.handoverroom.bean.EmptyBean;
import com.jinke.housekeeper.saas.handoverroom.config.HandoverRoomConfig;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.handoverroom.service.EndHandoverRoomBiz;
import com.jinke.housekeeper.saas.handoverroom.service.impl.EndHandoverRoomBizIml;
import com.jinke.housekeeper.saas.handoverroom.view.EndHandoverRoomView;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/26
 */

public class EndHandoverRoomPresenter extends BasePresenter<EndHandoverRoomView> implements OnRequestListener<EmptyBean> {
    private EndHandoverRoomBiz requestBiz;

    public EndHandoverRoomPresenter(Context mContext) {
        requestBiz = new EndHandoverRoomBizIml(mContext);
    }

    public void takeHouse(Map<String, String> map) {
        if (null != mView) {
            if (null != HandoverRoomConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", HandoverRoomConfig.getTokenBean().getToken());
                requestBiz.takeHouse(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    @Override
    public void onSuccess(EmptyBean emptyBean) {
        if (null != mView) {
            mView.takeHouseSuccess(emptyBean);
        }
    }

    @Override
    public void onError(String Code, String Msg) {
        if (null != mView){
            mView.onError(Msg);
        }
    }
}
