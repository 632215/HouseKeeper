package com.jinke.housekeeper.saas.equipment.ui.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;

import com.blankj.utilcode.util.ToastUtils;
import com.dk.bleNfc.BleManager.BleManager;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseActivity;
import com.jinke.housekeeper.saas.equipment.bean.AddPointBean;
import com.jinke.housekeeper.saas.equipment.bean.AddPointCallBackBean;
import com.jinke.housekeeper.saas.equipment.bean.AddPointListBean;
import com.jinke.housekeeper.saas.equipment.bean.DeviceTypeBean;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.config.SaveAddPointListConfig;
import com.jinke.housekeeper.saas.equipment.precenter.AddIDListPresenter;
import com.jinke.housekeeper.saas.equipment.ui.adapter.AddIDListAdapter;
import com.jinke.housekeeper.saas.equipment.ui.widget.CustomListView;
import com.jinke.housekeeper.saas.equipment.view.AddIDListView;
import com.jinke.housekeeper.saas.patrol.ui.activity.PatrolLinkActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.widget.ProgressDialog;

import static com.jinke.housekeeper.saas.equipment.ui.activity.AddIDActivity.EquipmentAdd;
import static com.jinke.housekeeper.saas.equipment.ui.activity.StandBookActivity.EQUIPMENT_SELECTION;

public class AddIDListActivity extends BaseActivity<AddIDListView, AddIDListPresenter> implements AddIDListView {

    @Bind(R.id.add_id_list)
    CustomListView addIdList;

    private Map<String, String> map;
    private AddIDListAdapter addIDListAdapter;
    private List<AddPointBean> infoList;
    private String UID;
    private BroadcastReceiver bleIndoReceiver;

