package com.jinke.housekeeper.saas.patrol.listener;

/**
 * function:
 * author: hank
 * date: 2017/11/13
 */

public interface PatrolReplaceListener<T> {

    void onBackPatrolReplace(T t);

    void onError(String code, String msg);
}
