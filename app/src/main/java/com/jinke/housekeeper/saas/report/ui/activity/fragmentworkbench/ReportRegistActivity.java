package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.saas.report.bean.BackUpsInfo;
import com.jinke.housekeeper.saas.report.bean.RecorderBean;
import com.jinke.housekeeper.saas.report.bean.SelfCheckInfo;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.presenter.ReportRegisterActivityPresenter;
import com.jinke.housekeeper.saas.report.service.listener.PictureSelectListener;
import com.jinke.housekeeper.saas.report.ui.activity.RoomActivity;
import com.jinke.housekeeper.saas.report.ui.adapter.PictureGridAdapter;
import com.jinke.housekeeper.saas.report.ui.widget.AudioRecorderButton;
import com.jinke.housekeeper.saas.report.ui.widget.LifePaymentWindow;
import com.jinke.housekeeper.saas.report.ui.widget.MyGridView;
import com.jinke.housekeeper.saas.report.ui.widget.ProgressDialog;
import com.jinke.housekeeper.saas.report.util.KeyBoardUtils;
import com.jinke.housekeeper.saas.report.util.PictureSelectUtils;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.StringUtil;
import com.jinke.housekeeper.saas.report.util.TencentCountUtil;
import com.jinke.housekeeper.saas.report.view.ReportRegistActivityView;
import com.jinke.housekeeper.ui.activity.RegisterDepartmentActivity;
import com.jinke.housekeeper.ui.activity.RegisterFirmActivity;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.lidroid.xutils.http.RequestParams;
import com.tencent.stat.StatService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

/**
 * Created by Administrator on 2017/3/23.
 */
