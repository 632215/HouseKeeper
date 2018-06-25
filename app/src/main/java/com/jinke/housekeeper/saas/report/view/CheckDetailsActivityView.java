package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface CheckDetailsActivityView {
    void getProcessDetailOnNext(ProcessDetailBean info);

    void getProcessDetailonError(String code, String msg);
}
