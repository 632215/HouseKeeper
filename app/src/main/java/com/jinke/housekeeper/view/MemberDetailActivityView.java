package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.TestInfo;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface MemberDetailActivityView extends BaseView{
    void getUserDeleteNext(TestInfo testInfo);

    void getUserDeleteError(String code, String msg);
}
