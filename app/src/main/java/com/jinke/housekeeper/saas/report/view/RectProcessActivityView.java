package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.saas.report.bean.ProcessNodeBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface RectProcessActivityView {
    void getDetailsDataonNext(ProcessNodeBean processNodeBean);

    void getDetailsDataonError(String code, String msg);
}
