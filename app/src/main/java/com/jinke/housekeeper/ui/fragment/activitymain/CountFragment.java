package com.jinke.housekeeper.ui.fragment.activitymain;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseFragmentV4;
import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.bean.UserInfo;
import com.jinke.housekeeper.ui.fragment.fragmentcount.PersonalFragment;
import com.jinke.housekeeper.ui.fragment.fragmentcount.ProjectSFragment;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.utils.TencentCountUtil;

import butterknife.Bind;
import www.jinke.com.library.widget.NavigationView;

/**
 * Created by 32 on 2016/12/26.
 */

public class CountFragment extends BaseFragmentV4 implements RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.titleBar)
    NavigationView titleBar;
    @Bind(R.id.radiogroup)
    RadioGroup radiogroup;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private UserInfo userInfo;
    private PersonalFragment personalFragment;
    private ProjectSFragment projectSFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_count;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        titleBar.setTitle(getString(R.string.activity_main_self_examination_statistics));
        titleBar.setBackVisible(View.GONE);
        manager = getChildFragmentManager();
        transaction = manager.beginTransaction();
        userInfo = CommonlyUtils.getUserInfo(getActivity());
        setDefaultFragment();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    public void setDefaultFragment() {
        switch (userInfo.getRoleType()) {
            case "1": //总部
            case "2"://分公司
                radiogroup.setVisibility(View.GONE);
                if (projectSFragment == null) {
                    projectSFragment = new ProjectSFragment();
                }
                transaction.replace(R.id.viewPager, projectSFragment);
                radiogroup.check(R.id.history);
                break;
            case "3"://项目人员
                radiogroup.setVisibility(View.VISIBLE);
                if (personalFragment == null) {
                    personalFragment = new PersonalFragment();
                }
                transaction.add(R.id.viewPager, personalFragment);
                radiogroup.check(R.id.today);
                break;
        }
        transaction.commit();
        radiogroup.setOnCheckedChangeListener(this);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        manager = getChildFragmentManager();
        transaction = manager.beginTransaction();
        switch (checkedId) {
            case R.id.today:
                TencentCountUtil.count(getActivity(),"PersonalFragment");
                if (personalFragment == null) {
                    personalFragment = new PersonalFragment();
                }
                transaction.replace(R.id.viewPager, personalFragment);
                break;
            case R.id.history:
                TencentCountUtil.count(getActivity(),"ProjectSFragment");
                if (projectSFragment == null) {
                    projectSFragment = new ProjectSFragment();
                }
                transaction.replace(R.id.viewPager, projectSFragment);
                break;
        }
        transaction.commit();
    }
}
