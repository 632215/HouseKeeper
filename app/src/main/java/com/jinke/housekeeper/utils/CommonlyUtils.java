package com.jinke.housekeeper.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.CountXMListInfo;
import com.jinke.housekeeper.bean.PageInfo;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.bean.RegisterProjectBean;
import com.jinke.housekeeper.bean.SessionInfo;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.ui.activity.LoginActivity;
import com.jinke.housekeeper.ui.widget.LoadingLayout;

import www.jinke.com.library.utils.NetWorksUtils;
import www.jinke.com.library.widget.MaterialDialog;


/**
 * Created by root on 5/01/17.
 */

public class CommonlyUtils {
    public static RegisterProjectBean.ListObjBean beanHome = null;
    public static RegisterProjectBean.ListObjBean bean = null;
    public static RegisterDepartmentBean.ListObjBean contactsBean = null;
    public static RegisterDepartmentBean.ListObjBean pointsBean = null;
    public static RegisterDepartmentBean.ListObjBean listObjBean = null;
    public static CountXMListInfo.ListObjBean mCountXml = null;
    static SharedPreferences mySharedPreferences;

    /**
     * 自查统计界面三个Bean参数
     */
    public static RegisterProjectBean.ListObjBean inspectionStatisticsByMonth = null;//第一个图表
    public static RegisterProjectBean.ListObjBean inspectProblemByMonth = null;//第二个图表
    public static RegisterProjectBean.ListObjBean inspectProblemByPoint = null;//第三个图表


    /**
     * 分页信息
     *
     * @param page 当前页
     * @return
     */
    public static String pageInfo(int page) {
        PageInfo info = new PageInfo();
        info.setCurrentPage(page);
        info.setOrder("asc");
        info.setPageSize(20);
        String pageStr = new Gson().toJson(info);
        return pageStr;
    }

