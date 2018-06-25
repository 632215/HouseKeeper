package com.jinke.housekeeper.saas.handoverroom.precenter;

import android.content.Context;

import com.jinke.housekeeper.saas.handoverroom.base.BasePresenter;
import com.jinke.housekeeper.saas.handoverroom.bean.EmptyBean;
import com.jinke.housekeeper.saas.handoverroom.bean.FindStateBean;
import com.jinke.housekeeper.saas.handoverroom.bean.HandoverRoomBean;
import com.jinke.housekeeper.saas.handoverroom.config.HandoverRoomConfig;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnFindStateRequestListener;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnGetHouseInfoRequestListener;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.handoverroom.service.BeganHandoverRoomBiz;
import com.jinke.housekeeper.saas.handoverroom.service.impl.BeganHandoverRoomBizIml;
import com.jinke.housekeeper.saas.handoverroom.view.BeganHandoverRoomView;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/26
 */

public class BeganHandoverRoomPresenter extends BasePresenter<BeganHandoverRoomView>
        implements OnRequestListener<EmptyBean>, OnGetHouseInfoRequestListener<HandoverRoomBean>, OnFindStateRequestListener<FindStateBean> {

    private BeganHandoverRoomBiz requestBiz;

    public BeganHandoverRoomPresenter(Context mContext) {
        requestBiz = new BeganHandoverRoomBizIml(mContext);
    }

    public void handleHouse(Map<String, String> map) {
        if (null != mView && null != HandoverRoomConfig.getTokenBean() && null != HandoverRoomConfig.getTokenBean().getToken()) {
            mView.showLoading();
            map.put("token", HandoverRoomConfig.getTokenBean().getToken());
            requestBiz.handleHouse(map, this);
        }
    }

    public void findState(Map<String, String> map) {
        if (null != mView ) {
            if (null != HandoverRoomConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", HandoverRoomConfig.getTokenBean().getToken());
                requestBiz.findState(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    public void getHouseInfo(Map<String, String> map) {
        if (null != mView) {
            if (null != HandoverRoomConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", HandoverRoomConfig.getTokenBean().getToken());
                requestBiz.getHouseInfo(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    @Override
    public void onSuccess(EmptyBean emptyBean) {
        if (null != mView) {
            mView.handleHouseSuccess();
        }
    }

    @Override
    public void getHouseInfoSuccess(HandoverRoomBean handoverRoomBean) {
        if (null != mView) {
            mView.getHouseInfoSuccess(handoverRoomBean);
        }
    }

    @Override
    public void findStateSuccess(FindStateBean findStateBean) {
        if (null != mView) {
            mView.findStateSuccess(findStateBean);
        }
    }

    @Override
    public void onError(String Code, String Msg) {
        if (null != mView) {
            mView.onError(Msg);
        }
    }
}
