package com.jinke.housekeeper.saas.report.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.EmptyBean;
import com.jinke.housekeeper.bean.MsgBean;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface NewsFragmentView extends BaseView{
    void initMagDataNext(MsgBean info);

    void initMagDataError(String code, String msg);

    void updateReadStatusError(String code, String msg);

    void updateReadStatusNext(EmptyBean info,int p);
}
