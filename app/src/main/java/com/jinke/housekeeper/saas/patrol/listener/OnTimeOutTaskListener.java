package com.jinke.housekeeper.saas.patrol.listener;

/**
 * author : huominghao
 * date : 2018/2/5 0005
 * function :
 */

public interface OnTimeOutTaskListener<T> {

    void onTimeOutTaskSuccess(T t);

    void onError(String Code, String Msg);
}
