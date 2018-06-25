package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.TestInfo;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface RegisterActivityView extends BaseView{
    void getRegisterDataonNext(TestInfo info);

    void getRegisterDataonError(String code, String msg);
}
