package com.jinke.housekeeper.saas.patrol.view;

import com.jinke.housekeeper.saas.patrol.bean.ContrastDataBean;
import com.jinke.housekeeper.saas.patrol.bean.PointDataBean;
import com.jinke.housekeeper.saas.patrol.bean.PointProjectDataBean;
import com.jinke.housekeeper.saas.patrol.bean.TimeDataBean;

import java.util.List;

/**
 * author : huominghao
 * date : 2018/3/2 0002
 * function :
 */

public interface StatementView {

    void isStartBean(PointDataBean dataBean);

    void onError(String msg);
}
