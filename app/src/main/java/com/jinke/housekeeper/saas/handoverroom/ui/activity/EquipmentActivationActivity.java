package com.jinke.housekeeper.saas.handoverroom.ui.activity;

import android.view.View;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.handoverroom.base.BaseActivity;
import com.jinke.housekeeper.saas.handoverroom.base.BasePresenter;

public class EquipmentActivationActivity extends BaseActivity {

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_equipment_activation;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.equipment_activation_activity));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.handover_room_back_ico);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }
}
