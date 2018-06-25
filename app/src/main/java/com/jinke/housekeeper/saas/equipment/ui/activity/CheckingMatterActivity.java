package com.jinke.housekeeper.saas.equipment.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.google.gson.Gson;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.equipment.bean.TaskBean;
import com.jinke.housekeeper.saas.equipment.config.Constant;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.ui.widget.CustomDialog;
import com.jinke.housekeeper.saas.report.bean.BackUpsInfo;
import com.jinke.housekeeper.saas.report.bean.RecorderBean;
import com.jinke.housekeeper.saas.report.bean.SelfCheckInfo;
import com.jinke.housekeeper.saas.report.helper.LocationService;
import com.jinke.housekeeper.saas.report.presenter.ReportRegisterActivityPresenter;
import com.jinke.housekeeper.saas.report.service.listener.PictureSelectListener;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.ImagePagerActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.PicDealingActivity;
import com.jinke.housekeeper.saas.report.ui.adapter.PictureGridAdapter;
import com.jinke.housekeeper.saas.report.ui.widget.LifePaymentWindow;
import com.jinke.housekeeper.saas.report.ui.widget.MyGridView;
import com.jinke.housekeeper.saas.report.ui.widget.ProgressDialog;
import com.jinke.housekeeper.saas.report.util.KeyBoardUtils;
import com.jinke.housekeeper.saas.report.util.PictureSelectUtils;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.saas.report.view.ReportRegistActivityView;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 巡检报事 不属于巡检模块
 */
