package com.jinke.housekeeper.saas.patrol.listener;

/**
 * function:
 * author: hank
 * date: 2017/7/25
 */

public interface IAddPointListener<T>{

    void onBackAddPoint(T t);

    void onError(String code, String msg);

    /**
     * function:
     * author: hank
     * date: 2017/8/7
     */

    interface OnGetPoinListListener<T> {

        void OnGetPoinListSuccess(T t);

        void onError(String Code, String Msg);
    }
}
