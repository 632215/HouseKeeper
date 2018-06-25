package com.jinke.housekeeper.saas.equipment.ui.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseActivity;
import com.jinke.housekeeper.saas.equipment.bean.ElectricDataBean;
import com.jinke.housekeeper.saas.equipment.bean.ElectricMonthData;
import com.jinke.housekeeper.saas.equipment.bean.PointBean;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.precenter.StatisticsePresenter;
import com.jinke.housekeeper.saas.equipment.view.StatisticseView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class StatisticsActivity extends BaseActivity<StatisticseView, StatisticsePresenter> implements StatisticseView {


    @Bind(R.id.stand_book_object)
    TextView standBookObject;
    @Bind(R.id.stand_book_object_day_f)
    LineChart standBookObjectDayF;
    @Bind(R.id.stand_book_object_day_t)
    LineChart standBookObjectDayT;
    @Bind(R.id.stand_book_month_1)
    BarChart standBookMonth1;
    @Bind(R.id.stand_book_month_2)
    BarChart standBookMonth2;
    @Bind(R.id.stand_book_object_day)
    RelativeLayout standBookObjectDay;
    @Bind(R.id.stand_book_object_month)
    RelativeLayout standBookObjectMonth;
    private Map<String, String> map;

    @Override
    public StatisticsePresenter initPresenter() {
        return new StatisticsePresenter(StatisticsActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_statistics;
    }

    @Override
    protected void initView() {
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.equipment_back_ico);
        initDate();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    private void initDate() {
        if (null != getIntent().getStringExtra("tag")) {
            standBookObject.setText(EquipmentConfig.getProjectName());
            if ("1".equals(getIntent().getStringExtra("tag"))) {
                setTitle(getString(R.string.statistics_day));
                map = new HashMap<>();
                presenter.getElectricData(map);
            } else {
                setTitle(getString(R.string.statistics_month));
                map = new HashMap<>();
                presenter.getElectricMonthData(map);
            }
        } else {
        }
    }

    @Override
    public void getElectricDataSuccess(ElectricDataBean electricDataBean) {
        standBookObjectDay.setVisibility(View.VISIBLE);
        int MaxE;
        if (electricDataBean.getMaxSE() < 1000 && electricDataBean.getMaxE() < 1000) {
            MaxE = 1000;
        } else {
            if (electricDataBean.getMaxSE() >= electricDataBean.getMaxE()) {
                MaxE = electricDataBean.getMaxSE();
            } else {
                MaxE = electricDataBean.getMaxE();
            }
        }
        initLine(standBookObjectDayF, MaxE, electricDataBean.getElectricList(), electricDataBean.getElectricSList());

        int MaxW;
        if (electricDataBean.getMaxSW() < 1000 && electricDataBean.getMaxW() < 1000) {
            MaxW = 1000;
        } else {
            if (electricDataBean.getMaxSW() >= electricDataBean.getMaxW()) {
                MaxW = electricDataBean.getMaxSW();
            } else {
                MaxW = electricDataBean.getMaxW();
            }
        }
        initLine(standBookObjectDayT, MaxW, electricDataBean.getWaterList(), electricDataBean.getWaterSList());
    }


    @Override
    public void getElectricMonthDataSuccess(ElectricMonthData electricMonthData) {
        standBookObjectMonth.setVisibility(View.VISIBLE);
        initStandBook(standBookMonth1, 0, electricMonthData.getElectricList(), electricMonthData.getElectricSList());
        initStandBook(standBookMonth2, 0, electricMonthData.getWaterList(), electricMonthData.getWaterSList());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(String msg) {

    }

    private void initStandBook(BarChart barChart, float i, List<PointBean> nowList, List<PointBean> beforeList) {
        barChart.setBackgroundColor(getResources().getColor(R.color.equipment_bg_5));
        barChart.setDrawGridBackground(false);
        barChart.setDescription(null);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(0.2f);
        xAxis.setDrawAxisLine(true);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(false);//横向线
        leftAxis.setAxisMinimum(0); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);

        // set data
        ChartData<?> mChartData = generateDataBar( nowList, beforeList);
        barChart.setData((BarData) mChartData);

        // do not forget to refresh the chart
        barChart.animateX(750);
        barChart.invalidate();
    }

    private void initLine(LineChart lineChart, float i, List electricList, List electricSList) {
        lineChart.setBackgroundColor(getResources().getColor(R.color.equipment_bg_5));
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(null);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setLabelCount(5, true);
        leftAxis.setAxisMinimum(0);
        leftAxis.setDrawGridLines(false);//横向线
        leftAxis.setDrawAxisLine(true);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        // set data
        ChartData<?> mChartData = generateDataLine(electricList, electricSList);
        lineChart.setData((LineData) mChartData);

        // do not forget to refresh the chart
        lineChart.animateX(750);
        lineChart.invalidate();
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateDataLine(List<PointBean> nowList, List<PointBean> beforeList) {
        ArrayList<Entry> entries1 = new ArrayList<>();
        int max = nowList.size() > beforeList.size() ? nowList.size() : beforeList.size();
        for (int i = 0; i < max; i++) {
            if (i < nowList.size()) {
                entries1.add(new Entry(nowList.get(i).getX(), nowList.get(i).getY()));
            }else {
                entries1.add(new Entry(i + 1, 0));
            }
        }

        LineDataSet lineDataSet1 = new LineDataSet(entries1, null);
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(getResources().getColor(R.color.equipment_bg_3));
        lineDataSet1.setDrawValues(true); // 是否在点上绘制Value
        lineDataSet1.setValueTextColor(getResources().getColor(R.color.equipment_bg_3));
        lineDataSet1.setValueTextSize(8f);

        ArrayList<Entry> entries2 = new ArrayList<Entry>();
        for (int i = 0; i < max; i++) {
            if (i < beforeList.size()) {
                entries2.add(new Entry(beforeList.get(i).getX(), beforeList.get(i).getY()));
            }else {
                entries2.add(new Entry(i + 1, 0));
            }
        }

        LineDataSet lineDataSet2 = new LineDataSet(entries2, null);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(getResources().getColor(R.color.equipment_bg_6));
        lineDataSet2.setDrawValues(true); // 是否在点上绘制Value
        lineDataSet2.setValueTextColor(getResources().getColor(R.color.equipment_bg_6));
        lineDataSet2.setValueTextSize(8f);

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
        sets.add(lineDataSet1);
        sets.add(lineDataSet2);

        LineData cd = new LineData(sets);
        return cd;
    }

    private BarData generateDataBar(List<PointBean> nowList, List<PointBean> beforeList) {
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        for (int i = 0; i < nowList.size(); i++) {
            entries.add(new BarEntry(nowList.get(i).getX() - 0.2f, nowList.get(i).getY()));
            entries.add(new BarEntry(beforeList.get(i).getX() + 0.2f, beforeList.get(i).getY()));
        }
        final int[] POINT_COLORS = {getResources().getColor(R.color.equipment_bg_3), getResources().getColor(R.color.equipment_bg_6)};
        BarDataSet d = new BarDataSet(entries, null);
        d.setColors(POINT_COLORS);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.4f);
        return cd;
    }

}