public class CheckingMatterActivity extends BaseActivity<ReportRegistActivityView, ReportRegisterActivityPresenter> implements
        View.OnClickListener, ReportRegistActivityView, RadioUtil.RadioPlayListener, PictureGridAdapter.PicDeleteListener, PictureSelectListener {

    @Bind(R.id.checking_matter_object)
    TextView matterObject;
    @Bind(R.id.checking_matter_location)
    TextView matterLocation;
    @Bind(R.id.checking_matter_problem_promoter)
    TextView matterProblemPromoter;
    @Bind(R.id.checking_matter_submit_time)
    TextView matterSubmitTime;
    @Bind(R.id.checking_matter_submit_info)
    EditText matterSubmitInfo;
    @Bind(R.id.checking_matter_submit_img_list)
    MyGridView matterSubmitImgList;
    @Bind(R.id.checking_matter_activity)
    LinearLayout checkingMatterActivity;

    private TaskBean.PointListBean pointListBean;

    //    百度地图
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private String latitude = "";// 纬度
    private String lontitude = "";// 经度
    private String addr = "";//地址信息
    private LocationService locationService;
    private static final int SELF_DEAL = 4;//标识常量
    private ProgressDialog dialog;//加载进度框

    //照片适配器
    private PictureGridAdapter pictureAdatper;
    private ArrayList<String> pictureList = new ArrayList<>();
    private ArrayList<RecorderBean> recorderList = new ArrayList<>();
    private LifePaymentWindow window;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_checking_matter;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CommonlyUtils.listObjBean = null;
        CommonlyUtils.pointsBean = null;
        CommonlyUtils.bean = null;
        ButterKnife.unbind(this);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public ReportRegisterActivityPresenter initPresenter() {
        return new ReportRegisterActivityPresenter(this);
    }

    @OnClick({R.id.checking_matter_cancel, R.id.checking_matter_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checking_matter_cancel:///标题取消
                finish();
                break;

            case R.id.checking_matter_submit://标题提交
                if (matterSubmitInfo.getText().toString().trim().length() < 1) {
                    showToast(getString(R.string.null_text));
                } else {
                    recorderList.size();
                    if (pictureList.size() < 2) {
                        showToast(getString(R.string.please_select_pic));
                    } else {
                        checkInfo();//检查信息是否填写完整
                    }
                }
                break;

            case R.id.checking_matter_activity:
                KeyBoardUtils.closeKeybord(matterSubmitInfo, this);
                break;

        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 图片接收处理
     *
     * @param pictureList
     */
    @Override
    public void setPicRefurbishData(ArrayList<String> pictureList) {
        this.pictureList = pictureList;
        pictureAdatper.setData(pictureList);
    }

    @Override
    public void setPhotoData(String absolutePath) {
        startDeal(absolutePath);
    }

    /**
     * 照片删除或者添加回调
     *
     * @param o
     * @param position
     */
    @Override
    public void picDeleteCallBack(Object o, int position) {
        if (pictureList != null) {
            pictureList.remove(position);
            pictureAdatper.setData(pictureList);
        }
    }

    @Override
    public void addPictureCallBack() {
        if (pictureList.size() > 6) {
            showToast(getString(R.string.activity_report_register_limit_tips));
        } else {
            addPicture();
        }
    }

    @Override
    public void enlargeImage(int picAddress) {
        List<String> tempList = new ArrayList<>();
        tempList.addAll(pictureList);
        tempList.remove(pictureList.size() - 1);
        ImagePagerActivity.startActivity(this, tempList, picAddress);
    }

    private void startDeal(String absolutePath) {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
        // 将图片路径photoPath传到所要调试的模块
        Intent photoDealIntent = new Intent(this, PicDealingActivity.class);
        photoDealIntent.putExtra("photoPath", absolutePath);
        this.startActivityForResult(photoDealIntent, PictureSelectUtils.PIC_DEALED);
    }

    @Override
    public void onRadioPlayOver(boolean playOver) {
    }

    //地图定位监听
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                DecimalFormat df = new DecimalFormat("#.00000000");
                latitude = df.format(location.getLatitude());// 纬度
                lontitude = df.format(location.getLongitude());// 经度
                addr = location.getAddrStr();//地址信息
                if (mLocationClient != null && mLocationClient.isStarted()) {
                    mLocationClient.stop();
                }
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }

    @Override
    public void showLoading() {
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PictureSelectUtils.dealStartActivityResult(this, requestCode, resultCode, data, pictureList, this);
    }

    protected void initView() {
        locationService = MyApplicaption.getInstance().locationService;
        initPicView();
        setTitle(getString(R.string.activity_checking_matter));
        setTitleBarBgColor(R.color.equipment_title_bg);
        setTitleColor(getResources().getColor(R.color.equipment_title_text));
        showBackwardView("", true);
        showBackwardViewIco(R.drawable.equipment_back_ico);
        initDate();
    }

    private void initPicView() {
        pictureAdatper = new PictureGridAdapter(this, pictureList, this);
        matterSubmitImgList.setAdapter(pictureAdatper);
        pictureAdatper.setData(pictureList);
    }

    private void initDate() {
        pointListBean = (TaskBean.PointListBean) getIntent().getSerializableExtra(Constant.DATE);
        if (null != pointListBean) {
            matterObject.setText(pointListBean.getPointName());
            matterLocation.setText(pointListBean.getInstallationOcation() + " " + pointListBean.getDeviceName());
            matterProblemPromoter.setText(EquipmentConfig.getUserName());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = simpleDateFormat.format(new Date());
            matterSubmitTime.setText(date);
        } else {
            ToastUtils.showLong(CheckingMatterActivity.this, getString(R.string.data_failure));
        }
    }

    private void addPicture() {//添加图片
        if (pictureList.size() >= 7) {
            showToast(getString(R.string.activity_report_register_limit_tips));
        } else {
            window = new LifePaymentWindow(this);
            window.setOnCheckingListener(new LifePaymentWindow.OnCheckingListener() {
                @Override
                public void checkType(String type) {
                    window.dimisWindow();
                    PictureSelectUtils.initCamera(CheckingMatterActivity.this);
                }
            });
            window.showWindow(checkingMatterActivity);
        }
    }

    private void checkInfo() {
        if (pictureList.size() > 0) {
            pictureList.remove(pictureList.size() - 1);
        }

        if (matterSubmitInfo.getText().toString().trim().length() < 1) {
            showToast(getString(R.string.null_text));
            return;
        }
        if (pictureList.size() < 1) {
            showToast(getString(R.string.please_select_pic));
            return;
        }
        mLocationClient = new LocationClient(getApplicationContext());
        //定位参数的设定
        mLocationClient.setLocOption(locationService.getDefaultLocationClientOption());
        mLocationClient.registerLocationListener(myListener);       //声明LocationClient类
        mLocationClient.start();
        getUpFile();
    }

    /**
     * 上传数据
     */
    public void getUpFile() {
        BackUpsInfo backUpsInfo = new BackUpsInfo();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date = simpleDateFormat.format(new Date());
        final RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", MyApplicaption.getSessionId());
        params.addBodyParameter("userId", MyApplicaption.getUserId());
        final SelfCheckInfo info = new SelfCheckInfo();//发起检查信息(json格式的String)
        int picFileSize = 0;
        for (int i = 0; i < pictureList.size(); i++) {
            File picFile = new File(pictureList.get(i));
            if (picFile.exists()) {
                params.addBodyParameter("file" + i, picFile);
                picFileSize++;
            }
        }
        params.addBodyParameter("fileSize", String.valueOf(picFileSize));
        if (recorderList.size() > 0) {
            int recorderFileSize = 0;
            for (int i = 0; i < recorderList.size(); i++) {
                File recorderFile = new File(recorderList.get(i).getFilePath());
                if (recorderFile.exists()) {
                    params.addBodyParameter("audio" + i, recorderFile);
                    recorderFileSize++;
                }
            }
            params.addBodyParameter("audioSize", String.valueOf(recorderFileSize));
        }
        info.setOrgId(EquipmentConfig.getProjectId());
        //取文字消息
        if (matterSubmitInfo.getText() != null || matterSubmitInfo.getText().toString().trim() != "") {
            info.setSuperviseDescribe(pointListBean.getPointName() + ";" + pointListBean.getInstallationOcation() + ";" + matterSubmitInfo.getText().toString().trim());
        }
        info.setLongitude(lontitude);
        info.setLatitude(latitude);
        info.setMapName(addr);
        info.setInspTypeId("1");
        final String json = new Gson().toJson(info);
        params.addBodyParameter("info", json);
        //添加http请求头
        SortedMap<String, String> map = new TreeMap<>();
        map.put("fileSize", String.valueOf(picFileSize));
        map.put("sessionId", MyApplicaption.getSessionId());
        map.put("userId", MyApplicaption.getUserId());
        map.put("info", json);
        params.addHeader("signature", MyApplicaption.createSign(map));
        params.addHeader("uid", MyApplicaption.getUserId());
        backUpsInfo.setJson(json);
        backUpsInfo.setDate(date);
        backUpsInfo.setPictureList(pictureList);
        backUpsInfo.setRecorderList(recorderList);
        backUpsInfo.setRecordTime("");//语音时间
        backUpsInfo.setPointKey("");//关键位置
        backUpsInfo.setText(pointListBean.getPointName() + pointListBean.getInstallationOcation() + matterSubmitInfo.getText().toString().trim());//文字消息 对象 + 位置 + 文字
        backUpsInfo.setCategory("");//所属类型
        presenter.upLoadData(params, backUpsInfo);
    }

    @Override
    public void upLoadDataResult(String s) {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle(getString(R.string.checking_matter_title));
        String message;
        if ("0".equals(s)) {
            message = getString(R.string.checking_matter_success_message);
        } else {
            message = getString(R.string.checking_matter_defeated_message);
        }
        builder.setMessage(message);
        builder.setNegativeButton(getString(R.string.checking_matter_sure),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(Constant.DATE, matterSubmitInfo.getText().toString().trim());
                        setResult(Constant.CheckingInformationCallBack, intent);
                        dialog.dismiss();
                        finish();
                    }
                });

        builder.create().show();
    }

    @Override
    public void showMessage(String errorMsg) {
        showToast(errorMsg);
    }

}

