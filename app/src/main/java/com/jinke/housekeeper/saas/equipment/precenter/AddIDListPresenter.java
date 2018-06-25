package com.jinke.housekeeper.saas.equipment.precenter;

import android.content.Context;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.bean.AddPointCallBackBean;
import com.jinke.housekeeper.saas.equipment.bean.EmptyBean;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.http.listener.OnRequestListener;
import com.jinke.housekeeper.saas.equipment.service.AddIDListBiz;
import com.jinke.housekeeper.saas.equipment.service.impl.AddIDListBizIml;
import com.jinke.housekeeper.saas.equipment.view.AddIDListView;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/10/10
 */

public class AddIDListPresenter extends BasePresenter<AddIDListView> implements OnRequestListener<AddPointCallBackBean> {

    private AddIDListBiz requestBiz;

    public AddIDListPresenter(Context mContext) {
        requestBiz = new AddIDListBizIml(mContext);
    }

    public void getAddPoint(Map<String, String> map) {
        if (null != mView) {
            if (null != EquipmentConfig.getTokenBean().getToken()) {
                mView.showLoading();
                map.put("projectId", EquipmentConfig.getProjectId());
                map.put("token", EquipmentConfig.getTokenBean().getToken());
                requestBiz.getAddPoint(map, this);
            }else {
                mView.onError("err:000");//token为空
            }
        }
    }

    @Override
    public void onSuccess(AddPointCallBackBean addPointCallBackBean) {
        if (null != mView) {
            mView.getAddPointSuccess(addPointCallBackBean);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (null != mView)
            mView.onError(msg);
    }
}