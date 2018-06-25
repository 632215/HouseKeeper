package com.jinke.housekeeper.saas.report.ui.fragment.fragmentdeal;

import android.Manifest;
import android.content.Intent;
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
import com.jinke.housekeeper.saas.report.presenter.WorkFragmentPresenter;
import com.jinke.housekeeper.saas.report.ui.activity.WorkScenesActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.CheckDetailsActivity;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.ContactsActivity;
import com.jinke.housekeeper.saas.report.ui.widget.AudioRecorderButton;
import com.jinke.housekeeper.saas.report.ui.widget.ProgressDialog;
import com.jinke.housekeeper.saas.report.util.KeyBoardUtils;
import com.jinke.housekeeper.saas.report.util.RadioUtil;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.TencentCountUtil;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.saas.report.view.WorkFragmentView;
import com.jinke.housekeeper.ui.activity.RegisterDepartmentActivity;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.lidroid.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

/**
 * Created by 32 on 2017/1/6.
 * 工单派发
 */
public class WorkFragment extends BaseFragmentV4<WorkFragmentView, WorkFragmentPresenter> implements
        View.OnClickListener, RadioUtil.RadioPlayListener, WorkFragmentView {
    @Bind(R.id.type)
    TextView type;
    @Bind(R.id.typeView)
    RelativeLayout typeView;
    @Bind(R.id.person)
    TextView person;
    @Bind(R.id.personview)
    RelativeLayout personview;
    //键盘/语音录入切换
    @Bind(R.id.fragment_work_rl_voice)
    RelativeLayout rlVoice;
    @Bind(R.id.fragment_work_voice_anim)
    View voiceAnim;
    @Bind(R.id.fragment_work_tx_voice_time)
    TextView txVoiceTime;
    @Bind(R.id.fragment_work_rl_text)
    RelativeLayout rlText;
    @Bind(R.id.fragment_work_tx_describe)
    TextView txDescribe;
    @Bind(R.id.fragment_work_img_voice)
    ImageView imgVoice;
    @Bind(R.id.fragment_work_btn_audio_recorder)
    AudioRecorderButton btnAudioRecorderButton;
    @Bind(R.id.fragment_work_et_input)
    EditText etInput;
    @Bind(R.id.fragment_work_tx_send)
    TextView txSend;
    private boolean flag = false;//表示音频是否打开
    private WaitRectifiedBean.ListObjBean listObjBean;
    private ArrayList<RecorderBean> recorderList = new ArrayList<>();
    private boolean changeTV = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_work;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        CheckDetailsActivity checkDetailsActivity = (CheckDetailsActivity) getActivity();
        listObjBean = checkDetailsActivity.listObjBean;
        initAudioButtonListener();//结束录音回调
    }

    @Override
    public WorkFragmentPresenter initPresenter() {
        return new WorkFragmentPresenter(getActivity());
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
                TencentCountUtil.count(WorkFragment.this.getActivity(), "audioRecorderButton");
            }
        });
        btnAudioRecorderButton.setStartRecorderCallBack(new AudioRecorderButton.AudioStartRecorderCallBack() {
            @Override
            public void onStart() {
                RadioUtil.closeRadio(WorkFragment.this.getActivity(), voiceAnim);
            }
        });
    }

    @OnClick({R.id.typeView, R.id.personview, R.id.fragment_work_rl_voice,
            R.id.fragment_work_img_voice_clean,
            R.id.fragment_work_tx_text_clean,
            R.id.fragment_work_img_voice,
            R.id.fragment_work_tx_send})
    public void onClick(View v) {
        File file;
        switch (v.getId()) {
            case R.id.typeView://整改类型
                Intent intent = new Intent(getActivity(), WorkScenesActivity.class);
                startActivity(intent);
                break;

            case R.id.personview://整改人
                if (CommonlyUtils.listObjBean != null) {
                    Intent context = new Intent(getActivity(), ContactsActivity.class);
                    context.putExtra("projectId", listObjBean.getOrgId());
                    context.putExtra("codeId", CommonlyUtils.listObjBean.getId());
                    context.putExtra("processId", listObjBean.getId());
                    startActivity(context);
                } else {
                    showToast("请选择项目类型");
                    return;
                }
                break;

            case R.id.fragment_work_rl_voice:
                playRadio();
                break;

            case R.id.fragment_work_img_voice_clean:
                rlVoice.setVisibility(GONE);
                RadioUtil.closeRadio(getActivity(), voiceAnim);
                file = new File(recorderList.get(0).getFilePath());
                file.delete();
                recorderList.clear();
                break;

            case R.id.fragment_work_tx_text_clean:
                rlText.setVisibility(View.GONE);
                txDescribe.setText("");
                break;

            case R.id.fragment_work_img_voice:
                changeTV();
                break;

            case R.id.fragment_work_tx_send:
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

    private ProgressDialog dialog;

    public void getappHandleData() {
        RequestParams params = new RequestParams();
        //添加http请求头
        SortedMap<String, String> map = new TreeMap<>();
        params.addBodyParameter("sessionId", SharedPreferencesUtils.init(getActivity()).getString("quality_sessionId"));
        params.addBodyParameter("userId", MyApplicaption.getUserId());
        params.addBodyParameter("taskId", listObjBean.getTaskId());
        params.addBodyParameter("processId", listObjBean.getId());
        params.addBodyParameter("type", "3");
        if (txDescribe.getText() != null || txDescribe.getText().toString().trim() != "") {
            params.addBodyParameter("remark", txDescribe.getText().toString().trim());
            map.put("remark", txDescribe.getText().toString().trim());
        }
        params.addBodyParameter("sceneId", CommonlyUtils.listObjBean.getId());
        params.addBodyParameter("personId", CommonlyUtils.contactsBean.getId());
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
        map.put("type", "3");
        map.put("sceneId", CommonlyUtils.listObjBean.getId());
        map.put("personId", CommonlyUtils.contactsBean.getId());
        if (recorderList != null && recorderList.size() > 0)
            map.put("audioSize", String.valueOf(recorderFileSize));
        params.addHeader("signature", MyApplicaption.createSign(map));
        params.addHeader("uid", MyApplicaption.getUserId());
        presenter.getappHandleData(params);
    }

    @Override
    public void getappHandleDataSuccess() {
        getActivity().finish();
    }

    @Override
    public void getappHandleDataFailure(String errorMsg) {
        showToast(errorMsg);
    }

    //检测数据属否填写
    public void checkDelayData() {
        if (CommonlyUtils.contactsBean == null) {
            showToast("请选择项目委托人");
            return;
        }
        if (CommonlyUtils.listObjBean == null) {
            showToast("请选择项目");
            return;
        }
        if (recorderList.size() <= 0 && txDescribe.getText().toString().trim().equals("")) {
            showToast("请输入检查描述或语音描述");
            return;
        }
        getappHandleData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CommonlyUtils.listObjBean != null) {
            type.setText(CommonlyUtils.listObjBean.getName());
        }

        if (CommonlyUtils.contactsBean != null) {
            person.setText(CommonlyUtils.contactsBean.getName());
        }
        ButterKnife.unbind(getActivity());
        RadioUtil.closeRadio(getActivity(), voiceAnim);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CommonlyUtils.listObjBean = null;
        CommonlyUtils.contactsBean = null;
        RadioUtil.closeRadio(getActivity(), voiceAnim);

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
    public void onRadioPlayOver(boolean isPlay) {
        flag = isPlay;
    }
}
