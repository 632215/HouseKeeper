package com.jinke.housekeeper.saas.handoverroom.precenter;

import android.content.Context;

import com.jinke.housekeeper.saas.handoverroom.base.BasePresenter;
import com.jinke.housekeeper.saas.handoverroom.bean.EmptyBean;
import com.jinke.housekeeper.saas.handoverroom.bean.FindListDataBean;
import com.jinke.housekeeper.saas.handoverroom.config.HandoverRoomConfig;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnBindingDeviceRequestListener;
import com.jinke.housekeeper.saas.handoverroom.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.handoverroom.service.EquipmentConfigurationBiz;
import com.jinke.housekeeper.saas.handoverroom.service.impl.EquipmentConfigurationBizIml;
import com.jinke.housekeeper.saas.handoverroom.view.EquipmentConfigurationView;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/12/1
 */

public class EquipmentConfigurationPresenter extends BasePresenter<EquipmentConfigurationView>
        implements OnRequestListener<FindListDataBean> ,OnBindingDeviceRequestListener<EmptyBean> {

    private EquipmentConfigurationBiz requestBiz;

    public EquipmentConfigurationPresenter(Context mContext) {
        requestBiz = new EquipmentConfigurationBizIml(mContext);
    }

    public void findListData(Map<String, String> map) {
        if (null != mView) {
            if (null != HandoverRoomConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", HandoverRoomConfig.getTokenBean().getToken());
                map.put("userId", HandoverRoomConfig.getTokenBean().getUserId());
                requestBiz.findListData(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    public void bindingDevice(Map<String, String> map) {
        if (null != mView && null != HandoverRoomConfig.getTokenBean() && null != HandoverRoomConfig.getTokenBean().getToken()) {
            if (null != HandoverRoomConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", HandoverRoomConfig.getTokenBean().getToken());
                requestBiz.bindingDevice(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    @Override
    public void onSuccess(FindListDataBean findListDataBean) {
        if (null != mView) {
            mView.findListDataSuccess(findListDataBean);
        }
    }

    @Override
    public void onBindingDeviceSuccess(EmptyBean emptyBean) {
        if (null != mView) {
            mView.bindingDeviceSuccess();
        }
    }

    @Override
    public void onError(String Code, String Msg) {
        if (null != mView){
            mView.onError(Msg);
        }
    }
}
