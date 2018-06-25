package com.jinke.housekeeper.saas.patrol.listener;

/**
 * author : huominghao
 * date : 2018/1/26 0026
 * function :
 */

public interface PointPlanListener<T> {

    void onPointPlanList(T t);

    void onError(String code, String msg);
}
