package com.jinke.housekeeper.saas.report.ui.fragment.fragmentdeal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.saas.report.bean.RecorderBean;
import com.jinke.housekeeper.saas.report.bean.WaitRectifiedBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.presenter.ReportFragmentPresenter;
import com.jinke.housekeeper.saas.report.service.listener.PictureSelectListener;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.CheckDetailsActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.ImagePagerActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.PicDealingActivity;
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
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.saas.report.view.ReportFragmentView;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

/**
 * Created by 32 on 2017/1/6.
 * 延迟日志
 */
public class ReportFragment extends BaseFragmentV4<ReportFragmentView, ReportFragmentPresenter> implements View.OnClickListener,
        RadioUtil.RadioPlayListener,
        PictureGridAdapter.PicDeleteListener, ReportFragmentView, PictureSelectListener {
    @Bind(R.id.fragment_finish_rl_root)
    LinearLayout rlRoot;
    @Bind(R.id.fragment_finish_mygridview)
    MyGridView gridView;
    //语音、文字输入的结果展示
    @Bind(R.id.fragment_finish_rl_voice)
    RelativeLayout rlVoice;
    @Bind(R.id.fragment_finish_voice_anim)
    View voiceAnim;
    @Bind(R.id.fragment_finish_rl_text)
    RelativeLayout rlText;
    @Bind(R.id.fragment_finish_tx_voice_time)
    TextView txVoiceTime;
    @Bind(R.id.fragment_finish_tx_describe)
    TextView txDescribe;
    @Bind(R.id.fragment_finish_btn_audio_recorder)
    AudioRecorderButton btnAudioRecorderButton;
    @Bind(R.id.fragment_finish_et_input)
    EditText etInput;
    @Bind(R.id.fragment_finish_img_voice)
    ImageView imgVoice;
    @Bind(R.id.fragment_finish_tx_send)
    TextView txSend;
    public String vFNFinish = null;
    private boolean flag = false;//表示音频是否打开
    private CheckDetailsActivity checkDetailsActivity;
    private ProgressDialog dialog;
    private WaitRectifiedBean.ListObjBean listObjBean;
    private boolean changeTV = true;
    //照片适配器
    private PictureGridAdapter pictureAdatper;
    private ArrayList<String> pictureList = new ArrayList<>();
    private ArrayList<RecorderBean> recorderList = new ArrayList<>();
    private LifePaymentWindow window;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_finish;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        checkDetailsActivity = (CheckDetailsActivity) getActivity();
        listObjBean = checkDetailsActivity.listObjBean;
        initAudioButtonListener();//结束录音回调
        initPicView();
    }

    private void initPicView() {
        pictureAdatper = new PictureGridAdapter(getActivity(), pictureList, ReportFragment.this);
        gridView.setAdapter(pictureAdatper);
        pictureAdatper.setData(pictureList);
    }

    private void initAudioButtonListener() {
        btnAudioRecorderButton.setFinishRecorderCallBack(new AudioRecorderButton.AudioFinishRecorderCallBack() { //结束录音回调
            public void onFinish(float seconds, String voiceFilePath) {
                int mSeconds = (int) seconds;
                rlVoice.setVisibility(View.VISIBLE);
                txVoiceTime.setText(mSeconds + "″");
                if (recorderList == null) {
                    recorderList = new ArrayList<>();
                }
                recorderList.clear();
                recorderList.add(new RecorderBean(String.valueOf(mSeconds), voiceFilePath));
                TencentCountUtil.count(ReportFragment.this.getActivity(), "audioRecorderButton");
            }
        });
        btnAudioRecorderButton.setStartRecorderCallBack(new AudioRecorderButton.AudioStartRecorderCallBack() {
            @Override
            public void onStart() {
                RadioUtil.closeRadio(ReportFragment.this.getActivity(), voiceAnim);
            }
        });
    }

    @OnClick({R.id.fragment_finish_img_voice_clean, R.id.fragment_finish_tx_text_clean,
            R.id.fragment_finish_tx_send,
            R.id.fragment_finish_rl_voice, R.id.fragment_finish_img_voice})
    public void onClick(View v) {
        File file;
        switch (v.getId()) {
            case R.id.fragment_finish_tx_send:
                sendMsg();
                break;

            case R.id.fragment_finish_img_voice_clean:
                rlVoice.setVisibility(GONE);
                RadioUtil.closeRadio(getActivity(), voiceAnim);
                file = new File(recorderList.get(0).getFilePath());
                file.delete();
                recorderList.clear();
                break;

            case R.id.fragment_finish_tx_text_clean:
                rlText.setVisibility(View.GONE);
                txDescribe.setText("");
                break;

            case R.id.fragment_finish_rl_voice:
                playRadio();
                break;

            case R.id.fragment_finish_img_voice:
                changeTV();
                break;
        }
    }

    private void playRadio() {
        if (flag == false) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ToastUtils.showLongToast(getResources().getString(R.string.please_open_record_permision));
            } else {
                flag = true;
                if (voiceAnim == null) {
                    voiceAnim = getActivity().findViewById(R.id.voiceAnim);
                }
                voiceAnim.setBackgroundResource(R.drawable.v_anim3);
                RadioUtil.openRadio(getActivity(), voiceAnim, recorderList.get(0).getFilePath(), this);
            }
        } else {
            flag = false;
            RadioUtil.closeRadio(getActivity(), voiceAnim);
        }
    }

    private void changeTV() {
        if (changeTV == false) {
            btnAudioRecorderButton.setVisibility(View.GONE);
            etInput.setVisibility(View.VISIBLE);
            txSend.setVisibility(View.VISIBLE);
            imgVoice.setImageResource(R.drawable.voice);
            changeTV = true;
        } else {
            KeyBoardUtils.closeKeybord(etInput, getActivity());
            btnAudioRecorderButton.setVisibility(View.VISIBLE);
            txSend.setVisibility(View.GONE);
            etInput.setVisibility(View.GONE);
            imgVoice.setImageResource(R.drawable.keyboard);
            changeTV = false;
        }
    }

    private void addPicture() {//添加图片
        if (pictureList.size() >= 7) {
            showToast(getString(R.string.activity_report_register_limit_tips));
        } else {
            window = new LifePaymentWindow(getActivity());
            window.setOnCheckingListener(new LifePaymentWindow.OnCheckingListener() {
                @Override
                public void checkType(String type) {
                    window.dimisWindow();
                    PictureSelectUtils.initFragmentCamera(ReportFragment.this);
                }
            });
            window.showWindow(rlRoot);
        }
    }

    private void sendMsg() {
        KeyBoardUtils.closeKeybord(etInput, getActivity());
        if (etInput.getText().toString().trim().equals("") || etInput.getText() == null) {
            showToast(getResources().getString(R.string.null_text));
        } else {
            rlText.setVisibility(View.VISIBLE);
            txDescribe.setText(etInput.getText().toString().trim());
            etInput.getText().clear();
        }
    }

