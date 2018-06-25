package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.MsgFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/6.
 */

public interface MsgFragmentBiz {
    void requestMsgList(SortedMap<String, String> map, MsgFragmentListener listener);

    void requestMsgData(SortedMap<String, String> map, MsgFragmentListener listener);

    void updateReadStatus(SortedMap<String, String> map, MsgFragmentListener listener, int p);
}
