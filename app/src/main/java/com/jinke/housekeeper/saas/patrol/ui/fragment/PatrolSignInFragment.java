package com.jinke.housekeeper.saas.patrol.ui.fragment;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.NetworkUtil;
import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.base.BaseFragment;
import com.jinke.housekeeper.saas.patrol.bean.IsStartBean;
import com.jinke.housekeeper.saas.patrol.bean.PlanListBean;
import com.jinke.housekeeper.saas.patrol.bean.PointPlanBean;
import com.jinke.housekeeper.saas.patrol.bean.SavePointBean;
import com.jinke.housekeeper.saas.patrol.bean.SavePointListBean;
import com.jinke.housekeeper.saas.patrol.config.PatrolConfig;
import com.jinke.housekeeper.saas.patrol.config.SavePointListConfig;
import com.jinke.housekeeper.saas.patrol.precenter.PatrolSignInPresenter;
import com.jinke.housekeeper.saas.patrol.ui.activity.PatrolBeganActivity;
import com.jinke.housekeeper.saas.patrol.ui.activity.PatrolLinkActivity;
import com.jinke.housekeeper.saas.patrol.ui.activity.PointPlanListActivity;
import com.jinke.housekeeper.saas.patrol.ui.adapter.PatrolPathAdapter;
import com.jinke.housekeeper.saas.patrol.ui.widget.CustomListView;
import com.jinke.housekeeper.saas.patrol.ui.widget.LoadingLayout;
import com.jinke.housekeeper.saas.patrol.utils.DateUtil;
import com.jinke.housekeeper.saas.patrol.view.PatrolSignInView;
import com.jinke.housekeeper.utils.CommonlyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatrolSignInFragment extends BaseFragment<PatrolSignInView, PatrolSignInPresenter> implements PatrolSignInView {

    @Bind(R.id.patrol_personnel)
    TextView patrolPersonnel;
    @Bind(R.id.patrol_position)
    TextView patrolPosition;
    @Bind(R.id.patrol_now_time)
    TextView patrolNowTime;
    @Bind(R.id.patrol_path)
    CustomListView patrolPath;
    @Bind(R.id.fragment_patrol_sign_in_loading)
    LoadingLayout fragmentPatrolSignInLoading;
    @Bind(R.id.sign_in_button)
    TextView signInButton;
    @Bind(R.id.patrol_way)
    LinearLayout patrolWay;
    @Bind(R.id.sign_out_button)
    TextView signOutButton;
    @Bind(R.id.patrol_now_time_text)
    TextView patrolNowTimeText;
    @Bind(R.id.patrol_plan_name)
    TextView patrolPlanName;
    @Bind(R.id.patrol_plan_ico)
    TextView patrolPlanIco;
    @Bind(R.id.point_all_hint)
    TextView pointAllHint;
    @Bind(R.id.point_hint)
    TextView pointHint;


    private int CLICK_FLAG = 0;//是否签到 0默认 1签到 2签退 3巡更 4结束流程
    private PatrolPathAdapter patrolPathAdapter;
    private PatrolBeganActivity.FragmentSwitch fragmentSwitch;
    private PlanListBean planListBean;//计划巡检列表
    private int patrolRecordNumber = 0;//已检点位
    private int patrolRecordLeakNumber = 0;//漏检点位
    private Map<String, String> map;
    private String signId;
    private String systemSignInTime = "";
    private String systemSignOutTime = "";
    private String UID;
    private BroadcastReceiver bleIndoReceiver;
    private MediaPlayer mediaPlayer;
    private int operationIndex = -1;//记录当前操作的是缓存中的第几条的下标
    private boolean updateState = true;//上传数据类型  true表示巡更中上传  false表示结束巡更的上传

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_patrol_sign_in;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        fragmentPatrolSignInLoading.setStatus(null == systemSignInTime ? LoadingLayout.Success : LoadingLayout.Success);
        patrolPersonnel.setText(PatrolConfig.getTokenBean().getStaffName());
        patrolPosition.setText(PatrolConfig.getTokenBean().getPosition());
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.message_come);
        if (null != signId && !"".equals(signId)) {
            if (null != systemSignInTime)
                setPatrolNowTimeText(systemSignInTime);
            signOutButton.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.INVISIBLE);
            patrolWay.setVisibility(View.VISIBLE);
            CLICK_FLAG = 3;
        } else {
            signOutButton.setVisibility(View.INVISIBLE);
            signInButton.setVisibility(View.VISIBLE);
            map = new HashMap<>();
            presenter.getSystemTime(map);
        }
        //初始化PatrolPathAdapter
        initPlanListBean();
        List<IsStartBean.ListDataBean> listDataBeans = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            listDataBeans.add(new IsStartBean.ListDataBean());
        }
        patrolPathAdapter = new PatrolPathAdapter(getActivity(), listDataBeans);
        patrolPath.setAdapter(patrolPathAdapter);

        registerReceiver();
        //如果是在巡更过程中进入就判断是否有缓存，有缓存直接上传
        if (3 == CLICK_FLAG) {
            patrolNowTimeText.setText(getActivity().getString(R.string.patrol_sign_in_time));
            boolean sendDate = false;
            for (SavePointBean savePointBean : SavePointListConfig.getSavePointListBean(getActivity()).getPointListBeen()) {
                if (null != savePointBean.getListData() && 0 != savePointBean.getListData().size()) {
                    sendDate = true;
                    break;
                }
            }
            if (sendDate) {
                map = new HashMap<>();
                map.put("signId", signId);
                map.put("projectId", CommonlyUtils.getUserInfo(getActivity()).getLeftOrgId());
                map.put("resultList", packagingDate(SavePointListConfig.getSavePointListBean(getActivity())));
                presenter.patrolPunchCard(map);
            }
        }
    }

    @Override
    public PatrolSignInPresenter initPresenter() {
        return new PatrolSignInPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @OnClick({R.id.sign_in_button, R.id.sign_out_button, R.id.patrol_plan_layout})
    protected void buttonOnClock(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                if (0 == CLICK_FLAG) {
                    CLICK_FLAG = 1;
                }
                map = new HashMap<>();
                presenter.getSystemTime(map);
                break;
            case R.id.sign_out_button:
                if (3 == CLICK_FLAG) {
                    CLICK_FLAG = 2;
                }
                updateState = false;
                map = new HashMap<>();
                presenter.getSystemTime(map);
                break;
            case R.id.patrol_plan_layout:
                startActivityForResult(new Intent(getActivity(), PointPlanListActivity.class), 9211);
                break;
        }
    }

    public void setFragmentSwitch(PatrolBeganActivity.FragmentSwitch fragmentSwitch) {
        this.fragmentSwitch = fragmentSwitch;
    }

    @Override
    public void signInSuccess(String signId) {
        this.signId = signId;
        patrolNowTimeText.setText(getActivity().getString(R.string.patrol_sign_in_time));
        signOutButton.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.INVISIBLE);
        patrolWay.setVisibility(View.VISIBLE);
        CLICK_FLAG = 3;
        fragmentSwitch.fragmentSwitch(systemSignInTime, systemSignOutTime, patrolRecordNumber,patrolRecordLeakNumber);
    }

    @Override
    public void signOutSuccess() {
        //清除缓存中的数据
        SavePointListBean patrolRecordListBeen = SavePointListConfig.getSavePointListBean(getActivity());
        if ( null == patrolRecordListBeen || null == patrolRecordListBeen.getPointListBeen() || 0 == patrolRecordListBeen.getPointListBeen().size()) {
            patrolRecordNumber = 0;
        } else {
            for (SavePointBean savePointBean :patrolRecordListBeen.getPointListBeen()){
                patrolRecordNumber += savePointBean.getRow();
                patrolRecordLeakNumber += savePointBean.getLeak();
            }
        }
        SavePointListConfig.setSavePointListBean(getActivity(), null);
        fragmentSwitch.fragmentSwitch(systemSignInTime, systemSignOutTime, patrolRecordNumber,patrolRecordLeakNumber);
        CLICK_FLAG = 4;
    }

    @Override
    public void patrolPunchCardSuccess() {
        if (!updateState) {
            map = new HashMap<>();
            map.put("signId", signId);
            map.put("outTime", systemSignOutTime);
            presenter.signOutPatrol(map);
        } else {
            SavePointListBean savePointListBean = SavePointListConfig.getSavePointListBean(getActivity());
            for (SavePointBean savePointBean : savePointListBean.getPointListBeen()) {
                if (null != savePointBean.getCompleteTime() && !"".equals(savePointBean.getCompleteTime())) {
                    savePointListBean.getPointListBeen().remove(savePointBean);
                } else {
                    if (null != savePointBean.getListData()) {
                        savePointBean.getListData().clear();
                    }
                }
            }
            SavePointListConfig.setSavePointListBean(getActivity(), savePointListBean);
        }
    }

    @Override
    public void getSystemTimeSuccess(String systemTime) {
        switch (CLICK_FLAG) {
            //默认时间标志
            case 0:
                setPatrolNowTimeText(systemTime);
                break;
            //签到时间标志
            case 1:
                this.systemSignInTime = systemTime;
                setPatrolNowTimeText(systemSignInTime);
                map = new HashMap<>();
                map.put("signTime", systemSignInTime);
                presenter.signPatrol(map);
                break;
            //签退时间标志
            case 2:
                this.systemSignOutTime = systemTime;
                SavePointListBean patrolRecordListBeen = SavePointListConfig.getSavePointListBean(getActivity());
                if (null == patrolRecordListBeen.getPointListBeen() || 0 ==  patrolRecordListBeen.getPointListBeen().size()|| -1 == operationIndex) {
                    map = new HashMap<>();
                    map.put("signId", signId);
                    map.put("outTime", systemSignOutTime);
                    presenter.signOutPatrol(map);
                } else {
                    map = new HashMap<>();
                    map.put("signId", signId);
                    map.put("projectId", CommonlyUtils.getUserInfo(getActivity()).getLeftOrgId());
                    map.put("resultList", packagingDate(patrolRecordListBeen));
                    presenter.patrolPunchCard(map);
                }
                break;
            //巡更时间标志(作废)
            case 3:
                break;
        }
    }


    @Override
    public void showLoading() {
    }

    @Override
    public void onError(String msg) {
        if ("巡更时长不得低于1分钟".equals(msg)) {
            CLICK_FLAG = 3;
        }
        ToastUtils.showShort( msg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 9211:
                //回调函数加载任务列表
                if (null != data && null != data.getSerializableExtra("date")) {
                    IsStartBean isStartBean = (IsStartBean) data.getSerializableExtra("date");
                    PointPlanBean.ListDataBean pointPlanBean = (PointPlanBean.ListDataBean) data.getSerializableExtra("dateName");
                    planListBean.setIsStartBean(isStartBean);
                    planListBean.setListDataBean(pointPlanBean);
                    setPlanName(planListBean.getListDataBean().getTaskName());
                    SavePointListBean patrolRecordListBeen = SavePointListConfig.getSavePointListBean(getActivity());
                    //遍历缓存数据是否与当前选择任务相同，相同的需要比对数据。防止数据没有上传
                    boolean isNew = true;//是否是新任务  true是
                    for (int i = 0; i < patrolRecordListBeen.getPointListBeen().size(); i++) {
                        //比对任务ID是否相同，相同执行完比对后跳出循环
                        if (String.valueOf(planListBean.getListDataBean().getTaskId())
                                .equals(patrolRecordListBeen.getPointListBeen().get(i).getTaskId())) {
                            operationIndex = i;
                            for (SavePointBean.ListDataBean listDataBean
                                    : patrolRecordListBeen.getPointListBeen().get(i).getListData()) {
                                for (IsStartBean.ListDataBean dataBean : planListBean.getIsStartBean().getListData()) {
                                    if (listDataBean.getPointId().equals(dataBean.getPointId())) {
                                        //更新相同打卡点时间
                                        dataBean.setCompleteTime(listDataBean.getInspecTime());
                                    }
                                }
                            }
                            isNew = false;
                            break;
                        }
                    }
                    //新任务加入列表
                    if (isNew) {
                        operationIndex++;
                        if (0 == operationIndex){
                            patrolRecordListBeen.getPointListBeen().clear();
                        }
                        SavePointBean savePointBean = new SavePointBean();
                        savePointBean.setRow(0);
                        savePointBean.setCompleteTime("");
                        savePointBean.setTaskId(String.valueOf(planListBean.getListDataBean().getTaskId()));
                        savePointBean.setListData(new ArrayList<SavePointBean.ListDataBean>());
                        patrolRecordListBeen.getPointListBeen().add(savePointBean);
                    }
                    SavePointListConfig.setSavePointListBean(getActivity(),patrolRecordListBeen);
                    //统计已经打卡点位
                    int row = 0;//已经巡检点位
                    for (IsStartBean.ListDataBean dataBean : planListBean.getIsStartBean().getListData()) {
                        if (null != dataBean.getCompleteTime() && !"".equals(dataBean.getCompleteTime())) {
                            row++;
                        }
                    }
                    //保存漏检点位
                    SavePointListBean patrolRecordBean = SavePointListConfig.getSavePointListBean(getActivity());
                    patrolRecordBean.getPointListBeen().get(operationIndex)
                            .setLeak(planListBean.getIsStartBean().getListData().size()-row);
                    SavePointListConfig.setSavePointListBean(getActivity(),patrolRecordBean);

                    pointHint.setText(row + "");
                    pointAllHint.setText(planListBean.getIsStartBean().getListData().size() + "");
                    //更新显示内容
                    patrolPathAdapter.setPatrolRecordListBean(planListBean.getIsStartBean().getListData());
                }
                break;
        }
    }

    public void setInitDate(String signId, String systemSignInTime) {
        this.signId = signId;
        this.systemSignInTime = systemSignInTime;
    }

    private void registerReceiver() {
        bleIndoReceiver = new BleIndoReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PatrolLinkActivity.BLE_INFO_BROADCAST_ACTION);
        getActivity().registerReceiver(bleIndoReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        getActivity().unregisterReceiver(bleIndoReceiver);
    }


    private class BleIndoReceiver extends BroadcastReceiver {
        //接收到广播后自动调用该方法
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != signId && !"".equals(signId)) {
                UID = null;
                String getUID = intent.getStringExtra(PatrolLinkActivity.BLE_INFO_UID);
                boolean findLoctionState = true;
                for (IsStartBean.ListDataBean listBean : planListBean.getIsStartBean().getListData()) {
                    if (getUID.equals(listBean.getPointId())) {
                        UID = getUID;
                        findLoctionState = false;
                        break;
                    }
                }
                //判断打的点位是否在任务列表中
                if (findLoctionState) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage("对不起，没有找到对应的点位信息！\n请重新选择任务")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                if (null != UID) {
                    mediaPlayer.start();
                    //判断打卡时间是否在五分钟之内
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String dateString = formatter.format(curDate);
                    for (IsStartBean.ListDataBean savePointBean : planListBean.getIsStartBean().getListData()) {
                        if (!"".equals(savePointBean.getCompleteTime())) {
                            int timeDifference = Integer.parseInt(
                                    DateUtil.getTimeDifference(DateUtil.string2Date(savePointBean.getCompleteTime(), "yyyy-MM-dd HH:mm:ss")
                                            , DateUtil.string2Date(dateString, "yyyy-MM-dd HH:mm:ss")));
                            if (UID.equals(savePointBean.getPointId()) && timeDifference < 5) {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage("对不起，你已经完成该点位巡更！\n五分钟打卡只计算一次哟！")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                                return;
                            }
                        }
                    }
                    //判断是否有网没有网就保存数据，有网就上传数据
                    SavePointListBean patrolRecordListBeen = SavePointListConfig.getSavePointListBean(getActivity());
                    SavePointBean.ListDataBean date = new SavePointBean.ListDataBean();
                    date.setPointId(UID);
                    date.setInspecTime(dateString);
                    int row = patrolRecordListBeen.getPointListBeen().get(operationIndex).getRow();
                    patrolRecordListBeen.getPointListBeen().get(operationIndex).setRow(++row);
                    patrolRecordListBeen.getPointListBeen().get(operationIndex).getListData().add(date);

                    //更新显示内容
                    boolean addComplete = true;//false没有完成巡检
                    for (IsStartBean.ListDataBean dataBean : planListBean.getIsStartBean().getListData()) {
                        if (UID.equals(dataBean.getPointId())) {
                            //更新相同打卡点时间
                            dataBean.setCompleteTime(dateString);
                        }
                        //判断是否有未打卡的点 改变状态
                        if (null == dataBean.getCompleteTime() || "".equals(dataBean.getCompleteTime())) {
                            addComplete = false;
                        }
                    }
                    //设置任务完成时间
                    if (addComplete) {
                        patrolRecordListBeen.getPointListBeen().get(operationIndex).setCompleteTime(dateString);
                    }
                    SavePointListConfig.setSavePointListBean(getActivity(), patrolRecordListBeen);
                    //统计已经打卡点位
                    int rows = 0;//已经巡检点位
                    for (IsStartBean.ListDataBean dataBean : planListBean.getIsStartBean().getListData()) {
                        if (null != dataBean.getCompleteTime() && !"".equals(dataBean.getCompleteTime())) {
                            rows++;
                        }
                    }
                    
                    //保存漏检点位
                    SavePointListBean patrolRecordBean = SavePointListConfig.getSavePointListBean(getActivity());
                    patrolRecordBean.getPointListBeen().get(operationIndex)
                            .setLeak(planListBean.getIsStartBean().getListData().size() - rows);
                    SavePointListConfig.setSavePointListBean(getActivity(),patrolRecordBean);
                    pointHint.setText(rows + "");
                    patrolPathAdapter.setPatrolRecordListBean(planListBean.getIsStartBean().getListData());
                    //提交数据
                    if (NetworkUtil.initConnectState()) {
                        map = new HashMap<>();
                        map.put("signId", signId);
                        map.put("projectId", CommonlyUtils.getUserInfo(getActivity()).getLeftOrgId());
                        map.put("resultList", packagingDate(SavePointListConfig.getSavePointListBean(getActivity())));
                        presenter.patrolPunchCard(map);
                    }
                } else {
                    ToastUtils.showShort("点位信息不存在");
                }
            } else {
                ToastUtils.showShort( "请先签到");
            }
        }
    }

    /**
     * 打包打卡数据
     *
     * @param patrolRecordListBeen 需要打包的数据
     * @return
     */
    private String packagingDate(SavePointListBean patrolRecordListBeen) {
        try {
            JSONArray resultListJsonArray = new JSONArray();
            for (SavePointBean savePointBean : patrolRecordListBeen.getPointListBeen()) {
                JSONObject resultObject = new JSONObject();
                resultObject.put("taskId", savePointBean.getTaskId());
                resultObject.put("completeTime", savePointBean.getCompleteTime());
                JSONArray listDataJsonArray = new JSONArray();
                if (null != savePointBean.getListData() && 0 != savePointBean.getListData().size()) {
                    for (SavePointBean.ListDataBean dataBean : savePointBean.getListData()) {
                        JSONObject listDataObject = new JSONObject();
                        listDataObject.put("pointId", dataBean.getPointId());
                        listDataObject.put("inspecTime", dataBean.getInspecTime());
                        listDataJsonArray.put(listDataObject);
                    }
                }
                resultObject.put("listData", listDataJsonArray);
                resultListJsonArray.put(resultObject);
            }
            return resultListJsonArray.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 设置签到时间
     *
     * @param systemTime
     */
    private void setPatrolNowTimeText(String systemTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(systemTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        if (null != date)
            patrolNowTime.setText(showDateFormat.format(date));
    }

    /**
     * 初始化PlanListBean 单个任务列表点位列表实体
     */
    private void initPlanListBean() {
        planListBean = new PlanListBean();
        IsStartBean isStartBean = new IsStartBean();
        List<IsStartBean.ListDataBean> listDataBean = new ArrayList<>();
        isStartBean.setListData(listDataBean);
        PointPlanBean.ListDataBean patrolRecordListBeen = new PointPlanBean.ListDataBean();
        planListBean.setListDataBean(patrolRecordListBeen);
        planListBean.setIsStartBean(isStartBean);
    }

    /**
     * 设置任务名称
     *
     * @param planName
     */
    public void setPlanName(String planName) {
        if (null != planName && !"".equals(planName)) {
            patrolPlanName.setText(planName);
            patrolPlanIco.setText("");
        } else {
            patrolPlanName.setText("");
            patrolPlanIco.setText(getText(R.string.please_select));
        }
    }

}
