package com.jinke.housekeeper.saas.handoverroom.http.listener;

/**
 * function:
 * author: hank
 * date: 2017/12/1
 */

public interface OnFindStateRequestListener<T> {

    void findStateSuccess(T t);

    void onError(String Code, String Msg);
}
