package com.jinke.housekeeper.service.biz;

import com.jinke.housekeeper.saas.report.presenter.WaitRectifiedFragmentPresenter;
import com.jinke.housekeeper.saas.report.service.listener.WaitRectifiedFragmentListener;

import java.util.SortedMap;

/**
 * Created by Administrator on 2017/9/11.
 */

public interface WaitRectifiedFragmentBiz {
    void getWaitList(SortedMap<String, String> map, WaitRectifiedFragmentListener listener);

    void getAppTaskSignData(SortedMap<String, String> map, WaitRectifiedFragmentListener listener);
}
