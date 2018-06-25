package com.jinke.housekeeper.saas.report.ui.widget;


import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.http.HttpResult;
import com.jinke.housekeeper.saas.report.http.progress.ProgressSubscriber;
import com.jinke.housekeeper.saas.report.http.progress.SubscriberOnNextListener;
import com.jinke.housekeeper.saas.report.ui.adapter.SelecterPuItemAdatper;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.saas.report.bean.MyWindowsInfo;
import com.jinke.housekeeper.bean.RegisterDepartmentBean;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.utils.CommonlyUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by pc on 2017/2/21.
 */

public class MyPopWindows extends PopupWindow implements View.OnClickListener {

    private Context mContext;
    private String isAll = "0";

    public MyPopWindows(Context context) {
        super(context);
        this.mContext = context;
        initBasePopupWindow();
    }


    public void setIsAll(String isAll) {
        this.isAll = isAll;
    }


    /**
     * 初始化BasePopupWindow的一些信息
     */
    private void initBasePopupWindow() {
        setAnimationStyle(android.R.style.Animation_Dialog);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private EditText sponsor, rectificationMan;
    private TextView startTime, endTime, txReset, complish, timeRange;
    private MyGridView keyPointList, category, state;
    private SelecterPuItemAdatper keyPointAdapter, categoryAdapter, stateAdatper;
    private LinearLayout peopleLayout, stateLayout;//整改人，完成状态

    @Override
    public void setContentView(View contentView) {
        super.setContentView(contentView);
        if (contentView != null) {
//            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            initView(contentView);
            addKeyListener(contentView);
        }
    }

    private void initTime() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 0);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        startTime.setText(dateString);
        endTime.setText(dateString);
    }

    Button rootView;

    /**
     * 控件初始化
     *
     * @param contentView
     */
    public void initView(final View contentView) {
        sponsor = (EditText) contentView.findViewById(R.id.sponsor);
        rectificationMan = (EditText) contentView.findViewById(R.id.rectificationMan);
        timeRange = (TextView) contentView.findViewById(R.id.timeRange);
        startTime = (TextView) contentView.findViewById(R.id.pu_startTime);
        endTime = (TextView) contentView.findViewById(R.id.pu_endTime);
        keyPointList = (MyGridView) contentView.findViewById(R.id.keyPointList);
        category = (MyGridView) contentView.findViewById(R.id.category);
        state = (MyGridView) contentView.findViewById(R.id.state);
        txReset = (TextView) contentView.findViewById(R.id.tx_Reset);
        complish = (TextView) contentView.findViewById(R.id.complish);
        peopleLayout = (LinearLayout) contentView.findViewById(R.id.peopleLayout);
        stateLayout = (LinearLayout) contentView.findViewById(R.id.stateLayout);
        rootView = (Button) contentView.findViewById(R.id.rootView);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        complish.setOnClickListener(this);
        txReset.setOnClickListener(this);
        keyPointAdapter = new SelecterPuItemAdatper(mContext, keyPoint);
        keyPointList.setAdapter(keyPointAdapter);
        categoryAdapter = new SelecterPuItemAdatper(mContext, categorylist);
        category.setAdapter(categoryAdapter);
        stateAdatper = new SelecterPuItemAdatper(mContext, statelist);
        state.setAdapter(stateAdatper);

        timeRange.setText(isAll.equals("0") ? "时间范围" : "自检时段");
        peopleLayout.setVisibility(isAll.equals("0") ? View.GONE : View.VISIBLE);
        stateLayout.setVisibility(isAll.equals("0") ? View.GONE : View.VISIBLE);
        initTime();
        initStateData();
        getCJContentData();
        getGJContentData1();
        initItem();
    }

    private void initItem() {
        category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (RegisterDepartmentBean.ListObjBean bean : categorylist) {
                    bean.setSelecter(false);
                }
                categorylist.get(position).setSelecter(true);
                categoryAdapter.setData(categorylist);
            }
        });

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        keyPointList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (RegisterDepartmentBean.ListObjBean bean : keyPoint) {
                    bean.setSelecter(false);
                }
                keyPoint.get(position).setSelecter(true);
                keyPointAdapter.setData(keyPoint);
            }
        });

        state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (RegisterDepartmentBean.ListObjBean bean : statelist) {
                    bean.setSelecter(false);
                }
                statelist.get(position).setSelecter(true);
                stateAdatper.setData(statelist);
            }
        });
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }


    /**
     * 为窗体添加outside点击事件
     */
    private void addKeyListener(View contentView) {
        if (contentView != null) {
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);
            setBackgroundDrawable(new BitmapDrawable());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pu_startTime:
                selecterTime(startTime);
                break;

            case R.id.pu_endTime:
                selecterTime(endTime);
                break;

            case R.id.tx_Reset:
                initTime();
                getCJContentData();
                getGJContentData1();
                initStateData();
                initItem();
                sponsor.setText("");
                rectificationMan.setText("");
                break;

            case R.id.complish:
                MyWindowsInfo info = new MyWindowsInfo();
                info.setStartTime(startTime.getText().toString().trim());
                info.setEndTime(endTime.getText().toString().trim());
                info.setSponsor(sponsor.getText().toString().trim());
                info.setRectificationMan(rectificationMan.getText().toString().trim());

                //遍历所属类别
                for (RegisterDepartmentBean.ListObjBean bean : categorylist) {
                    if (bean.isSelecter()) {
                        info.setCategoryId(bean.getId());
                        info.setCategoryName(bean.getName());


                    }
                }

                //遍历关键点位
                for (RegisterDepartmentBean.ListObjBean bean : keyPoint) {
                    if (bean.isSelecter()) {
                        info.setKeyPointId(bean.getId());
                        info.setKeyPointName(bean.getName());
                    }
                }
                //遍历完成状态
                for (RegisterDepartmentBean.ListObjBean bean : statelist) {
                    if (bean.isSelecter()) {
                        info.setState(bean.getName());
                    }
                }
                EventBus.getDefault().post(info);
                dismiss();
                break;
        }
    }

    private int mYear;
    private int mMonth;
    private int mDay;
    private String time;

    public void selecterTime(final TextView txTime) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                mYear = year;
                mMonth = month;
                mDay = day;
