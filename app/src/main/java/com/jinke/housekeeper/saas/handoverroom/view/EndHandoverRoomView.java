package com.jinke.housekeeper.saas.handoverroom.view;

import com.jinke.housekeeper.saas.handoverroom.bean.EmptyBean;

/**
 * function:
 * author: hank
 * date: 2017/11/26
 */

public interface EndHandoverRoomView {
    void takeHouseSuccess(EmptyBean emptyBean);

    void showLoading();

    void onError(String msg);
}
