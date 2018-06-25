package com.jinke.housekeeper.saas.equipment.http.listener;

public interface OnRequestListener<T> {
    void onSuccess(T t);

    void onError(String Code, String Msg);
}