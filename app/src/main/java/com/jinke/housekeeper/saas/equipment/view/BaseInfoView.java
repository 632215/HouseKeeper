package com.jinke.housekeeper.saas.equipment.view;

import com.jinke.housekeeper.saas.equipment.bean.DeviceInfoBean;

/**
 * function:
 * author: hank
 * date: 2017/9/30
 */

public interface BaseInfoView {

    /**
     * 获取系统时间
     *
     * @param deviceInfoBean 系统时间
     */
    void getDeviceInfo(DeviceInfoBean deviceInfoBean);

    void showLoading();

    void onError(String msg);
}
