package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.bean.AppHandleInfo;
import com.jinke.housekeeper.saas.patrol.base.BaseView;
import com.jinke.housekeeper.saas.report.bean.ProcessDetailBean;
import com.jinke.housekeeper.ui.widget.CustomPopWindows;

/**
 * Created by root on 18-4-24.
 */

public interface IWaitingResponseView  extends BaseView{

    void onProcessDetailOnNext(ProcessDetailBean info);

    /**
     * 拨打电话，联系业主
     * @param callPhoneWindows
     */
    void onCallBack(CustomPopWindows callPhoneWindows);

    /**
     * 开始处理成功
     * @param info
     */
    void onAppointment(AppHandleInfo info);
}
