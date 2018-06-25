package com.jinke.housekeeper.saas.equipment.http.listener;

/**
 * function:
 * author: hank
 * date: 2017/10/11
 */

public interface OnRequestTaskListener<T> {

    void onTaskSuccess(T t);

    void onError(String Code, String Msg);
}
