package com.jinke.housekeeper.saas.equipment.http.listener;

/**
 * function:
 * author: hank
 * date: 2017/11/18
 */

public interface GetElectricMonthDataOnRequestListener<T> {

    void getElectricMonthDataSuccess(T t);

    void onError(String Code, String Msg);
}
