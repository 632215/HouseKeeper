package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.base.BasePresenter;
import com.jinke.housekeeper.saas.report.ui.activity.fragmentmsg.ReportDealActivity;
import com.jinke.housekeeper.saas.report.ui.widget.NavigationView;
import com.tencent.stat.StatService;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/31.
 */

public class CommitResultActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.title)
    NavigationView title;
    @Bind(R.id.imgView)
    ImageView imgView;
    @Bind(R.id.tvResult)
    TextView tvResult;
    @Bind(R.id.tvExplain)
    TextView tvExplain;
    @Bind(R.id.returnHome)
    TextView returnHome;
    @Bind(R.id.continueInspections)
    TextView continueInspections;
    @Bind(R.id.Inspections_continue_again)
    LinearLayout InspectionsContinueAgain;
    @Bind(R.id.continueInspections_continue)
    TextView continueInspectionsContinue;
    @Bind(R.id.Inspections_continue)
    LinearLayout InspectionsContinue;
    private String result;
    private String patrol;
    private String inspType;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_commitresult;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        result = getIntent().getExtras().getString("result");
        patrol = getIntent().getExtras().getString("patrol");
        inspType = getIntent().getExtras().getString("inspType");
        initTextInfo();//初始化文字信息
        title.setBackVisible(View.GONE);
        title.setTitle(result.equals("0") ? "上传成功" : "上传失败");
        returnHome.setOnClickListener(this);
        continueInspections.setOnClickListener(this);
        continueInspectionsContinue.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initTextInfo() {
        imgView.setImageResource(result.equals("0") ? (R.drawable.uploadyes) : (R.drawable.uploadno));
        tvResult.setText(result.equals("0") ? "上传完毕" : "文件已保存");
        tvExplain.setText(result.equals("0") ? "您上传到的文件会显示\n" +
                "在“个人中心>已提交的报事“里面" : "由于网络原因您上传的文件\n" +
                "可以在“个人中心>离线上传”里面重新上传");
        if ("patrol".equals(patrol)) {
            InspectionsContinueAgain.setVisibility(View.GONE);
            InspectionsContinue.setVisibility(View.VISIBLE);
        } else {
            InspectionsContinueAgain.setVisibility(View.VISIBLE);
            InspectionsContinue.setVisibility(View.GONE);
            returnHome.setText(result.equals("0") ? "巡检处理" : "离线上传");
            continueInspections.setText(result.equals("0") ? "继续巡检" : "继续巡检");
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.returnHome:
//                intent.setClass(CommitResultActivity.this, result.equals("0") ? ReportCommitActivity.class : FileUploadingActivity.class);
                intent.setClass(CommitResultActivity.this, result.equals("0") ? ReportDealActivity.class : FileUploadingActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.continueInspections:
                intent.setClass(CommitResultActivity.this, ReportRegistActivity.class);
                intent.putExtra("inspType", inspType);
                startActivity(intent);
                finish();
                break;

            case R.id.continueInspections_continue:
                finish();
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(CommitResultActivity.this);
        StatService.trackBeginPage(CommitResultActivity.this, "问题上传结果");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(CommitResultActivity.this);
        StatService.trackEndPage(CommitResultActivity.this, "问题上传结果");
    }
}
