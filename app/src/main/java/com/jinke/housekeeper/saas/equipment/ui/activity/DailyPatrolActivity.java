package com.jinke.housekeeper.saas.equipment.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseActivity;
import com.jinke.housekeeper.saas.equipment.bean.DailyPatrolBean;
import com.jinke.housekeeper.saas.equipment.bean.DailyPatrolRecordListBean;
import com.jinke.housekeeper.saas.equipment.bean.TaskBean;
import com.jinke.housekeeper.saas.equipment.bean.TaskListBean;
import com.jinke.housekeeper.saas.equipment.precenter.DailyPatrolPresenter;
import com.jinke.housekeeper.saas.equipment.ui.adapter.DailyPatrolRecordAdapter;
import com.jinke.housekeeper.saas.equipment.ui.widget.CustomDialog;
import com.jinke.housekeeper.saas.equipment.ui.widget.CustomListView;
import com.jinke.housekeeper.saas.equipment.utils.SharedPreferenceUtil;
import com.jinke.housekeeper.saas.equipment.view.DailyPatrolView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.NetWorksUtils;

import static com.jinke.housekeeper.saas.equipment.bean.TaskBean.TASK_NO_TSTARTED;
import static com.jinke.housekeeper.saas.equipment.bean.TaskBean.TASK_TO_PERFORM;
import static com.jinke.housekeeper.saas.equipment.ui.activity.BluetoothLinkActivity.DAILY_PATROL;

public class DailyPatrolActivity extends BaseActivity<DailyPatrolView, DailyPatrolPresenter> implements DailyPatrolView {

    //7天完成率
    @Bind(R.id.equipment_progress)
    TextView equipmentProgress;
    //完成及时率
    @Bind(R.id.daily_patrol_complete_time_rate)
    TextView dailyPatrolCompleteTimeRate;
    //超时任务
    @Bind(R.id.daily_patrol_timeout_task)
    TextView dailyPatrolTimeoutTask;
    //待完成任务
    @Bind(R.id.daily_patrol_action_required)
    TextView dailyPatrolActionRequired;
    //记录列表
    @Bind(R.id.daily_patrol_record)
    CustomListView dailyPatrolRecord;

    private Map<String, String> map;
    private DailyPatrolRecordAdapter dailyPatrolRecordAdapter;
    private List<DailyPatrolRecordListBean> infoList;
    private TaskListBean taskListBean;


