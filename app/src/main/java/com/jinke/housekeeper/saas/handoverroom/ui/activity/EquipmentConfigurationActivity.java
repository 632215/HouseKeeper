package com.jinke.housekeeper.saas.handoverroom.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.handoverroom.base.BaseActivity;
import com.jinke.housekeeper.saas.handoverroom.bean.FindListDataBean;
import com.jinke.housekeeper.saas.handoverroom.precenter.EquipmentConfigurationPresenter;
import com.jinke.housekeeper.saas.handoverroom.ui.widget.CustomDialog;
import com.jinke.housekeeper.saas.handoverroom.view.EquipmentConfigurationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class EquipmentConfigurationActivity extends BaseActivity<EquipmentConfigurationView, EquipmentConfigurationPresenter>
        implements EquipmentConfigurationView {

    @Bind(R.id.configuration_project)
    TextView configurationProject;
    @Bind(R.id.configuration_equipment)
    TextView configurationEquipment;

    private FindListDataBean findListDataBean;
    private FindListDataBean.ProjectListDataBean projectListDataBean;
    private FindListDataBean.DeviceListDataBean deviceListDataBean;

    @Override
    public EquipmentConfigurationPresenter initPresenter() {
        return new EquipmentConfigurationPresenter(EquipmentConfigurationActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_equipment_configuration;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.equipment_configuration_activity));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.handover_room_back_ico);

        initDate();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 9211:
                if (null != data && null != data.getSerializableExtra("date")) {
                    if ("1".equals(data.getStringExtra("type"))) {
                        projectListDataBean = (FindListDataBean.ProjectListDataBean) data.getSerializableExtra("date");
                        if (null != projectListDataBean) {
                            configurationProject.setText(projectListDataBean.getProjectName());
                        } else {
                            ToastUtils.showShort( "数据加载失败请重新选择");
                        }
                    } else {
                        deviceListDataBean = (FindListDataBean.DeviceListDataBean) data.getSerializableExtra("date");
                        if (null != deviceListDataBean) {
                            configurationEquipment.setText(deviceListDataBean.getDeviceName());
                        } else {
                            ToastUtils.showShort("数据加载失败请重新选择");
                        }
                    }
                }
                break;
        }
    }

    @OnClick({R.id.configuration_project_layout, R.id.configuration_equipment_layout, R.id.configuration_project_sure})
    public void EquipmentConfigurationOnClickListener(View view) {
        Intent intent = new Intent(EquipmentConfigurationActivity.this, EquipmentConfigListActivity.class);
        JSONArray jsonArray = new JSONArray();
        try {
            switch (view.getId()) {
                case R.id.configuration_project_layout:
                    if (null != findListDataBean && null != findListDataBean.getProjectListData()) {
                        for (FindListDataBean.ProjectListDataBean projectListDataBean : findListDataBean.getProjectListData()) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("projectName", projectListDataBean.getProjectName());
                            jsonObject.put("projectId", projectListDataBean.getProjectId());
                            jsonArray.put(jsonObject.toString());
                        }
                        intent.putExtra("date", jsonArray.toString());
                        intent.putExtra("type", "1");
                        startActivityForResult(intent, 9211);
                    } else {
                        ToastUtils.showShort("没有小区信息");
                    }
                    break;
                case R.id.configuration_equipment_layout:
                    if (null != findListDataBean && null != findListDataBean.getDeviceListData()) {
                        for (FindListDataBean.DeviceListDataBean deviceListDataBean : findListDataBean.getDeviceListData()) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("deviceName", deviceListDataBean.getDeviceName());
                            jsonObject.put("deviceSerial", deviceListDataBean.getDeviceSerial());
                            jsonArray.put(jsonObject.toString());
                        }
                        intent.putExtra("date", jsonArray.toString());
                        intent.putExtra("type", "2");
                        startActivityForResult(intent, 9211);
                    } else {
                        ToastUtils.showShort( "没有设备信息");
                    }
                    break;

                case R.id.configuration_project_sure:
                    if (null != projectListDataBean && null != deviceListDataBean) {
                        CustomDialog.Builder builder = new CustomDialog.Builder(this);
                        builder.setMessage(deviceListDataBean.getDeviceName() + "配属到"+ projectListDataBean.getProjectName());
                        builder.setTitle(getString(R.string.checking_dialog_title));
                        builder.setPositiveButton(getString(R.string.checking_dialog_back),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.setNegativeButton(getString(R.string.checking_dialog_sure),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Map<String, String> map = new HashMap<>();
                                        map.put("deviceSerial",deviceListDataBean.getDeviceSerial());
                                        map.put("projectId",projectListDataBean.getProjectId());
                                        presenter.bindingDevice(map);
                                        dialog.dismiss();
                                    }
                                });
                        builder.create().show();
                    } else {
                        ToastUtils.showShort("请选择设备对应的项目");
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void findListDataSuccess(FindListDataBean findListDataBean) {
        EquipmentConfigurationActivity.this.findListDataBean = findListDataBean;
    }

    @Override
    public void bindingDeviceSuccess() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage("设备配属成功");
        builder.setTitle(getString(R.string.checking_dialog_title));

        builder.setNegativeButton(getString(R.string.checking_dialog_sure),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        builder.create().show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(String msg) {

    }

    private void initDate() {
        Map<String, String> map = new HashMap<>();
        presenter.findListData(map);
    }

}
