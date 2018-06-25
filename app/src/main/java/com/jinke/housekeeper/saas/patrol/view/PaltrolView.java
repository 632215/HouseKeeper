package com.jinke.housekeeper.saas.patrol.view;

import com.jinke.housekeeper.saas.patrol.bean.PointListBean;

import java.util.List;

/**
 * Created by root on 17-6-13.
 */

public interface PaltrolView {

    void showMessage(String message);

    void showLoading();

    void getPointListBean(List<PointListBean> list);

    void onError(String msg);
}
