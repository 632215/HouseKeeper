package com.jinke.housekeeper.saas.patrol.ui.widget.sign_calender;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 *  原版DatePicker
 */
public class DatePicker2 extends LinearLayout {
    private DPTManager mTManager;// 主题管理器
    private DPLManager mLManager;// 语言管理器

    private MonthView monthView;// 月视图
    private List<Calendar> postDate;
    private List<Calendar> leakDate;

    private static final int ID_tvMonth = 1;



    public DatePicker2(Context context) {
        this(context, null);
    }

    public void setInfoDate(List<Calendar> postDate, List<Calendar> leakDate){
        this.postDate = postDate;
        this.leakDate = leakDate;
    }

    public DatePicker2(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mTManager = DPTManager.getInstance();
        mLManager = DPLManager.getInstance();

        // 设置排列方向为竖向
        setOrientation(VERTICAL);

        LayoutParams llParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);


        // 周视图根布局
        LinearLayout llWeek = new LinearLayout(context);
        llWeek.setBackgroundColor(Color.parseColor("#E6F3FD"));
        llWeek.setOrientation(HORIZONTAL);
        int llWeekPadding = MeasureUtil.dp2px(context, 5);
        llWeek.setPadding(0, llWeekPadding, 0, llWeekPadding);
        LayoutParams lpWeek = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpWeek.weight = 1;

        //年
        RelativeLayout.LayoutParams lpYear =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpYear.addRule(RelativeLayout.CENTER_VERTICAL);
        lpYear.addRule(RelativeLayout.RIGHT_OF,ID_tvMonth);

        lpYear.setMargins(40,0,0,0);
        //月
        RelativeLayout.LayoutParams lpMonth =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpMonth.addRule(RelativeLayout.CENTER_IN_PARENT);
        // --------------------------------------------------------------------------------周视图
        for (int i = 0; i < mLManager.titleWeek().length; i++) {
            TextView tvWeek = new TextView(context);
            tvWeek.setText(mLManager.titleWeek()[i]);
            tvWeek.setGravity(Gravity.CENTER);
            tvWeek.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tvWeek.setTextColor(Color.parseColor("#1D1D26"));
            llWeek.addView(tvWeek, lpWeek);
        }
        addView(llWeek, llParams);

        // ------------------------------------------------------------------------------------月视图
        monthView = new MonthView(context,postDate,leakDate);
        monthView.setOnDateChangeListener(new MonthView.OnDateChangeListener() {
            @Override
            public void onMonthChange(int month) {
            }

            @Override
            public void onYearChange(int year) {
            }

            @Override
            public void onAllChange(int year, int month) {

            }
        });
        monthView.setOnDateScrollChangeListener(new MonthView.OnDateScrollChangeListener() {
            @Override
            public void scrollLeft(int year, int month) {
            }

            @Override
            public void scrollRight(int year, int month) {
            }

            @Override
            public void scrollTop(int year, int month) {
            }

            @Override
            public void scrollBottom(int year, int month) {
            }
        });
        addView(monthView, llParams);
    }

    /**
     * 设置初始化年月日期
     *
     * @param year  ...
     * @param month ...
     */
    public void setDate(int year, int month) {
        if (month < 1) {
            month = 1;
        }
        if (month > 12) {
            month = 12;
        }
        monthView.setDate(year, month);
    }

    public void setDPDecor(DPDecor decor) {
        monthView.setDPDecor(decor);
    }

    /**
     * 设置日期选择模式
     *
     * @param mode ...
     */
    public void setMode(DPMode mode) {
//        if (mode != DPMode.MULTIPLE) {
//            tvEnsure.setVisibility(GONE);
//        }
        monthView.setDPMode(mode);
    }

    /**
     * 节日标识
     * @param isFestivalDisplay
     */
    public void setFestivalDisplay(boolean isFestivalDisplay) {
        monthView.setFestivalDisplay(isFestivalDisplay);
    }

    /**
     * 今天标识
     * @param isTodayDisplay
     */
    public void setTodayDisplay(boolean isTodayDisplay) {
        monthView.setTodayDisplay(isTodayDisplay);
    }

    /**
     * 假期标识
     * @param isHolidayDisplay
     */
    public void setHolidayDisplay(boolean isHolidayDisplay) {
        monthView.setHolidayDisplay(isHolidayDisplay);
    }

    /**
     * 补休标识
     * @param isDeferredDisplay
     */
    public void setDeferredDisplay(boolean isDeferredDisplay) {
        monthView.setDeferredDisplay(isDeferredDisplay);
    }

}
