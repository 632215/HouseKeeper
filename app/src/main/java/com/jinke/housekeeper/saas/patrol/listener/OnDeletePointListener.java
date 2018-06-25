package com.jinke.housekeeper.saas.patrol.listener;

public interface OnDeletePointListener<T> {
    void onDeleteSuccess(T t);

    void onError(String Code, String Msg);
}