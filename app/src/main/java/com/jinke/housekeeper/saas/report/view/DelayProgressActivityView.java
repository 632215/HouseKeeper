package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.saas.report.bean.NodeDelayBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface DelayProgressActivityView {

    void getProcessDetailOnNext(NodeDelayBean info);

    void getProcessDetailOnError(String code, String msg);
}
