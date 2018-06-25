package com.jinke.housekeeper.saas.report.bean;

/**
 * Created by root on 1/9/17.
 */

public class TodayWorkInfo {


    /**
     * object : {"envirCompleted":0,"envirRate":0,"envirTotal":0,"proCompleted":0,"proRate":0,"proTotal":0,"safeCompleted":4,"safeRate":0.2,"safeTotal":20,"serviceCompleted":8,"serviceRate":0.4,"serviceTotal":20}
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
         * envirCompleted : 0
         * envirRate : 0.0
         * envirTotal : 0
         * proCompleted : 0
         * proRate : 0.0
         * proTotal : 0
         * safeCompleted : 4
         * safeRate : 0.2
         * safeTotal : 20
         * serviceCompleted : 8
         * serviceRate : 0.4
         * serviceTotal : 20
         */

        private int envirCompleted;
        private double envirRate;
        private int envirTotal;
        private int proCompleted;
        private double proRate;
        private int proTotal;
        private int safeCompleted;
        private double safeRate;
        private int safeTotal;
        private int serviceCompleted;
        private double serviceRate;
        private int serviceTotal;

        public int getEnvirCompleted() {
            return envirCompleted;
        }

        public void setEnvirCompleted(int envirCompleted) {
            this.envirCompleted = envirCompleted;
        }

        public float getEnvirRate() {
            return Float.parseFloat(String.valueOf(envirRate)) * 100;
        }

        public void setEnvirRate(double envirRate) {
            this.envirRate = envirRate;
        }

        public int getEnvirTotal() {
            return envirTotal;
        }

        public void setEnvirTotal(int envirTotal) {
            this.envirTotal = envirTotal;
        }

        public int getProCompleted() {
            return proCompleted;
        }

        public void setProCompleted(int proCompleted) {
            this.proCompleted = proCompleted;
        }

        public float getProRate() {
            return Float.parseFloat(String.valueOf(proRate)) * 100;
        }

        public void setProRate(double proRate) {
            this.proRate = proRate;
        }

        public int getProTotal() {
            return proTotal;
        }

        public void setProTotal(int proTotal) {
            this.proTotal = proTotal;
        }

        public int getSafeCompleted() {
            return safeCompleted;
        }

        public void setSafeCompleted(int safeCompleted) {
            this.safeCompleted = safeCompleted;
        }

        public float getSafeRate() {
            return Float.parseFloat(String.valueOf(safeRate)) * 100;
        }

        public void setSafeRate(double safeRate) {
            this.safeRate = safeRate;
        }

        public int getSafeTotal() {
            return safeTotal;
        }

        public void setSafeTotal(int safeTotal) {
            this.safeTotal = safeTotal;
        }

        public int getServiceCompleted() {
            return serviceCompleted;
        }

        public void setServiceCompleted(int serviceCompleted) {
            this.serviceCompleted = serviceCompleted;
        }

        public float getServiceRate() {
            return Float.parseFloat(String.valueOf(serviceRate)) * 100;
        }

        public void setServiceRate(double serviceRate) {
            this.serviceRate = serviceRate;
        }

        public int getServiceTotal() {
            return serviceTotal;
        }

        public void setServiceTotal(int serviceTotal) {
            this.serviceTotal = serviceTotal;
        }
    }
}
