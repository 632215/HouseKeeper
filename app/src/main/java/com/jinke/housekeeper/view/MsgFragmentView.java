package com.jinke.housekeeper.view;

import com.jinke.housekeeper.base.BaseView;
import com.jinke.housekeeper.bean.MsgBean;
import com.jinke.housekeeper.bean.PersonalTaskBean;

/**
 * Created by Administrator on 2017/9/6.
 */

public interface MsgFragmentView extends BaseView {
    void requestMsgListNext(MsgBean info);

    void requestMsgDataNext(PersonalTaskBean info);

    void requestMsgDataError(String code, String msg);

    void requestMsgListError(String code, String msg);

    void updateReadStatusNext(int p);

    void showLoading();
}
