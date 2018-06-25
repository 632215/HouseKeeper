package com.jinke.housekeeper.saas.equipment.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.saas.equipment.base.BaseFragment;
import com.jinke.housekeeper.saas.equipment.base.BasePresenter;
import com.jinke.housekeeper.saas.equipment.config.EquipmentConfig;
import com.jinke.housekeeper.saas.equipment.http.Api;
import com.jinke.housekeeper.saas.equipment.ui.activity.StandBookActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import www.jinke.com.library.utils.NetWorksUtils;

public class InspectProjectFragment extends BaseFragment {

    @Bind(R.id.inspect_project_web_view)
    WebView inspectProjectWebView;
    @Bind(R.id.inspect_project_hint)
    TextView inspectProjectHint;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_inspect_project;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        initWebView();
    }

    private void initWebView() {
        StandBookActivity standBookActivity = (StandBookActivity) getActivity();
        if (null != standBookActivity && null != standBookActivity.getDeviceTypeBean()) {
            if (NetWorksUtils.isConnected(getActivity()) && null != standBookActivity.getDeviceTypeBean().getId()) {
                inspectProjectHint.setVisibility(View.GONE);
                String loadUrl = Api.BASE_URL + "app/patrolProject?token=" + EquipmentConfig.getTokenBean().getToken() +
                        "&projectId=" + EquipmentConfig.getProjectId() + "&typeId=" + standBookActivity.getDeviceTypeBean().getId() + "";
                inspectProjectWebView.loadUrl(loadUrl);
            } else {
                inspectProjectHint.setVisibility(View.VISIBLE);
            }
        }
    }

}