//    List<String> tempList = new ArrayList<>();

    //检测数据属否填写
    public void checkFinishData() {
        if (pictureList.size() < 1) {
            showToast("请选择图片");
            return;
        }
        if (recorderList.size() <= 0 && StringUtil.isEmpty(txDescribe.getText().toString().trim())) {
            showToast("请输入检查描述或语音描述");
            return;
        }
        getUpFile();
    }

    public void getUpFile() {
        RequestParams params = new RequestParams();
        //添加http请求头
        SortedMap<String, String> map = new TreeMap<>();
        params.addBodyParameter("sessionId", SharedPreferencesUtils.init(getActivity()).getString("quality_sessionId"));
        params.addBodyParameter("userId", MyApplicaption.getUserId());
        params.addBodyParameter("taskId", listObjBean.getTaskId());
        params.addBodyParameter("id", listObjBean.getId());
        int picFileSize = 0;
        if (txDescribe.getText().toString().trim() != "") {
            params.addBodyParameter("describe", txDescribe.getText().toString().trim());
            map.put("describe", txDescribe.getText().toString().trim());
        }
        if (pictureList != null && pictureList.size() > 1) {
            for (int i = 0; i < pictureList.size(); i++) {
                File picFile = new File(pictureList.get(i));
                if (picFile.exists()) {
                    params.addBodyParameter("file" + i, picFile);
                    picFileSize++;
                }
            }
            params.addBodyParameter("fileSize", String.valueOf(picFileSize));
        }
        int recorderFileSize = 0;
        if (recorderList != null && recorderList.size() > 0) {
            for (int i = 0; i < recorderList.size(); i++) {
                File recorderFile = new File(recorderList.get(i).getFilePath());
                if (recorderFile.exists()) {
                    params.addBodyParameter("audio" + i, recorderFile);
                    recorderFileSize++;
                }
            }
            params.addBodyParameter("audioSize", String.valueOf(recorderFileSize));
        }
        map.put("sessionId", SharedPreferencesUtils.init(getActivity()).getString("quality_sessionId"));
        map.put("userId", MyApplicaption.getUserId());
        map.put("taskId", listObjBean.getTaskId());
        map.put("id", listObjBean.getId());
        if (pictureList != null && pictureList.size() >= 2)
            map.put("fileSize", String.valueOf(picFileSize));
        if (recorderList != null && recorderList.size() > 0)
            map.put("audioSize", String.valueOf(recorderFileSize));
        params.addHeader("signature", ReportConfig.createSign(map));
        params.addHeader("uid", MyApplicaption.getUserId());
        presenter.getUpFile(params);
    }

    @Override
    public void getUpFileonSuccess() {
        getActivity().finish();
    }

    @Override
    public void getUpFileonFailure(String errorMsg) {
        showToast(errorMsg);
    }

    private void startDeal(String absolutePath) {
        // 将图片路径photoPath传到所要调试的模块
        Intent photoDealIntent = new Intent(getActivity(),
                PicDealingActivity.class);
        photoDealIntent.putExtra("photoPath", absolutePath);
        this.startActivityForResult(photoDealIntent, PictureSelectUtils.PIC_DEALED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("32s", "reportFragment-onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        PictureSelectUtils.dealStartActivityResult(getActivity(), requestCode, resultCode, data, pictureList, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RadioUtil.closeRadio(getActivity(), voiceAnim);
        ButterKnife.unbind(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        RadioUtil.closeRadio(getActivity(), voiceAnim);

    }

    @Override
    public ReportFragmentPresenter initPresenter() {
        return new ReportFragmentPresenter(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(getActivity());
        RadioUtil.closeRadio(getActivity(), voiceAnim);
    }

    @Override
    public void onResume() {
        super.onResume();
        RadioUtil.closeRadio(getActivity(), voiceAnim);
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRadioPlayOver(boolean playOver) {
        flag = playOver;
    }

    /**
     * 图片删除回调
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

    /**
     * 图片添加回调
     */
    @Override
    public void addPictureCallBack() {
        if (pictureList.size() > 6) {
            showToast(getString(R.string.activity_report_register_limit_tips));
        } else {
            addPicture();
        }
    }

    /**
     * 图片点击回调
     *
     * @param picAddress
     */
    @Override
    public void enlargeImage(int picAddress) {
        List<String> tempList = new ArrayList<>();
        tempList.addAll(pictureList);
        tempList.remove(pictureList.size() - 1);
        ImagePagerActivity.startActivity(getActivity(), tempList, picAddress);
    }

    @Override
    public void showLoading() {
        dialog = new ProgressDialog(getActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    /**
     * 图片添加
     *
     * @param pictureList
     */
    @Override
    public void setPicRefurbishData(ArrayList<String> pictureList) {
        this.pictureList = pictureList;
        pictureAdatper.setData(pictureList);
    }

    /**
     * 处理图片
     *
     * @param absolutePath
     */
    @Override
    public void setPhotoData(String absolutePath) {
        startDeal(absolutePath);
    }

}
