package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface CheckDetailsActivityListener {
    void getProcessDetailonNext(ProcessDetailBean info);

    void getProcessDetailonError(String code, String msg);
}
