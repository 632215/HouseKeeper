package com.jinke.housekeeper.saas.handoverroom.view;

import com.jinke.housekeeper.saas.handoverroom.bean.FindListDataBean;

/**
 * function:
 * author: hank
 * date: 2017/12/1
 */

public interface EquipmentConfigurationView {

    void findListDataSuccess(FindListDataBean findListDataBean);

    void bindingDeviceSuccess();

    void showLoading();

    void onError(String msg);
}
