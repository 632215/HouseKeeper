package com.jinke.housekeeper.ui.activity.fragmentkonwledge;

import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.http.HttpMethods;
import com.jinke.housekeeper.bean.LibDetailsInfo;
import com.jinke.housekeeper.presenter.LibDetailsActivityPresenter;
import com.jinke.housekeeper.view.LibDetailsActivityView;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench.ReportRegistActivity;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.tencent.stat.StatService;

import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;
import www.jinke.com.library.utils.NetWorksUtils;
import www.jinke.com.library.widget.NavigationView;

/**
 * Created by root on 17-1-12.
 */

public class LibDetailsActivity extends BaseActivity<LibDetailsActivityView, LibDetailsActivityPresenter> implements
        LoadingLayout.OnReloadListener,
        View.OnClickListener,
        NavigationView.OnNacigationTitleCallback, LibDetailsActivityView {
    @Bind(R.id.titleBar)
    NavigationView titleBar;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.checkDetails)
    TextView checkDetails;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    private String keyId;
    WebSettings settings;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_libedetails;
    }

    @Override
    protected void initView() {
        titleBar.setTitle(getString(R.string.activity_lib_detail_detail));
        keyId = getIntent().getStringExtra("keyId");
        titleBar.setOnNavigationCallback(this);
        checkDetails.setOnClickListener(this);
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.No_Network);
        loadLayout.setOnReloadListener(this);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (loadLayout != null)
                    loadLayout.setStatus(LoadingLayout.Success);
            }
        });
        settings = webview.getSettings();
        settings.setTextSize(WebSettings.TextSize.SMALLER);
        settings.setJavaScriptEnabled(true);
        getDetailsData();
    }

    @Override
    public LibDetailsActivityPresenter initPresenter() {
        return new LibDetailsActivityPresenter(this);
    }

    public void getDetailsData() {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("userId", MyApplicaption.getUserId());
        map.put("sessionId", MyApplicaption.getSessionId());
        map.put("keyId", keyId);
        presenter.getDetailsData(map);
    }

    @Override
    public void getDetailsDataonNext(LibDetailsInfo info) {
        webview.loadUrl(HttpMethods.CSLIB_URL + info.getObj().getHref());//测式
    }

    @Override
    public void getDetailsDataonError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(LibDetailsActivity.this, code);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onClick(View v) {
        //知识库
        Intent intent = new Intent(LibDetailsActivity.this, ReportRegistActivity.class);
        intent.putExtra("inspType", "122");
        startActivity(intent);
        finish();
    }

    @Override
    public void onReload(View v) {
        CommonlyUtils.setLoadConnected(loadLayout, LibDetailsActivity.this);
        getDetailsData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(LibDetailsActivity.this);
        StatService.trackBeginPage(LibDetailsActivity.this, "知识库详情页面");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(LibDetailsActivity.this);
        StatService.trackEndPage(LibDetailsActivity.this, "知识库详情页面");
    }
}
