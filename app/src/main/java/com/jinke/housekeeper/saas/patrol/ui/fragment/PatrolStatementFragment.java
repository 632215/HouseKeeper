package com.jinke.housekeeper.saas.patrol.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.saas.patrol.base.BaseFragment;
import com.jinke.housekeeper.saas.patrol.bean.ContrastDataBean;
import com.jinke.housekeeper.saas.patrol.bean.DatePickerPoint;
import com.jinke.housekeeper.saas.patrol.bean.PointProjectDataBean;
import com.jinke.housekeeper.saas.patrol.precenter.PatrolStatementPresenter;
import com.jinke.housekeeper.saas.patrol.ui.adapter.PatrolStatementAdapter;
import com.jinke.housekeeper.saas.patrol.ui.widget.date_picker.CustomDatePicker;
import com.jinke.housekeeper.saas.patrol.view.PatrolStatementView;
import com.jinke.housekeeper.saas.report.ui.widget.CustomListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class PatrolStatementFragment extends BaseFragment<PatrolStatementView, PatrolStatementPresenter>
        implements PatrolStatementView {

    @Bind(R.id.date_picker_month)
    TextView datePickerMonth;
    @Bind(R.id.date_picker_year)
    TextView datePickerYear;
    @Bind(R.id.date_picker_layout)
    RelativeLayout datePickerLayout;
    @Bind(R.id.date_picker_month_n)
    TextView datePickerMonthN;
    @Bind(R.id.date_picker_year_n)
    TextView datePickerYearN;
    @Bind(R.id.date_picker_layout_2)
    RelativeLayout datePickerLayout2;
    @Bind(R.id.date_picker_hint_1)
    TextView datePickerHint1;
    @Bind(R.id.date_picker_hint_2)
    TextView datePickerHint2;
    @Bind(R.id.date_picker_map)
    BarChart datePickerMap;
    @Bind(R.id.date_picker_map_empty)
    TextView datePickerMapEmpty;
    @Bind(R.id.date_picker_map_list)
    CustomListView datePickerMapList;
    @Bind(R.id.date_picker_map_list_body)
    RelativeLayout datePickerMapListBody;
    @Bind(R.id.date_picker_map_company_name)
    TextView datePickerMapCompanyName;

    private int tag = 0;


    private PatrolStatementAdapter statementAdapter;
    private List<DatePickerPoint> firstList;
    private List<DatePickerPoint> secondList;
    private List<String> nameString;


    private UserInfo userInfo;
    private List<PointProjectDataBean> pointProjectDataBeans;
    private CustomDatePicker customDatePicker1;
    private CustomDatePicker customDatePicker2;

    public String datePickerString;
    public String datePickerNString;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patrol_statement;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        datePickerMapListBody.setVisibility(1 == tag ? View.VISIBLE : View.GONE);
        firstList = new ArrayList<>();
        secondList = new ArrayList<>();
        nameString = new ArrayList<>();
        initStandBook(firstList, secondList);
        pointProjectDataBeans = new ArrayList<>();
        statementAdapter = new PatrolStatementAdapter(getContext(), pointProjectDataBeans);
        datePickerMapList.setAdapter(statementAdapter);
        initDatePicker();
    }

    @Override
    public void onResume() {
        super.onResume();
        datePickerString = datePickerYear.getText() + "-" + datePickerMonth.getText() + "-01";
        datePickerNString = datePickerYearN.getText() + "-" + datePickerMonthN.getText() + "-01";
        getNetDate();
    }

    @Override
    public PatrolStatementPresenter initPresenter() {
        return new PatrolStatementPresenter(getActivity());
    }

    @OnClick({R.id.date_picker_layout, R.id.date_picker_layout_2})
    public void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.date_picker_layout:
                // 日期格式为yyyy-MM-dd
                customDatePicker1.show(datePickerYear.getText() + "-" + datePickerMonth.getText() + "-01");
                break;
            case R.id.date_picker_layout_2:
                // 日期格式为yyyy-MM-dd
                customDatePicker2.show(datePickerYearN.getText() + "-" + datePickerMonthN.getText() + "-01");
                break;
        }
    }

    private void initStandBook(List<DatePickerPoint> firstList, List<DatePickerPoint> secondList) {
        if (null == firstList || firstList.size() == 0) {
            datePickerMapEmpty.setVisibility(View.VISIBLE);
            datePickerMap.setVisibility(View.GONE);
        } else {
            datePickerMapEmpty.setVisibility(View.GONE);
            datePickerMap.setVisibility(View.VISIBLE);//设置是否可见
            datePickerMap.setBackgroundColor(getResources().getColor(R.color.equipment_bg_5));
            datePickerMap.setDrawGridBackground(false);
            datePickerMap.setDescription(null);// 数据描述
            datePickerMap.setDragEnabled(true);// 是否可以拖拽
            datePickerMap.setTouchEnabled(true);//设置是否可以触摸
            datePickerMap.setScaleEnabled(true);// 是否可以缩放
            datePickerMap.animateXY(1000, 2000);//设置动画
            datePickerMap.setScaleX(1);
            XAxis xAxis = datePickerMap.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置
            xAxis.setDrawGridLines(false);//不显示网格
            xAxis.setGranularity(0.2f);

            if (firstList.size() < 1) {
                xAxis.setLabelCount(2, false);
            } else {
                xAxis.setLabelCount(firstList.size() - 1, false);
            }
            xAxis.setDrawAxisLine(true);
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    String callBackString = "";
                    for (int i = 0; i < nameString.size(); i++) {
                        if (i + 1.0f == value) {
                            callBackString = nameString.get(i);
                        }
                    }
                    return callBackString;
                }
            });

            Legend legend = datePickerMap.getLegend();
            legend.setEnabled(false);

            YAxis leftAxis = datePickerMap.getAxisLeft();
            leftAxis.setLabelCount(5, false);
            leftAxis.setDrawAxisLine(true);
            leftAxis.setDrawGridLines(false);//横向线
            leftAxis.setAxisMinimum(0); // this replaces setStartAtZero(true)
            YAxis rightAxis = datePickerMap.getAxisRight();
            rightAxis.setEnabled(false);
            // set data
            ChartData<?> mChartData = generateDataBar(firstList, secondList);
            datePickerMap.setData((BarData) mChartData);
            // do not forget to refresh the chart
            datePickerMap.animateX(750);
            datePickerMap.invalidate();
        }

    }

    private BarData generateDataBar(List<DatePickerPoint> firstList, List<DatePickerPoint> secondList) {
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        for (int i = 0; i < firstList.size(); i++) {
            float[] vals;
            if (0 != secondList.size()) {
                vals = new float[]{firstList.get(i).getY(), secondList.get(i).getY()};
            } else {
                vals = new float[]{firstList.get(i).getY()};
            }
            entries.add(new BarEntry(firstList.get(i).getX(), vals));
        }
        final int[] POINT_COLORS = {getResources().getColor(R.color.equipment_bg_7), getResources().getColor(R.color.equipment_bg_8)};
        BarDataSet barDataSet = new BarDataSet(entries, null);
        barDataSet.setColors(POINT_COLORS);
        BarData cd = new BarData(barDataSet);
        cd.setBarWidth(0.4f);
        return cd;
    }

    @Override
    public void pointProjectData(List<PointProjectDataBean> dataBean) {
        //巡更报表数据
        pointProjectDataBeans.clear();
        pointProjectDataBeans = dataBean;
        nameString.clear();
        int i = 1;
        for (PointProjectDataBean bean : dataBean) {
            DatePickerPoint firstListPoint = new DatePickerPoint();
            firstListPoint.setX(i);
            firstListPoint.setY(Float.parseFloat(bean.getToDayLou()));
            firstList.add(firstListPoint);
            DatePickerPoint secondListPoint = new DatePickerPoint();
            secondListPoint.setX(i);
            secondListPoint.setY(Float.parseFloat(bean.getToDayLou()) + Float.parseFloat(bean.getToDayComplent()));
            secondList.add(secondListPoint);
            switch (bean.getType()) {
                case 1:
                    nameString.add(bean.getPointName());
                    datePickerMapListBody.setVisibility(View.GONE);
                    break;
                case 2:
                    nameString.add(bean.getPlanName());
                    datePickerMapListBody.setVisibility(View.VISIBLE);
                    datePickerMapCompanyName.setText("计划名称");
                    break;
                case 3:
                    nameString.add(bean.getProjectName());
                    datePickerMapListBody.setVisibility(View.VISIBLE);
                    datePickerMapCompanyName.setText("项目名称");
                    break;
            }
            i++;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (1 == tag) {
                    initStandBook(firstList, secondList);
                    statementAdapter.setRefreshData(pointProjectDataBeans);
                } else {
                    secondList.clear();
                    datePickerMapListBody.setVisibility(View.GONE);
                    initStandBook(firstList, secondList);
                    statementAdapter.setRefreshData(pointProjectDataBeans);
                }
            }
        });
    }

    @Override
    public void contrastData(List<ContrastDataBean> dataBean) {
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onAnaysis(String back) {

    }

    public Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (1 == msg.what) {
                userInfo = (UserInfo) msg.obj;
                if (null == presenter) {
                    presenter = new PatrolStatementPresenter(getActivity());
                }
                getNetDate();
            }
        }
    };

    public void setTag(int tag) {
        this.tag = tag;
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());

        datePickerMonth.setText(now.split("-")[1]);
        datePickerYear.setText(now.split("-")[0]);
        datePickerMonthN.setText(now.split("-")[1]);
        datePickerYearN.setText(now.split("-")[0]);
        datePickerString = datePickerYear.getText() + "-" + datePickerMonth.getText() + "-01";
        datePickerNString = datePickerYearN.getText() + "-" + datePickerMonthN.getText() + "-01";

        customDatePicker1 = new CustomDatePicker(getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                datePickerMonth.setText(time.split("-")[1]);
                datePickerYear.setText(time.split("-")[0]);

                datePickerString = datePickerYear.getText() + "-" + datePickerMonth.getText() + "-01";
                datePickerNString = datePickerYearN.getText() + "-" + datePickerMonthN.getText() + "-01";
                getNetDate();
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime("3"); // 不显示时和分 1显示时分 2显示日 3显示月
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                datePickerMonthN.setText(time.split("-")[1]);
                datePickerYearN.setText(time.split("-")[0]);
                datePickerString = datePickerYear.getText() + "-" + datePickerMonth.getText() + "-01";
                datePickerNString = datePickerYearN.getText() + "-" + datePickerMonthN.getText() + "-01";
                getNetDate();
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime("3"); // 不显示时和分 1显示时分 2显示日 3显示月
        customDatePicker2.setIsLoop(false); // 不允许循环滚动
    }

    private void getNetDate() {
        if (null != userInfo && null != presenter) {
            Map<String, String> map = new HashMap<>();
            map.put("userId", userInfo.getUserId());
            map.put("projectId", userInfo.getLeftOrgId());
            map.put("startTime", datePickerString);
            map.put("endTime", datePickerNString);
            presenter.pointProjectData(map);
        }
    }
}