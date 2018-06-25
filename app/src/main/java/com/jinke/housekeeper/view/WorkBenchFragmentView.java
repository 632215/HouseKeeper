package com.jinke.housekeeper.view;

import com.jinke.housekeeper.bean.OpenIdBean;
import com.jinke.housekeeper.saas.report.bean.SessionBean;

/**
 * Created by Administrator on 2017/9/6.
 */

public interface WorkBenchFragmentView {
    void getMapPointNext(OpenIdBean info);

    void getMapPointError(String code, String msg);

    void onQualityInspect(SessionBean info);
}