public class ReportRegistActivity extends BaseActivity<ReportRegistActivityView, ReportRegisterActivityPresenter> implements
        View.OnClickListener, ReportRegistActivityView, RadioUtil.RadioPlayListener,
        PictureGridAdapter.PicDeleteListener, PictureSelectListener, AudioRecorderButton.AudioFinishRecorderCallBack {
    @Bind(R.id.rootView)
    RelativeLayout rootView;
    @Bind(R.id.self_checking_project)
    TextView self_checking_Project;
    @Bind(R.id.self_checking_type)
    TextView self_checking_Type;
    @Bind(R.id.pointKey)
    TextView pointKey;
    @Bind(R.id.myGridView)
    MyGridView myGridView;
    @Bind(R.id.voiceLayout)
    RelativeLayout voiceLayout;
    @Bind(R.id.voiceAnim)
    View voiceAnim;
    @Bind(R.id.voiceTime)
    TextView voiceTime;
    @Bind(R.id.textLayout)
    RelativeLayout textLayout;
    @Bind(R.id.textDescribe)
    TextView textDescribe;
    @Bind(R.id.imgVoice)
    ImageView imgVoice;
    @Bind(R.id.audioRecorderButton)
    AudioRecorderButton audioRecorderButton;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.tx_send)
    TextView textSendMsg;
    @Bind(R.id.img_register_describe_left)
    ImageView img_register_describe_left;
    @Bind(R.id.tvg_room_project_value)
    TextView tvg_room_project_value;
    private boolean changeTV = true;
    private boolean flag = false;//表示音频是否打开
    private String patrolState = "";//判断进入入口 ""首页进入  "patrol"巡更进入
    private String inspType = "";
    private static final int SELF_DEAL = 4;//标识常量
    private String isown = "0";//默认不自检
    private ProgressDialog dialog;//加载进度框

    //照片适配器
    private PictureGridAdapter pictureAdapter;
    private ArrayList<String> pictureList = new ArrayList<>();
    private ArrayList<RecorderBean> recorderList = new ArrayList<>();
    private LifePaymentWindow window;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_reportregist;
    }

    protected void initView() {
        patrolState = getIntent().getStringExtra("patrol");
        if (null != getIntent().getStringExtra("inspType")) {
            inspType = getIntent().getStringExtra("inspType");
        }
        initAudioButtonListener();
        initPicView();
        setProjectDefault(); //设置默认项目，根据当前所选项目而定
    }

    private void initAudioButtonListener() {
        //语音输入完毕监听
        audioRecorderButton.setFinishRecorderCallBack(this);
        audioRecorderButton.setStartRecorderCallBack(new AudioRecorderButton.AudioStartRecorderCallBack() {
            @Override
            public void onStart() {
                RadioUtil.closeRadio(ReportRegistActivity.this, voiceAnim);
            }
        });
    }

    private void initPicView() {
        pictureAdapter = new PictureGridAdapter(this, pictureList, this);
        myGridView.setAdapter(pictureAdapter);
        pictureAdapter.setData(pictureList);
    }

    private void setProjectDefault() {
        self_checking_Project.setText(CommonlyUtils.bean == null ? CommonlyUtils.getUserInfo(this).getLeftOrgName() : CommonlyUtils.bean.getName());
        self_checking_Project.setTextColor(getResources().getColor(R.color.black));
        //初始化百度地图定位
        presenter.initLocation();
    }

    @OnClick({R.id.tx_send, R.id.voiceClear, R.id.textClear, R.id.voiceLayout,
            R.id.rl_self_checking_point_key, R.id.rl_self_checking_type,
            R.id.rl_self_checking_project, R.id.activity_report_register_text_title_back,
            R.id.report_text_title_submit, R.id.imgVoice, R.id.rl_self_checking_room,
            R.id.layout_text_voice})
    public void onClick(View view) {
        File file;
        switch (view.getId()) {
            case R.id.activity_report_register_text_title_back:///标题取消
                finish();
                break;

            case R.id.report_text_title_submit://标题提交
                checkInfo();//检查信息是否填写完整
                break;

            case R.id.tx_send:
                sendMsg();//发送文字消息到缓冲区
                break;

            case R.id.layout_text_voice:
                KeyBoardUtils.closeKeybord(editText, this);
                break;

            case R.id.voiceClear://语音删除
                voiceLayout.setVisibility(GONE);
                RadioUtil.closeRadio(this, voiceAnim);//关闭语音
                file = new File(recorderList.get(0).getFilePath());//删除本地文件
                file.delete();
                recorderList = null;
                break;

            case R.id.textClear://文字删除
                textLayout.setVisibility(GONE);
                textDescribe.setText("");
                break;

            case R.id.voiceLayout://语音布局
                if (flag == false) {
                    flag = true;
                    RadioUtil.openRadio(this, voiceAnim, recorderList.get(0).getFilePath(), this);
                } else {
                    flag = false;
                    RadioUtil.closeRadio(this, voiceAnim);
                }
                break;

            case R.id.rl_self_checking_project://所属项目
                Intent intents = new Intent(this, RegisterFirmActivity.class);
                intents.putExtra("flag", "4");
                startActivity(intents);
                break;

            case R.id.rl_self_checking_point_key://关键点位
                String Id = CommonlyUtils.bean == null ? CommonlyUtils.getUserInfo(this).getLeftOrgId() : CommonlyUtils.bean.getId();
                if (!StringUtils.isEmpty(Id)) {
                    Intent key = new Intent(this, KeyPointsActivity.class);
                    key.putExtra("projectId", Id);
                    startActivity(key);
                } else {
                    ToastUtils.showShort("请选择所属项目!");
                }
                break;

            case R.id.rl_self_checking_type://所属类型
                String pId = CommonlyUtils.bean == null ? CommonlyUtils.getUserInfo(this).getLeftOrgId() : CommonlyUtils.bean.getId();
                Intent intent = new Intent(this, RegisterDepartmentActivity.class);
                intent.putExtra("projectId", pId);
                startActivity(intent);
                break;

            case R.id.rl_self_checking_room://所属房间
                String projectId = CommonlyUtils.bean == null ? CommonlyUtils.getUserInfo(this).getLeftOrgId() : CommonlyUtils.bean.getId();
                if (!StringUtil.isEmpty(projectId)) {
                    Intent roomIntent = new Intent(this, RoomActivity.class);
                    roomIntent.putExtra("projectId", projectId);
                    startActivity(roomIntent);
                } else {
                    ToastUtils.showShort("请选择报事项目!");
                }
                break;
            //Text和Voice切换
            case R.id.imgVoice:
                changeTV();
                break;

        }
    }

    private void changeTV() {
        audioRecorderButton.setVisibility(changeTV ? View.VISIBLE : GONE);
        editText.setVisibility(changeTV ? View.GONE : View.VISIBLE);
        textSendMsg.setVisibility(changeTV ? View.GONE : View.VISIBLE);
        imgVoice.setImageResource(changeTV ? R.drawable.keyboard : R.drawable.voice);
        changeTV = changeTV ? false : true;
        if (changeTV) {
            KeyBoardUtils.closeKeybord(editText, this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELF_DEAL:
                try {
                    isown = data.getStringExtra("isown");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                PictureSelectUtils.dealStartActivityResult(this, requestCode, resultCode, data, pictureList, this);
                break;
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
                    PictureSelectUtils.initCamera(ReportRegistActivity.this);
                }
            });
            window.showWindow(rootView);
        }
    }

    private void checkInfo() {
        if (self_checking_Project.getText().equals("")) {
            showToast(getString(R.string.please_select_project));
            return;
        }
        if (StringUtils.isEmpty(SharedPreferencesUtils.init(this).getString("roomName"))) {
            ToastUtils.showShort("请选择所属房间!");
            return;
        }
        if (CommonlyUtils.listObjBean == null) {
            showToast("请选择所属类型！");
            return;
        }
        if (CommonlyUtils.pointsBean == null) {
            ToastUtils.showShort("请选择报事位置!");
            return;
        }

        if ((recorderList.size() == 0 && textDescribe.getText().toString().trim().equals(""))) {
            showToast(getString(R.string.please_select_describe));
            return;
        }
        if (pictureList.size() < 1) {
            showToast(getString(R.string.please_select_pic));
            return;
        }
        getUpFile();
    }

    private void sendMsg() {
        KeyBoardUtils.closeKeybord(editText, this);
        if (editText.getText().toString().trim().equals("") || editText.getText() == null) {
            showToast(getResources().getString(R.string.null_text));
        } else {
            textLayout.setVisibility(View.VISIBLE);
            textDescribe.setText(editText.getText().toString().trim());
            editText.getText().clear();
        }
    }

    private SimpleDateFormat sDateFormat;

    public void getUpFile() {
        BackUpsInfo backUpsInfo = new BackUpsInfo();
        sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date = sDateFormat.format(new java.util.Date());
        final RequestParams params = new RequestParams();
        params.addBodyParameter("inspTypeId", inspType);//上传类型
        params.addBodyParameter("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
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
        //组装语音文件
        int recorderFileSize = 0;
        if (recorderList.size() > 0) {
            for (int i = 0; i < recorderList.size(); i++) {
                File recorderFile = new File(recorderList.get(i).getFilePath());
                if (recorderFile.exists()) {
                    params.addBodyParameter("audio" + i, recorderFile);
                    recorderFileSize++;
                }
            }
            params.addBodyParameter("audioSize", String.valueOf(recorderFileSize));
        }
        info.setSceneId(CommonlyUtils.listObjBean.getId());
        info.setOrgId(CommonlyUtils.bean == null ? CommonlyUtils.getUserInfo(this).getLeftOrgId() : CommonlyUtils.bean.getId());
        info.setAreaId(CommonlyUtils.pointsBean.getId());
        //取文字消息
        if (textDescribe.getText() != null || textDescribe.getText().toString().trim() != "") {
            info.setSuperviseDescribe(textDescribe.getText().toString().trim());
        }
        info.setIsown(isown);
        info.setLongitude(SharedPreferencesUtils.init(this).getString("lontitude"));
        info.setLatitude(SharedPreferencesUtils.init(this).getString("latitude"));
        info.setMapName(SharedPreferencesUtils.init(this).getString("address"));
        info.setRoomId(SharedPreferencesUtils.init(this).getString("roomId"));
        info.setRoomNum(SharedPreferencesUtils.init(this).getString("roomName"));
        info.setInspTypeId(inspType);

        final String json = new Gson().toJson(info);
        params.addBodyParameter("info", json);
        //添加http请求头
        SortedMap<String, String> map = new TreeMap<>();
        if (pictureList != null && pictureList.size() >= 2)
            map.put("fileSize", String.valueOf(picFileSize));
        if (recorderList != null && recorderList.size() > 0)
            map.put("audioSize", String.valueOf(recorderFileSize));
        map.put("inspTypeId", inspType);//上传类型
        map.put("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
        map.put("userId", MyApplicaption.getUserId());
        map.put("info", json);
        params.addHeader("signature", ReportConfig.createSign(map));
        params.addHeader("uid", MyApplicaption.getUserId());
        backUpsInfo.setJson(json);
        backUpsInfo.setDate(date);
        backUpsInfo.setPictureList(pictureList);
        backUpsInfo.setRecorderList(recorderList);
        backUpsInfo.setRecordTime(voiceTime.getText().toString().trim());
        backUpsInfo.setPointKey(pointKey.getText().toString().trim());
        backUpsInfo.setText(textDescribe.getText().toString().trim());
        backUpsInfo.setCategory(self_checking_Type.getText().toString().trim());
        presenter.upLoadData(params, backUpsInfo);
    }

    @Override
    public void upLoadDataResult(String result) {
        //上传报事结果//0表示成功，1表示失败
        Intent intent = new Intent(ReportRegistActivity.this, CommitResultActivity.class);
        intent.putExtra("patrol", patrolState);
        intent.putExtra("result", result);
        intent.putExtra("inspType",inspType);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMessage(String errorMsg) {
        showToast(errorMsg);
    }


    @Override
    public void onResume() {
        super.onResume();
        setProjectDefault();
        RadioUtil.closeRadio(this, voiceAnim);
        if (CommonlyUtils.listObjBean != null) {
            self_checking_Type.setText(CommonlyUtils.listObjBean.getName());
            self_checking_Type.setTextColor(getResources().getColor(R.color.black));
        }

        if (CommonlyUtils.pointsBean != null) {
            pointKey.setText(CommonlyUtils.pointsBean.getName());
            pointKey.setTextColor(getResources().getColor(R.color.black));
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
        StatService.onResume(ReportRegistActivity.this);
        StatService.trackBeginPage(ReportRegistActivity.this, "启动页面");

        //报事房号，报事id
        String roomId = SharedPreferencesUtils.init(this).getString("roomId");
        if (!StringUtils.isEmpty(roomId)) {
            tvg_room_project_value.setText(SharedPreferencesUtils.init(this).getString("roomName"));
            tvg_room_project_value.setTextColor(getResources().getColor(R.color.black));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        flag = false;
        RadioUtil.closeRadio(this, voiceAnim);
        StatService.onPause(ReportRegistActivity.this);
        StatService.trackEndPage(ReportRegistActivity.this, "启动页面");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RadioUtil.closeRadio(this, voiceAnim);
        CommonlyUtils.listObjBean = null;
        CommonlyUtils.pointsBean = null;
        CommonlyUtils.bean = null;
        ButterKnife.unbind(this);
        SharedPreferencesUtils.init(this).put("roomId", "").put("roomName", "");
    }

    @Override
    public ReportRegisterActivityPresenter initPresenter() {
        return new ReportRegisterActivityPresenter(this);
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
        pictureAdapter.setData(pictureList);
    }

    @Override
    public void setPhotoData(String absolutePath) {
        //图片压缩完成进行编辑　
        // 将图片路径photoPath传到所要调试的模块
        Intent photoDealIntent = new Intent(this, PicDealingActivity.class);
        photoDealIntent.putExtra("photoPath", absolutePath);
        this.startActivityForResult(photoDealIntent, PictureSelectUtils.PIC_DEALED);
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
            pictureAdapter.setData(pictureList);
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


    @Override
    public void onRadioPlayOver(boolean playOver) {
        flag = playOver;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
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
    public void onFinish(float seconds, String filePath) {
        //语音输入完毕
        int mSeconds = (int) seconds;
        voiceLayout.setVisibility(View.VISIBLE);
        voiceTime.setText(mSeconds + "″");
        if (recorderList == null) {
            recorderList = new ArrayList<>();
        }
        recorderList.clear();
        recorderList.add(new RecorderBean(String.valueOf(mSeconds), filePath));
        TencentCountUtil.count(ReportRegistActivity.this, "audioRecorderButton");
    }
}

