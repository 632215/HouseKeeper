package com.jinke.housekeeper.saas.patrol.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.saas.patrol.base.BaseFragment;
import com.jinke.housekeeper.saas.patrol.bean.TimeDataBean;
import com.jinke.housekeeper.saas.patrol.precenter.PatrolStatCalePresenter;
import com.jinke.housekeeper.saas.patrol.ui.widget.date_picker.CustomDatePicker;
import com.jinke.housekeeper.saas.patrol.ui.widget.sign_calender.DPCManager;
import com.jinke.housekeeper.saas.patrol.ui.widget.sign_calender.DatePicker2;
import com.jinke.housekeeper.saas.patrol.view.PatrolStatCaleView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * author : huominghao
 * date : 2018/2/26 0026
 * function :
 */

public class PatrolStatCaleFragment extends BaseFragment<PatrolStatCaleView, PatrolStatCalePresenter> implements PatrolStatCaleView {

    @Bind(R.id.date_picker)
    DatePicker2 datePicker;
    @Bind(R.id.date_picker_leak)
    TextView datePickerLeak;
    @Bind(R.id.date_picker_post)
    TextView datePickerPost;
    @Bind(R.id.date_picker_normal)
    TextView datePickerNormal;
    @Bind(R.id.date_picker_month)
    TextView datePickerMonth;
    @Bind(R.id.date_picker_year)
    TextView datePickerYear;

    private UserInfo userInfo;
    private List<TimeDataBean> dataBean;
    private CustomDatePicker customDatePicker;
    public String datePickerString;

    public PatrolStatCaleFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patrol_stat_cale;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initDatePicker();
        myCalendar();
    }

    @Override
    public PatrolStatCalePresenter initPresenter() {
        return new PatrolStatCalePresenter(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        datePickerString = datePickerYear.getText() + "-" + datePickerMonth.getText() + "-01";
        getNeaDate();

    }

    @OnClick({R.id.date_picker_layout})
    public void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.date_picker_layout:
                // 日期格式为yyyy-MM-dd
                customDatePicker.show(datePickerYear.getText() + "-" + datePickerMonth.getText() + "-01");
                break;
        }
    }

    private void myCalendar() {
        List<String> tmp = new ArrayList<>();
        tmp.add("2016-8-2");
        tmp.add("2016-8-4");
        tmp.add("2016-08-21");
        tmp.add("2016-08-09");
        List<Calendar> leakDate = new ArrayList<>();//漏检
        List<Calendar> postDate = new ArrayList<>();//报事
        DPCManager.getInstance(postDate, leakDate).setDecorBG(tmp);

        datePicker.setFestivalDisplay(false); //是否显示节日
        datePicker.setHolidayDisplay(false); //是否显示假期
        datePicker.setDeferredDisplay(false); //是否显示补休

        if (null != dataBean) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(2018, 2 - 1, 8);
            for (TimeDataBean date : dataBean) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(2018, 2 - 1, 8);
                if (date.isIfLou()) {
                    leakDate.add(calendar);
                } else {
                    if (date.isIfReport()) {
                        postDate.add(calendar);
                    }
                }

            }

            DPCManager.getInstance(postDate, leakDate).setDecorBG(tmp);
            datePickerLeak.setText(getString(R.string.date_picker_leak) + ":" + leakDate.size());
            datePickerPost.setText(getString(R.string.date_picker_post) + ":" + postDate.size());
            datePicker.setInfoDate(postDate, leakDate);
        }
        int mYear = Integer.parseInt(datePickerYear.getText().toString()); // 获取当前年份
        int mMonth = Integer.parseInt(datePickerMonth.getText().toString());// 获取当前月份
        datePicker.setDate(mYear, mMonth);
    }

    @Override
    public void timeData(List<TimeDataBean> dataBean) {
        this.dataBean = dataBean;
        myCalendar();
    }

    @Override
    public void onError(String msg) {

    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (1 == msg.what) {
                userInfo = (UserInfo) msg.obj;
                if (null == presenter) {
                    presenter = new PatrolStatCalePresenter(getActivity());
                }
                getNeaDate();
            }

        }
    };

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        datePickerMonth.setText(now.split("-")[1]);
        datePickerYear.setText(now.split("-")[0]);

        customDatePicker = new CustomDatePicker(getActivity(), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                datePickerMonth.setText(time.split("-")[1]);
                datePickerYear.setText(time.split("-")[0]);
                datePickerString = datePickerYear.getText() + "-" + datePickerMonth.getText() + "-01";
                getNeaDate();
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime("3"); // 不显示时和分 1显示时分 2显示日 3显示月
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }

    public void getNeaDate() {
        if (null != userInfo && null != presenter) {
            Map<String, String> map = new HashMap<>();
            map.put("userId", userInfo.getUserId());
            map.put("projectId", userInfo.getLeftOrgId());
            map.put("startTime", datePickerString);
            presenter.timeData(map);
        }
    }
}
