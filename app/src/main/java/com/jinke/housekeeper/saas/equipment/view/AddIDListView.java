package com.jinke.housekeeper.saas.equipment.view;

import com.jinke.housekeeper.saas.equipment.bean.AddPointCallBackBean;

/**
 * function:
 * author: hank
 * date: 2017/10/10
 */

public interface AddIDListView {

    void getAddPointSuccess(AddPointCallBackBean addPointCallBackBean);

    void showLoading();

    void onError(String msg);
}
