package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.saas.report.bean.NodeElseBean;
import com.jinke.housekeeper.saas.report.bean.WorkBean;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface ProcessDetailActivityView extends BaseView{
    void getWorkDetailonNext(WorkBean.ObjBean workBean);

    void getWorkDetailonError(String code, String msg);

    void getProcessDetailonNext(NodeElseBean info);

    void getProcessDetailonError(String code, String msg);
}
