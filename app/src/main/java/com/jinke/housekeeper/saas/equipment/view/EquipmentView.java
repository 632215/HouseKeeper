package com.jinke.housekeeper.saas.equipment.view;

/**
 * function: Equipment 视图控制
 *
 * author: hank
 * date: 2017/9/20
 */

public interface EquipmentView {

    void getTokenSuccess();

    void getNoPointSuccess(String ifDevice);

    void showLoading();

    void onError(String msg);
}
