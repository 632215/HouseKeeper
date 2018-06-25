package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.saas.report.bean.NodeDetailBean;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface DealProblemActivityView {
    void getProcessDetailonError(String code, String msg);

    void getProcessDetailonNext(NodeDetailBean info);
}