//                int m = (mMonth + 1) < 10 ? 0 + (mMonth + 1) : (mMonth + 1);
//                int d = (mDay < 10) ? 0 + mDay : mDay;
                String m = (mMonth + 1) < 10 ? "0" + String.valueOf((mMonth + 1)) : String.valueOf((mMonth) + 1);
                String d = (mDay < 10) ? "0" + String.valueOf(mDay) : String.valueOf(mDay);
                time = mYear + "-" + m + "-" + d;
                txTime.setText(time);

                //根据时间查询分公司数据
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private List<RegisterDepartmentBean.ListObjBean> keyPoint = new ArrayList<>();
    private List<RegisterDepartmentBean.ListObjBean> categorylist = new ArrayList<>();
    private List<RegisterDepartmentBean.ListObjBean> statelist = new ArrayList<>();

    /**
     * 选择完成状态
     */
    private void initStateData() {
        statelist = new ArrayList<>();
        RegisterDepartmentBean.ListObjBean overBean = new RegisterDepartmentBean.ListObjBean();
        RegisterDepartmentBean.ListObjBean delayBean = new RegisterDepartmentBean.ListObjBean();
        RegisterDepartmentBean.ListObjBean finishBean = new RegisterDepartmentBean.ListObjBean();
        overBean.setName("已超时");
        delayBean.setName("已延时");
        finishBean.setName("已完成");
        statelist.add(delayBean);
        statelist.add(overBean);
        statelist.add(finishBean);
        stateAdatper.setData(statelist);
    }

    /**
     * 选择所属类别
     */
    public void getCJContentData() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<RegisterDepartmentBean>() {
            @Override
            public void onNext(RegisterDepartmentBean info) {
                categorylist.clear();
                if (info != null) {
                    categorylist = info.getListObj();
                    categoryAdapter.setData(categorylist);

                }
            }

            @Override
            public void onError(String Code, String Msg) {
                ToastUtils.showLong(mContext, Msg);
                CommonlyUtils.errorState(mContext, Code);
            }
        };

        SortedMap<String, String> map = new TreeMap<>();
        HttpMethods.getInstance().getCJContentData(new ProgressSubscriber<HttpResult<RegisterDepartmentBean>>(onNextListener, mContext, false), map);
    }

    /**
     * 选择关键点位
     */
    public void getGJContentData1() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<RegisterDepartmentBean>() {
            @Override
            public void onNext(RegisterDepartmentBean info) {
                keyPoint.clear();
                if (info != null) {
                    keyPoint = info.getListObj();
                    keyPointAdapter.setData(keyPoint);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                ToastUtils.showLong(mContext, Msg);
                CommonlyUtils.errorState(mContext, Code);
            }
        };

        SortedMap<String, String> map = new TreeMap<>();
        map.put("pageInfo", CommonlyUtils.pageInfo(1));
        map.put("parentId", "");
        map.put("key", "");
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", SharedPreferencesUtils.init(mContext).getString("quality_sessionId"));
        HttpMethods.getInstance().getGJContentData(new ProgressSubscriber<HttpResult<RegisterDepartmentBean>>(onNextListener, mContext, false), map, ReportConfig.createSign(map));
    }
}
