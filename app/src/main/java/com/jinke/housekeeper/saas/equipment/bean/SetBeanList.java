package com.jinke.housekeeper.saas.equipment.bean;

import java.io.Serializable;
import java.util.List;

/**
 * function: 连接设置实体
 * author: hank
 * date: 2017/9/12
 */

public class SetBeanList implements Serializable {


    private List<SetBean> setBeanList;

    public SetBeanList(List<SetBean> setBeanList) {
        this.setBeanList = setBeanList;
    }

    public List<SetBean> getSetBeanList() {
        return setBeanList;
    }

    public void setSetBeanList(List<SetBean> setBeanList) {
        this.setBeanList = setBeanList;
    }

    public static class SetBean implements Serializable {
        private String setString;
        private boolean setState;

        public SetBean(String setString, boolean setState) {
            this.setString = setString;
            this.setState = setState;
        }

        public String getSetString() {
            return setString;
        }

        public void setSetString(String setString) {
            this.setString = setString;
        }

        public boolean isSetState() {
            return setState;
        }

        public void setSetState(boolean setState) {
            this.setState = setState;
        }
    }


}
