package com.jinke.housekeeper.ui.fragment.activitymain;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.bean.AppHandleInfo;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.presenter.UsersFragmentPresenter;
import com.jinke.housekeeper.ui.activity.LoginActivity;
import com.jinke.housekeeper.ui.activity.fragmentuser.MemberlistActivity;
import com.jinke.housekeeper.ui.activity.fragmentuser.PwdActivity;
import com.jinke.housekeeper.ui.activity.fragmentuser.UserInfoActivity;
import com.jinke.housekeeper.ui.activity.fragmentuser.VersionActivity;
import com.jinke.housekeeper.ui.widget.CustomsDialog;
import com.jinke.housekeeper.ui.widget.SwitchView;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.utils.TextUtils;
import com.jinke.housekeeper.view.UsersFragmentView;
import com.tencent.stat.StatService;

import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import butterknife.OnClick;
import www.jinke.com.library.utils.NetWorksUtils;
import www.jinke.com.library.widget.NavigationView;


/**
 * Created by Administrator on 2017/7/27.
 */

public class UsersFragment extends BaseFragmentV4<UsersFragmentView, UsersFragmentPresenter> implements
        CustomsDialog.onYesOnclickListener,
        CustomsDialog.onNoOnclickListener, UsersFragmentView {
    @Bind(R.id.fragment_user_layout_root)
    RelativeLayout rootView;
    @Bind(R.id.fragment_user_navigationview)
    NavigationView title;
    @Bind(R.id.fragment_user_image_head)
    TextView headImag;
    @Bind(R.id.fragment_user_text_name)
    TextView textName;
    @Bind(R.id.fragment_user_text_project)
    TextView textProject;
    @Bind(R.id.fragment_user_text_post)
    TextView textPost;
    @Bind(R.id.fragment_user_layout_people_review)
    RelativeLayout layoutPeopleReview;
    @Bind(R.id.fragment_user_text_push_switch)
    ImageView fragmentUserTextPushSwitch;
    private UserInfo userInfo;
    CustomsDialog customsDialog;

    private boolean isChangeState = false;//是否发起网络请求，false 没有发起网络请求，true 发起网络请求

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_users;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initTitle();
        initInfo();
        getPermission();
        if (NetWorksUtils.isConnected(getActivity())) {
            SortedMap<String, String> map = new TreeMap<>();
            map.put("userId", MyApplicaption.getUserId());
            map.put("sessionId", MyApplicaption.getSessionId());
            isChangeState = true;
            presenter.userPush(map);
        } else {
            if ("Y".equals(CommonlyUtils.getPushState(this.getContext()))) {
                fragmentUserTextPushSwitch.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.push_state_open_button));
            } else {
                fragmentUserTextPushSwitch.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.push_state_close_button));
            }
        }
        fragmentUserTextPushSwitch.setOnClickListener(onClickListener);
    }

    @Override
    public UsersFragmentPresenter initPresenter() {
        return new UsersFragmentPresenter(getActivity());
    }

    private void initInfo() {
        userInfo = CommonlyUtils.getUserInfo(getActivity());
        if (userInfo != null) {
            TextUtils.setText(textName, getString(R.string.fragment_user_name), "#292929", userInfo.getName());
            if (userInfo.getCommpany().equals("")) {
                TextUtils.setText(textProject, getString(R.string.fragment_user_company), "#292929", "暂无");
            } else {
                TextUtils.setText(textProject, getString(R.string.fragment_user_company), "#292929", userInfo.getCommpany());
            }
            TextUtils.setText(textPost, getString(R.string.fragment_user_post), "#292929", userInfo.getRoles());
            setHead();
        }
    }

    private void setHead() {
        if (userInfo.getName().equals("")) {
            headImag.setText("JK");
        } else {
            headImag.setText(userInfo.getName().substring(0, 1));
        }
    }

    private void initTitle() {
        title.setSaveVISIBLE(View.GONE);
        title.setBackVisible(View.GONE);
        title.setDepartLineVisible(View.GONE);
        title.setTitle(getActivity().getString(R.string.fragment_user_title));
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (NetWorksUtils.isConnected(getActivity())) {
                SortedMap<String, String> map = new TreeMap<>();
                map.put("userId", MyApplicaption.getUserId());
                map.put("sessionId", MyApplicaption.getSessionId());
                if ("Y".equals(CommonlyUtils.getPushState(UsersFragment.this.getActivity()))) {
                    Toast toast = Toast.makeText(getContext(), "此关闭推送通知对警告消息无效", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                if ("Y".equals(CommonlyUtils.getPushState(UsersFragment.this.getActivity()))) {
                    map.put("isOpenPush", "N");
                } else {
                    map.put("isOpenPush", "Y");
                }
                presenter.userPush(map);
            } else {
                Toast toast = Toast.makeText(getContext(), "网络中断，请检查网络后重新再试", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }

        }
    };


    private SwitchView.OnCheckedChangeListener onCheckedChangeListener = new SwitchView.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(View view, final boolean isChecked) {
            if (!isChangeState) {

            } else {
                isChangeState = false;
            }

        }
    };


    @OnClick({R.id.fragment_user_text_view, R.id.fragment_user_layout_people_review,
//            R.id.activity_user_info_layout_contact,
            R.id.fragment_user_layout_password,
            R.id.fragment_user_layout_version,
            R.id.fragment_user_text_exit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_user_text_view://查看
                count("fragment_user_text_view");
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.fragment_user_layout_people_review://人员审核
                count("fragment_user_layout_company");
                startActivity(new Intent(getActivity(), MemberlistActivity.class));
                break;
            case R.id.fragment_user_layout_password://修改密码
                count("fragment_user_layout_password");
                startActivityForResult(new Intent(getActivity(), PwdActivity.class), 9211);
                break;
            case R.id.fragment_user_layout_version://关于我们
                count("fragment_user_layout_version");
                startActivity(new Intent(getActivity(), VersionActivity.class));
                break;
            case R.id.fragment_user_text_exit://退出登陆
                count("fragment_user_text_exit");
                showExitTips();
                break;
        }
    }

    private void getPermission() {
        UserInfo info = CommonlyUtils.getUserInfo(getActivity());
        layoutPeopleReview.setVisibility(info.getPower().equals("2") ? View.VISIBLE : View.GONE);
    }

    private void showExitTips() {
        customsDialog = new CustomsDialog(getActivity());
        customsDialog.setTitle(getString(R.string.dialog_exit_title));
        customsDialog.setMessage(getString(R.string.dialog_exit_context));
        customsDialog.setNoOnclickListener(getString(R.string.cancle), this);
        customsDialog.setYesOnclickListener(getString(R.string.sure), this);
        customsDialog.show();
    }

    /**
     * 退出登录
     */
    public void getLoginOut() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", MyApplicaption.getSessionId());
        presenter.getLoginOut(map);
    }

    @Override
    public void getLoinOutonNext(AppHandleInfo info) {
        CommonlyUtils.clearUserInfo(getActivity());
        CommonlyUtils.clearData(getActivity());
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void getLoinOutonError(String code, String msg) {
        showToast(msg);
        CommonlyUtils.errorState(getActivity(), code);
    }

    @Override
    public void userPushNext(String info) {
        CommonlyUtils.savePushState(this.getContext(), info);
        if ("Y".equals(CommonlyUtils.getPushState(this.getContext()))) {
            fragmentUserTextPushSwitch.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.push_state_open_button));
        } else {
            fragmentUserTextPushSwitch.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.push_state_close_button));
        }
    }

    @Override
    public void userPushError(String code, String msg) {
        showToast(msg);
        CommonlyUtils.errorState(getActivity(), code);
    }

    private void count(String reportRegistration) {
        // 统计按钮被点击次数，统计对象：OK按钮
        Properties prop = new Properties();
        prop.setProperty("name", reportRegistration);
        StatService.trackCustomKVEvent(getActivity(), "UsersFragment" + reportRegistration + "_click", prop);
    }

    @Override
    public void onYesClick() {
        getLoginOut();
    }

    @Override
    public void onNoClick() {
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 9211:
                if (null != data && "1".equals(data.getStringExtra("date"))) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
                break;
        }
    }

}
