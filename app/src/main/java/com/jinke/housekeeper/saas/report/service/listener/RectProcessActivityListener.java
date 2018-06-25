package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.ProcessNodeBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface RectProcessActivityListener {
    void getDetailsDataonNext(ProcessNodeBean processNodeBean);

    void getDetailsDataonError(String code, String msg);
}
