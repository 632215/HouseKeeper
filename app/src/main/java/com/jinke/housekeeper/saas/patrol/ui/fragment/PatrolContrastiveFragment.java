package com.jinke.housekeeper.saas.patrol.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.saas.patrol.base.BaseFragment;
import com.jinke.housekeeper.saas.patrol.bean.ContrastDataBean;
import com.jinke.housekeeper.saas.patrol.bean.PointProjectDataBean;
import com.jinke.housekeeper.saas.patrol.precenter.PatrolStatementPresenter;
import com.jinke.housekeeper.saas.patrol.view.PatrolStatementView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class PatrolContrastiveFragment extends BaseFragment<PatrolStatementView, PatrolStatementPresenter>
        implements PatrolStatementView {

    @Bind(R.id.date_picker_day)
    LineChart datePickerDay;
    @Bind(R.id.date_picker_month)
    LineChart datePickerMonth;
    @Bind(R.id.date_picker_year)
    LineChart datePickerYear;
    @Bind(R.id.date_picker_day_empty)
    TextView datePickerDayEmpty;
    @Bind(R.id.date_picker_month_empty)
    TextView datePickerMonthEmpty;
    @Bind(R.id.date_picker_year_empty)
    TextView datePickerYearEmpty;
    @Bind(R.id.tv_analysis_switch)
    TextView tv_analysis_switch;

    private List<String> todayNameString = new ArrayList<>();
    private List<String> monthNameString = new ArrayList<>();
    private List<String> yearNameString = new ArrayList<>();
    private UserInfo userInfo;
    private List<ContrastDataBean> todayDataBean = new ArrayList<>();
    private List<ContrastDataBean> monthDataBean = new ArrayList<>();
    private List<ContrastDataBean> yearDataBean = new ArrayList<>();

    public PatrolContrastiveFragment() {
        // Required empty public constructor

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patrol_contrastive;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {
        super.onResume();
        getNetDate();
    }

    @Override
    public PatrolStatementPresenter initPresenter() {
        return new PatrolStatementPresenter(getActivity());
    }

    @Override
    public void pointProjectData(List<PointProjectDataBean> dataBean) {

    }

    @OnClick({R.id.tv_analysis_switch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_analysis_switch:
                presenter.showWindow(tv_analysis_switch, getActivity(), userInfo);
                break;
        }
    }

    @Override
    public void contrastData(List<ContrastDataBean> dataBean) {
        todayDataBean.clear();
        todayNameString.clear();
        if (null != dataBean && 0 != dataBean.size()) {

//            switch (dataBean.get(0).getFlag()) {
//                case "1":
//                    if (null != todayDataBean) {
//                        todayDataBean.clear();
//                    } else {
//                        todayDataBean = new ArrayList<>();
//                    }
//                    if (null != todayNameString) {
//                        todayNameString.clear();
//                    } else {
//                        todayNameString = new ArrayList<>();
//                    }
//                    break;
//
//                case "2":
//                    if (null != monthDataBean) {
//                        monthDataBean.clear();
//                    } else {
//                        monthDataBean = new ArrayList<>();
//                    }
//                    if (null != monthNameString) {
//                        monthNameString.clear();
//                    } else {
//                        monthNameString = new ArrayList<>();
//                    }
//                    break;
//                case "3":
//                    if (null != yearDataBean) {
//                        yearDataBean.clear();
//                    } else {
//                        yearDataBean = new ArrayList<>();
//                    }
//                    if (null != yearNameString) {
//                        yearNameString.clear();
//                    } else {
//                        yearNameString = new ArrayList<>();
//                    }
//                    break;
//            }

            for (ContrastDataBean contrastDataBean : dataBean) {
                todayNameString.add(contrastDataBean.getPlanName());
                todayDataBean.add(contrastDataBean);
            }
        }
//        for (ContrastDataBean contrastDataBean : dataBean) {
//            switch (contrastDataBean.getFlag()) {
//                case "1":
//                    todayNameString.add(contrastDataBean.getPlanName());
//                    todayDataBean.add(contrastDataBean);
//                    break;
//                case "2":
//                    monthNameString.add(contrastDataBean.getPlanName());
//                    monthDataBean.add(contrastDataBean);
//                    break;
//                case "3":
//                    yearNameString.add(contrastDataBean.getPlanName());
//                    yearDataBean.add(contrastDataBean);
//                    break;
//            }
//        }
        if (0 != todayDataBean.size()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    datePickerDayEmpty.setVisibility(View.GONE);
                    datePickerDay.setVisibility(View.VISIBLE);
                    initStandBook(todayDataBean, datePickerDay, todayNameString);
                }
            });
        }