    /**
     * @param loadingLayout
     * @param mContext
     */
    public static void setLoadConnected(LoadingLayout loadingLayout, Context mContext) {
        if (!NetWorksUtils.isConnected(MyApplicaption.getInstance())) {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.net_error), Toast.LENGTH_SHORT).show();
            return;
        }
        loadingLayout.setStatus(NetWorksUtils.isConnected(MyApplicaption.getInstance()) ? LoadingLayout.Loading : LoadingLayout.No_Network);
    }

    public static void saveUserInfo(Context mContext, UserInfo info) {
        mySharedPreferences = mContext.getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("userName", info.getUserName());
        editor.putString("pass", info.getPassWord());
        editor.putString("session", info.getSessionid());
        editor.putString("userId", info.getUserId());
        editor.putString("name", info.getName());
        editor.putString("organization", info.getOrganization());
        editor.putString("roles", info.getRoles());
        editor.putString("roleType", info.getRoleType());
        editor.putString("orgCode", info.getOrgCodel());
        editor.putString("type", info.getType());
        editor.putString("orgName", info.getOrgName());
        editor.putString("power", info.getPower());

        editor.putString("leftOrgId", info.getLeftOrgId());
        editor.putString("leftOrgName", info.getLeftOrgName());
        editor.putString("mobile", info.getMobile());
        editor.putString("fenCompany", info.getFenCompany());
        editor.putString("position", info.getPosition());
        editor.putString("deptName", info.getDeptName());
        editor.putString("sex", info.getSex());
        editor.putString("company", info.getCommpany());

        RegisterProjectBean.ListObjBean listbean = new RegisterProjectBean.ListObjBean();
        listbean.setId(info.getLeftOrgId());
        listbean.setName(info.getLeftOrgName());
        beanHome = listbean;

        editor.commit();
    }

    public static UserInfo getUserInfo(Context mContext) {
        UserInfo info = new UserInfo();
        mySharedPreferences = mContext.getSharedPreferences("user", Activity.MODE_PRIVATE);
        info.setUserName(mySharedPreferences.getString("userName", ""));
        info.setPassWord(mySharedPreferences.getString("pass", ""));

//        info.setUserId(mySharedPreferences.getLong("userId", 0));
//        info.setUserId(mySharedPreferences.getLong("userId", Long.valueOf(0)));
        info.setUserId(mySharedPreferences.getString("userId", ""));

        info.setSessionid(mySharedPreferences.getString("session", ""));
        info.setName(mySharedPreferences.getString("name", ""));
        info.setOrganization(mySharedPreferences.getString("organization", ""));
        info.setRoles(mySharedPreferences.getString("roles", ""));
        info.setRoleType(mySharedPreferences.getString("roleType", ""));
        info.setOrgCodel(mySharedPreferences.getString("orgCode", ""));
        info.setType(mySharedPreferences.getString("type", ""));
        info.setOrgName(mySharedPreferences.getString("orgName", ""));
        info.setPower(mySharedPreferences.getString("power", ""));

        info.setLeftOrgId(mySharedPreferences.getString("leftOrgId", ""));
        info.setLeftOrgName(mySharedPreferences.getString("leftOrgName", ""));
        info.setMobile(mySharedPreferences.getString("mobile", ""));

        info.setFenCompany(mySharedPreferences.getString("fenCompany", ""));
        info.setDeptName(mySharedPreferences.getString("deptName", ""));
        info.setPosition(mySharedPreferences.getString("position", ""));
        info.setSex(mySharedPreferences.getString("sex", ""));
        info.setCommpany(mySharedPreferences.getString("company", ""));

        return info;
    }

    public static void saveSession(Context mContext, SessionInfo sessionInfo) {
        mySharedPreferences = mContext.getSharedPreferences("Session", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("userId", sessionInfo.getUserId());
        editor.putString("session", sessionInfo.getSessionId());
        editor.putString("OrgCodel", sessionInfo.getOrgCodel());
        editor.putString("OrgName", sessionInfo.getOrgName());
        editor.putString("roleType", sessionInfo.getRoleType());
        editor.putString("username", sessionInfo.getUserName());
        editor.putString("password", sessionInfo.getPassword());
        editor.commit();
    }

    public static SessionInfo getSessionInfo(Context mContext) {
        SessionInfo sessionInfo = new SessionInfo();
        mySharedPreferences = mContext.getSharedPreferences("Session", Activity.MODE_PRIVATE);
        sessionInfo.setUserId(mySharedPreferences.getString("userId", ""));
        sessionInfo.setSessionId(mySharedPreferences.getString("session", ""));
        sessionInfo.setOrgName(mySharedPreferences.getString("OrgName", ""));
        sessionInfo.setOrgCodel(mySharedPreferences.getString("OrgCodel", ""));
        sessionInfo.setRoleType(mySharedPreferences.getString("roleType", ""));
        sessionInfo.setPassword(mySharedPreferences.getString("password", ""));
        sessionInfo.setUserName(mySharedPreferences.getString("username", ""));
        return sessionInfo;
    }

    public static void clearSessionInfo(Context mContext) {
        mySharedPreferences = mContext.getSharedPreferences("Session", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void clearUserInfo(Context mContext) {
        mySharedPreferences = mContext.getSharedPreferences("user", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void clearData(Context mContext) {
        inspectionStatisticsByMonth = null;//第一个图表
        inspectProblemByMonth = null;//第二个图表
        inspectProblemByPoint = null;//第三个图表
    }

    static MaterialDialog materialDialog;

    public static void errorState(final Context mcontex, String code) {
        switch (code) {
            case "7":
                if (materialDialog != null) {
                    materialDialog.dismiss();
                    materialDialog = null;
                }
                materialDialog = new MaterialDialog(mcontex);
                materialDialog.setTitle("登录失效");
                materialDialog.setMessage("有一台设备在其他地方登录，是否重新登录");
                materialDialog.setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mcontex, LoginActivity.class);
                        mcontex.startActivity(intent);
                        BaseActivity.exit();
                    }
                });
                materialDialog.show();
                break;
        }
    }

    public static void savePushState(Context mContext, String pushState) {
        mySharedPreferences = mContext.getSharedPreferences("PushState", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("pushState", pushState);
        editor.commit();
    }

    public static String getPushState(Context mContext) {
        SessionInfo sessionInfo = new SessionInfo();
        mySharedPreferences = mContext.getSharedPreferences("PushState", Activity.MODE_PRIVATE);
        return mySharedPreferences.getString("pushState","Y");
    }

}
