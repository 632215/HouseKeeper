package com.jinke.housekeeper.saas.report.service;

import com.jinke.housekeeper.saas.report.bean.BackUpsInfo;
import com.jinke.housekeeper.saas.report.service.listener.ReportRegistActivityListener;
import com.lidroid.xutils.http.RequestParams;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface ReportRegisterActivityBiz {
    void upLoadData(RequestParams params, BackUpsInfo backUpsInfo,ReportRegistActivityListener listener);
}
