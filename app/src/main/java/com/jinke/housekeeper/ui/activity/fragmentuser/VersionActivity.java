package com.jinke.housekeeper.ui.activity.fragmentuser;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.ui.widget.CustomPopWindows;
import com.tencent.bugly.beta.Beta;
import com.tencent.stat.StatService;

import butterknife.Bind;
import www.jinke.com.library.widget.NavigationView;

/**
 * Created by 32 on 2017/1/5.
 */

public class VersionActivity extends BaseActivity implements NavigationView.OnNacigationTitleCallback, View.OnClickListener {
    @Bind(R.id.activity_version_layout_root)
    LinearLayout layoutRoot;
    String flag = "";
    @Bind(R.id.activity_version_text_call)
    TextView textCall;
    @Bind(R.id.titleBar)
    NavigationView title;

    @Bind(R.id.tv_version)
    TextView version;

    @Bind(R.id.btn_version)
    Button btn_version;
    private CustomPopWindows callPhoneWindows;//拨打电话的弹出窗口
    private TextView titleContent;//标题
    private TextView cancleDelete;
    private TextView sureDelete;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_version;
    }

    @Override
    protected void initView() {
        title.setTitle(getResources().getString(R.string.fragment_user_version));
        title.setOnNavigationCallback(this);
        btn_version.setOnClickListener(this);
        textCall.setOnClickListener(this);
        version.setText(getPackageInfo(VersionActivity.this).versionName);
        if (flag.equals("latest")) {
            btn_version.setText(getResources().getString(R.string.fragment_user_version));
            btn_version.setBackground(getResources().getDrawable(R.drawable.shape_latestversion_button_background));
        }
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }
    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_version:
                Beta.checkUpgrade();
                break;
            case R.id.activity_version_text_call:
                callPhone();
                break;
        }
    }

    private void callPhone() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ToastUtils.showShort("请打开电话权限！");
        } else {
            callPhoneWindows = new CustomPopWindows(this);
            callPhoneWindows.setContentView(View.inflate(this, R.layout.deletepicwindows, null));
            View deletePicView = callPhoneWindows.getContentView();
            titleContent = (TextView) deletePicView.findViewById(R.id.titleContent);
            cancleDelete = (TextView) deletePicView.findViewById(R.id.cancleDelete);
            sureDelete = (TextView) deletePicView.findViewById(R.id.sureDelete);
            titleContent.setText("是否拨打" + textCall.getText().toString().trim() + "的电话？");
            callPhoneWindows.showAtLocation(layoutRoot, Gravity.CENTER, 0, 0);
            cancleDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callPhoneWindows.dismiss();
                }
            });
            sureDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //用intent启动拨打电话
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textCall.getText().toString().trim()));
                    if (ActivityCompat.checkSelfPermission(VersionActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    VersionActivity.this.startActivity(intent);
                }
            });
        }
    }

    //版本号
    public int getVersionCode() {
        return getPackageInfo(VersionActivity.this).versionCode;
    }


    private PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(VersionActivity.this);
        StatService.trackBeginPage(VersionActivity.this, "版本信息");
    }


    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(VersionActivity.this);
        StatService.trackEndPage(VersionActivity.this, "版本信息");
    }
}
