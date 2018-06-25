package com.jinke.housekeeper.ui.activity.fragmentuser;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.presenter.UserInfoActivityPresenter;
import com.jinke.housekeeper.view.UserInfoActivityView;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.utils.TextUtils;

import butterknife.Bind;
import www.jinke.com.library.widget.NavigationView;

/**
 * Created by Administrator on 2017/8/25.
 */

public class UserInfoActivity extends BaseActivity<UserInfoActivityView,UserInfoActivityPresenter> implements NavigationView.OnNacigationTitleCallback {
    @Bind(R.id.activity_user_info_navigationview)
    NavigationView title;
    //    @Bind(R.id.activity_user_info_image_head)
//    ImageView imageHead;
    @Bind(R.id.activity_user_info_image_head)
    TextView imageHead;
    @Bind(R.id.activity_user_info_text_name)
    TextView textName;
    @Bind(R.id.activity_user_info_text_company)
    TextView txCompany;
    @Bind(R.id.activity_user_info_text_sex)
    TextView textSex;
    @Bind(R.id.activity_user_info_text_fen_company_more)
    TextView textFenCompany;
    @Bind(R.id.activity_user_info_text_department_more)
    TextView textDepartment;
    @Bind(R.id.activity_user_info_text_position_more)
    TextView textPosition;
    @Bind(R.id.activity_user_info_text_phone_more)
    TextView textPhone;
    @Bind(R.id.activity_user_info_text_project_more)
    TextView textProject;
    @Bind(R.id.activity_user_info_layout_fen_company)
    RelativeLayout layoutFenCompany;
    @Bind(R.id.activity_user_info_layout_department)
    RelativeLayout layoutDepartment;
    @Bind(R.id.activity_user_info_layout_position)
    RelativeLayout layoutPosition;
    @Bind(R.id.activity_user_info_layout_phone)
    RelativeLayout layoutPhone;
    @Bind(R.id.activity_user_info_layout_project)
    RelativeLayout layoutProject;
    private UserInfo userInfo;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        initTitle();
        initInfo();
    }

    @Override
    public UserInfoActivityPresenter initPresenter() {
        return new UserInfoActivityPresenter(this);
    }

    private void initTitle() {
        title.setSaveVISIBLE(View.GONE);
        title.setBackVisible(View.VISIBLE);
        title.setDepartLineVisible(View.GONE);
        title.setOnNavigationCallback(this);
        title.setTitle(getString(R.string.activity_user_info_title));

    }

    private void initInfo() {
        userInfo = CommonlyUtils.getUserInfo(this);
        if (userInfo != null) {
            TextUtils.setText(textName, getString(R.string.fragment_user_name), "#292929", userInfo.getName());
            if (userInfo.getCommpany().toString().trim().equals("")){
                TextUtils.setText(txCompany, getString(R.string.fragment_user_company), "#292929", "暂无");
            }else {
                TextUtils.setText(txCompany, getString(R.string.fragment_user_company), "#292929", userInfo.getCommpany());
            }
            if (userInfo.getSex().toString().trim().equals("")){
                TextUtils.setText(textSex, getString(R.string.fragment_user_sex), "#292929","男");
            }else {
                TextUtils.setText(textSex, getString(R.string.fragment_user_sex), "#292929", userInfo.getSex());
            }
            setHead();
            if (userInfo.getFenCompany().equals("")) {
                layoutFenCompany.setVisibility(View.GONE);
            }else {
                textFenCompany.setText(userInfo.getFenCompany());
            }
            if (userInfo.getDeptName().equals("")){
                layoutDepartment.setVisibility(View.GONE);
            }else {
                textDepartment.setText(userInfo.getDeptName());
            }
            if (userInfo.getPosition().equals("")) {
                layoutPosition.setVisibility(View.GONE);
            }else {
                textPosition.setText(userInfo.getPosition());
            }
            if (userInfo.getMobile().equals("")){
                layoutPhone.setVisibility(View.GONE);
            }else {
                textPhone.setText(userInfo.getMobile());
            }
            if (userInfo.getLeftOrgName().equals("")){
                layoutProject.setVisibility(View.GONE);
            }else {
                textProject.setText(userInfo.getLeftOrgName());
            }
        }
    }

    private void setHead() {
        if (userInfo.getName().equals("")) {
            imageHead.setText("JK");
        } else {
            imageHead.setText(userInfo.getName().substring(0, 1));
        }
    }

    @Override
    public void onBackClick() {
        finish();
    }

}
