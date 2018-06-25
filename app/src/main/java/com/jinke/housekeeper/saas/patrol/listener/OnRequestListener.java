package com.jinke.housekeeper.saas.patrol.listener;

public interface OnRequestListener<T> {
    void onSuccess(T t);

    void onError(String Code, String Msg);
}