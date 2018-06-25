package com.jinke.housekeeper.saas.equipment.http.listener;

/**
 * function:三表详情
 * author: hank
 * date: 2017/11/11
 */

public interface GetParameterOnRequestListener<T> {

    void onGetParameterSuccess(T t);

    void onError(String Code, String Msg);
}
