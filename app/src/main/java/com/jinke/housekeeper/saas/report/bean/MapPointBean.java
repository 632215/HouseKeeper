package com.jinke.housekeeper.saas.report.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */
public class MapPointBean {
    /**
     * org : {"province":"北京市","lot":116.395645,"lat":39.929986,"city":"市辖区","name":"测试项目中华坊"}
     * list : [{"name":"单元大厅门口","lnt":"-122.406417","lat":"37.785834"},{"name":"车库出入口","lnt":"-122.406417","lat":"37.785834"},{"name":"单元大厅门口","lnt":"-122.406417","lat":"37.785834"},{"name":"车库","lnt":"0.0","lat":"0.0"},{"name":"车库","lnt":"0.0","lat":"0.0"},{"name":"单元大厅","lnt":"0.0","lat":"0.0"},{"name":"车库出入口","lnt":"-122.406417","lat":"37.785834"},{"name":"单元大厅门口","lnt":"-122.406417","lat":"37.785834"},{"name":"单元大厅门口","lnt":"-122.406417","lat":"37.785834"},{"name":"单元大厅门口","lnt":"-122.406417","lat":"37.785834"},{"name":"单元大厅门口","lnt":"-122.406417","lat":"37.785834"},{"name":"车库出入口","lnt":"-122.406417","lat":"37.785834"},{"name":"车库","lnt":"0.0","lat":"0.0"},{"name":"单元大厅","lnt":"0.0","lat":"0.0"},{"name":"运动场地","lnt":"0.0","lat":"0.0"},{"name":"运动场地","lnt":"0.0","lat":"0.0"},{"name":"车库","lnt":"0.0","lat":"0.0"},{"name":"单元大厅门口","lnt":"-122.406417","lat":"37.785834"},{"name":"车库","lnt":"0.0","lat":"0.0"}]
     */

    private OrgBean org;
    private List<ListBean> list;

    public OrgBean getOrg() {
        return org;
    }

    public void setOrg(OrgBean org) {
        this.org = org;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class OrgBean {
        /**
         * province : 北京市
         * lot : 116.395645
         * lat : 39.929986
         * city : 市辖区
         * name : 测试项目中华坊
         */

        private String province;
        private double lot;
        private double lat;
        private String city;
        private String name;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public double getLot() {
            return lot;
        }

        public void setLot(double lot) {
            this.lot = lot;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ListBean {
        /**
         * name : 单元大厅门口
         * lnt : -122.406417
         * lat : 37.785834
         */

        private String name;
        private String lnt;
        private String lat;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLnt() {
            return lnt;
        }

        public void setLnt(String lnt) {
            this.lnt = lnt;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
