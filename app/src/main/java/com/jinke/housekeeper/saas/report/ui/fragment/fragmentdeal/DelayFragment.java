package com.jinke.housekeeper.saas.report.ui.fragment.fragmentdeal;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.saas.report.bean.RecorderBean;
import com.jinke.housekeeper.saas.report.bean.WaitRectifiedBean;
import com.jinke.housekeeper.saas.report.config.ReportConfig;
import com.jinke.housekeeper.saas.report.presenter.DelayFragmentPresenter;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.CheckDetailsActivity;
import com.jinke.housekeeper.saas.report.ui.widget.AudioRecorderButton;
import com.jinke.housekeeper.saas.report.ui.widget.ProgressDialog;
import com.jinke.housekeeper.saas.report.util.KeyBoardUtils;
import com.jinke.housekeeper.saas.report.util.MediaPlayerManager;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.TencentCountUtil;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.saas.report.view.DelayFragmentView;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 32 on 2017/1/6.
 */
public class DelayFragment extends BaseFragmentV4<DelayFragmentView, DelayFragmentPresenter> implements
        View.OnClickListener, DelayFragmentView, RadioUtil.RadioPlayListener {
    @Bind(R.id.txt_endTime)
    TextView txtEndTime;
    @Bind(R.id.img_voice)
    ImageView imgVoice;
    @Bind(R.id.btn_audio_recorder)
    AudioRecorderButton btnAudioRecorderButton;
    @Bind(R.id.tx_send)
    TextView txSend;
    //语音、文字输入的结果展示
    @Bind(R.id.rv_voice)
    RelativeLayout voiceLayout;
    @Bind(R.id.voiceAnim)
    View voiceAnim;
    @Bind(R.id.tx_voice_time)
    TextView txVoiceTime;
    @Bind(R.id.textLayout)
    RelativeLayout textLayout;
    @Bind(R.id.tx_text_clear)
    ImageView textClear;
    @Bind(R.id.textDescribe)
    TextView textDescribe;
    @Bind(R.id.et_input)
    EditText etInput;
    private boolean flag = false;//表示音频是否打开
    private WaitRectifiedBean.ListObjBean listObjBean;
    private boolean changeTV = true;
    CheckDetailsActivity checkDetailsActivity;
    private ProgressDialog dialog;
    private ArrayList<RecorderBean> recorderList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_delay;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        checkDetailsActivity = (CheckDetailsActivity) getActivity();
        listObjBean = checkDetailsActivity.listObjBean;
        initAudioButtonListener();//结束录音回调
    }

    private void initAudioButtonListener() {
        btnAudioRecorderButton.setFinishRecorderCallBack(new AudioRecorderButton.AudioFinishRecorderCallBack() { //结束录音回调
            public void onFinish(float seconds, String voiceFilePath) {
                int mSeconds = (int) seconds;
                voiceLayout.setVisibility(View.VISIBLE);
                txVoiceTime.setText(mSeconds + "″");
                if (recorderList == null) {
                    recorderList = new ArrayList<>();
                }
                recorderList.clear();
                recorderList.add(new RecorderBean(String.valueOf(mSeconds), voiceFilePath));
                TencentCountUtil.count(DelayFragment.this.getActivity(), "audioRecorderButton");
            }
        });
        btnAudioRecorderButton.setStartRecorderCallBack(new AudioRecorderButton.AudioStartRecorderCallBack() {
            @Override
            public void onStart() {
                RadioUtil.closeRadio(DelayFragment.this.getActivity(), voiceAnim);
            }
        });
    }

    /**
     * 延时申请
     */
    public void getappHandleData() {
        //添加http请求头
        SortedMap<String, String> map = new TreeMap<>();
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", SharedPreferencesUtils.init(getActivity()).getString("quality_sessionId"));
        params.addBodyParameter("userId", MyApplicaption.getUserId());
        params.addBodyParameter("taskId", listObjBean.getTaskId());
        params.addBodyParameter("processId", listObjBean.getId());
        params.addBodyParameter("type", "2");
        if (textDescribe.getText() != null || textDescribe.getText().toString().trim() != "") {
            params.addBodyParameter("remark", textDescribe.getText().toString().trim());
            map.put("remark", textDescribe.getText().toString().trim());
        }
        params.addBodyParameter("superviseCompletetime", txtEndTime.getText().toString().trim());
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
        map.put("processId", listObjBean.getId());
        map.put("type", "2");
        map.put("superviseCompletetime", txtEndTime.getText().toString().trim());

        if (recorderList != null && recorderList.size() > 0)
            map.put("audioSize", String.valueOf(recorderList.size()));

        params.addHeader("signature", ReportConfig.createSign(map));
        params.addHeader("uid", MyApplicaption.getUserId());
        presenter.getappHandleData(params);
    }

    @Override
    public void getappHandleDataonSuccess() {
        getActivity().finish();
    }

    @Override
    public void getappHandleshowToast(String s) {
        showToast(s);
    }

    @OnClick({R.id.rv_endtime, R.id.tx_send,
            R.id.img_voice_clear, R.id.tx_text_clear,
            R.id.rv_voice, R.id.img_voice})
    public void onClick(View v) {
        File file;
        switch (v.getId()) {
            case R.id.tx_send:
                KeyBoardUtils.closeKeybord(etInput, getActivity());
                if (etInput.getText().toString().trim().equals("") || etInput.getText() == null) {
                    showToast(getString(R.string.fragment_delay_please_imput_delay_reason));
                } else {
                    textLayout.setVisibility(View.VISIBLE);
                    textDescribe.setText(etInput.getText().toString().trim());
                    etInput.getText().clear();
                }
                break;
            case R.id.img_voice_clear:
                voiceLayout.setVisibility(View.GONE);
                MediaPlayerManager.release();
                file = new File(recorderList.get(0).getFilePath());
                file.delete();
                recorderList.clear();
                break;
            case R.id.tx_text_clear:
                textLayout.setVisibility(View.GONE);
                textDescribe.setText("");
                break;
            case R.id.rv_voice:
                playRadio();
                break;

            case R.id.img_voice:
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
                break;
            case R.id.rv_endtime:
                selecterTime(txtEndTime);
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

    //检测数据属否填写
    public void checkDelayData() {
        if (txtEndTime.getText() == null || txtEndTime.getText().toString().trim() == "") {
            showToast("请选择截止时间");
            return;
        }
        if ((recorderList.size() <= 0 && textDescribe.getText().toString().trim().equals(""))) {
            showToast("请输入文字描述或语音描述");
            return;
        }
        getappHandleData();
    }

    SimpleDateFormat sDateFormat;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String time;

    public void selecterTime(final TextView txTime) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                mYear = year;
                mMonth = month;
                mDay = day;
                int m = (mMonth + 1) < 10 ? 0 + (mMonth + 1) : (mMonth + 1);
                int d = (mDay < 10) ? 0 + mDay : mDay;
                time = mYear + "-" + m + "-" + d;
                sDateFormat = new SimpleDateFormat("hh:mm:ss");
                final String date = sDateFormat.format(new java.util.Date());
                txTime.setText(time + " " + date);
                //根据时间查询分公司数据
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MediaPlayerManager.release();
        ButterKnife.unbind(getActivity());
        RadioUtil.closeRadio(getActivity(), voiceAnim);
    }

    @Override
    public void onPause() {
        super.onPause();
        RadioUtil.closeRadio(getActivity(), voiceAnim);
    }

    @Override
    public DelayFragmentPresenter initPresenter() {
        return new DelayFragmentPresenter(getActivity());
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
    public void showLoading() {
        dialog = new ProgressDialog(getActivity());
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void onRadioPlayOver(boolean isOver) {
        flag = isOver;
    }
}
