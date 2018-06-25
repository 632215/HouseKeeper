package com.jinke.housekeeper.saas.equipment.precenter;

import android.content.Context;

import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.bean.ElectricDataBean;
import com.jinke.housekeeper.saas.equipment.bean.ElectricMonthData;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.http.listener.GetElectricMonthDataOnRequestListener;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.service.StatisticseBiz;
import com.jinke.housekeeper.saas.equipment.service.impl.StatisticseBizIml;
import com.jinke.housekeeper.saas.equipment.view.StatisticseView;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/18
 */

public class StatisticsePresenter extends BasePresenter<StatisticseView> implements OnRequestListener<ElectricDataBean>
        ,GetElectricMonthDataOnRequestListener<ElectricMonthData> {

    private StatisticseBiz requestBiz;

    public StatisticsePresenter(Context mContext) {
        requestBiz = new StatisticseBizIml(mContext);
    }

    public void getElectricData(Map<String, String> map) {
        if (null != mView) {
            if (null != EquipmentConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("projectId", EquipmentConfig.getProjectId());
                map.put("token", EquipmentConfig.getTokenBean().getToken());
                requestBiz.getElectricData(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    public void getElectricMonthData(Map<String, String> map) {
        if (null != mView) {
            if (null != EquipmentConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("projectId", EquipmentConfig.getProjectId());
                map.put("token", EquipmentConfig.getTokenBean().getToken());
                requestBiz.getElectricMonthData(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    @Override
    public void onSuccess(ElectricDataBean electricDataBean) {
        if (null != mView){
            mView.getElectricDataSuccess(electricDataBean);
        }
    }

    @Override
    public void getElectricMonthDataSuccess(ElectricMonthData electricMonthData) {
        if (null != mView){
            mView.getElectricMonthDataSuccess(electricMonthData);
        }
    }

    @Override
    public void onError(String Code, String Msg) {
        if (null != mView){
            mView.onError("err:000");//token为空
        }
    }
}
