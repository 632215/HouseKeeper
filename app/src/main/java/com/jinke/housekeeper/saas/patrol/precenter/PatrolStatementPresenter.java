package com.jinke.housekeeper.saas.patrol.precenter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.saas.patrol.base.BasePresenter;
import com.jinke.housekeeper.saas.patrol.bean.ContrastDataBean;
import com.jinke.housekeeper.saas.patrol.bean.PointProjectDataBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.listener.ContrastRequestListener;
import com.jinke.housekeeper.saas.patrol.listener.OnRequestListener;
import com.jinke.housekeeper.saas.patrol.service.PatrolStatementBiz;
import com.jinke.housekeeper.saas.patrol.service.impl.PatrolStatementBizIml;
import com.jinke.housekeeper.saas.patrol.view.PatrolStatementView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author : huominghao
 * date : 2018/3/4 0004
 * function :
 */

public class PatrolStatementPresenter extends BasePresenter<PatrolStatementView>
        implements OnRequestListener<List<PointProjectDataBean>>, View.OnClickListener, ContrastRequestListener<List<ContrastDataBean>> {

    private PatrolStatementBiz requestBiz;
    private LayoutInflater inflater;
    private UserInfo userInfo;

    public PatrolStatementPresenter(Context context) {
        requestBiz = new PatrolStatementBizIml(context);
    }

    public void pointProjectData(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                map.put("token", PatrolConfig.getTokenBean().getToken());
//                map.clear();
//                map.put("userId", "1493085931767");
//                map.put("token", "6aa091c923644c6387e75b039bba5dd9");
//                map.put("startTime", "2017-1-2 10:37");
//                map.put("endTime", "2018-5-2 10:35");
//                map.put("projectId", "208");
                requestBiz.pointProjectData(map, this);
            } else {
                mView.onError("登录失效请重新登录！");
            }
        }
    }

    public void contrastData(Map<String, String> map) {
        if (null != mView) {
            if (null != PatrolConfig.getTokenBean().getToken()) {
                map.put("token", PatrolConfig.getTokenBean().getToken());
                requestBiz.contrastData(map, this);
            } else {
                mView.onError("登录失效请重新登录！");
            }
        }
    }

    @Override
    public void onSuccess(List<PointProjectDataBean> pointProjectDataBeans) {
        if (null != mView) {
            mView.pointProjectData(pointProjectDataBeans);
        }
    }

    @Override
    public void onContrastSuccess(List<ContrastDataBean> contrastDataBeans) {
        if (null != mView) {
            mView.contrastData(contrastDataBeans);
        }
    }

    @Override
    public void onError(String Code, String Msg) {
        if (null != mView) {
            mView.onError("登录失效请重新登录！");
        }
    }

    PopupWindow popupWindow;

    /**
     * 显示切换弹窗
     *
     * @param textView 　显示的控件位置
     * @param mContext 　当前页面对象
     * @param userInfo
     */
    public void showWindow(TextView textView, Context mContext, UserInfo userInfo) {
        this.userInfo = userInfo;
        inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.windo_analysis_switch, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv_old_day = (TextView) view.findViewById(R.id.tv_old_day);
        TextView tv_old_moth = (TextView) view.findViewById(R.id.tv_old_moth);
        TextView tv_old_year = (TextView) view.findViewById(R.id.tv_old_year);

        // TODO: 2016/5/17 设置动画
//        popupWindow.setAnimationStyle(R.style.popup_window_anim);
        // TODO: 2016/5/17 设置背景颜色
        popupWindow.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.white)));
        // TODO: 2016/5/17 设置可以获取焦点
        popupWindow.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        popupWindow.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        popupWindow.update();
        popupWindow.showAsDropDown(textView, 0, 20);
        tv_old_day.setOnClickListener(this);
        tv_old_moth.setOnClickListener(this);
        tv_old_year.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Map<String, String> todayMap = new HashMap<>();
        if (mView != null && userInfo != null && popupWindow != null) {
            switch (v.getId()) {
                case R.id.tv_old_day:
                    mView.onAnaysis("与昨日对比");
                    todayMap.put("userId", userInfo.getUserId());
                    todayMap.put("projectId", userInfo.getLeftOrgId());
                    todayMap.put("flag", "1");
                    contrastData(todayMap);

                    break;
                case R.id.tv_old_moth:
                    mView.onAnaysis("与上月对比");
                    todayMap.put("userId", userInfo.getUserId());
                    todayMap.put("projectId", userInfo.getLeftOrgId());
                    todayMap.put("flag", "2");
                    contrastData(todayMap);
                    break;
                case R.id.tv_old_year:
                    mView.onAnaysis("与去年对比");
                    todayMap.put("userId", userInfo.getUserId());
                    todayMap.put("projectId", userInfo.getLeftOrgId());
                    todayMap.put("flag", "3");
                    contrastData(todayMap);
                    break;
            }
            popupWindow.dismiss();
        }
    }
}
