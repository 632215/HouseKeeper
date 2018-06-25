package com.jinke.housekeeper.applicaption;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.adtech.sys.util.Encrypt;
import com.adtech.sys.util.MD5Util;
import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.Utils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.SessionInfo;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.saas.report.helper.LocationService;
import com.jinke.housekeeper.ui.activity.MainsActivity;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.utils.URLUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatCrashReporter;
import com.tencent.stat.StatReportStrategy;
import com.tencent.stat.StatService;

import org.litepal.LitePalApplication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import www.jinke.com.library.utils.StringUtils;

/**
 * Created by root on 22/12/16.
 */
public class MyApplicaption extends BaiduTTApplicaption {
    public static String isSelf = null;
    private static MyApplicaption application;
    public static LocationService locationService;

    private int urlType = 1;//0 本地环境 1测试环境 2正式环境1 3正式环境,4,华美美丽山
    private String appUrl;
    private String equipmentUrl;
    private String patrolUrl;
    private String reportUrl;
    private String handoverRoomUrl;

    public int getUrlType() {
        return urlType;
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.selfcheckingeditframe, android.R.color.black);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new ("更新于 %s"));//指定为经典Header，默认是贝塞尔雷达Header
            }
        });

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                layout.setPrimaryColorsId(R.color.selfcheckingeditframe, android.R.color.black);//全局设置主题颜色
                return new ClassicsFooter(context);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        iniTencent();
        locationService = new LocationService(this);
        LitePalApplication.initialize(this);
        application = this;
        Utils.init(this);
        initLayout();
        checkUpdate();
        initJPush();
        initURL(urlType);
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JPushInterface.getRegistrationID(this);
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.mipmap.housekeeper;
    }

    private void initURL(int urlType) {
        Map<String, String> stringMap = new HashMap<>();
        switch (urlType) {
            //0 本地环境 1测试环境 2正式环境1 3正式环境,4,华美美丽山
            case 0:
                stringMap = URLUtil.getNativeURL();
                break;
            //测试环境
            case 1:
                stringMap = URLUtil.getDebugURL();
                break;
            //正式环境1
            case 2:
                stringMap = URLUtil.getRegularURL1();
                break;
            //正式环境
            case 3:
                stringMap = URLUtil.getRegularURL2();
                break;
            //华美美丽山
            case 4:
                stringMap = URLUtil.getHuMei();
                break;
        }
        Iterator entryIterator = stringMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) entryIterator.next();
            switch ((String) entry.getKey()) {
                case "appUrl":
                    appUrl = (String) entry.getValue();
                    break;
                case "equipmentUrl":
                    equipmentUrl = (String) entry.getValue();
                    break;
                case "patrolUrl":
                    patrolUrl = (String) entry.getValue();
                    break;
                case "reportUrl":
                    reportUrl = (String) entry.getValue();
                    break;
                case "handoverRoomUrl":
                    handoverRoomUrl = (String) entry.getValue();
                    break;
            }
        }
    }

    public void iniTencent() {
        StatService.setContext(this);
        StatConfig.setDebugEnable(false);
        // 自动监控Activity生命周期，可以代替之前手动调用的onResume和onPause，防止漏打点的情况
        StatService.registerActivityLifecycleCallbacks(this);
        //自动捕获程序异常并上传
        StatConfig.setAutoExceptionCaught(true);
        // 选择默认的上报策略
        StatConfig.setStatSendStrategy(StatReportStrategy.PERIOD);
        // 10分钟上报一次的周期
        StatConfig.setSendPeriodMinutes(10);
        //上传安装渠道
        StatConfig.setInstallChannel(this, "应用宝");
        //错误日志捕获并上传，参数配置
        StatCrashReporter crashReporter = StatCrashReporter.getStatCrashReporter(this);
        // 开启异常时的实时上报
        crashReporter.setEnableInstantReporting(true);
        // 开启java异常捕获
        crashReporter.setJavaCrashHandlerStatus(true);
        // 开启Native c/c++，即so的异常捕获
        // 请根据需要添加，记得so文件
        crashReporter.setJniNativeCrashStatus(true);
    }

    public static String getUserId() {
        SessionInfo sessionInfo = CommonlyUtils.getSessionInfo(application);
        return sessionInfo.getUserId();
    }

    public static String getSessionId() {
        SessionInfo sessionInfo = CommonlyUtils.getSessionInfo(application);
        return sessionInfo.getSessionId();
    }

    /**
     * 创建签名
     *
     * @param map
     * @return
     */
    public static String createSign(SortedMap<String, String> map) {
        String tokenCommonlyUtils;
        SortedMap<Object, Object> sortedMap = new TreeMap<>();
        Set es = map.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry sign = (Map.Entry) it.next();
            Object k = sign.getKey();
            Object v = sign.getValue();
            sortedMap.put(k, v);
        }

        SessionInfo info = CommonlyUtils.getSessionInfo(getInstance());
        //请求加密
        if (!StringUtils.isEmpty(info.getUserName()) && !StringUtils.isEmpty(info.getPassword())) {
            tokenCommonlyUtils = Encrypt.md5(info.getUserName() + Encrypt.md5(info.getPassword()) + "jkwycruise");
        } else {
            UserInfo userInfo = CommonlyUtils.getUserInfo(getInstance());
            tokenCommonlyUtils = Encrypt.md5(userInfo.getUserName() + Encrypt.md5(userInfo.getPassWord()) + "jkwycruise");
        }

        return MD5Util.createSign(tokenCommonlyUtils, "UTF-8", sortedMap);
    }


    /**
     * 加载默认布局页面
     */
    private void initLayout() {
        LoadingLayout.getConfig()
                .setErrorText("抱歉，数据出错")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.drawable.kong)
                .setEmptyImage(R.drawable.empty)
                .setNoNetworkImage(R.drawable.empty_wifi)
                .setAllTipTextColor(R.color.gray)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.gray)
                .setReloadButtonWidthAndHeight(150, 40)
                .setAllPageBackgroundColor(R.color.background)
                .setLoadingPageLayout(R.layout.define_loading_page);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MyApplicaption getInstance() {
        return application;
    }

    public void checkUpdate() {
        /***** Beta高级设置 *****/
        /**
         * true表示app启动自动初始化升级模块;
         * false不会自动初始化;
         * 开发者如果担心sdk初始化影响app启动速度，可以设置为false，
         * 在后面某个时刻手动调用Beta.init(getApplicationContext(),false);
         */
        Beta.autoInit = true;

        /**
         * true表示初始化时自动检查升级;
         * false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
         */
        Beta.autoCheckUpgrade = true;

        /**
         * 设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);
         */
        Beta.upgradeCheckPeriod = 60 * 1000;

        /**
         * 设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
         */
        Beta.initDelay = 1 * 1000;

        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源;
         */
        Beta.largeIconId = R.drawable.ic_launcher;
        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源Id;
         */
        Beta.smallIconId = R.drawable.ic_launcher;

        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        Beta.defaultBannerId = R.drawable.ic_launcher;

        /**
         * 点击过确认的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        Beta.showInterruptedStrategy = true;

        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗;
         * 不设置会默认所有activity都可以显示弹窗;
         */
        Beta.canShowUpgradeActs.add(MainsActivity.class);

        /***** 统一初始化Bugly产品，包含Beta *****/
        Bugly.init(getApplicationContext(), "0489262b02", true);
    }

    public String getAppUrl() {
        return appUrl;
    }

    public String getEquipmentUrl() {
        return equipmentUrl;
    }

    public String getPatrolUrl() {
        return patrolUrl;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public String getHandoverRoomUrl() {
        return handoverRoomUrl;
    }

}
