package com.jinke.housekeeper.saas.handoverroom.http;

/**
 * function:
 * author: hank
 * date: 2017/9/20
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);

    void onError(String Code, String Msg);
}
