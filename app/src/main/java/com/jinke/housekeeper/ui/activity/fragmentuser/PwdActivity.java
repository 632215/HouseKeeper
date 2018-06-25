package com.jinke.housekeeper.ui.activity.fragmentuser;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.AppHandleInfo;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.presenter.PwdActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.ui.activity.LoginActivity;
import com.jinke.housekeeper.view.PwdActivityView;
import com.jinke.housekeeper.ui.widget.RegisterDialog;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.tencent.stat.StatService;

import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import com.jinke.housekeeper.saas.report.util.KeyBoardUtils;

/**
 * Created by 32 on 2017/1/5.
 */
public class PwdActivity extends BaseActivity<PwdActivityView, PwdActivityPresenter> implements View.OnClickListener
        , NavigationView.OnNacigationTitleCallback
        , PwdActivityView {
    private UserInfo user;
    @Bind(R.id.et_pwd_oldpwd)
    EditText oldPwd;
    @Bind(R.id.et_pwd_newpwd)
    EditText newPwd;
    @Bind(R.id.et_pwd_configernewpwd)
    EditText configerNewPwd;
    @Bind(R.id.btn_pwd_congigerupdate)
    Button congigerUpdate;
    @Bind(R.id.titleBar)
    NavigationView title;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_pwd;
    }

    @Override
    protected void initView() {
        oldPwd.setOnClickListener(this);
        newPwd.setOnClickListener(this);
        configerNewPwd.setOnClickListener(this);
        congigerUpdate.setOnClickListener(this);
        title.setTitle(getResources().getString(R.string.updatepwd));
        title.setOnNavigationCallback(this);
        user = CommonlyUtils.getUserInfo(PwdActivity.this);
    }

    @Override
    public PwdActivityPresenter initPresenter() {
        return new PwdActivityPresenter(this);
    }


    public void keyboard(View view) {
        KeyBoardUtils.closeKeybord(oldPwd, PwdActivity.this);
        KeyBoardUtils.closeKeybord(newPwd, PwdActivity.this);
        KeyBoardUtils.closeKeybord(configerNewPwd, PwdActivity.this);
    }

    /**
     * 修改密码
     *
     * @param trim
     */
    private void getUpdatePwd(String trim) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("sessionId", MyApplicaption.getSessionId());
        map.put("userId", MyApplicaption.getUserId());
        map.put("lastpwd", CommonlyUtils.getUserInfo(this).getPassWord());
        map.put("newpwd", trim);
        user.setPassWord("");
        CommonlyUtils.saveUserInfo(PwdActivity.this, user);
        showTipDialog();
        presenter.getUpdatePwd(map);
    }

    @Override
    public void getUpdatePwdError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(PwdActivity.this, code);
    }

    @Override
    public void getUpdatePwdNext() {
        Log.i("32s", "getUpdatePwdNext");
    }

    @Override
    public void getLoinOutonNext(AppHandleInfo info) {
        RegisterDialog registerDialog = new RegisterDialog(PwdActivity.this, R.style.RegisterDialog,
                new RegisterDialog.OnRegisterDialogListener() {
                    @Override
                    public void onClick() {
                        CommonlyUtils.clearUserInfo(PwdActivity.this);
                        CommonlyUtils.clearData(PwdActivity.this);
                        Intent intent = new Intent();
                        intent.putExtra("date","1");
                        setResult(9211,intent);
                        PwdActivity.this.finish();
                    }
                });
        registerDialog.setTips("消息提示");
        registerDialog.setContents("密码修改成功，请重新登录！");
        registerDialog.show();
    }

    @Override
    public void getLoinOutonError(String code, String msg) {

    }

    private void showTipDialog() {
        Log.i("32s", "进入ShowTipDialog");
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", MyApplicaption.getSessionId());
        presenter.getLoinOuton(map);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(PwdActivity.this);
        StatService.trackBeginPage(PwdActivity.this, "修改密码");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(PwdActivity.this);
        StatService.trackEndPage(PwdActivity.this, "修改密码");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pwd_congigerupdate:
                if (oldPwd.getText().toString().trim().length() == 0) {
                    ToastUtils.showShort("请输入原密码");
                    oldPwd.setText("");
                    newPwd.setText("");
                    configerNewPwd.setText("");
                    return;
                }
                if (!oldPwd.getText().toString().trim().equals(CommonlyUtils.getUserInfo(this).getPassWord())) {
                    ToastUtils.showShort("原密码错误");
                    oldPwd.setText("");
                    newPwd.setText("");
                    configerNewPwd.setText("");
                    return;
                }
                if (newPwd.getText() == null) {
                    ToastUtils.showShort("请输入新密码");
                    oldPwd.setText("");
                    newPwd.setText("");
                    configerNewPwd.setText("");
                    return;
                }
                if (newPwd.getText().toString().trim().length() < 6) {
                    ToastUtils.showShort("请输入大余6为位的新密码");
                    newPwd.setText("");
                    configerNewPwd.setText("");
                    return;
                }
                if (configerNewPwd.getText() == null) {
                    ToastUtils.showShort("请再次输入新密码");
                    return;
                }

                if (configerNewPwd.getText().toString().trim().length() < 6) {
                    ToastUtils.showShort("确认密码错误");
                    newPwd.setText("");
                    configerNewPwd.setText("");
                    return;
                }
                if (!configerNewPwd.getText().toString().trim().equals(newPwd.getText().toString().trim())) {
                    ToastUtils.showShort("两次密码输入有误");
                    oldPwd.setText("");
                    return;
                }
                getUpdatePwd(newPwd.getText().toString().trim());
                break;
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
}
