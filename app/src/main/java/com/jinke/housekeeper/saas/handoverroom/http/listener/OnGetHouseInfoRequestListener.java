package com.jinke.housekeeper.saas.handoverroom.http.listener;

public interface OnGetHouseInfoRequestListener<T> {
    void getHouseInfoSuccess(T t);

    void onError(String Code, String Msg);
}