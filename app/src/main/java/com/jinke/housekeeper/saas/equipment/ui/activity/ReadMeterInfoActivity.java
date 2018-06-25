package com.jinke.housekeeper.saas.equipment.ui.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseActivity;
import com.jinke.housekeeper.saas.equipment.bean.ParameterBean;
import com.jinke.housekeeper.saas.equipment.bean.ReadWatchBean;
import com.jinke.housekeeper.saas.equipment.precenter.ThreeTablePresenter;
import com.jinke.housekeeper.saas.equipment.ui.adapter.ReadMeterInfoAdapter;
import com.jinke.housekeeper.saas.equipment.ui.widget.CustomDialog;
import com.jinke.housekeeper.saas.equipment.ui.widget.CustomListView;
import com.jinke.housekeeper.saas.equipment.view.ThreeTableView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class ReadMeterInfoActivity extends BaseActivity<ThreeTableView, ThreeTablePresenter> implements ThreeTableView {

    @Bind(R.id.read_meter_info_meter_time)
    TextView readMeterInfoMeterTime;
    @Bind(R.id.read_meter_info_meter_site)
    TextView readMeterInfoMeterSite;
    @Bind(R.id.read_meter_info_list)
    CustomListView readMeterInfoList;

    private String deviceId;
    private Map<String, String> map;
    private ReadMeterInfoAdapter readMeterInfoAdapter;
    private List<ParameterBean.ListSubjectBean> returnListSubjectBean;
    private ParameterBean parameterBean;

    @Override
    public ThreeTablePresenter initPresenter() {
        return new ThreeTablePresenter(ReadMeterInfoActivity.this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_read_meter_info;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.activity_read_meter_info));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardViewIco(R.drawable.equipment_back_ico);
        initDate();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.read_meter_info_cancel, R.id.read_meter_info_submit})
    public void readMeterInfoOnClickListener(View view) {
        switch (view.getId()) {
            case R.id.read_meter_info_cancel:
                finish();
                break;

            case R.id.read_meter_info_submit:
                returnListSubjectBean.size();
                boolean isSubment = true;//是否提交数据 true提交 false不提交
                //判断是否后空数据没有输入，有就跳出循环不提交
                if (null == parameterBean || null == returnListSubjectBean || 0 == returnListSubjectBean.size()) {
                    isSubment = false;
                }
                for (ParameterBean.ListSubjectBean subjectBean : returnListSubjectBean) {
                    if (null == subjectBean.getToday() || "".equals(subjectBean.getToday())) {
                        isSubment = false;
                    }
                }
                if (isSubment) {
                    try {
                        //数据json话
                        JSONArray submentJsonArray = new JSONArray();
                        JSONObject submentJsonObject = new JSONObject();
                        submentJsonObject.put("watchId", deviceId);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        submentJsonObject.put("readTime", dateFormat.format(new java.util.Date()));
                        //环境参数数据
                        JSONArray parameterList = new JSONArray();
                        for (ParameterBean.ListParameterBean parameterBean : parameterBean.getListParameter()) {
                            JSONObject parameterBeanObject = new JSONObject();
                            parameterBeanObject.put("parameterId",parameterBean.getId());
                            parameterBeanObject.put("parameterName",parameterBean.getParameter());
                            parameterBeanObject.put("parameterValue","");
                            parameterList.put(parameterBeanObject);
                        }
                        submentJsonObject.put("parameterList", parameterList);

                        //抄表参数数据
                        JSONArray subjectList = new JSONArray();
                        for (ParameterBean.ListSubjectBean subjectBean : returnListSubjectBean) {
                            JSONObject subjectBeanObject = new JSONObject();
                            subjectBeanObject.put("subjectId",subjectBean.getId());
                            subjectBeanObject.put("subjectName",subjectBean.getSubject());
                            subjectBeanObject.put("subjectValue",subjectBean.getToday());
                            subjectBeanObject.put("ifFirst",subjectBean.getIfFirst());
                            subjectList.put(subjectBeanObject);
                        }
                        submentJsonObject.put("subjectList", subjectList);
                        submentJsonArray.put(submentJsonObject);
                        //发送数据
                        map = new HashMap<>();
                        map.put("resultList", submentJsonArray.toString());
                        presenter.uploadData(map);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtils.showShort( "请完善数据再次提交");
                }
                break;
        }
    }

    private void initDate() {
        if (null != getIntent().getStringExtra("deviceId")
                && null != getIntent().getStringExtra("location")
                && null != getIntent().getStringExtra("name")) {
            returnListSubjectBean = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            readMeterInfoMeterTime.setText(dateFormat.format(new java.util.Date()));
            readMeterInfoMeterSite.setText(getIntent().getStringExtra("location") + " " + getIntent().getStringExtra("name"));
            deviceId = getIntent().getStringExtra("deviceId");
            map = new HashMap<>();
            map.put("deviceId", deviceId);
            presenter.getParameter(map);
        } else {
            ToastUtils.showShort( "获取数据失败，请重新选择");
            finish();
        }
    }

    @Override
    public void readWatchSuccess(ReadWatchBean readWatchBean) {

    }

    @Override
    public void getParameterSuccess(ParameterBean parameterBean) {
        if (null != parameterBean &&
                null != parameterBean.getListSubject() && parameterBean.getListSubject().size() > 0) {
            this.parameterBean = new ParameterBean();
            this.parameterBean = parameterBean;
            readMeterInfoAdapter = new ReadMeterInfoAdapter(ReadMeterInfoActivity.this, parameterBean.getListSubject(), infoInterface);
            readMeterInfoList.setAdapter(readMeterInfoAdapter);
        }
    }

    @Override
    public void completeTaskSuccess() {
        CustomDialog.Builder builder1 = new CustomDialog.Builder(this);
        builder1.setMessage(getString(R.string.read_meter_info_dialog_message));
        builder1.setTitle(getString(R.string.read_meter_info_dialog_title));
        builder1.setNegativeButton(getString(R.string.checking_dialog_sure),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        builder1.create().show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onError(String msg) {

    }

    public interface GetInputInfoInterface {
        void getInputInfo(List<ParameterBean.ListSubjectBean> listSubjectBean);
    }

    private GetInputInfoInterface infoInterface = new GetInputInfoInterface() {
        @Override
        public void getInputInfo(List<ParameterBean.ListSubjectBean> listSubjectBean) {
            returnListSubjectBean = listSubjectBean;
        }

    };

}
