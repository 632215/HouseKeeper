package com.jinke.housekeeper.http.progress;

/**
 * Created by liukun on 16/3/10.
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);

    void onError(String Code, String Msg);
}
