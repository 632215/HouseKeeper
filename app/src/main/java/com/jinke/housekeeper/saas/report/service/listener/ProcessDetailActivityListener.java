package com.jinke.housekeeper.saas.report.service.listener;

import com.jinke.housekeeper.saas.report.bean.NodeElseBean;
import com.jinke.housekeeper.saas.report.bean.WorkBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface ProcessDetailActivityListener {
    void getWorkDetailonError(String code, String msg);

    void getWorkDetailonNext(WorkBean.ObjBean workBean);

    void getProcessDetailonNext(NodeElseBean info);

    void getProcessDetailonError(String code, String msg);
}
