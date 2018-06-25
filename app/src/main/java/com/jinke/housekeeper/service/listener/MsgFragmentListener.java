package com.jinke.housekeeper.service.listener;

import com.jinke.housekeeper.bean.MsgBean;
import com.jinke.housekeeper.bean.PersonalTaskBean;

/**
 * Created by Administrator on 2017/9/6.
 */

public interface MsgFragmentListener {
    void requestMsgListNext(MsgBean info);

    void requestMsgListError(String code, String msg);

    void requestMsgDataNext(PersonalTaskBean info);

    void requestMsgDataError(String code, String msg);

    void updateReadStatusNext(int p);
}
