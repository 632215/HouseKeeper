package com.jinke.housekeeper.ui.activity.fragmentuser;

import android.widget.ListView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.presenter.MemberDetailsActivityPresenter;
import com.jinke.housekeeper.adapter.MemberDetailsAapter;
import com.jinke.housekeeper.bean.MemberListBean;
import com.jinke.housekeeper.view.MemberDetailsActivityView;
import com.tencent.stat.StatService;

import butterknife.Bind;
import www.jinke.com.library.widget.NavigationView;

/**
 * Created by root on 17-4-6.
 */

public class MemberDetailsActivity extends BaseActivity<MemberDetailsActivityView,MemberDetailsActivityPresenter> implements NavigationView.OnNacigationTitleCallback {
    @Bind(R.id.titleBar)
    NavigationView titleBar;
    @Bind(R.id.listView)
    ListView listView;
    MemberListBean.ObjBean objBean;
    MemberDetailsAapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_memberdetails;
    }

    @Override
    protected void initView() {
        objBean = (MemberListBean.ObjBean) getIntent().getSerializableExtra("obj");
        titleBar.setOnNavigationCallback(this);
        if (objBean != null) {
            titleBar.setTitle(objBean.getName());
            adapter = new MemberDetailsAapter(MemberDetailsActivity.this, objBean.getUlist());
            listView.setAdapter(adapter);
        }

    }

    @Override
    public void onBackClick() {
        finish();

    }
    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(MemberDetailsActivity.this);
        StatService.trackBeginPage(MemberDetailsActivity.this, "人员详情");
    }

    @Override
    public MemberDetailsActivityPresenter initPresenter() {
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MemberDetailsActivity.this);
        StatService.trackEndPage(MemberDetailsActivity.this, "人员详情");
    }
}
