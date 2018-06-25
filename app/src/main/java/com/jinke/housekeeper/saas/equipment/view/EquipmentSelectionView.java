package com.jinke.housekeeper.saas.equipment.view;

import com.jinke.housekeeper.saas.equipment.bean.DeviceTypeBean;

/**
 * function: EquipmentSelectionActivity 视图控制
 * author: hank
 * date: 2017/9/29
 */

public interface EquipmentSelectionView {

    void getDeviceTypeSuccess(DeviceTypeBean deviceTypeBean);

    void showLoading();

    void onError(String msg);
}
