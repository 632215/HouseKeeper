package com.jinke.housekeeper.saas.equipment.http.listener;

/**
 * function:
 * author: hank
 * date: 2017/10/10
 */

public interface OnRequestNoPointListener<T> {

    void onNoPointSuccess(T t);

    void onError(String Code, String Msg);
}
