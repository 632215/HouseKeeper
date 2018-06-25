package com.jinke.housekeeper.saas.handoverroom.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.handoverroom.base.BaseActivity;
import com.jinke.housekeeper.saas.handoverroom.base.BasePresenter;
import com.jinke.housekeeper.saas.handoverroom.bean.FindListDataBean;
import com.jinke.housekeeper.saas.handoverroom.ui.adapter.HandRoomSelectionAdapter;
import com.jinke.housekeeper.saas.handoverroom.ui.widget.CustomListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class EquipmentConfigListActivity extends BaseActivity {
    @Bind(R.id.equipment_config_list)
    CustomListView equipmentConfigList;

    private HandRoomSelectionAdapter handRoomSelectionAdapter;
    private List<FindListDataBean.ProjectListDataBean> projectListDataBeans;
    private List<FindListDataBean.DeviceListDataBean> DeviceListDataBean;
    private String type;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_equipment_config_list;
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

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.putExtra("type",type);
            if (null != projectListDataBeans) {
                intent.putExtra("date" ,projectListDataBeans.get(position));
            } else {
                intent.putExtra("date" ,DeviceListDataBean.get(position));
            }
            setResult(9211,intent);
            finish();
        }
    };

    private void initDate() {
        if (null != getIntent().getStringExtra("date") && null != getIntent().getStringExtra("type")) {
            try {
                type = getIntent().getStringExtra("type");
                JSONArray jsonArray = new JSONArray(getIntent().getStringExtra("date"));
                if ("1".equals(getIntent().getStringExtra("type"))) {
                    projectListDataBeans = new ArrayList<>();
                    for (int i = 0 ; i < jsonArray.length() ; i ++){
                        FindListDataBean.ProjectListDataBean dataBean = new FindListDataBean.ProjectListDataBean();
                        JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                        dataBean.setProjectName(jsonObject.getString("projectName"));
                        dataBean.setProjectId(jsonObject.getString("projectId"));
                        projectListDataBeans.add(dataBean);
                    }
                } else {
                    DeviceListDataBean = new ArrayList<>();
                    for (int i = 0 ; i < jsonArray.length() ; i ++){
                        FindListDataBean.DeviceListDataBean dataBean = new FindListDataBean.DeviceListDataBean();
                        JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                        dataBean.setDeviceName(jsonObject.getString("deviceName"));
                        dataBean.setDeviceSerial(jsonObject.getString("deviceSerial"));
                        DeviceListDataBean.add(dataBean);
                    }
                }
                initAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showShort( "数据加载失败请重新加载");
            finish();
        }
    }

    private void initAdapter() {
        handRoomSelectionAdapter = new HandRoomSelectionAdapter(EquipmentConfigListActivity.this ,projectListDataBeans ,DeviceListDataBean );
        equipmentConfigList.setAdapter(handRoomSelectionAdapter);
        equipmentConfigList.setOnItemClickListener(onItemClickListener);
    }

}
