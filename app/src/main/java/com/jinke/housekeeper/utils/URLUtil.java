package com.jinke.housekeeper.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * function: url
 * author: hank
 * date: 2017/9/29
 */

public class URLUtil {
    /**
     * 正式环境1
     *
     * @return
     */
    public static Map<String, String> getRegularURL1() {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("appUrl", "http://api.tq-service.com/oa/");
        stringMap.put("equipmentUrl", "http://api.tq-service.com/dian/");
        stringMap.put("patrolUrl", "http://api.tq-service.com/dian/");
        stringMap.put("reportUrl", "http://api.tq-service.com/oa/");
        stringMap.put("handoverRoomUrl", "http://dev-oa.tq-service.com/jiefang/");
        return stringMap;
    }
    /**
     * 正式环境1
     *
     * @return
     */
    public static Map<String, String> getHuMei() {

        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("appUrl", "http://api.tq-service.com/cruise/");
        stringMap.put("equipmentUrl", "http://cruise-api.tq-service.com/device/");
        stringMap.put("patrolUrl", "http://cruise-api.tq-service.com/dian/");
        stringMap.put("reportUrl", "http://api.tq-service.com/repair/");
        stringMap.put("handoverRoomUrl", "http://dev-oa.tq-service.com/jiefang/");
        return stringMap;
    }
    /**
     * 正式环境2
     *
     * @return
     */
    public static Map<String, String> getRegularURL2() {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("appUrl", "http://oa-api.tq-service.com/");
        stringMap.put("equipmentUrl", "http://oa-api.tq-service.com/device/");
        stringMap.put("patrolUrl", "http://oa-api.tq-service.com/dian/");
        stringMap.put("reportUrl", "http://oa-api.tq-service.com/");
        stringMap.put("handoverRoomUrl", "http://oa-api.tq-service.com/jiefang/");
        return stringMap;
    }

    /**
     * 测试环境
     *
     * @return
     */
    public static Map<String, String> getDebugURL() {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("appUrl", "http://dev-oa-api.tq-service.com/");
        stringMap.put("equipmentUrl", "http://dev-oa-api.tq-service.com/device/");
        stringMap.put("patrolUrl", "http://dev-oa-api.tq-service.com/dian/");
//        stringMap.put("reportUrl", "http://dev-oa-api.tq-service.com/");
        stringMap.put("reportUrl", "http://dev-oa.tq-service.com/repair/");
        stringMap.put("handoverRoomUrl", "http://dev-oa-api.tq-service.com/jiefang/");
        return stringMap;
    }

    /**
     * 本地
     * @return
     */
    public static Map<String, String> getNativeURL() {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("appUrl", "http://10.15.208.53:8080/Cruiselch4/");//所有的基础信息
//        stringMap.put("appUrl", "http://10.15.208.86:8080/Cruiselch4/");
        stringMap.put("equipmentUrl", "http://10.15.208.53:8089/DeviceMvn/");//设备巡检
//        stringMap.put("equipmentUrl", "http://10.15.208.116:8089/DeviceMvn/");//设备巡检
        stringMap.put("patrolUrl", "http://10.15.208.53:8080/tqPatrol/");//巡更
//        stringMap.put("patrolUrl", "http://10.15.208.116:8080/tqPatrol/");//巡更http://10.15.208.86:8080/Cruiselchx
        stringMap.put("reportUrl", "http://10.15.208.53:8089/CruMaven/");//配置巡检/工单池
//        stringMap.put("reportUrl", "http://10.15.208.86:8080/Cruiselchx/");//配置巡检/工单池
//        stringMap.put("reportUrl", "http://10.15.208.116:8080/Cruiselch4/");//配置巡检
        stringMap.put("handoverRoomUrl", "http://10.15.208.116:8080/device/");//接房
        return stringMap;
    }

}
