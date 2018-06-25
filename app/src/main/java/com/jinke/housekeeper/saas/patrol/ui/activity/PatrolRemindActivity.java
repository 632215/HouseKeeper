package com.jinke.housekeeper.saas.patrol.ui.activity;

import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.patrol.base.BaseActivity;
import com.jinke.housekeeper.saas.patrol.bean.AlarmClockBean;
import com.jinke.housekeeper.saas.patrol.bean.AlarmClockListBean;
import com.jinke.housekeeper.saas.patrol.bean.RemindTimeBean;
import com.jinke.housekeeper.saas.patrol.config.AlarmClockConfig;
import com.jinke.housekeeper.saas.patrol.precenter.RemindTimePresenter;
import com.jinke.housekeeper.saas.patrol.ui.adapter.PatrolRemindAdapter;
import com.jinke.housekeeper.saas.patrol.ui.widget.LoadingLayout;
import com.jinke.housekeeper.saas.patrol.utils.LogUtil;
import com.jinke.housekeeper.saas.patrol.utils.RemindTimeView;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.loonggg.lib.alarmmanager.clock.AlarmManagerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class PatrolRemindActivity extends BaseActivity<RemindTimeView, RemindTimePresenter>
        implements RemindTimeView, LoadingLayout.OnReloadListener {

    @Bind(R.id.patrol_remind_list)
    ListView patrolRemindList;
    @Bind(R.id.patrol_remind_empty)
    RelativeLayout patrolRemindEmpty;
    @Bind(R.id.activity_patrol_manage)
    LoadingLayout activityPatrolManage;

    private PatrolRemindAdapter patrolRemindAdapter;
    private List<AlarmClockBean> listDataBeen;
    private Map<String, String> map = new HashMap<>();

    @Override
    public RemindTimePresenter initPresenter() {
        return new RemindTimePresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_patrol_remind;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_patrol_remind));
        hindTitleLine();
        showBackwardView(R.string.empty, true);
        activityPatrolManage.setStatus(LoadingLayout.Loading);

        listDataBeen = new ArrayList<>();
        patrolRemindAdapter = new PatrolRemindAdapter(this, listDataBeen);
        patrolRemindList.setAdapter(patrolRemindAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDate();
        initList();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }


    private void initDate() {
        AlarmClockListBean alarmClockListBean = AlarmClockConfig.getAlarmClockBean(getApplicationContext());
        if (null == alarmClockListBean) {
            alarmClockListBean = new AlarmClockListBean();
        }
        if (null == alarmClockListBean.getAlarmClockList()) {
            alarmClockListBean.setAlarmClockList(new ArrayList<AlarmClockBean>());
        }

    }


