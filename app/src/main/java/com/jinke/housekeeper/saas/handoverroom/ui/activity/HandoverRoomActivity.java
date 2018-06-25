package com.jinke.housekeeper.saas.handoverroom.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.handoverroom.base.BaseActivity;
import com.jinke.housekeeper.saas.handoverroom.config.HandoverRoomConfig;
import com.jinke.housekeeper.saas.handoverroom.precenter.HandoverRoomPresenter;
import com.jinke.housekeeper.saas.handoverroom.view.HandoverRoomView;
import com.jinke.housekeeper.saas.report.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HandoverRoomActivity extends BaseActivity<HandoverRoomView, HandoverRoomPresenter> implements HandoverRoomView {

    @Bind(R.id.equipment_activation_activity_layout)
    RelativeLayout equipmentActivationActivityLayout;
    @Bind(R.id.equipment_configuration_activity_layout)
    RelativeLayout equipmentConfigurationActivityLayout;
    @Bind(R.id.bean_handover_room_activity_layout)
    RelativeLayout beanHandoverRoomActivityLayout;
    @Bind(R.id.bean_handover_room_activity_hint)
    RelativeLayout beanHandoverRoomActivityHint;
    @Bind(R.id.equipment_activation_activity_hint)
    RelativeLayout equipmentActivationActivityHint;

    @Override
    public HandoverRoomPresenter initPresenter() {
        return new HandoverRoomPresenter(HandoverRoomActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_handover_room;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_handover_room));
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


    @OnClick({R.id.equipment_configuration_activity, R.id.bean_handover_room_activity, R.id.equipment_activation_activity})
    public void handoverRoomOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.equipment_activation_activity:
                ToastUtils.showShort(HandoverRoomActivity.this, "此功能暂时未开通，请联系管理员激活设备");
//                startActivity(new Intent(HandoverRoomActivity.this, EquipmentActivationActivity.class));
                break;
            case R.id.equipment_configuration_activity:
                startActivity(new Intent(HandoverRoomActivity.this, EquipmentConfigurationActivity.class));
                break;
            case R.id.bean_handover_room_activity:
                startActivity(new Intent(HandoverRoomActivity.this, BeganHandoverRoomActivity.class));
                break;
        }
    }

    private void initDate() {
        String openId = getIntent().getStringExtra("openId");
        String projectId = getIntent().getStringExtra("projectId");
        String projectName = getIntent().getStringExtra("projectName");
        String userName = getIntent().getStringExtra("userName");
        String userID = getIntent().getStringExtra("userID");
        HandoverRoomConfig.setProjectId(projectId);
        HandoverRoomConfig.setProjectName(projectName);
        HandoverRoomConfig.setUserName(userName);
        HandoverRoomConfig.setUserID(userID);
        if (null != openId && null != projectId && null != projectName) {
            Map<String, String> map = new HashMap<>();
            map.put("openId", openId);
            map.put("projectId", projectId);
            presenter.getUserToken(map);
        } else {
            ToastUtils.showShort(this, "获取信息失败，请重新获取");
            finish();
        }
    }


    private boolean showDialog = true;

    @Override
    public void getUserTokenSuccess() {
        if (!"1".equals(HandoverRoomConfig.getTokenBean().getIfManage())) {
            equipmentActivationActivityLayout.setVisibility(View.GONE);
            equipmentConfigurationActivityLayout.setVisibility(View.GONE);
            beanHandoverRoomActivityHint.setVisibility(View.VISIBLE);
            equipmentActivationActivityHint.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(String msg) {
        if (showDialog) {
            new AlertDialog.Builder(HandoverRoomActivity.this).setTitle("提示")//设置对话框标题
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

}
