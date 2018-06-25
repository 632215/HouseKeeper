package com.jinke.housekeeper.saas.equipment.service.impl;

import android.content.Context;

import com.jinke.housekeeper.saas.equipment.bean.HttpResult;
import com.jinke.housekeeper.saas.equipment.bean.NoPointBean;
import com.jinke.housekeeper.saas.equipment.bean.TokenBean;
import com.jinke.housekeeper.saas.equipment.http.HttpMethods;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.ProgressSubscriber;
import com.jinke.housekeeper.saas.equipment.http.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestNoPointListener;
import com.jinke.housekeeper.saas.equipment.service.EquipmentBiz;

import java.util.Map;

/**
 * function: 数据桥梁处理实现类
 * author: hank
 * date: 2017/9/20
 */

public class EquipmentBizIml implements EquipmentBiz {

    private Context mContext;

    public EquipmentBizIml(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void requestForTokenData(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<TokenBean>() {

            @Override
            public void onNext(TokenBean tokenBeen) {
                if (null != tokenBeen) {
                    listener.onSuccess(tokenBeen);
                } else {
                    listener.onError("001", "获取数据失败");
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getToken(new ProgressSubscriber<HttpResult<TokenBean>>(onNextListener, mContext), map);
    }

    @Override
    public void requestForNoPointData(Map<String, String> map, final OnRequestNoPointListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<NoPointBean>() {

            @Override
            public void onNext(NoPointBean noPointBean) {
                if (null != noPointBean) {
                    listener.onNoPointSuccess(noPointBean);
                } else {
                    listener.onError("001", "获取数据失败");
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getNoPoint(new ProgressSubscriber<HttpResult<NoPointBean>>(onNextListener, mContext), map);
    }
}
