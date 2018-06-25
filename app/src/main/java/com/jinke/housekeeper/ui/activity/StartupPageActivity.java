package com.jinke.housekeeper.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.Utils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.utils.PermissionUtils;
import com.tencent.stat.StatService;

import butterknife.Bind;
import www.jinke.com.library.base.BaseActivity;

/**
 * Created by root on 16-11-14.
 */
public class StartupPageActivity extends BaseActivity {
    @Bind(R.id.startup)
    LinearLayout startup;

    @Override
    protected int getContentViewId() {
        //全沉浸式布局
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        return R.layout.activity_startuppage;
    }

    @Override
    protected void initView() {
        StatService.trackCustomBeginEvent(this, "oncreate", "首页启动");
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(2000);
        startup.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                if (checkSelfPermission(requestPermissions)) {
                    ActivityCompat.requestPermissions(StartupPageActivity.this, requestPermissions, REQUEST_EXTERNAL_STORAGE);
                } else {
                    startActivity(new Intent(StartupPageActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(StartupPageActivity.this);
        StatService.trackBeginPage(StartupPageActivity.this, "启动页面");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(StartupPageActivity.this);
        StatService.trackEndPage(StartupPageActivity.this, "启动页面");
    }

    private final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] requestPermissions = {
            PermissionUtils.PERMISSION_RECORD_AUDIO,
            PermissionUtils.PERMISSION_GET_ACCOUNTS,
            PermissionUtils.PERMISSION_READ_PHONE_STATE,
            PermissionUtils.PERMISSION_CALL_PHONE,
            PermissionUtils.PERMISSION_CAMERA,
            PermissionUtils.PERMISSION_ACCESS_FINE_LOCATION,
            PermissionUtils.PERMISSION_ACCESS_COARSE_LOCATION,
            PermissionUtils.PERMISSION_READ_EXTERNAL_STORAGE,
            PermissionUtils.PERMISSION_WRITE_EXTERNAL_STORAGE
    };

    private boolean checkSelfPermission(String[] requestPermissions) {
        for (int i = 0; i < requestPermissions.length; i++) {
            if (ContextCompat.checkSelfPermission(StartupPageActivity.this, requestPermissions[i]) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (checkSelfPermission(requestPermissions)) {
                    AlertDialog.Builder normalDialog = new AlertDialog.Builder(StartupPageActivity.this);
                    normalDialog.setTitle("提示");
                    normalDialog.setMessage("请打开对应的应用权限再试");
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    // 显示
                    normalDialog.setCancelable(false);
                    normalDialog.show();
                } else {
                    if (MyApplicaption.getSessionId().equals("")) {
                        startActivity(new Intent(StartupPageActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(StartupPageActivity.this, LoginActivity.class));
                        finish();
                    }
                }
                break;
        }
    }
}
