package com.jinke.housekeeper.saas.equipment.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseFragment;
import com.jinke.housekeeper.saas.equipment.bean.DeviceInfoBean;
import com.jinke.housekeeper.saas.equipment.precenter.BaseInfoPresenter;
import com.jinke.housekeeper.saas.equipment.ui.activity.StandBookActivity;
import com.jinke.housekeeper.saas.equipment.view.BaseInfoView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class BaseInfoFragment extends BaseFragment<BaseInfoView, BaseInfoPresenter> implements BaseInfoView {

    @Bind(R.id.stand_book_administrative_areas)
    TextView standBookAdministrativeAreas;
    @Bind(R.id.stand_book_equipment_number)
    TextView standBookEquipmentNumber;
    @Bind(R.id.stand_book_equipment_name)
    TextView standBookEquipmentName;
    @Bind(R.id.stand_book_installation_location)
    TextView standBookInstallationLocation;

    @Bind(R.id.stand_book_person_liable)
    TextView standBookPersonLiable;
    @Bind(R.id.stand_book_equipment_body)
    LinearLayout standBookEquipmentBody;
    @Bind(R.id.stand_book_equipment_details_ico)
    ImageView standBookEquipmentDetailsIco;

    @Bind(R.id.stand_book_building)
    TextView standBookBuilding;
    @Bind(R.id.stand_book_quality_assurance)
    TextView standBookQualityAssurance;
    @Bind(R.id.stand_book_up_time)
    TextView standBookUpTime;
    @Bind(R.id.stand_book_down_time)
    TextView standBookDownTime;
    @Bind(R.id.stand_book_year_date)
    TextView standBookYearDate;
    @Bind(R.id.stand_book_manufacturer)
    TextView standBookManufacturer;
    @Bind(R.id.stand_book_place_origin)
    TextView standBookPlaceOrigin;
    @Bind(R.id.stand_book_factory_number)
    TextView standBookFactoryNumber;
    @Bind(R.id.stand_book_date_production)
    TextView standBookDateProduction;
    @Bind(R.id.stand_book_quality_assurance_due)
    TextView standBookQualityAssuranceDue;
    @Bind(R.id.stand_book_discard_time_limit)
    TextView standBookDiscardTimeLimit;
    @Bind(R.id.stand_book_active_time)
    TextView standBookActiveTime;
    @Bind(R.id.stand_book_rejection_time)
    TextView standBookRejectionTime;
    @Bind(R.id.stand_book_state)
    TextView standBookState;
    @Bind(R.id.stand_book_koriyasu_units)
    TextView standBookKoriyasuUnits;
    @Bind(R.id.stand_book_date_start_maintenance_contract)
    TextView standBookDateStartMaintenanceContract;
    @Bind(R.id.stand_book_date_end_maintenance_contract)
    TextView standBookDateEndMaintenanceContract;
    @Bind(R.id.stand_book_linkman_contacts)
    TextView standBookLinkmanContacts;
    @Bind(R.id.stand_book_contact_information)
    TextView standBookContactInformation;
    @Bind(R.id.stand_book_supplier)
    TextView standBookSupplier;
    @Bind(R.id.stand_book_installation_unit)
    TextView standBookInstallationUnit;
    @Bind(R.id.stand_book_installation_contract_no)
    TextView standBookInstallationContractNo;
    @Bind(R.id.stand_book_installation_contract_date_start)
    TextView standBookInstallationContractDateStart;
    @Bind(R.id.stand_book_installation_contract_date_end)
    TextView standBookInstallationContractDateEnd;
    @Bind(R.id.stand_book_supplier_state)
    TextView standBookSupplierState;

    private String cardId;
    private boolean showEquipmentDetails;
    private Map map;
    private DeviceInfoBean deviceInfoBean;

    @Override
    public BaseInfoPresenter initPresenter() {
        return new BaseInfoPresenter(getActivity());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_info;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        showEquipmentDetails = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        initDateDeviceId();
    }

    @OnClick({R.id.stand_book_equipment_details})
    public void standBookOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.stand_book_equipment_details:
                if (null != deviceInfoBean) {
                    if (showEquipmentDetails) {
                        standBookEquipmentBody.setVisibility(View.GONE);
                        standBookEquipmentDetailsIco.setImageResource(R.drawable.equipment_open_ico);
                    } else {
                        standBookEquipmentBody.setVisibility(View.VISIBLE);
                        standBookEquipmentDetailsIco.setImageResource(R.drawable.equipment_next_ico);
                    }
                    showEquipmentDetails = !showEquipmentDetails;
                } else {
                    ToastUtils.showShort("请选择需要查看的设备");
                }
                break;
        }
    }


    @Override
    public void getDeviceInfo(DeviceInfoBean deviceInfoBean) {
        if (null != deviceInfoBean) {
            this.deviceInfoBean = deviceInfoBean;
            getName.getName(deviceInfoBean.getListData().get(0).getInstallationOcation() + "  " + deviceInfoBean.getListData().get(0).getDeviceName());
            //设备基础信息
            standBookAdministrativeAreas.setText(deviceInfoBean.getListData().get(0).getProjectName());
            standBookEquipmentNumber.setText(deviceInfoBean.getListData().get(0).getDeviceNum());
            standBookEquipmentName.setText(deviceInfoBean.getListData().get(0).getDeviceName());
            standBookInstallationLocation.setText(deviceInfoBean.getListData().get(0).getInstallationOcation());
            //设备详情
            standBookBuilding.setText(deviceInfoBean.getListData().get(0).getInstallationOcation());
            standBookQualityAssurance.setText(deviceInfoBean.getListData().get(0).getShelfLife());
            standBookUpTime.setText(deviceInfoBean.getListData().get(0).getEnableTime());
//        standBookDownTime.setText(deviceInfoBean.getListData().get(0));
            standBookPersonLiable.setText(deviceInfoBean.getListData().get(0).getLiabilityPerson());
            //生产单位信息
            standBookYearDate.setText(deviceInfoBean.getListData().get(0).getAnnualTime());
            standBookManufacturer.setText(deviceInfoBean.getListData().get(0).getManufacturer());
            standBookPlaceOrigin.setText(deviceInfoBean.getListData().get(0).getHome());
            standBookFactoryNumber.setText(deviceInfoBean.getListData().get(0).getFactoryNum());
            standBookDateProduction.setText(deviceInfoBean.getListData().get(0).getFactoryTime());
            standBookQualityAssuranceDue.setText(deviceInfoBean.getListData().get(0).getShelfLife());
            standBookDiscardTimeLimit.setText(deviceInfoBean.getListData().get(0).getScrapTime());
            standBookActiveTime.setText(deviceInfoBean.getListData().get(0).getFactoryNum());
            standBookRejectionTime.setText(deviceInfoBean.getListData().get(0).getScrapTime());
//        standBookState.setText(deviceInfoBean.getListData().get(0));
            //维保单位信息
            standBookKoriyasuUnits.setText(deviceInfoBean.getListData().get(0).getServiceUnit());
            standBookDateStartMaintenanceContract.setText(deviceInfoBean.getListData().get(0).getMaintenanceStime());
            standBookDateEndMaintenanceContract.setText(deviceInfoBean.getListData().get(0).getMaintenanceEtime());
            standBookLinkmanContacts.setText(deviceInfoBean.getListData().get(0).getLiabilityPerson());
//        standBookContactInformation.setText(deviceInfoBean.getListData().get(0));
            //供应商信息
            standBookSupplier.setText(deviceInfoBean.getListData().get(0).getSupplier());
            standBookInstallationUnit.setText(deviceInfoBean.getListData().get(0).getInstallUnitId());
            standBookInstallationContractNo.setText(deviceInfoBean.getListData().get(0).getContractNum());
            standBookInstallationContractDateStart.setText(deviceInfoBean.getListData().get(0).getContractStime());
            standBookInstallationContractDateEnd.setText(deviceInfoBean.getListData().get(0).getContractEtime());
//        standBookSupplierState.setText(deviceInfoBean.getListData().get(0));
        } else {
            ToastUtils.showShort("对应点位信息不存在");
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(String msg) {

    }

    /**
     * 通过设备ID初始化台账信息
     */
    private void initDateDeviceId() {
        StandBookActivity standBookActivity = (StandBookActivity) getActivity();
        if (null != standBookActivity && null != standBookActivity.getDeviceTypeBean()) {
            if (null != standBookActivity.getDeviceTypeBean().getDeviceId()) {
                map = new HashMap<>();
                map.put("deviceId", standBookActivity.getDeviceTypeBean().getDeviceId());
                presenter.getDeviceInfo(map);
            } else if (null != standBookActivity.getDeviceTypeBean().getCardId()) {
                map = new HashMap<>();
                map.put("cardId", standBookActivity.getDeviceTypeBean().getCardId());
                presenter.getDeviceInfo(map);
            }
        }
    }
    private StandBookActivity.GetName getName;
    public void setGetName(StandBookActivity.GetName getName) {
        this.getName = getName;
    }
}
