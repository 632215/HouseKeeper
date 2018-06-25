package com.jinke.housekeeper.saas.patrol.listener;

/**
 * function:
 * author: hank
 * date: 2017/7/25
 */

public interface IPatrolRecordListener<T> {
    
    void onBackPatrolRecord(T t);

    void onError(String code, String msg);
}
