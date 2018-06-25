package com.jinke.housekeeper.saas.equipment.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseActivity;
import com.jinke.housekeeper.saas.equipment.bean.AddPointBean;
import com.jinke.housekeeper.saas.equipment.bean.AddPointListBean;
import com.jinke.housekeeper.saas.equipment.bean.DeviceTypeBean;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.config.SaveAddPointListConfig;
import com.jinke.housekeeper.saas.equipment.precenter.EquipmentSelectionPresenter;
import com.jinke.housekeeper.saas.equipment.ui.adapter.EquipmentSelectionAdapter;
import com.jinke.housekeeper.saas.equipment.ui.widget.CustomListView;
import com.jinke.housekeeper.saas.equipment.view.EquipmentSelectionView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static com.jinke.housekeeper.saas.equipment.ui.activity.StandBookActivity.EQUIPMENT_SELECTION;

public class EquipmentSelectionActivity extends BaseActivity<EquipmentSelectionView, EquipmentSelectionPresenter> implements EquipmentSelectionView {

    @Bind(R.id.equipment_selection_list_view)
    CustomListView inspectProjectListView;

    private Map<String, String> map;

    private EquipmentSelectionAdapter equipmentSelectionAdapter;
    private List<DeviceTypeBean.ListDataBean> infoList;
    private String parentId;
    private String typeId;
    private String projectId;
    private String dateId;

    @Override
    public EquipmentSelectionPresenter initPresenter() {
        return new EquipmentSelectionPresenter(EquipmentSelectionActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_equipment_selection;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_equipment_selection));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.equipment_back_ico);
        initDate();
        initAdapter();
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
        if (null != getIntent().getStringExtra("dateId") ){
            dateId = getIntent().getStringExtra("dateId");
        }
        projectId = EquipmentConfig.getProjectId();
        map = new HashMap<>();
        map.put("parentId", parentId);
        map.put("typeId", typeId);
        map.put("projectId", projectId);
        if ("1".equals(getIntent().getStringExtra("date"))) {
            map.put("pointId", getIntent().getStringExtra("date"));
        }
        presenter.getDeviceType(map);
    }

    private void initAdapter() {
        infoList = new ArrayList<>();
        equipmentSelectionAdapter = new EquipmentSelectionAdapter(this, infoList);
        inspectProjectListView.setAdapter(equipmentSelectionAdapter);
        inspectProjectListView.setOnItemClickListener(onItemClickListener);
    }

    /**
     * ------------ 界面数据初始化方法end ------------
     */

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (null == infoList.get(position).getInstallationOcation()) {
                parentId = infoList.get(position).getId();
                typeId = infoList.get(position).getType();
                initDate();
            } else {
                Intent intent = new Intent();
                intent.putExtra("date", infoList.get(position));
                if (null != dateId){
                    intent.putExtra("dateId", dateId);
                }
                // 设置结果，并进行传送
                EquipmentSelectionActivity.this.setResult(EQUIPMENT_SELECTION, intent);
                finish();
            }
        }
    };

    /**
     * ------------ 网络请求相关方式实现began ------------
     */
    @Override
    public void getDeviceTypeSuccess(DeviceTypeBean deviceTypeBean) {
        if (null == infoList) {
            infoList = new ArrayList<>();
        }
        infoList = deviceTypeBean.getListData();
        if (null != getIntent().getStringExtra("date") && "1".equals(getIntent().getStringExtra("date"))&& 0 != infoList.size()
                && null != infoList.get(0).getDeviceId()) {
            AddPointListBean addPointListBean = SaveAddPointListConfig.getSavePointListBean(this);
            Iterator<DeviceTypeBean.ListDataBean> it = infoList.iterator();
            while (it.hasNext()) {
                DeviceTypeBean.ListDataBean temp = it.next();
                for (AddPointBean bean : addPointListBean.getAddPointBeanList())
                    if (temp.getDeviceId().equals(bean.getDeviceId())) {
                        it.remove();
                    }
            }
        }
        equipmentSelectionAdapter.setInfoListBean(infoList);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(String msg) {

    }
    /** ------------ 网络请求相关方式实现end ------------ */

}
