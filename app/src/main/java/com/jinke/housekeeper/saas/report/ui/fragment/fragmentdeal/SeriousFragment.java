package com.jinke.housekeeper.saas.report.ui.fragment.fragmentdeal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
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
import com.jinke.housekeeper.saas.report.presenter.SeriousFragmentPresenter;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.CheckDetailsActivity;
import com.jinke.housekeeper.saas.report.ui.widget.AudioRecorderButton;
import com.jinke.housekeeper.saas.report.ui.widget.ProgressDialog;
import com.jinke.housekeeper.saas.report.util.KeyBoardUtils;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.TencentCountUtil;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.saas.report.view.SeriousFragmentView;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

/**
 * Created by 32 on 2017/1/6.
 */
public class SeriousFragment extends BaseFragmentV4<SeriousFragmentView, SeriousFragmentPresenter> implements
        View.OnClickListener,
        RadioUtil.RadioPlayListener,
        SeriousFragmentView {
    @Bind(R.id.fragment_serious_rl_voice)
    RelativeLayout rlVoice;
    @Bind(R.id.fragment_serious_voice_anim)
    View voiceAnim;
    @Bind(R.id.fragment_serious_tx_voice_time)
    TextView txVoiceTime;
    @Bind(R.id.fragment_serious_rl_text)
    RelativeLayout rlText;
    @Bind(R.id.fragment_serious_tx_describe)
    TextView txDescribe;
    @Bind(R.id.fragment_serious_img_voice)
    ImageView imgVoice;
    @Bind(R.id.fragment_serious_btn_audio_recorder)
    AudioRecorderButton btnAudioRecorderButton;
    @Bind(R.id.fragment_serious_et_input)
    EditText etInput;
    @Bind(R.id.fragment_serious_tx_send)
    TextView txSend;
    private boolean flag = false;//表示音频是否打开
    private WaitRectifiedBean.ListObjBean listObjBean;
    CheckDetailsActivity checkDetailsActivity;
    private ArrayList<RecorderBean> recorderList = new ArrayList<>();
    private boolean changeTV = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_serious;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        checkDetailsActivity = (CheckDetailsActivity) getActivity();
        listObjBean = checkDetailsActivity.listObjBean;
        initAudioButtonListener();//结束录音回调
    }

    @Override
    public SeriousFragmentPresenter initPresenter() {
        return new SeriousFragmentPresenter(getActivity());
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
                TencentCountUtil.count(SeriousFragment.this.getActivity(), "audioRecorderButton");
            }
        });
        btnAudioRecorderButton.setStartRecorderCallBack(new AudioRecorderButton.AudioStartRecorderCallBack() {
            @Override
            public void onStart() {
                RadioUtil.closeRadio(SeriousFragment.this.getActivity(), voiceAnim);
            }
        });
    }

    private ProgressDialog dialog;

    public void getappHandleData() {
        RequestParams params = new RequestParams();
        //添加http请求头
        SortedMap<String, String> map = new TreeMap<>();
        params.addBodyParameter("sessionId", SharedPreferencesUtils.init(getActivity()).getString("quality_sessionId"));
        params.addBodyParameter("userId", MyApplicaption.getUserId());
        params.addBodyParameter("taskId", listObjBean.getTaskId());
        params.addBodyParameter("processId", listObjBean.getId());
        params.addBodyParameter("type", "4");
        if (txDescribe.getText() != null || txDescribe.getText().toString().trim() != "") {
            params.addBodyParameter("remark", txDescribe.getText().toString().trim());
            map.put("remark", txDescribe.getText().toString().trim());
        }
        params.addBodyParameter("projectId", listObjBean.getOrgId());
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
        map.put("type", "4");
        map.put("projectId", listObjBean.getOrgId());
        if (recorderList != null && recorderList.size() > 0)
            map.put("audioSize", String.valueOf(recorderFileSize));

        Set es = map.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry sign = (Map.Entry) it.next();
            Object k = sign.getKey();
            Object v = sign.getValue();
        }
        params.addHeader("signature", ReportConfig.createSign(map));
        params.addHeader("uid", MyApplicaption.getUserId());
        presenter.getappHandleData(params);
    }

    @Override
    public void getappHandleDataFailure(String string) {
        showToast(string);
    }

    @Override
    public void getappHandleDataSuccess() {
        getActivity().finish();
    }

    @OnClick({R.id.fragment_serious_rl_voice, R.id.fragment_serious_img_voice_clean,
            R.id.fragment_serious_tx_text_clean,
            R.id.fragment_serious_img_voice,
            R.id.fragment_serious_tx_send})
    public void onClick(View v) {
        File file;
        switch (v.getId()) {
            case R.id.fragment_serious_rl_voice:
                playRadio();
                break;

            case R.id.fragment_serious_img_voice_clean:
                rlVoice.setVisibility(GONE);
                RadioUtil.closeRadio(getActivity(), voiceAnim);
                file = new File(recorderList.get(0).getFilePath());
                file.delete();
                recorderList.clear();
                break;

            case R.id.fragment_serious_tx_text_clean:
                rlText.setVisibility(View.GONE);
                txDescribe.setText("");
                break;

            case R.id.fragment_serious_img_voice:
                changeTV();
                break;

            case R.id.fragment_serious_tx_send:
                sendMsg();
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
                    voiceAnim = getActivity().findViewById(R.id.fragment_serious_voice_anim);
                }
                voiceAnim.setBackgroundResource(R.drawable.v_anim3);
                RadioUtil.openRadio(getActivity(), voiceAnim, recorderList.get(0).getFilePath(), this);
            }
        } else {
            flag = false;
            RadioUtil.closeRadio(getActivity(), voiceAnim);
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

    //检测数据属否填写
    public void checkDelayData() {
        if (recorderList.size() <= 0 && txDescribe.getText().toString().trim().equals("")) {
            showToast("请输入检查描述或语音描述");
            return;
        }
        getappHandleData();
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRadioPlayOver(boolean playOver) {
        flag = playOver;
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

}