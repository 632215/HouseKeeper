package com.jinke.housekeeper.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.presenter.RegisterActivityPresenter;
import com.jinke.housekeeper.ui.widget.RegisterDialog;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.utils.KeyBoardUtils;
import com.jinke.housekeeper.view.RegisterActivityView;
import com.tencent.stat.StatService;

import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import www.jinke.com.library.widget.NavigationView;


/**
 * Created by 32 on 2016/12/21.
 */

public class RegisterActivity extends BaseActivity<RegisterActivityView, RegisterActivityPresenter> implements
        View.OnClickListener, RegisterActivityView, NavigationView.OnNacigationTitleCallback {
    @Bind(R.id.activity_register_navigationview)
    NavigationView title;
    @Bind(R.id.et_register_phone)
    EditText etRegisterPhone;
    @Bind(R.id.fax)
    EditText fax;
    @Bind(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @Bind(R.id.et_register_confirmpwd)
    EditText etRegisterConfirmpwd;
    @Bind(R.id.et_register_name)
    EditText etRegisterName;
    @Bind(R.id.tv_rigster_project)
    TextView tvRigsterProject;
    @Bind(R.id.tv_rigster_department)
    TextView tvRigsterDepartment;
    @Bind(R.id.tv_register_errortips)
    TextView tvRegisterErrortips;
    @Bind(R.id.register_root)
    LinearLayout linearLayout;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        initTitle();
        UserInfo userInfo = CommonlyUtils.getUserInfo(RegisterActivity.this);
        userInfo.setRoleType("");
        userInfo.setOrgCodel("");
        CommonlyUtils.saveUserInfo(RegisterActivity.this, userInfo);
        linearLayout.setOnClickListener(this);
        etRegisterPhone.setOnClickListener(this);
        fax.setOnClickListener(this);
    }

    private void initTitle() {
        title.setTitle(getResources().getString(R.string.title_register));
        title.setOnNavigationCallback(this);
    }

    @OnClick({R.id.et_register_phone,
            R.id.rl_register_project,
            R.id.rl_register_department,
            R.id.register_root,
            R.id.btn_registerNow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_register_phone:
                tvRegisterErrortips.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_register_project:
                Intent intentRegisterProject = new Intent(this, RegisterFirmActivity.class);
                intentRegisterProject.putExtra("parentId", "");
                intentRegisterProject.putExtra("flag", "");
                startActivity(intentRegisterProject);
                break;

            case R.id.rl_register_department:
                Intent intent = new Intent(this, RegisterDepartmentActivity.class);
                startActivity(intent);
                break;
            case R.id.register_root:
                KeyBoardUtils.closeKeybord(etRegisterPhone, RegisterActivity.this);
                KeyBoardUtils.closeKeybord(etRegisterPwd, RegisterActivity.this);
                KeyBoardUtils.closeKeybord(etRegisterConfirmpwd, RegisterActivity.this);
                KeyBoardUtils.closeKeybord(etRegisterName, RegisterActivity.this);
                KeyBoardUtils.closeKeybord(fax, RegisterActivity.this);
                break;

            case R.id.btn_registerNow:
                if (etRegisterPhone.getText().toString().trim().length() != 11) {
                    ToastUtils.showShort("请输入合法的手机号码");
                    return;
                }

                if (etRegisterPwd.getText().toString().trim().length() < 6) {
                    ToastUtils.showShort("请输入大余6为位的密码");
                    return;
                }

                if (!etRegisterConfirmpwd.getText().toString().trim().equals(etRegisterPwd.getText().toString().trim())) {
                    ToastUtils.showShort("两次密码输入不合法");
                    return;
                }
                if (etRegisterName.getText().toString().trim().length() <= 0) {
                    ToastUtils.showShort("用户名输入不合法");
                    return;
                }
                if (CommonlyUtils.bean == null) {
                    ToastUtils.showShort("请选择所属项目");
                    return;
                }
//                if (CommonlyUtils.listObjBean == null) {
//                    ToastUtils.showShort("请选择所属部门");
//                    return;
//                }
//                if (fax.getText().toString().trim().length() <= 0) {
//                    ToastUtils.showShort("请输入您的岗位");
//                    return;
//                }
                getRegisterData();
                break;
        }
    }

    public void registerNow() {
        //弹出对话框
        RegisterDialog registerDialog = new RegisterDialog(RegisterActivity.this, R.style.RegisterDialog,
                new RegisterDialog.OnRegisterDialogListener() {
                    @Override
                    public void onClick() {
                        finish();
                    }
                });
        registerDialog.setTips(getResources().getString(R.string.register_successed));
        registerDialog.setContents(getResources().getString(R.string.register_successed_tips));
        registerDialog.show();
    }

    public void getRegisterData() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("loginAcct", etRegisterPhone.getText().toString().trim());
        map.put("password", etRegisterConfirmpwd.getText().toString().trim());
        map.put("nickName", etRegisterName.getText().toString().trim());
        map.put("organId",CommonlyUtils.bean.getId());
        presenter.getRegisterData(map);
    }

    @Override
    public void getRegisterDataonNext(com.jinke.housekeeper.bean.TestInfo info) {
        JPushInterface.setAlias(RegisterActivity.this, info.getUserId(), null);
        registerNow();
    }

    @Override
    public void getRegisterDataonError(String code, String msg) {
        tvRegisterErrortips.setText(msg);
        tvRegisterErrortips.setVisibility(View.VISIBLE);
        CommonlyUtils.errorState(RegisterActivity.this, code);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CommonlyUtils.bean != null) {
            tvRigsterProject.setText(CommonlyUtils.bean.getName());
            tvRigsterProject.setTextColor(getResources().getColor(R.color.black));
        }

        if (CommonlyUtils.listObjBean != null) {
            tvRigsterDepartment.setText(CommonlyUtils.listObjBean.getName());
            tvRigsterDepartment.setTextColor(getResources().getColor(R.color.black));
        }
        StatService.onResume(RegisterActivity.this);
        StatService.trackBeginPage(RegisterActivity.this, "用户注册");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonlyUtils.bean = null;
        CommonlyUtils.listObjBean = null;
    }

    @Override
    public RegisterActivityPresenter initPresenter() {
        return new RegisterActivityPresenter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(RegisterActivity.this);
        StatService.trackEndPage(RegisterActivity.this, "用户注册");
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onBackClick() {
        finish();
    }
}
