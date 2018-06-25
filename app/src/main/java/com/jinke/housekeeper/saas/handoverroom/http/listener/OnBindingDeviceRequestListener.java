package com.jinke.housekeeper.saas.handoverroom.http.listener;

public interface OnBindingDeviceRequestListener<T> {
    void onBindingDeviceSuccess(T t);

    void onError(String Code, String Msg);
}