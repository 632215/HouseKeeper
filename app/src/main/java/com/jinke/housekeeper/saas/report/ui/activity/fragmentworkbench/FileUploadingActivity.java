package com.jinke.housekeeper.saas.report.ui.activity.fragmentworkbench;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.applicaption.MyApplicaption;
import com.jinke.housekeeper.base.BaseActivity;
import com.jinke.housekeeper.db.SelfCheckingBean;
import com.jinke.housekeeper.saas.report.bean.RecorderBean;
import com.jinke.housekeeper.saas.report.presenter.FileUploadingActivityPresenter;
import com.jinke.housekeeper.saas.report.ui.adapter.FileUpLoadAdapter;
import com.jinke.housekeeper.saas.report.ui.widget.FillListView;
import com.jinke.housekeeper.saas.report.ui.widget.ProgressDialog;
import com.jinke.housekeeper.saas.report.util.SharedPreferencesUtils;
import com.jinke.housekeeper.saas.report.util.ToastUtils;
import com.jinke.housekeeper.saas.report.view.FileUploadingActivityView;
import com.jinke.housekeeper.ui.widget.LoadingLayout;
import com.lidroid.xutils.http.RequestParams;
import com.tencent.stat.StatService;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/21.
 */
public class FileUploadingActivity extends BaseActivity<FileUploadingActivityView, FileUploadingActivityPresenter> implements
        View.OnClickListener,
        FileUpLoadAdapter.SelectNumCallBack,
        LoadingLayout.OnReloadListener,
        FileUploadingActivityView {
    @Bind(R.id.title)
    TextView title;//标题栏
    @Bind(R.id.customtitle_back)
    ImageView customtitle_back;
    @Bind(R.id.customtitle_save)
    TextView customtitle_save;
    @Bind(R.id.selectLayout)
    LinearLayout selectLayout;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.fillListView)
    FillListView fillListView;
    @Bind(R.id.btnLayout)
    LinearLayout btnLayout;
    @Bind(R.id.deleteFile)
    TextView deleteFile;
    @Bind(R.id.reLoadFile)
    TextView reLoadFile;
    @Bind(R.id.fillListLine)
    TextView fillListLine;
    @Bind(R.id.loadLayout)
    LoadingLayout loadLayout;
    FileUpLoadAdapter fileUpLoadAdapter;
    List<SelfCheckingBean> selfCheckingBeenList;
    public static boolean isInEdit = false;
    private ArrayList<Integer> upLoadFileList = new ArrayList<>();
    private SelfCheckingBean selfCheckingBean;
    private ArrayList<String> pictureList = new ArrayList<>();
    private ArrayList<RecorderBean> recorderList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fileuploading;
    }

    @Override
    protected void initView() {
        customtitle_back.setOnClickListener(this);
        customtitle_save.setOnClickListener(this);
        deleteFile.setOnClickListener(this);
        checkBox.setOnClickListener(this);
        reLoadFile.setOnClickListener(this);
        loadLayout.setOnReloadListener(this);
        initData();
    }

    private void initData() {
        selfCheckingBeenList = DataSupport.where("userId = ?", MyApplicaption.getUserId()).find(SelfCheckingBean.class);
        if (selfCheckingBeenList.size() > 0) {
            fillListLine.setVisibility(View.VISIBLE);
        }
        loadLayout.setStatus(selfCheckingBeenList.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
        fileUpLoadAdapter = new FileUpLoadAdapter(selfCheckingBeenList, FileUploadingActivity.this, FileUploadingActivity.this);
        fillListView.setAdapter(fileUpLoadAdapter);
        fillListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FileUpLoadAdapter.ViewHolder holder = (FileUpLoadAdapter.ViewHolder) view.getTag();
                holder.checkBox.toggle();
                fileUpLoadAdapter.getIsSelected().put(position, holder.checkBox.isChecked());
            }
        });
    }


    private void setInComplish() {
        title.setText(getString(R.string.activity_quality_inspection_offline_upload));
        selectLayout.setVisibility(View.GONE);
        btnLayout.setVisibility(View.GONE);
        fillListView.setDividerHeight(1);
        customtitle_save.setText(getString(R.string.activity_file_uploading_edit));
        customtitle_save.setTextColor(getResources().getColor(R.color.frame_blue));
        isInEdit = false;
    }

    private void setInEdit() {
        customtitle_save.setText(getString(R.string.activity_file_uploading_complish));
        title.setText(getString(R.string.activity_file_uploading_edit));
        fillListView.setDividerHeight(0);
        selectLayout.setVisibility(View.VISIBLE);
        btnLayout.setVisibility(View.VISIBLE);
        customtitle_save.setTextColor(getResources().getColor(R.color.gray));
        isInEdit = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.customtitle_back:
                finish();
                break;

            case R.id.customtitle_save:
                if (isInEdit == false) {
                    setInEdit();
                } else {
                    setInComplish();
                }
                break;

            case R.id.checkBox:
                if (checkBox.isChecked()) {
                    for (int i = 0; i < selfCheckingBeenList.size(); i++) {
                        fileUpLoadAdapter.getIsSelected().put(i, true);
                    }
                    customtitle_save.setText(getString(R.string.activity_file_uploading_complish));
                    fileUpLoadAdapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < selfCheckingBeenList.size(); i++) {
                        if (fileUpLoadAdapter.getIsSelected().get(i)) {
                            fileUpLoadAdapter.getIsSelected().put(i, false);
                        }
                    }
                    customtitle_save.setText(getString(R.string.activity_file_uploading_cancle));
                    fileUpLoadAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.deleteFile:
                selectFile();
                if (upLoadFileList.size() == 0) {
                    ToastUtils.showLongToast(getString(R.string.activity_file_uploading_no_file_selected));
                    break;
                }
                for (int x : upLoadFileList) {
                    //取数据库中的该条数据
                    selfCheckingBean = selfCheckingBeenList.get(x);
                    deleteUpLoadFile(selfCheckingBean);
                    fileUpLoadAdapter.notifyDataSetChanged();
                }
                finish();
                break;

            case R.id.reLoadFile:
                selectFile();
                if (upLoadFileList.size() == 0) {
                    ToastUtils.showLongToast(getString(R.string.activity_file_uploading_no_file_selected));
                    break;
                }
                for (int x : upLoadFileList) {
                    //取数据库中的该条数据
                    selfCheckingBean = selfCheckingBeenList.get(x);
                    getUpFile(selfCheckingBean);
                }
                break;
        }
    }

    //删除文件
    private void deleteUpLoadFile(SelfCheckingBean bean) {
        String info = bean.getInfo();
        DataSupport.deleteAll(SelfCheckingBean.class, "info = ? ", info);
    }

    private void selectFile() {
        isInEdit = false;
        upLoadFileList = new ArrayList<>();
        Iterator iterator = FileUpLoadAdapter.isSelected.entrySet().iterator();
        selectLayout.setVisibility(View.GONE);
        btnLayout.setVisibility(View.GONE);
        customtitle_save.setText(getString(R.string.activity_file_uploading_edit));
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            int num = (int) entry.getKey();
            boolean tag = (boolean) entry.getValue();
            if (tag == true) {
                upLoadFileList.add(num);
            }
        }
    }

    ProgressDialog dialog;

    public void getUpFile(final SelfCheckingBean selfCheckingBean) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
        params.addBodyParameter("userId", MyApplicaption.getUserId());
        params.addBodyParameter("info", selfCheckingBean.getInfo());
        pictureList.clear();
        pictureList.addAll(selfCheckingBean.getPictureList());
        int fileSize = 0;
        for (int i = 0; i < pictureList.size(); i++) {
            File picFile = new File(pictureList.get(i));
            if (picFile.exists()) {
                params.addBodyParameter("file" + i, picFile);
                fileSize++;
            }
        }
        params.addBodyParameter("fileSize", String.valueOf(fileSize));
        recorderList.clear();
        if (selfCheckingBean.getRecordNameList() != null && selfCheckingBean.getRecordNameList().size() > 0) {
            List<RecorderBean> recorderBeanList = new ArrayList<>();
            for (int x = 0; x < selfCheckingBean.getRecordNameList().size(); x++) {
                recorderBeanList.add(new RecorderBean(selfCheckingBean.getRecordTimeList().get(x), selfCheckingBean.getRecordNameList().get(x)));
            }
            recorderList.addAll(recorderBeanList);
            params.addBodyParameter("audio0", new File(recorderList.get(0).getFilePath()));
            params.addBodyParameter("audioSize", "1");
        }
        //生成header
        SortedMap<String, String> map = new TreeMap<>();
        if (pictureList != null && pictureList.size() >= 2)
            map.put("fileSize", String.valueOf(fileSize));
        if (recorderList != null && recorderList.size() > 0)
            map.put("audioSize", String.valueOf(recorderList.size()));
        map.put("sessionId", SharedPreferencesUtils.init(this).getString("quality_sessionId"));
        map.put("userId", MyApplicaption.getUserId());
        map.put("info", selfCheckingBean.getInfo());
        params.addHeader("signature", MyApplicaption.createSign(map));
        params.addHeader("uid", MyApplicaption.getUserId());
        presenter.getUpFile(params, selfCheckingBean);
    }

    @Override
    public void getUpFileonSuccess(SelfCheckingBean selfCheckingBean, String s) {
        deleteUpLoadFile(selfCheckingBean);
        stateResultActivity(s);//跳转上传成功页面
    }

    @Override
    public void getUpFileshowToast(String errorMsg) {
       com.blankj.utilcode.util.ToastUtils.showShort(errorMsg);
    }

    @Override
    public void getUpFileshowonFailure(String msg) {
        stateResultActivity(msg);//跳转上传失败页面
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(FileUploadingActivity.this);
        StatService.trackBeginPage(FileUploadingActivity.this, "文件上传");
        setInComplish();
    }

    @Override
    public FileUploadingActivityPresenter initPresenter() {
        return new FileUploadingActivityPresenter(this);
    }

    @Override
    public void getSelectNum(int num) {
        checkBox.setChecked(num == selfCheckingBeenList.size() ? true : false);
    }

    @Override
    public void onReload(View v) {
        initData();
    }

    //0表示成功，1表示失败
    private void stateResultActivity(String result) {
        Intent intent = new Intent(FileUploadingActivity.this, CommitResultActivity.class);
        intent.putExtra("result", result);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(FileUploadingActivity.this);
        StatService.trackEndPage(FileUploadingActivity.this, "文件上传");
    }

    @Override
    public void showLoading() {
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }
}
