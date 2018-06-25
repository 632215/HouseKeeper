package com.jinke.housekeeper.saas.patrol.view;

import com.jinke.housekeeper.saas.patrol.bean.TimeDataBean;

import java.util.List;

/**
 * author : huominghao
 * date : 2018/3/4 0004
 * function :
 */

public interface PatrolStatCaleView {

    void timeData(List<TimeDataBean> dataBean);

    void onError(String msg);
}
