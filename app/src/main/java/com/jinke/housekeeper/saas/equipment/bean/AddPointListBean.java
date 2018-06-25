package com.jinke.housekeeper.saas.equipment.bean;

import java.io.Serializable;
import java.util.List;

public class AddPointListBean implements Serializable {
        public List<AddPointBean> addPointBeanList;

        public List<AddPointBean> getAddPointBeanList() {
            return addPointBeanList;
        }

        public void setAddPointBeanList(List<AddPointBean> addPointBeanList) {
            this.addPointBeanList = addPointBeanList;
        }
    }