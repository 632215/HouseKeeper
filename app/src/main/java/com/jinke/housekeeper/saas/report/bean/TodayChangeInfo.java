package com.jinke.housekeeper.saas.report.bean;

/**
 * Created by root on 1/9/17.
 */

public class TodayChangeInfo {


    /**
     * object : {"changed":0,"handUp":1,"overTime":37,"unChanged":4}
     */

    private ObjectBean object;

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public static class ObjectBean {
        /**
         * changed : 0
         * handUp : 1
         * overTime : 37
         * unChanged : 4
         */

        private int changed;
        private int handUp;
        private int overTime;
        private int unChanged;

        public int getChanged() {
            return changed;
        }

        public void setChanged(int changed) {
            this.changed = changed;
        }

        public int getHandUp() {
            return handUp;
        }

        public void setHandUp(int handUp) {
            this.handUp = handUp;
        }

        public int getOverTime() {
            return overTime;
        }

        public void setOverTime(int overTime) {
            this.overTime = overTime;
        }

        public int getUnChanged() {
            return unChanged;
        }

        public void setUnChanged(int unChanged) {
            this.unChanged = unChanged;
        }
    }
}