//        if (0 != monthDataBean.size()) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    datePickerMonthEmpty.setVisibility(View.GONE);
//                    datePickerMonth.setVisibility(View.VISIBLE);
//                    initStandBook(monthDataBean, datePickerMonth, monthNameString);
//                }
//            });
//        }
//        if (0 != yearDataBean.size()) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    datePickerYearEmpty.setVisibility(View.GONE);
//                    datePickerYear.setVisibility(View.VISIBLE);
//                    initStandBook(yearDataBean, datePickerYear, yearNameString);
//                }
//            });
//        }

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onAnaysis(String back) {
        tv_analysis_switch.setText(back);
    }

    private void initStandBook(List<ContrastDataBean> firstList, LineChart lineChart, final List<String> nameString) {
        lineChart.setBackgroundColor(getResources().getColor(R.color.equipment_bg_5));
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(null);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
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

        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(false);//横向线
        leftAxis.setAxisMinimum(0); // this replaces setStartAtZero(true)

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        // set data
        ChartData<?> mChartData = generateDataLine(firstList);
        lineChart.setData((LineData) mChartData);

        // do not forget to refresh the chart
        lineChart.animateX(750);
        lineChart.invalidate();
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

    private void getNetDate() {
        if (null != userInfo && null != presenter) {

            Map<String, String> todayMap = new HashMap<>();
            todayMap.put("userId", userInfo.getUserId());
            todayMap.put("projectId", userInfo.getLeftOrgId());
            todayMap.put("flag", "1");
            presenter.contrastData(todayMap);

//            Map<String, String> monthMap = new HashMap<>();
//            monthMap.put("userId", userInfo.getUserId());
//            monthMap.put("projectId", userInfo.getLeftOrgId());
//            monthMap.put("flag", "2");
//            presenter.contrastData(monthMap);
//
//            Map<String, String> yearMap = new HashMap<>();
//            yearMap.put("userId", userInfo.getUserId());
//            yearMap.put("projectId", userInfo.getLeftOrgId());
//            yearMap.put("flag", "3");
//            presenter.contrastData(yearMap);
        }
    }

    private LineData generateDataLine(List<ContrastDataBean> firstList) {
        ArrayList<Entry> entries1 = new ArrayList<>();
        for (int i = 0; i < firstList.size(); i++) {
            entries1.add(new Entry(i + 1, Float.parseFloat(firstList.get(i).getToDayLou())));
        }

        LineDataSet lineDataSet1 = new LineDataSet(entries1, null);
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(getResources().getColor(R.color.equipment_bg_7));
        lineDataSet1.setDrawValues(true); // 是否在点上绘制Value
        lineDataSet1.setValueTextColor(getResources().getColor(R.color.equipment_bg_7));
        lineDataSet1.setValueTextSize(8f);

        ArrayList<Entry> entries2 = new ArrayList<Entry>();
        for (int i = 0; i < firstList.size(); i++) {
            entries2.add(new Entry(i + 1, Float.parseFloat(firstList.get(i).getYesterdayLou())));
        }
        LineDataSet lineDataSet2 = new LineDataSet(entries2, null);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(getResources().getColor(R.color.equipment_bg_8));
        lineDataSet2.setDrawValues(true); // 是否在点上绘制Value
        lineDataSet2.setValueTextColor(getResources().getColor(R.color.equipment_bg_8));
        lineDataSet2.setValueTextSize(8f);

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
        sets.add(lineDataSet1);
        sets.add(lineDataSet2);

        LineData cd = new LineData(sets);
        return cd;
    }

}
