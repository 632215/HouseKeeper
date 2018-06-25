package com.jinke.housekeeper.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.bean.ScenePageInfo;
import com.jinke.housekeeper.presenter.MainsActivityPresenter;
import com.jinke.housekeeper.ui.fragment.activitymain.CountFragment;
import com.jinke.housekeeper.ui.fragment.activitymain.KnowledgeFragment;
import com.jinke.housekeeper.ui.fragment.activitymain.MsgFragment;
import com.jinke.housekeeper.ui.fragment.activitymain.UsersFragment;
import com.jinke.housekeeper.ui.fragment.activitymain.WorkBenchFragment;
import com.jinke.housekeeper.utils.ACache;
import com.jinke.housekeeper.utils.CommonlyUtils;
import com.jinke.housekeeper.view.MainsActivityView;
import com.tencent.stat.StatService;

import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * 新主页
 */
public class MainsActivity extends BaseActivity<MainsActivityView, MainsActivityPresenter> implements RadioGroup.OnCheckedChangeListener, MainsActivityView {
    @Bind(R.id.activity_main_btn)
    RadioGroup radioGroup;
    private MsgFragment msgFragment;
    private CountFragment countFragment;
    private KnowledgeFragment knowledgeFragment;
    private UsersFragment usersFragment;
    private WorkBenchFragment workBenchFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private long mExitTime;
    private ACache aCache;
    //缓存时长
    public static int CACHINGTIME = 60 * 100 * 60;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mains;
    }

    @Override
    protected void initView() {
        aCache = ACache.get(this);
        setDefaultFragment();
        //更新本地缓存项目数据
        getAllScenePage();
    }

    @Override
    public MainsActivityPresenter initPresenter() {
        return new MainsActivityPresenter(this);
    }

    private void getAllScenePage() {
        SortedMap<String, String> map = new TreeMap<>();
        presenter.getAllScenePage(map);
    }

    @Override
    public void getAllScenePageOnError(String code, String msg) {
        ToastUtils.showShort(msg);
        CommonlyUtils.errorState(MyApplicaption.getInstance(), code);
    }

    @Override
    public void getAllScenePageOnNext(ScenePageInfo info) {
        aCache.put("ScenePage", info, CACHINGTIME);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void setDefaultFragment() {
        if (msgFragment == null) {
            msgFragment = new MsgFragment();
        }
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.activity_main_fragment, msgFragment);
        transaction.commit();
        radioGroup.check(R.id.activity_main_radio_button_msg);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        switch (checkedId) {
            case R.id.activity_main_radio_button_msg://消息
                if (msgFragment == null) {
                    msgFragment = new MsgFragment();
                }
                transaction.replace(R.id.activity_main_fragment, msgFragment);
                break;

            case R.id.activity_main_radio_button_knowledge://知识库
                if (knowledgeFragment == null) {
                    knowledgeFragment = new KnowledgeFragment();
                }
                transaction.replace(R.id.activity_main_fragment, knowledgeFragment);
                break;

            case R.id.activity_main_radio_button_workbench://工作台
                if (workBenchFragment == null) {
                    workBenchFragment = new WorkBenchFragment();
                }
                transaction.replace(R.id.activity_main_fragment, workBenchFragment);
                break;

            case R.id.activity_main_radio_button_examination://自查统计
                if (countFragment == null) {
                    countFragment = new CountFragment();
                }
                transaction.replace(R.id.activity_main_fragment, countFragment);
                break;

            case R.id.activity_main_radio_button_personal://个人中心
                if (usersFragment == null) {
                    usersFragment = new UsersFragment();
                }
                transaction.replace(R.id.activity_main_fragment, usersFragment);
                break;
        }
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, getString(R.string.activity_main_text_drop_out), Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }
        StatService.onResume(MainsActivity.this);
        StatService.trackBeginPage(MainsActivity.this, "MainsActivity");
    }


    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(MainsActivity.this);
        StatService.trackEndPage(MainsActivity.this, "MainsActivity");
    }

    private void doNext(int requestCode, int[] grantResults) {
        switch (requestCode) {
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(MainsActivity.this, "请在应用管理中打开“定位”访问权限！", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

}
