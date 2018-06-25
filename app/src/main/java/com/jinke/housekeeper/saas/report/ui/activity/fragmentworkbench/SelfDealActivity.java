package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.content.Intent;
import android.view.View;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/4.
 */

public class SelfDealActivity extends BaseActivity implements NavigationView.OnNacigationTitleCallback {
    @Bind(R.id.titleBar)
    NavigationView title;

    private String isown = "0";//默认不自检

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_selfdeal;
    }

    @Override
    protected void initView() {
        initTitle();
    }

    private void initTitle() {
        title.setSaveVISIBLE(View.GONE);
        title.setBackVisible(View.VISIBLE);
        title.setDepartLineVisible(View.VISIBLE);
        title.setOnNavigationCallback(this);
        title.setTitle(getString(R.string.activity_report_register_self_deal));
    }

    @OnClick({R.id.text_self, R.id.text_others})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_self:
                isown = "1";
                break;
            case R.id.text_others:
                isown = "0";
                break;
        }
        Intent intent = new Intent(this, ReportRegistActivity.class);
        intent.putExtra("isown", isown);
//        intent.putExtra("inspType","")
        setResult(RESULT_OK, intent);
        this.finish();
    }

    @Override
    public void onBackClick() {
//        Intent intent = new Intent(this, ReportRegistActivity.class);
//        intent.putExtra("isown", "0");
//        setResult(RESULT_OK, intent);
        finish();
    }
}