    @Override
    public AddIDListPresenter initPresenter() {
        return new AddIDListPresenter(AddIDListActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_add_id_list;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_add_id_list));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.equipment_back_ico);
        initAdapter();
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    private void initAdapter() {
        AddPointListBean addPointListBean = SaveAddPointListConfig.getSavePointListBean(this);
        if (null != addPointListBean && 0 != addPointListBean.getAddPointBeanList().size()) {
            infoList = addPointListBean.getAddPointBeanList();
        }
        if (null == infoList) {
            infoList = new ArrayList<>();
            AddPointListBean listBean = new AddPointListBean();
            listBean.setAddPointBeanList(infoList);
            if (0 == infoList.size() || null != infoList.get(infoList.size() - 1).getCardId()) {
                AddPointBean addPointBean = new AddPointBean();
                infoList.add(addPointBean);
            }
            SaveAddPointListConfig.setSavePointListBean(this, listBean);
        } else {
            if (!EquipmentConfig.getProjectId().equals(infoList.get(0).getProjectId())) {
                //设置缓存
                infoList = new ArrayList<>();
                AddPointListBean listBean = new AddPointListBean();
                listBean.setAddPointBeanList(infoList);
                SaveAddPointListConfig.setSavePointListBean(this, listBean);
            }
            if (0 == infoList.size() || null != infoList.get(infoList.size() - 1).getCardId()) {
                AddPointBean addPointBean = new AddPointBean();
                infoList.add(addPointBean);
            }
        }
        addIDListAdapter = new AddIDListAdapter(this, infoList);
        addIdList.setAdapter(addIDListAdapter);
        addIdList.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        // 设置结果，并进行传送
        this.setResult(EquipmentAdd, intent);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.add_id_list_save})
    public void AddIDListOnClickClickListener(View view) {
        switch (view.getId()) {
            case R.id.add_id_list_save:
                AddPointListBean addPointListBean = SaveAddPointListConfig.getSavePointListBean(this);
                if (null != addPointListBean && 0 != addPointListBean.getAddPointBeanList().size()) {
                    map = new HashMap<>();
                    String pointList = "[";
                    for (int i = 0; i < addPointListBean.getAddPointBeanList().size(); i++) {
                        AddPointBean bean = addPointListBean.getAddPointBeanList().get(i);
                        pointList += "{\"cardId\":\"" + bean.getCardId()
                                + "\",\"deviceId\":\"" + bean.getDeviceId() + "\"}";
                        if (i < addPointListBean.getAddPointBeanList().size() - 1) {
                            pointList += ",";
                        }
                    }
                    pointList += "]";
                    map.put("pointList", pointList);
                    presenter.getAddPoint(map);
                } else {
                    ToastUtils.showShort( "请选择需要绑定的设备");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EQUIPMENT_SELECTION:
                if (null != data && null != data.getSerializableExtra("date")) {
                    DeviceTypeBean.ListDataBean deviceTypeBean = (DeviceTypeBean.ListDataBean) data.getSerializableExtra("date");
                    String dateId = null;
                    if (null != data.getStringExtra("dateId")) {
                        dateId = data.getStringExtra("dateId");
                    }
                    if (null != dateId) {
                        for (AddPointBean pointBean : infoList) {
                            if (dateId.equals(pointBean.getCardId())) {
                                pointBean.setDeviceId(deviceTypeBean.getDeviceId());
                                pointBean.setProjectId(EquipmentConfig.getProjectId());
                                pointBean.setDeviceName(deviceTypeBean.getName() + " " + deviceTypeBean.getInstallationOcation());
                            }
                        }
                    }
                    //设置缓存
                    AddPointListBean addPointListBean = new AddPointListBean();
                    addPointListBean.setAddPointBeanList(infoList);
                    SaveAddPointListConfig.setSavePointListBean(this, addPointListBean);
                    if (0 == infoList.size() || null != infoList.get(infoList.size() - 1).getCardId()) {
                        AddPointBean addPointBean = new AddPointBean();
                        infoList.add(addPointBean);
                    }
                    addIDListAdapter.setInfoListBean(infoList);
                }
                break;
        }
    }

    /**
     * ------------ 网络请求相关方式实现began ------------
     */
    @Override
    public void getAddPointSuccess(AddPointCallBackBean addPointCallBackBean) {

        AddPointListBean addPointListBean = new AddPointListBean();
        infoList.clear();
        addPointListBean.setAddPointBeanList(infoList);
        SaveAddPointListConfig.setSavePointListBean(this, addPointListBean);
        if (null == addPointCallBackBean.getCardId() || "".equals(addPointCallBackBean.getCardId())) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(AddIDListActivity.this).setTitle("提示")//设置对话框标题
                            .setMessage("添加成功")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    dialog.dismiss();
                                    setResult(EquipmentAdd, new Intent());
                                    AddIDListActivity.this.finish();
                                }
                            }).show();//在按键响应事件中显示此对话框
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(AddIDListActivity.this).setTitle("提示")//设置对话框标题
                            .setMessage("添加成功,但有部分点位重复，请重新添加")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    dialog.dismiss();
                                    setResult(EquipmentAdd, new Intent());
                                    AddIDListActivity.this.finish();
                                }
                            }).show();//在按键响应事件中显示此对话框
                }
            });
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(AddIDListActivity.this).setTitle("提示")//设置对话框标题
                        .setMessage(msg)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                dialog.dismiss();
                            }
                        }).show();//在按键响应事件中显示此对话框
            }
        });
    }

    /** ------------ 网络请求相关方式实现end ------------ */

    /**
     * ------------ 接收设备数据began ------------
     */

    private void registerReceiver() {
        bleIndoReceiver = new BleIndoReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PatrolLinkActivity.BLE_INFO_BROADCAST_ACTION);
        intentFilter.addAction(PatrolLinkActivity.BLE_LINK_BROADCAST_ACTION);
        registerReceiver(bleIndoReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        unregisterReceiver(bleIndoReceiver);
    }

    private Boolean isShowPatrolAddDialog = true;

    private class BleIndoReceiver extends BroadcastReceiver {
        //接收到广播后自动调用该方法
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case PatrolLinkActivity.BLE_INFO_BROADCAST_ACTION:
                    UID = intent.getStringExtra(PatrolLinkActivity.BLE_INFO_UID);
                    //设置点位信息
                    if ((null != infoList.get(infoList.size() - 1).getCardId() && null == infoList.get(infoList.size() - 1).getDeviceId())
                            || (null == infoList.get(infoList.size() - 1).getCardId() && null == infoList.get(infoList.size() - 1).getDeviceId())) {
                        //判断点位是否重复
                        for (int i = 0; i < infoList.size(); i++) {
                            if (UID.equals(infoList.get(i).getCardId()) && null != infoList.get(i).getDeviceId()) {
                                new AlertDialog.Builder(AddIDListActivity.this).setTitle("提示")//设置对话框标题
                                        .setMessage("该点位已经添加，请勿重复添加")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                                break;
                            }
                            if (i == infoList.size() - 1) {
                                infoList.get(infoList.size() - 1).setCardId(UID);
                            }
                        }
                    } else {
                        if (0 == infoList.size() || null != infoList.get(infoList.size() - 1).getCardId()) {
                            AddPointBean addPointBean = new AddPointBean();
                            infoList.add(addPointBean);
                        }
                    }
                    addIDListAdapter.setInfoListBean(infoList);
                    break;

                case PatrolLinkActivity.BLE_LINK_BROADCAST_ACTION:
                    if (BleManager.STATE_DISCONNECTED == intent.getIntExtra(PatrolLinkActivity.BLE_LINK_UID, -1)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isShowPatrolAddDialog) {
                                    new AlertDialog.Builder(AddIDListActivity.this).setTitle("提示")//设置对话框标题
                                            .setMessage("设备连接断开请重新连接")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    dialog.dismiss();
                                                }
                                            }).show();
                                    isShowPatrolAddDialog = !isShowPatrolAddDialog;
                                }
                            }
                        });
                    }
                    break;
            }

        }
    }

    private ProgressDialog dialog;

    public void showDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

    /**
     * 关闭对话框
     */
    public void dimissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
    /** ------------ 接收设备数据began ------------*/

}
