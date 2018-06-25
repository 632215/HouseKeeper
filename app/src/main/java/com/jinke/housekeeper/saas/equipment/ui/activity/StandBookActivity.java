package com.jinke.housekeeper.saas.equipment.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseActivity;
import com.jinke.housekeeper.saas.equipment.base.BaseFragment;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.bean.DeviceInfoBean;
import com.jinke.housekeeper.saas.equipment.bean.DeviceTypeBean;
import com.jinke.housekeeper.saas.equipment.bean.TaskBean;
import com.jinke.housekeeper.saas.equipment.bean.TaskListBean;
import com.jinke.housekeeper.saas.equipment.precenter.BaseInfoPresenter;
import com.jinke.housekeeper.saas.equipment.ui.fragment.BaseInfoFragment;
import com.jinke.housekeeper.saas.equipment.ui.fragment.InspectProjectFragment;
import com.jinke.housekeeper.saas.equipment.ui.fragment.MaintenanceFragment;
import com.jinke.housekeeper.saas.equipment.utils.SharedPreferenceUtil;
import com.jinke.housekeeper.saas.equipment.view.BaseInfoView;
import com.tencent.stat.StatService;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jinke.housekeeper.saas.equipment.bean.TaskBean.TASK_TO_PERFORM;
import static com.jinke.housekeeper.saas.equipment.ui.activity.BluetoothLinkActivity.STAND_BOOK;

public class StandBookActivity extends BaseActivity<BaseInfoView, BaseInfoPresenter> implements BaseInfoView {

    @Bind(R.id.stand_book_name)
    TextView standBookName;
    @Bind(R.id.stand_book_radio_group)
    RadioGroup standBookRadioGroup;
    @Bind(R.id.stand_book_began_button)
    TextView standBookBeganButton;
    @Bind(R.id.stand_book_began_layout)
    RelativeLayout standBookBeganLayout;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private BaseInfoFragment baseInfoFragment;
    private InspectProjectFragment inspectProjectFragment;
    private MaintenanceFragment maintenanceFragment;
    private DeviceTypeBean.ListDataBean deviceTypeBean;
    private TaskListBean taskListBean;

    public final static int EQUIPMENT_SELECTION = 9211;

