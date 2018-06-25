package com.jinke.housekeeper.saas.equipment.http.listener;

/**
 * function: 提交抄表惹怒我
 * author: hank
 * date: 2017/11/11
 */

public interface CompleteTaskOnRequestListener<T> {

    void onUploadDataSuccess(T t);

    void onError(String Code, String Msg);

}