//    private DeleteInform deleteInform = new DeleteInform() {
//        @Override
//        public void delete(int index) {
//            AlarmClockListBean alarmClockListBean = AlarmClockConfig.getAlarmClockBean(PatrolRemindActivity.this);
//            alarmClockListBean.getAlarmClockList().remove(index);
//            AlarmClockConfig.setAlarmClockBean(PatrolRemindActivity.this, alarmClockListBean);
//            initDate();
//            patrolRemindAdapter.setAlarmClockBean(clockBeanList);
//        }
//    };

    @Override
    public void showLoading() {
        showDialog();
    }

    @Override
    public void showMessage() {
        dimissDialog();
        activityPatrolManage.setStatus(LoadingLayout.Error);
    }

    @Override
    public void onRefreshData(RemindTimeBean remindTimeBean) {
        dimissDialog();
        activityPatrolManage.setStatus(LoadingLayout.Success);
        if (null != remindTimeBean && null != remindTimeBean.getListData()) {
            if (0 < remindTimeBean.getListData().size()) {
                //封装来之服务器的闹钟列表
                AlarmClockListBean clockListBean = new AlarmClockListBean();
                List<AlarmClockBean> alarmClockBeanList = new ArrayList<>();
                for (RemindTimeBean.ListDataBean bean : remindTimeBean.getListData()) {
                    AlarmClockBean alarmClockBean = new AlarmClockBean();
                    alarmClockBean.setAlarmId(Integer.parseInt(bean.getId()));
                    alarmClockBean.setStartTime(bean.getStartTime());
                    alarmClockBean.setEndTime(bean.getEndTime());
                    alarmClockBean.setStateAlarmClock(false);
                    alarmClockBeanList.add(alarmClockBean);
                }
                clockListBean.setAlarmClockList(alarmClockBeanList);

                //同步来之服务器的闹钟列表和本地列表
                AlarmClockListBean alarmClockListBean = AlarmClockConfig.getAlarmClockBean(this);

                if (null == alarmClockListBean) {
                    alarmClockListBean = new AlarmClockListBean();
                    alarmClockListBean.setAlarmClockList(new ArrayList<AlarmClockBean>());
                }

                //比对新列表与老列表是否有删除闹钟
                //新老列表数量不相等则表示有删除闹钟
                AlarmClockListBean deleteAlarmListBean = new AlarmClockListBean();
                List<AlarmClockBean> deleteListBean = new ArrayList<>();

                //修改获取到的闹钟列表状态

                for (int j = 0; j < alarmClockListBean.getAlarmClockList().size(); j++) {
                    for (int i = 0; i < clockListBean.getAlarmClockList().size(); i++) {
                        if (clockListBean.getAlarmClockList().get(i).getAlarmId()
                                == alarmClockListBean.getAlarmClockList().get(j).getAlarmId()) {
                            clockListBean.getAlarmClockList().get(i)
                                    .setStateAlarmClock(alarmClockListBean.getAlarmClockList().get(j).isStateAlarmClock());
                            break;
                        }else{
                            if(i+1 == clockListBean.getAlarmClockList().size()){
                                alarmClockListBean.getAlarmClockList().get(j).setStateAlarmClock(false);
                                deleteListBean.add(alarmClockListBean.getAlarmClockList().get(j));
                            }
                        }
                    }
                }


                //刷新删除闹钟列表
                deleteAlarmListBean.setAlarmClockList(deleteListBean);
                setClock(deleteAlarmListBean.getAlarmClockList());

                //存储更新过后闹钟列表
                AlarmClockConfig.setAlarmClockBean(this, clockListBean);

                //从新设置闹钟刷新列表
                listDataBeen = AlarmClockConfig.getAlarmClockBean(this).getAlarmClockList();
                if (listDataBeen.size() > 0) {
                    setClock(listDataBeen);
                    patrolRemindAdapter.setRemindTimeList(listDataBeen);
                    patrolRemindEmpty.setVisibility(View.GONE);
                } else {
                    patrolRemindEmpty.setVisibility(View.VISIBLE);
                }
            } else {
                //获取闹钟为0时清空闹钟列表  关闭闹钟
                listDataBeen = AlarmClockConfig.getAlarmClockBean(this).getAlarmClockList();
                if (null == listDataBeen) {
                    listDataBeen = new ArrayList<>();
                }
                for (int i = 0; i < listDataBeen.size(); i++) {
                    listDataBeen.get(i).setStateAlarmClock(false);
                }
                setClock(listDataBeen);
                AlarmClockListBean clockListBean = new AlarmClockListBean();
                listDataBeen.clear();
                clockListBean.setAlarmClockList(listDataBeen);
                AlarmClockConfig.setAlarmClockBean(this, clockListBean);
                if (listDataBeen.size() > 0) {
                    setClock(listDataBeen);
                    patrolRemindAdapter.setRemindTimeList(listDataBeen);
                    patrolRemindEmpty.setVisibility(View.GONE);
                } else {
                    patrolRemindEmpty.setVisibility(View.VISIBLE);
                }
            }
        } else {
            patrolRemindEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onReload(View v) {
        initList();
    }

    private void initList() {
        if (null != CommonlyUtils.getUserInfo(this).getLeftOrgId() && !"".equals(CommonlyUtils.getUserInfo(this).getLeftOrgId())) {
            map.put("projectId", CommonlyUtils.getUserInfo(this).getLeftOrgId());
            presenter.getRemindTime(map);
        } else {
            ToastUtils.showShort( "获取项目失败");
            finish();
        }
    }

    private void setClock(List<AlarmClockBean> clockBeanList) {
        for (AlarmClockBean clockBean : clockBeanList) {
            if (clockBean.isStateAlarmClock()) {
                String[] times = clockBean.getStartTime().split(":");
                AlarmManagerUtil.setAlarm(this, clockBean.getAlarmId(), Integer.parseInt(times[0]), Integer.parseInt(times[1])
                        , clockBean.getAlarmId(), 0, "闹钟" + clockBean.getStartTime() + "响了", 2);
                LogUtil.loge(clockBean.getAlarmId() + "闹钟" + clockBean.getStartTime() + "设置成功");
            } else {
                AlarmManagerUtil.cancelAlarm(this, clockBean.getAlarmId());
                LogUtil.loge("取消"+clockBean.getAlarmId()+"闹钟" + clockBean.getStartTime() + "设置成功");
            }
        }
    }

}
