package com.jinke.housekeeper.saas.patrol.listener;

/**
 * author : huominghao
 * date : 2018/3/4 0004
 * function :
 */

public interface ContrastRequestListener<T> {
    void onContrastSuccess(T t);

    void onError(String Code, String Msg);
}
