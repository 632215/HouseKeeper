package com.jinke.housekeeper.saas.patrol.config;

import com.jinke.housekeeper.saas.patrol.bean.TokenBean;

/**
 * Created by root on 17-7-24.
 */

public class PatrolConfig {
    /**
     * 巡更平台token
     */
    private static TokenBean tokenBean;

    public static TokenBean getTokenBean() {
        if (null == tokenBean){
            tokenBean = new TokenBean();
        }
        return tokenBean;
    }

    public static void setTokenBean(TokenBean tokenBea) {
        tokenBean = tokenBea;
    }
}
