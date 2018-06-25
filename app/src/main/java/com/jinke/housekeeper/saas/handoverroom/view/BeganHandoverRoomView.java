package com.jinke.housekeeper.saas.handoverroom.view;

import com.jinke.housekeeper.saas.handoverroom.bean.FindStateBean;
import com.jinke.housekeeper.saas.handoverroom.bean.HandoverRoomBean;

/**
 * function:
 * author: hank
 * date: 2017/11/26
 */

public interface BeganHandoverRoomView {
    void handleHouseSuccess();

    void getHouseInfoSuccess(HandoverRoomBean handoverRoomBean);

    void findStateSuccess(FindStateBean findStateBean);

    void showLoading();

    void onError(String msg);
}
