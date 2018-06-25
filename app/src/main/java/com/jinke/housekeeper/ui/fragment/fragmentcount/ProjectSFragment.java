package com.jinke.housekeeper.ui.fragment.fragmentcount;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.bean.ChangTotalInfo;
import com.jinke.housekeeper.bean.PointInfo;
import com.jinke.housekeeper.bean.StatisticsInfo;
import com.jinke.housekeeper.bean.StatisticstInfo;
import com.jinke.housekeeper.presenter.ProjectSFragmentPresenter;
import com.jinke.housekeeper.adapter.PersonSAdapter;
import com.jinke.housekeeper.utils.TencentCountUtil;
import com.jinke.housekeeper.view.ProjectSFragmentView;
import com.jinke.housekeeper.ui.activity.fragmentcount.DepartmentSelectActivity;
import com.jinke.housekeeper.ui.activity.fragmentcount.ProjectSelectionActivity;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by root on 17-3-14.
 */
public class ProjectSFragment extends BaseFragmentV4<ProjectSFragmentView, ProjectSFragmentPresenter> implements LoadingLayout.OnReloadListener, ProjectSFragmentView {
    @Bind(R.id.tx_bluerectangle)
    TextView txBluerectangle;
    @Bind(R.id.rec_person)
    RecyclerView recPerson;
    @Bind(R.id.tx_xuancharectangle)
    TextView txXuancharectangle;
    @Bind(R.id.tx_xunchaCompay)
    TextView txXunchaCompay;
    @Bind(R.id.rec_xuancha)
    RecyclerView recXuancha;
    @Bind(R.id.tx_Inspectionrectangle)
    TextView txInspectionrectangle;
    @Bind(R.id.tx_InspectionCompay)
    TextView txInspectionCompay;
    @Bind(R.id.rec_Inspection)
    RecyclerView recInspection;
    PersonSAdapter inspectAdapter, inspectAdapter1, statistics;
    UserInfo userInfo;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    private String parentId = "";
    private String flag = "", query = "", type = "";
    private String sceneId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        userInfo = CommonlyUtils.getUserInfo(getActivity());
        flag = userInfo.getRoleType();
        initData();
        loadLayout.setStatus(isConnected ? LoadingLayout.Loading : LoadingLayout.No_Network);
        loadLayout.setOnReloadListener(this);
        setRecyclerViewManager(recInspection);
        setRecyclerViewManager(recPerson);
        setRecyclerViewManager(recXuancha);
        //修改页面
        updataView();
        getChangTotal(query, type);
        getStatistics(sceneId);
        getStatisticst(sceneId);
    }

    @Override
    public ProjectSFragmentPresenter initPresenter() {
        return new ProjectSFragmentPresenter(getActivity());
    }

    /**
     * 设置recycview mangger
     *
     * @param recyclerView
     */
    public void setRecyclerViewManager(RecyclerView recyclerView) {
        LinearLayoutManager liearManager = new LinearLayoutManager(getActivity());
        liearManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(liearManager);
    }


    private void initData() {
        switch (flag) {
            case "3":
                parentId = userInfo.getOrgCodel();
                break;
            case "2":
            case "1":
                parentId = userInfo.getOrgCodel();
                break;
        }
    }

    private void updataView() {
        switch (flag) {
            case "1":
                getTime();
                Drawable drawableLeft = getResources().getDrawable(R.drawable.clock);
                drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                Drawable drawableRight = getResources().getDrawable(R.drawable.returnqq);
                drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
                txBluerectangle.setCompoundDrawables(drawableLeft, null, drawableRight, null);
                txBluerectangle.setText(startTimeShow);
                break;
            case "2":
                txBluerectangle.setText(CommonlyUtils.inspectionStatisticsByMonth == null ? userInfo.getLeftOrgName() : CommonlyUtils.inspectionStatisticsByMonth.getName());
                break;
            case "3":
                txBluerectangle.setText(CommonlyUtils.inspectionStatisticsByMonth == null ? userInfo.getLeftOrgName() : CommonlyUtils.inspectionStatisticsByMonth.getName());
                setArrow(txBluerectangle);
                setArrow(txXunchaCompay);
                setArrow(txInspectionCompay);
                break;
        }
        txXunchaCompay.setText(CommonlyUtils.inspectProblemByMonth == null ? userInfo.getLeftOrgName() : CommonlyUtils.inspectProblemByMonth.getName());//第二个列表
        txInspectionCompay.setText(CommonlyUtils.inspectProblemByPoint == null ? userInfo.getLeftOrgName() : CommonlyUtils.inspectProblemByPoint.getName());//第三个列表
        getTime();
        query = flag.equals("1") ? startTime : (CommonlyUtils.inspectionStatisticsByMonth == null ? userInfo.getLeftOrgId() : CommonlyUtils.inspectionStatisticsByMonth.getId());
        type = flag.equals("1") ? "1" : "2";
    }

    /**
     * 设置三角形
     *
     * @param text
     */
    private void setArrow(TextView text) {
        Drawable drawable = getResources().getDrawable(R.drawable.location);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        text.setCompoundDrawables(drawable, null, null, null);
    }

    private void getTime() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 0);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String dateString = formatter.format(date);
        startTime = dateString;
        SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
        String yearString = formatterYear.format(date);
        SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");
        String monthString = formatterMonth.format(date);
        startTimeShow = yearString + "年" + monthString + "月";
    }

    @OnClick({R.id.tx_bluerectangle, R.id.tx_xuancharectangle, R.id.tx_xunchaCompay, R.id.tx_Inspectionrectangle, R.id.tx_InspectionCompay})
    public void onClick(TextView v) {
        Intent intent = null;
        isfrist = false;
        isTwo = false;
        isThreed = false;
        switch (v.getId()) {
            //各分公司
            case R.id.tx_bluerectangle:
                TencentCountUtil.count(getActivity(), "tx_bluerectangle");
                isfrist = true;
                switch (flag) {
                    case "1"://总部
                        selecterTime();
                        break;
                    case "2"://分公司
                        selectProject(intent);
                        break;
                    case "3"://项目人员
                        break;
                }
                break;
            //类别
            case R.id.tx_xuancharectangle:
                TencentCountUtil.count(getActivity(), "tx_xuancharectangle");
                isTwo = true;
                intent = new Intent(getActivity(), DepartmentSelectActivity.class);
                startActivity(intent);
                break;
            case R.id.tx_xunchaCompay:
                TencentCountUtil.count(getActivity(), "tx_xunchaCompay");
                isTwo = true;
                if (flag.equals("3")) {
                } else {
                    intent = new Intent(getActivity(), ProjectSelectionActivity.class);
                    intent.putExtra("parentId", parentId);
                    intent.putExtra("flag", flag);
                    intent.putExtra("org", "inspectProblemByMonth");//表示从第二个列表处理
                    startActivity(intent);
                }
                break;
            //巡查
            case R.id.tx_Inspectionrectangle://
                TencentCountUtil.count(getActivity(), "tx_Inspectionrectangle");
                isThreed = true;
                intent = new Intent(getActivity(), DepartmentSelectActivity.class);
                startActivity(intent);
                break;
            case R.id.tx_InspectionCompay:
                TencentCountUtil.count(getActivity(), "tx_InspectionCompay");
                isThreed = true;
                if (flag.equals("3")) {
                } else {
                    intent = new Intent(getActivity(), ProjectSelectionActivity.class);
                    intent.putExtra("org", "inspectProblemByPoint");//表示从第三个列表处理
                    intent.putExtra("parentId", parentId);
                    intent.putExtra("flag", flag);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 选择项目
     */
    private void selectProject(Intent intent) {
        intent = new Intent(getActivity(), ProjectSelectionActivity.class);
        intent.putExtra("parentId", parentId);
        intent.putExtra("flag", flag);
        intent.putExtra("org", "inspectionStatisticsByMonth");//表示从第二个列表处理
        startActivity(intent);
    }

    /**
     * （1，总部，2分公司，3项目）
     * 第1个列表--巡查统计
     *
     * @param query
     * @param flag
     */
    public void getChangTotal(String query, String flag) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());//用户唯一标识
        map.put("sessionId", MyApplicaption.getSessionId());//登陆唯一标识
        map.put("query", query);//查询参数（时间格式：2016-12，项目传项目id）
        map.put("flag", flag);//类型（1，查询各分公司整改发现问题统计（按时间统计）,2查询各分公司整改发现问题统计（按项目统计）
        presenter.getChangTotal(map);
    }

    @Override
    public void getChangTotalNext(ChangTotalInfo info) {
        List<PointInfo> list = new ArrayList<>();
        loadLayout.setStatus(LoadingLayout.Success);
        List<ChangTotalInfo.ListObjBean> listObj = info.getListObj();
        float maxvalue = info.getMaxValue();
        for (int i = 0; i < listObj.size(); i++) {
            ChangTotalInfo.ListObjBean bean = listObj.get(i);
            float left = (bean.getRectificationNumber() / maxvalue) * 100 > 0 ? (bean.getRectificationNumber() / maxvalue) * 100 : 0f;
            float right = (bean.getFindNum() / maxvalue) * 100 > 0 ? (bean.getFindNum() / maxvalue) * 100 : 0f;
            PointInfo pointInfo = new PointInfo(left, right);
            pointInfo.setMonth(bean.getName());
            pointInfo.setRightText(Math.round(bean.getFindNum()) + "");
            pointInfo.setLeftText(Math.round(bean.getRectificationNumber()) + "");
            pointInfo.setRightColor(getResources().getColor(R.color.person_greay));
            pointInfo.setLeftColor(getResources().getColor(R.color.personf));
            list.add(pointInfo);
        }
        inspectAdapter1 = new PersonSAdapter(getActivity(), list);
        recPerson.setAdapter(inspectAdapter1);
        recPerson.scrollToPosition(inspectAdapter1.getItemCount() - 1);
    }

    @Override
    public void getChangTotalError(String code, String msg) {
        CommonlyUtils.errorState(getActivity(), code);
    }

    /**
     * 第二个列表
     *
     * @param sceneId
     */
    public void getStatistics(String sceneId) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("orgId", CommonlyUtils.inspectProblemByMonth == null ? userInfo.getLeftOrgId() : CommonlyUtils.inspectProblemByMonth.getId());//项目ID
        map.put("sceneId", sceneId);//场景ID
        map.put("userId", MyApplicaption.getUserId());//用户唯一标识
        map.put("sessionId", MyApplicaption.getSessionId());//登陆唯一标识
        presenter.getStatistics(map);
    }

    @Override
    public void getStatisticsNext(StatisticsInfo info) {
        loadLayout.setStatus(LoadingLayout.Success);
        List<PointInfo> listProject = new ArrayList<>();
        float maxvalue = info.getMaxValue();
        List<StatisticsInfo.ListObjBean> listObj = info.getListObj();
        for (int i = 0; i < listObj.size(); i++) {
            StatisticsInfo.ListObjBean bean = listObj.get(i);
            float left = (bean.getRectificationNumber() / maxvalue) * 100 > 0 ? (bean.getRectificationNumber() / maxvalue) * 100 : 0f;
            float right = (bean.getFindNumber() / maxvalue) * 100 > 0 ? (bean.getFindNumber() / maxvalue) * 100 : 0f;
            PointInfo pointInfo = new PointInfo(left, right);
            pointInfo.setMonth(bean.getMonth());
            pointInfo.setRightText(Math.round(bean.getFindNumber()) + "");
            pointInfo.setLeftText(Math.round(bean.getRectificationNumber()) + "");
            pointInfo.setRightColor(getResources().getColor(R.color.person_greay));
            pointInfo.setLeftColor(getResources().getColor(R.color.personProject));
            listProject.add(pointInfo);
        }
        statistics = new PersonSAdapter(getActivity(), listProject);
        recXuancha.setAdapter(statistics);
        recXuancha.scrollToPosition(statistics.getItemCount() - 1);
    }

    @Override
    public void getStatisticsError(String Code, String msg) {
        CommonlyUtils.errorState(getActivity(), Code);

    }

    /**
     * 第三个列表
     *
     * @param sceneId
     */
    public void getStatisticst(String sceneId) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());//用户唯一标识
        map.put("sessionId", userInfo.getSessionid());//登陆唯一标识
        map.put("orgId", CommonlyUtils.inspectProblemByPoint == null ? userInfo.getLeftOrgId() : CommonlyUtils.inspectProblemByPoint.getId());//项目ID
        map.put("sceneId", sceneId);//场景ID
        presenter.getStatisticst(map);
    }

    @Override
    public void getStatisticstNext(StatisticstInfo info) {
        loadLayout.setStatus(LoadingLayout.Success);
        List<PointInfo> listProject = new ArrayList<>();
        List<StatisticstInfo.ListObjBean> listObj = info.getListObj();
        float maxvalue = info.getMaxValue();
        for (int i = 0; i < listObj.size(); i++) {
            StatisticstInfo.ListObjBean bean = listObj.get(i);
            float left = (bean.getDealNum() / maxvalue) * 100 > 0 ? (bean.getDealNum() / maxvalue) * 100 : 0f;
            float right = (bean.getFindNum() / maxvalue) * 100 > 0 ? (bean.getFindNum() / maxvalue) * 100 : 0f;
            PointInfo pointInfo = new PointInfo(left, right);
            pointInfo.setMonth(bean.getName());
            pointInfo.setLeftText(Math.round(bean.getDealNum()) + "");
            pointInfo.setRightText(Math.round(bean.getFindNum()) + "");
            pointInfo.setRightColor(getResources().getColor(R.color.person_greay));
            pointInfo.setLeftColor(getResources().getColor(R.color.personProject));
            listProject.add(pointInfo);
        }
        inspectAdapter = new PersonSAdapter(getActivity(), listProject);
        recInspection.setAdapter(inspectAdapter);
        recInspection.scrollToPosition(inspectAdapter.getItemCount() - 1);
    }

    @Override
    public void getStatisticstError(String code, String msg) {
        CommonlyUtils.errorState(getActivity(), code);
    }

    private int mYear;
    private int mMonth;
    private int mDay;
    private String startTime = "";
    private String startTimeShow = "";

    public void selecterTime() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                mYear = year;
                mMonth = month;
                mDay = day;
                int m = (mMonth + 1) < 10 ? 0 + (mMonth + 1) : (mMonth + 1);
                int d = (mDay < 10) ? 0 + mDay : mDay;
                startTime = mYear + "-" + "0" + m;
                startTimeShow = mYear + "年" + m + "月";
                txBluerectangle.setText(startTimeShow);
                getChangTotal(startTime, type);
                //根据时间查询分公司数据
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private boolean isfrist = true;
    private boolean isTwo = false;
    private boolean isThreed = false;

    @Override
    public void onResume() {
        super.onResume();
        if (isfrist && CommonlyUtils.inspectionStatisticsByMonth != null) {//第一个数据列表查询
            loadLayout.setStatus(isConnected ? LoadingLayout.Loading : LoadingLayout.No_Network);
            if (flag.equals("1")) {
                getTime();
                txBluerectangle.setText(startTimeShow);
            } else {
                txBluerectangle.setText(CommonlyUtils.inspectionStatisticsByMonth.getName());
            }
            getChangTotal(CommonlyUtils.inspectionStatisticsByMonth.getId(), "2");
        }
        if (isTwo) {//第二个数据列表查询
            if (CommonlyUtils.listObjBean != null) {//场景id
                txXuancharectangle.setText(CommonlyUtils.listObjBean.getName());
                sceneId = CommonlyUtils.listObjBean.getId();
                getStatistics(sceneId);
            }
            if (CommonlyUtils.inspectProblemByMonth != null) {//项目id
                txXunchaCompay.setText(CommonlyUtils.inspectProblemByMonth.getName());
                getStatistics(sceneId);
            }
        }
        if (isThreed) {//第三个数据列表查询
            if (CommonlyUtils.listObjBean != null) {//场景id
                txInspectionrectangle.setText(CommonlyUtils.listObjBean.getName());
                sceneId = CommonlyUtils.listObjBean.getId();
                getStatisticst(sceneId);
            }
            if (CommonlyUtils.inspectProblemByPoint != null) {//项目id
                txInspectionCompay.setText(CommonlyUtils.inspectProblemByPoint.getName());
                getStatisticst(sceneId);
            }
        }
    }

    @Override
    public void onReload(View v) {
        CommonlyUtils.setLoadConnected(loadLayout, getActivity());
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
}