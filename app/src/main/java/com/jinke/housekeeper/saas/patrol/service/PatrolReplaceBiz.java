package com.jinke.housekeeper.saas.patrol.service;

import com.jinke.housekeeper.saas.patrol.listener.PatrolReplaceListener;

import java.util.Map;

/**
 * function:
 * author: hank
 * date: 2017/11/13
 */

public interface PatrolReplaceBiz {

    void patrolReplace(Map<String, String> map, PatrolReplaceListener listener);
}
