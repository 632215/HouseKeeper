package com.jinke.housekeeper.saas.handoverroom.precenter;

import android.content.Context;

import com.jinke.housekeeper.saas.handoverroom.base.BasePresenter;
import com.jinke.housekeeper.saas.handoverroom.bean.TokenBean;
import com.jinke.housekeeper.saas.handoverroom.config.HandoverRoomConfig;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.handoverroom.service.HandoverRoomBiz;
import com.jinke.housekeeper.saas.handoverroom.service.impl.HandoverRoomBizIml;
import com.jinke.housekeeper.saas.handoverroom.view.HandoverRoomView;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/26
 */

public class HandoverRoomPresenter extends BasePresenter<HandoverRoomView> implements OnRequestListener<TokenBean> {

    private HandoverRoomBiz requestBiz;

    public HandoverRoomPresenter(Context mContext) {
        requestBiz = new HandoverRoomBizIml(mContext);
    }

    public void getUserToken(Map<String, String> map) {
        if (null != mView) {
            mView.showLoading();
            requestBiz.getUserToken(map, this);
        }
    }

    @Override
    public void onSuccess(TokenBean tokenBean) {
        if (null != mView) {
            HandoverRoomConfig.setTokenBean(tokenBean);
            mView.getUserTokenSuccess();
        }
    }


    @Override
    public void onError(String Code, String Msg) {
        if (null != mView){
            mView.onError(Msg);
        }
    }
}