    @Override
    public DailyPatrolPresenter initPresenter() {
        return new DailyPatrolPresenter(DailyPatrolActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_daily_patrol;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_daily_patrol));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.equipment_back_ico);
        showForwardView(R.string.daily_patrol_flush);
        showForwardViewColor(getResources().getColor(R.color.equipment_text_3));
        taskListBean = (TaskListBean) SharedPreferenceUtil.get(DailyPatrolActivity.this, "DailyPatrolActivity", "TaskListBean");
        initAdapter();
        initDate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            sendCompleteTask();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        try {
            sendCompleteTask();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initDate();
    }


    @OnClick({R.id.daily_patrol_scan_start_task_button})
    public void dailyPatrolOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.daily_patrol_scan_start_task_button:
                Intent bluetoothLinkIntent = new Intent(DailyPatrolActivity.this, BluetoothLinkActivity.class);
                bluetoothLinkIntent.putExtra("date", "DAILY_PATROL");
                startActivityForResult(bluetoothLinkIntent, DAILY_PATROL);
                break;
        }
    }

    /**
     * ------------ 网络请求相关方式实现began ------------
     */
    @Override
    public void getStatisticsSuccess(DailyPatrolBean dailyPatrolBean) {
        SharedPreferenceUtil.save(DailyPatrolActivity.this, "DailyPatrolBean", "dailyPatrolBean", dailyPatrolBean);
        equipmentProgress.setText(dailyPatrolBean.getWeekRate() + "%");
        dailyPatrolCompleteTimeRate.setText(dailyPatrolBean.getCompletionRate());
        dailyPatrolTimeoutTask.setText(dailyPatrolBean.getOvertimeNum());
        dailyPatrolActionRequired.setText(dailyPatrolBean.getUnCompletion());
    }

    @Override
    public void getTaskSuccess(TaskListBean taskList) {
        if (null != taskList && null != taskList.getListData()) {
            taskListBean = (TaskListBean) SharedPreferenceUtil.get(DailyPatrolActivity.this, "DailyPatrolActivity", "TaskListBean");
            //判断taskListBean是否为空，为空则直接存入获取到的taskList
            if (null != taskListBean) {
                //判断缓存taskListBean是否有更新，但是保存本地taskListBean的状态
                //遍历取到的七天的任务
                for (int i = 0; i < taskList.getListData().size(); i++) {
                    //判断是否有状态为可以进行的任务
                    if (TaskBean.TASK_TO_PERFORM.equals(taskList.getListData().get(i).getTaskStatus())) {
                        //遍历缓存中的任务列表
                        for (TaskBean recordTaskBean : taskListBean.getListData()) {
                            //遍历缓存中的点位列表
                            for (TaskBean.PointListBean pointListBean : recordTaskBean.getPointList()) {
                                //判断是否有对应的任务 判断缓存任务重是否有完成未上传的任务
                                if (null != recordTaskBean.getId()
                                        && taskList.getListData().get(i).getId().equals(recordTaskBean.getId())
                                        && TaskBean.PointListBean.TASK_NO_UPDATE.equals(pointListBean.getStatus())) {
                                    //遍历七天任务符合条件的任务点位
                                    for (TaskBean.PointListBean pointBean : taskList.getListData().get(i).getPointList()) {
                                        //赋予完成未上传任务
                                        if (!TaskBean.PointListBean.TASK_COMPLETED.equals(pointBean.getStatus())
                                                && pointBean.getId().equals(pointListBean.getId())) {
                                            pointBean.setStatus(pointListBean.getStatus());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            List<TaskBean> recordBeanList = new ArrayList<>();
            List<List<TaskBean>> recordExecuteTaskList = new ArrayList<>();
            List<String> markTaskList = new ArrayList<>();
            //循环标签
            for (TaskBean taskBean : taskList.getListData()) {
                if (TASK_TO_PERFORM.equals(taskBean.getTaskStatus())) {
                    String markString = taskBean.getTaskOrder().split("_")[0];
                    if (markTaskList.size() == 0) {
                        markTaskList.add(markString);
                    }
                    for (String s : markTaskList) {
                        if (!markString.equals(s)) {
                            markTaskList.add(markString);
                            break;
                        }
                    }
                }
            }
            //根据标签处理成对应组别的任务列表
            for (String s : markTaskList) {
                List<TaskBean> beanList = new ArrayList<>();
                for (TaskBean taskBean : taskList.getListData()) {
                    if (TASK_TO_PERFORM.equals(taskBean.getTaskStatus())) {
                        String markString = taskBean.getTaskOrder().split("_")[0];
                        if (s.equals(markString)) {
                            taskBean.setTaskStatus(TASK_NO_TSTARTED);
                            beanList.add(taskBean);
                        }
                    }
                }
                recordExecuteTaskList.add(beanList);
            }

            //根据任务列表取到当前可执行任务
            for (List<TaskBean> beanList : recordExecuteTaskList) {
                if (0 != beanList.size()){
                    int minIndex  = 0;
                    for (int i = 0; i < beanList.size(); i++) {
                        //取出来的任务全部重置为1未开始，后面匹配到第一条设置为2待执行
                        if (i > 0) {
                            if (Integer.parseInt(beanList.get(i).getTaskOrder().split("_")[1]) <
                                    Integer.parseInt(beanList.get(i - 1).getTaskOrder().split("_")[1])) {
                                minIndex = i;
                            }
                        } else {
                            minIndex = i;
                        }
                    }
                    beanList.get(minIndex).setTaskStatus(TASK_TO_PERFORM);
                    recordBeanList.addAll(beanList);
                }
            }

            //更新处理后的任务状态
            for (TaskBean taskBeanList : taskList.getListData()){
                for (TaskBean taskBean : recordBeanList){
                    if (taskBeanList.getId().equals(taskBean.getId())){
                        taskBeanList = taskBean;
                    }
                }
            }

            //处理数据后存入缓存，再取出缓存中数据进行显示操作
            SharedPreferenceUtil.save(DailyPatrolActivity.this, "DailyPatrolActivity", "TaskListBean", taskList);
            taskListBean = (TaskListBean) SharedPreferenceUtil.get(DailyPatrolActivity.this, "DailyPatrolActivity", "TaskListBean");

            //判断infoList是否有更新，有更新就更新列表，但是保存现有展开状态
            infoList.clear();
            for (TaskBean taskBean : taskListBean.getListData()) {
                DailyPatrolRecordListBean dailyPatrolRecordListBean = new DailyPatrolRecordListBean();
                dailyPatrolRecordListBean.setTaskBean(taskBean);
                dailyPatrolRecordListBean.setShowState(false);
                infoList.add(dailyPatrolRecordListBean);
            }
            //更新本地缓存同时更新Adapter
            dailyPatrolRecordAdapter.setInfoListBean(infoList);
        }
    }

    @Override
    public void completeTaskSuccess() {
        //上传成功清除缓存，重新获取数据
        SharedPreferenceUtil.save(DailyPatrolActivity.this, "DailyPatrolActivity", "TaskListBean", null);
        initDate();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(String msg) {

    }

    /**
     * ------------ 网络请求相关方式实现end ------------
     */

    private void initDate() {
        if (NetWorksUtils.isConnected(this)) {
            map = new HashMap<>();
            presenter.getStatistics(map);
            presenter.getTask(map);
        } else {
            DailyPatrolBean dailyPatrolBean = (DailyPatrolBean) SharedPreferenceUtil.get(DailyPatrolActivity.this, "DailyPatrolBean", "dailyPatrolBean");
            if (null != dailyPatrolBean) {
                equipmentProgress.setText(dailyPatrolBean.getWeekRate() + "%");
                dailyPatrolCompleteTimeRate.setText(dailyPatrolBean.getCompletionRate());
                dailyPatrolTimeoutTask.setText(dailyPatrolBean.getOvertimeNum());
                dailyPatrolActionRequired.setText(dailyPatrolBean.getUnCompletion());
            }
        }
    }

    private void initAdapter() {
        //任务缓存为空时创建一个空的任务列表并存入缓存
        if (null == taskListBean) {
            taskListBean = new TaskListBean();
            List<TaskBean> taskBeanList = new ArrayList<>();
            taskListBean.setListData(taskBeanList);
            SharedPreferenceUtil.save(DailyPatrolActivity.this, "DailyPatrolActivity", "TaskListBean", taskListBean);
        }
        infoList = new ArrayList<>();
        dailyPatrolRecordAdapter = new DailyPatrolRecordAdapter(DailyPatrolActivity.this, infoList);
        dailyPatrolRecord.setAdapter(dailyPatrolRecordAdapter);
    }

    /**
     * 检查发送已完成未上传任务
     */
    private void sendCompleteTask() throws JSONException {
        TaskListBean listBean = (TaskListBean) SharedPreferenceUtil.get(DailyPatrolActivity.this, "DailyPatrolActivity", "TaskListBean");
        //判断数据是否为空 为空就不执行相关数据操作
        if (null != listBean && null != listBean.getListData()) {
            List<TaskBean.PointListBean> updatePointListBean = new ArrayList<>();
            JSONArray updateJsonArray = new JSONArray();//上传json
            for (TaskBean bean : listBean.getListData()) {
                if (TaskBean.TASK_TO_PERFORM.equals(bean.getTaskStatus())) {
                    JSONObject updateTaskList = new JSONObject();
                    updateTaskList.put("taskId", bean.getId());
                    updateTaskList.put("completeTime", bean.getCompleteTime());//需要巡检时候设置
                    JSONArray pointListJsonArray = new JSONArray();//上传json
                    for (TaskBean.PointListBean pointListBean : bean.getPointList()) {
                        if (TaskBean.PointListBean.TASK_NO_UPDATE.equals(pointListBean.getStatus())) {
                            updatePointListBean.add(pointListBean);
                            JSONObject updateJsonObject = new JSONObject();
                            updateJsonObject.put("pointId", pointListBean.getId());
                            updateJsonObject.put("inspecTime", pointListBean.getInspecTime());//需要巡检时候设置
                            updateJsonObject.put("result", pointListBean.getResult());//需要巡检时候设置
                            updateJsonObject.put("remark", pointListBean.getRemark());//需要巡检时候设置
                            pointListJsonArray.put(updateJsonObject);
                        }
                    }
                    if (0 != pointListJsonArray.length()) {
                        updateTaskList.put("listData", pointListJsonArray.toString());
                        updateJsonArray.put(updateTaskList);
                    }
                }
            }
            if (0 != updatePointListBean.size()) {
                if (NetWorksUtils.isConnected(this)) {
                    map = new HashMap<>();
                    map.put("resultList", updateJsonArray.toString());
                    presenter.completeTask(map);
                } else {
                    CustomDialog.Builder builder = new CustomDialog.Builder(this);
                    builder.setMessage(getString(R.string.daily_patrol_dialog_message_1) + updatePointListBean.size() + getString(R.string.daily_patrol_dialog_message_2));
                    builder.setTitle(getString(R.string.daily_patrol_dialog_title));
                    builder.setPositiveButton(getString(R.string.daily_patrol_dialog_close),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                    infoList.clear();
                    taskListBean = (TaskListBean) SharedPreferenceUtil.get(DailyPatrolActivity.this, "DailyPatrolActivity", "TaskListBean");
                    if (null != taskListBean) {
                        for (TaskBean taskBean : taskListBean.getListData()) {
                            DailyPatrolRecordListBean patrolRecordListBean = new DailyPatrolRecordListBean();
                            patrolRecordListBean.setTaskBean(taskBean);
                            patrolRecordListBean.setShowState(false);
                            infoList.add(patrolRecordListBean);
                        }
                        dailyPatrolRecordAdapter.setInfoListBean(infoList);
                    }
                }
            }
        }

    }


}
