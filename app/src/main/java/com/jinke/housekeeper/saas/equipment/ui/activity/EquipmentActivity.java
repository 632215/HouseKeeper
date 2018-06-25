package com.jinke.housekeeper.saas.equipment.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseActivity;
import com.jinke.housekeeper.saas.equipment.bean.IcoBean;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.precenter.EquipmentPresenter;
import com.jinke.housekeeper.saas.equipment.ui.adapter.EquipmentAppAdapter;
import com.jinke.housekeeper.saas.equipment.view.EquipmentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class EquipmentActivity extends BaseActivity<EquipmentView, EquipmentPresenter> implements EquipmentView {

    @Bind(R.id.equipment_app_list)
    RecyclerView equipmentAppList;


    private Map<String, String> map;
    private AlertView mAlertView;// 扫描设置弹出框 避免创建重复View，先创建View，然后需要的时候show出来，推荐这个做法
    private EquipmentAppAdapter inspectionAdapter;
    private IcoBean equipment_daily_patrol;
    private IcoBean equipment_scan;
    private IcoBean equipment_standing_book;
    private IcoBean equipment_third_cousin;
    private IcoBean equipment_add_id;
    private IcoBean equipment_inspection;
    private List<IcoBean> infoList;

    @Override
    public EquipmentPresenter initPresenter() {
        return new EquipmentPresenter(EquipmentActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_equipment;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_equipment));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.equipment_back_ico);
        initDate();
        initAppList();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    /**
     * ------------ 界面数据初始化方法began ------------
     */
    private void initDate() {
        map = new HashMap<>();
        String openId = getIntent().getStringExtra("openId");
        String projectId = getIntent().getStringExtra("projectId");
        String projectName = getIntent().getStringExtra("projectName");
        String userName = getIntent().getStringExtra("userName");
        String userID = getIntent().getStringExtra("userID");
        EquipmentConfig.setProjectId(projectId);
        EquipmentConfig.setProjectName(projectName);
        EquipmentConfig.setUserName(userName);
        EquipmentConfig.setUserID(userID);
        if (null != openId && null != projectId && null != projectName) {
            map.put("openId", openId);
            map.put("projectId", projectId);
            presenter.getTokenData(map);
        } else {
            ToastUtils.showShort("获取信息失败，请重新获取");
            finish();
        }
    }

    private void initAppList() {
        infoList = new ArrayList<>();
        equipment_daily_patrol = new IcoBean();//日常巡检
        equipment_daily_patrol.setInspectionTitleId(R.string.equipment_daily_patrol);
        equipment_daily_patrol.setInspectionTitleIcoId(R.drawable.equipment_daily_patrol_ico);
        equipment_scan = new IcoBean();//扫描
        equipment_scan.setInspectionTitleId(R.string.equipment_scan);
        equipment_scan.setInspectionTitleIcoId(R.drawable.equipment_scan_ico);
        equipment_standing_book = new IcoBean();//设备台帐
        equipment_standing_book.setInspectionTitleId(R.string.equipment_standing_book);
        equipment_standing_book.setInspectionTitleIcoId(R.drawable.equipment_standing_book_ico);
        equipment_third_cousin = new IcoBean();//三表
        equipment_third_cousin.setInspectionTitleId(R.string.equipment_third_cousin);
        equipment_third_cousin.setInspectionTitleIcoId(R.drawable.equipment_third_cousin_ico);
        equipment_add_id = new IcoBean();//添加设备ID
        equipment_add_id.setInspectionTitleId(R.string.equipment_add_id);
        equipment_add_id.setInspectionTitleIcoId(R.drawable.equipment_id_ico);
        equipment_inspection = new IcoBean();//维保巡检
        equipment_inspection.setInspectionTitleId(R.string.equipment_inspection);
        equipment_inspection.setInspectionTitleIcoId(R.drawable.equipment_inspection_ico);
        infoList.add(equipment_daily_patrol);
        infoList.add(equipment_scan);
        infoList.add(equipment_standing_book);
        infoList.add(equipment_third_cousin);
        equipmentAppList.setLayoutManager(new GridLayoutManager(EquipmentActivity.this, 3));
        inspectionAdapter = new EquipmentAppAdapter(EquipmentActivity.this, infoList, equipmentAppOnClickListener);
        equipmentAppList.setAdapter(inspectionAdapter);
    }
    /** ------------ 界面数据初始化方法end ------------ */

    /**
     * ------------ 网络请求相关方式实现began ------------
     */
    private boolean showDialog = true;

    @Override
    public void getTokenSuccess() {
        presenter.getNoPointData(map);
    }

    @Override
    public void getNoPointSuccess(String ifDevice) {
        infoList.clear();
        if ("1".equals(ifDevice)) {
            infoList.add(equipment_daily_patrol);
            infoList.add(equipment_scan);
            infoList.add(equipment_standing_book);
            infoList.add(equipment_third_cousin);
            infoList.add(equipment_add_id);
        } else {
            infoList.add(equipment_daily_patrol);
            infoList.add(equipment_scan);
            infoList.add(equipment_standing_book);
            infoList.add(equipment_third_cousin);
        }
        inspectionAdapter.setEquipmentAppAdapter(infoList);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(String msg) {
        if (showDialog) {
            new AlertDialog.Builder(EquipmentActivity.this).setTitle("提示")//设置对话框标题
                    .setMessage("网络请求失败，请检查网络再试")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            dialog.dismiss();
                        }
                    }).show();
            showDialog = false;
        }
    }
    /** ------------ 网络请求相关方式实现end ------------ */

    /**
     * ------------ 控件监听接口实现began ------------
     */
    private EquipmentAppAdapter.EquipmentAppOnClickListener equipmentAppOnClickListener = new EquipmentAppAdapter.EquipmentAppOnClickListener() {
        @Override
        public void equipmentAppOnClickListener(int position) {
            switch (position) {
                case 0://日常巡检
                    startActivity(new Intent(EquipmentActivity.this, DailyPatrolActivity.class));
                    break;
                case 1://扫描
//                    startActivity(new Intent(EquipmentActivity.this, ScanActivity.class));
//                    dialogEquipmentPopup();
                    ToastUtils.showShort("目前只支持蓝牙连接巡检");
                    break;
                case 2://设备台帐
                    startActivity(new Intent(EquipmentActivity.this, StandBookActivity.class));
                    break;
                case 3://三表能耗
                    startActivity(new Intent(EquipmentActivity.this, ThreeTableActivity.class));
                    break;
                case 4://添加设备ID
                    if (!"1".equals(EquipmentConfig.getTokenBean().getIfManage())) {
                        ToastUtils.showShort("您没有添加点位权限，请联系管理员");
                    } else {
                        startActivity(new Intent(EquipmentActivity.this, AddIDActivity.class));
                    }
                    break;
                case 5://维保巡检
                    startActivity(new Intent(EquipmentActivity.this, InspectionActivity.class));
                    break;
            }
        }

        /**
         * 扫描设置弹出框
         */
        private void dialogEquipmentPopup() {
            mAlertView = new AlertView(getString(R.string.equipment_popup_title)
                    , null, getString(R.string.equipment_popup_cancel)
                    , null
                    , new String[]{getString(R.string.equipment_popup_bluetooth), getString(R.string.equipment_popup_nfc), getString(R.string.equipment_popup_set)}
                    , EquipmentActivity.this
                    , AlertView.Style.ActionSheet
                    , new OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {

                }
            });
            mAlertView.setCancelable(true);
            mAlertView.show();
        }
    };
    /** ------------ 控件监听接口实现end ------------ */

    /** ------------ 方法began ------------ */
    /** ------------ 方法end ------------ */

}
