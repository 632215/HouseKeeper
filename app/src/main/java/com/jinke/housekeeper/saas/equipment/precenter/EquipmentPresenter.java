package com.jinke.housekeeper.saas.equipment.precenter;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.bean.NoPointBean;
import com.jinke.housekeeper.saas.equipment.bean.TokenBean;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestNoPointListener;
import com.jinke.housekeeper.saas.equipment.service.EquipmentBiz;
import com.jinke.housekeeper.saas.equipment.service.impl.EquipmentBizIml;
import com.jinke.housekeeper.saas.equipment.view.EquipmentView;

import java.util.Map;

/**
 * function: Equipment数据处理桥梁
 * author: hank
 * date: 2017/9/20
 */

public class EquipmentPresenter extends BasePresenter<EquipmentView> implements OnRequestListener<TokenBean>,OnRequestNoPointListener<NoPointBean> {

    private EquipmentBiz requestBiz;

    public EquipmentPresenter(Context mContext) {
        requestBiz = new EquipmentBizIml(mContext);
    }

    public void getTokenData(Map<String, String> map) {
        if (null != mView) {
            mView.showLoading();
            requestBiz.requestForTokenData(map, this);
        }
    }

    public void getNoPointData(Map<String, String> map) {
        if (null != mView) {
            if (null != EquipmentConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("token", EquipmentConfig.getTokenBean().getToken());
                requestBiz.requestForNoPointData(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    @Override
    public void onSuccess(TokenBean tokenBean) {
        if (null != mView){
            EquipmentConfig.setTokenBean(tokenBean);
            mView.getTokenSuccess();
        }
    }

    @Override
    public void onNoPointSuccess(NoPointBean noPointBean) {
        if (null != mView){
            mView.getNoPointSuccess(noPointBean.getIfDevice());
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (null != mView)
            mView.onError(msg);
    }
}
