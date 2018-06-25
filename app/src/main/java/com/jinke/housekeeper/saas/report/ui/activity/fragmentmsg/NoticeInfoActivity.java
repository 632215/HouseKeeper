package com.jinke.housekeeper.saas.report.ui.activity.fragmentmsg;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseActivity;

import com.jinke.housekeeper.saas.report.http.HttpMethods;
import com.jinke.housekeeper.saas.report.presenter.NoticeInfoActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.jinke.housekeeper.saas.report.util.NetWorksUtils;
import com.jinke.housekeeper.saas.report.view.NoticeInfoActivityView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.tencent.stat.StatService;

import butterknife.Bind;

/**
 * Created by 32 on 2017/1/5.
 */

public class NoticeInfoActivity extends BaseActivity<NoticeInfoActivityView,NoticeInfoActivityPresenter> implements
        LoadingLayout.OnReloadListener,
        NavigationView.OnNacigationTitleCallback,NoticeInfoActivityView
{

    @Bind(R.id.titleBar)
    NavigationView title;
    @Bind(R.id.tv_noticeinfo_title)
    TextView noticeTitle;
    @Bind(R.id.tv_noticeinfo_time)
    TextView noticeTime;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    private String url;
    WebSettings settings;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_noticeinfo;
    }

    @Override
    protected void initView() {
        title.setTitle(getResources().getString(R.string.noticedetails));
        title.setOnNavigationCallback(this);
        String title = getIntent().getStringExtra("title");
        String issuetime = getIntent().getStringExtra("issuetime");
        url = getIntent().getStringExtra("url");
        loadLayout.setOnReloadListener(this);
        loadLayout.setStatus(NetWorksUtils.isConnected(this) ? LoadingLayout.Loading : LoadingLayout.No_Network);
        noticeTitle.setText(title);
        noticeTime.setText(issuetime);

        settings = webview.getSettings();
        settings.setTextSize(WebSettings.TextSize.SMALLER);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (loadLayout != null)
                    loadLayout.setStatus(LoadingLayout.Success);
            }
        });
        webview.loadUrl(HttpMethods.CSLIB_URL + url);
    }

    @Override
    public void onBackClick() {
        finish();
    }


    @Override
    public void onReload(View v) {
        CommonlyUtils.setLoadConnected(loadLayout, NoticeInfoActivity.this);
        webview.loadUrl(HttpMethods.CSLIB_URL + url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(NoticeInfoActivity.this);
        StatService.trackBeginPage(NoticeInfoActivity.this, "通知信息详情页面");
    }

    @Override
    public NoticeInfoActivityPresenter initPresenter() {
        return new NoticeInfoActivityPresenter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(NoticeInfoActivity.this);
        StatService.trackEndPage(NoticeInfoActivity.this, "通知信息详情页面");
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
}
