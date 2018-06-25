package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.saas.report.bean.NodeDetailBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface FoundProblemActivityView {
    void getProcessDetailonNext(NodeDetailBean info);

    void getProcessDetailonError(String code, String msg);

    void grabSingleonNext();

    void grabSingleonError(String code, String msg);
}
