package com.jinke.housekeeper.saas.equipment.precenter;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.bean.EmptyBean;
import com.jinke.housekeeper.saas.equipment.bean.ParameterBean;
import com.jinke.housekeeper.saas.equipment.bean.ReadWatchBean;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.http.listener.CompleteTaskOnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.GetParameterOnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.service.ThreeTableListBiz;
import com.jinke.housekeeper.saas.equipment.service.impl.ThreeTableBizIml;
import com.jinke.housekeeper.saas.equipment.view.ThreeTableView;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/7
 */

public class ThreeTablePresenter extends BasePresenter<ThreeTableView> implements OnRequestListener<ReadWatchBean>
        ,GetParameterOnRequestListener<ParameterBean>,CompleteTaskOnRequestListener<EmptyBean> {

    private ThreeTableListBiz requestBiz;

    public ThreeTablePresenter(Context mContext) {
        requestBiz = new ThreeTableBizIml(mContext);
    }

    public void readWatch(Map<String, String> map) {
        if (null != mView) {
            if (null != EquipmentConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("projectId", EquipmentConfig.getProjectId());
                map.put("token", EquipmentConfig.getTokenBean().getToken());
                requestBiz.readWatch(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    public void getParameter(Map<String, String> map) {
        if (null != mView) {
            if (null != EquipmentConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("projectId", EquipmentConfig.getProjectId());
                map.put("token", EquipmentConfig.getTokenBean().getToken());
                requestBiz.getParameter(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    public void uploadData(Map<String, String> map) {
        if (null != mView) {
            if (null != EquipmentConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("projectId", EquipmentConfig.getProjectId());
                map.put("token", EquipmentConfig.getTokenBean().getToken());
                requestBiz.uploadData(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    @Override
    public void onSuccess(ReadWatchBean readWatchBean) {
        if (null != mView) {
            mView.readWatchSuccess(readWatchBean);
        }
    }

    @Override
    public void onGetParameterSuccess(ParameterBean parameterBean) {
        if (null != mView) {
            mView.getParameterSuccess(parameterBean);
        }
    }

    @Override
    public void onUploadDataSuccess(EmptyBean emptyBean) {
        if (null != mView) {
            mView.completeTaskSuccess();
        }
    }

    @Override
    public void onError(String Code, String Msg) {
        if (null != mView){
            mView.onError(Msg);
        }
    }
}
