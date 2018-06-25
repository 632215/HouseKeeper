package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.service.listener.PersonalFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface PersonalFragmentBiz {
     void getStatisticsgr(SortedMap<String, String> getStatisticsgr, PersonalFragmentListener listener);

    void getIndStaSponIns(SortedMap<String, String> map, PersonalFragmentListener listener);
}
