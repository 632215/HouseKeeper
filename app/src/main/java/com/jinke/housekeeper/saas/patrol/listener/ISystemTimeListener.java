package com.jinke.housekeeper.saas.patrol.listener;

/**
 * function:
 * author: hank
 * date: 2017/7/25
 */

public interface ISystemTimeListener<T>{

    void onBackSystemTime(T t);

    void onError(String code, String msg);
}
