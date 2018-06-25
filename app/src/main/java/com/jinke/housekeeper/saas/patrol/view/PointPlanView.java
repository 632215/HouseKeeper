package com.jinke.housekeeper.saas.patrol.view;

import com.jinke.housekeeper.saas.patrol.bean.IsStartBean;
import com.jinke.housekeeper.saas.patrol.bean.PointPlanBean;

/**
 * author : huominghao
 * date : 2018/1/25 0025
 * function :
 */

public interface PointPlanView {

    void showMessage(String message);

    void getPointListBean(PointPlanBean pointPlanBean);

    void isStartBean(IsStartBean isStartBean);

    void onError(String msg);
}
