package com.jinke.housekeeper.saas.patrol.view;

import com.jinke.housekeeper.saas.patrol.bean.ContrastDataBean;
import com.jinke.housekeeper.saas.patrol.bean.PointProjectDataBean;

import java.util.List;

/**
 * author : huominghao
 * date : 2018/3/4 0004
 * function :
 */

public interface PatrolStatementView {

    void pointProjectData(List<PointProjectDataBean> dataBean);

    void contrastData(List<ContrastDataBean> dataBean);

    void onError(String msg);

    void onAnaysis(String back);
}
