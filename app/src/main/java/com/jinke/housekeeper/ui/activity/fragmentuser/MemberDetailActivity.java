package com.jinke.housekeeper.ui.activity.fragmentuser;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.MemberListBean;
import com.jinke.housekeeper.bean.TestInfo;
import com.jinke.housekeeper.presenter.MemberDetailActivityPresenter;
import com.jinke.housekeeper.ui.activity.MainsActivity;
import com.jinke.housekeeper.ui.activity.RegisterDepartmentActivity;
import com.jinke.housekeeper.ui.activity.RegisterFirmActivity;
import com.jinke.housekeeper.view.MemberDetailActivityView;
import com.jinke.housekeeper.ui.widget.MaterialDialog;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.tencent.stat.StatService;

import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import www.jinke.com.library.widget.NavigationView;

/**
 * 人员信息操作
 * Created by 32 on 2017/3/19.
 */

public class MemberDetailActivity extends BaseActivity<MemberDetailActivityView, MemberDetailActivityPresenter> implements MemberDetailActivityView
        , NavigationView.OnNacigationTitleCallback
        , View.OnClickListener {
    @Bind(R.id.titleBar)
    NavigationView titleBar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.phone)
    TextView phone;
    @Bind(R.id.position)
    TextView position;
    @Bind(R.id.tv_rigster_project)
    TextView tv_rigster_project;
    @Bind(R.id.tv_rigster_department)
    TextView tv_rigster_department;
    @Bind(R.id.newMenberLayout)
    LinearLayout newMenberLayout;
    @Bind(R.id.agree)
    TextView agree;
    @Bind(R.id.newMenberDelete)
    TextView newMenberDelete;
    @Bind(R.id.oldMenberLayout)
    LinearLayout oldMenberLayout;
    @Bind(R.id.oldMenberDelete)
    TextView oldMenberDelete;
    private MemberListBean.ObjBean.UlistBean ulistBean;
    private String pass = null;//是否通过

    @Override
    protected int getContentViewId() {
        return R.layout.activity_memberdetail;
    }

    @Override
    protected void initView() {
        ulistBean = (MemberListBean.ObjBean.UlistBean) getIntent().getSerializableExtra("ulistBean");
        if (ulistBean.getCheckStatus().equals("Y")) {
            //根据人员性质拟定
            titleBar.setTitle("人员信息");
            oldMenberLayout.setVisibility(View.VISIBLE);
            newMenberLayout.setVisibility(View.GONE);
        } else {
            titleBar.setTitle("新员工申请加入");
            oldMenberLayout.setVisibility(View.GONE);
            newMenberLayout.setVisibility(View.VISIBLE);
        }
        titleBar.setOnNavigationCallback(this);
        name.setText(ulistBean.getNickName().equals("") ? "暂无" : ulistBean.getNickName());
        phone.setText(ulistBean.getPhone().equals("") ? "暂无" : ulistBean.getPhone());
        position.setText(ulistBean.getFax().equals("") ? "暂无" : ulistBean.getFax());
        tv_rigster_project.setText(ulistBean.getOrg().equals("") ? "暂无" : ulistBean.getOrg());
        tv_rigster_department.setText(ulistBean.getScenes().equals("") ? "暂无" : ulistBean.getScenes());
        initData();
    }

    @Override
    public MemberDetailActivityPresenter initPresenter() {
        return new MemberDetailActivityPresenter(this);
    }

    private void initData() {
        tv_rigster_project.setOnClickListener(this);
        tv_rigster_department.setOnClickListener(this);
        agree.setOnClickListener(this);
        newMenberDelete.setOnClickListener(this);
        oldMenberDelete.setOnClickListener(this);

    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rigster_project:
                Intent intents = new Intent(this, RegisterFirmActivity.class);
                intents.putExtra("parentId", "");
                intents.putExtra("flag", "3");
                startActivity(intents);
                break;
            case R.id.tv_rigster_department:
                Intent intent = new Intent(this, RegisterDepartmentActivity.class);
                startActivity(intent);
                break;
            case R.id.agree:
                pass = "true";
                examineUser("同意", "check");
                break;
            case R.id.newMenberDelete:
                pass = "false";
                examineUser("删除", "delete");
                break;
            case R.id.oldMenberDelete:
                examineUser("删除", "delete");
                break;

        }
    }

    private void examineUser(String message, final String dealResult) {
        final MaterialDialog materialDialog = new MaterialDialog(this);
        materialDialog.setTitle("提示消息");
        materialDialog.setMessage("是否确定" + message + ulistBean.getNickName() + "!");
        materialDialog.setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
                getUserDelete(dealResult);
            }
        });
        materialDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        });
        materialDialog.show();
    }

    /**
     * 删除人员
     *
     * @param dealResult
     */
    private void getUserDelete(String dealResult) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("userTargetId", String.valueOf(ulistBean.getId()));
        map.put("controlType", dealResult);
        if (pass != null) {
            map.put("pass", pass);
        }
        map.put("sessionId", MyApplicaption.getSessionId());
        presenter.getUserDelete(map);
    }

    @Override
    public void getUserDeleteNext(TestInfo testInfo) {
        startActivity(new Intent(MemberDetailActivity.this, MainsActivity.class));
        finish();
    }

    @Override
    public void getUserDeleteError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(MemberDetailActivity.this, code);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MemberDetailActivity.this);
        StatService.trackBeginPage(MemberDetailActivity.this, "人员信息");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MemberDetailActivity.this);
        StatService.trackEndPage(MemberDetailActivity.this, "人员信息");
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

}