    @Override
    public BaseInfoPresenter initPresenter() {
        return new BaseInfoPresenter(StandBookActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_stand_book;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_stand_book));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.equipment_back_ico);
        showForwardView(R.string.stand_book_link);
        showForwardViewColor(getResources().getColor(R.color.equipment_text_3));
        taskListBean = (TaskListBean) SharedPreferenceUtil.get(StandBookActivity.this, "DailyPatrolActivity", "TaskListBean");
        setDefaultRadio();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        Intent bluetoothLinkIntent = new Intent(StandBookActivity.this, BluetoothLinkActivity.class);
        bluetoothLinkIntent.putExtra("date", "STAND_BOOK");
        startActivityForResult(bluetoothLinkIntent, STAND_BOOK);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(StandBookActivity.this);
        StatService.trackBeginPage(StandBookActivity.this, getString(R.string.activity_stand_book));
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(StandBookActivity.this);
        StatService.trackEndPage(StandBookActivity.this, getString(R.string.activity_stand_book));
    }


    @OnClick({R.id.stand_book_name, R.id.stand_book_began_button})
    public void standBookOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.stand_book_name:
                startActivityForResult(new Intent(this, EquipmentSelectionActivity.class), EQUIPMENT_SELECTION);
                break;
            case R.id.stand_book_began_button:
                Intent bluetoothLinkIntent = new Intent(StandBookActivity.this, DailyPatrolActivity.class);
                startActivity(bluetoothLinkIntent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EQUIPMENT_SELECTION:
                if (null != data && null != data.getSerializableExtra("date")) {
                    deviceTypeBean = (DeviceTypeBean.ListDataBean) data.getSerializableExtra("date");
                    standBookName.setText(deviceTypeBean.getName() + "  " + deviceTypeBean.getInstallationOcation());
                    standBookBeganLayout.setVisibility(View.GONE);
                    if (null != taskListBean && null != taskListBean.getListData()) {
                        for (TaskBean taskBean : taskListBean.getListData()) {
                            if (TASK_TO_PERFORM.equals(taskBean.getTaskStatus())) {
                                for (TaskBean.PointListBean listBean : taskBean.getPointList()) {
                                    if (deviceTypeBean.getDeviceId().equals(listBean.getDeviceId()) && "".equals(listBean.getStatus())) {
                                        standBookBeganLayout.setVisibility(View.VISIBLE);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case STAND_BOOK:
                if (null != data && null != data.getStringExtra("date")) {
                    deviceTypeBean = new DeviceTypeBean.ListDataBean();
                    deviceTypeBean.setCardId(data.getStringExtra("date"));
                    Map map = new HashMap<>();
                    map.put("cardId", deviceTypeBean.getCardId());
                    presenter.getDeviceInfo(map);
                }
                break;
        }
    }

    private GetName getName = new GetName() {
        @Override
        public void getName(final String name) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    standBookName.setText(name);
                }
            });
        }

    };

    private void setDefaultRadio() {
        standBookRadioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();

        baseInfoFragment = new BaseInfoFragment();
        baseInfoFragment.setGetName(getName);
        inspectProjectFragment = new InspectProjectFragment();
        maintenanceFragment = new MaintenanceFragment();

        transaction.add(R.id.stand_book_frameLayout, baseInfoFragment);
        transaction.add(R.id.stand_book_frameLayout, inspectProjectFragment);
        transaction.add(R.id.stand_book_frameLayout, maintenanceFragment);

        transaction.show(baseInfoFragment);
        transaction.hide(inspectProjectFragment);
        transaction.hide(maintenanceFragment);
        transaction.commit();
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            fragmentManager = getFragmentManager();
            transaction = fragmentManager.beginTransaction();
            switch (checkedId) {
                case R.id.stand_book_base_info:
                    transaction.show(baseInfoFragment);
                    transaction.hide(inspectProjectFragment);
                    transaction.hide(maintenanceFragment);
                    break;
                case R.id.stand_book_inspect_project:
                    transaction.hide(baseInfoFragment);
                    transaction.show(inspectProjectFragment);
                    transaction.hide(maintenanceFragment);
                    break;
                case R.id.stand_book_maintenance_project:
                    transaction.hide(baseInfoFragment);
                    transaction.hide(inspectProjectFragment);
                    transaction.show(maintenanceFragment);
                    break;
            }
            transaction.commit();
        }
    };

    /**
     * 获取选择设备实体
     *
     * @return
     */
    public DeviceTypeBean.ListDataBean getDeviceTypeBean() {
        return deviceTypeBean;
    }

    @Override
    public void getDeviceInfo(DeviceInfoBean deviceInfoBean) {
        if (null != deviceInfoBean && null != deviceInfoBean.getListData() && 0 != deviceInfoBean.getListData().size()) {
            deviceTypeBean.setName(deviceInfoBean.getListData().get(0).getDeviceName());
            deviceTypeBean.setId(deviceInfoBean.getListData().get(0).getTypeId());
            deviceTypeBean.setInstallationOcation(deviceInfoBean.getListData().get(0).getInstallationOcation());
            standBookBeganLayout.setVisibility(View.GONE);
            if (null != taskListBean && null != taskListBean.getListData()) {
                for (TaskBean taskBean : taskListBean.getListData()) {
                    if (TASK_TO_PERFORM.equals(taskBean.getTaskStatus())) {
                        for (TaskBean.PointListBean listBean : taskBean.getPointList()) {
                            if (deviceTypeBean.getId().equals(listBean.getTypeId()) && "".equals(listBean.getStatus())) {
                                standBookBeganLayout.setVisibility(View.VISIBLE);
                                break;
                            }
                        }
                    }
                }
            }
            inspectProjectFragment.onResume();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(String msg) {

    }

    public interface GetName {
        void getName(String name);
    }

}
