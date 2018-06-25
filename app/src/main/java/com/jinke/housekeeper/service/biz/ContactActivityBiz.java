package com.jinke.housekeeper.service.biz;


import com.jinke.housekeeper.service.listener.ContactActivityListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public interface ContactActivityBiz {
    void getMailList(SortedMap<String, String> map, ContactActivityListener listener);
}
