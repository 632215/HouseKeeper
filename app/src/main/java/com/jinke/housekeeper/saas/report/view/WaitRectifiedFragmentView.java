package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.saas.report.bean.WaitRectifiedBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface WaitRectifiedFragmentView {
    void getWaitListonNext(WaitRectifiedBean waitRectifiedBean);

    void getWaitListonError(String code, String msg);

    void getAppTaskSignDataonError(String code, String msg);
}
