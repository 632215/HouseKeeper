package www.jinke.com.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import www.jinke.com.library.utils.NetWorksUtils;

/**
 * Created by root on 16-11-13.
 */

public abstract class BaseActivity extends FragmentActivity {

    //布局文件ID
    protected abstract int getContentViewId();

    protected abstract void initView();

    private static List<FragmentActivity> mList = new LinkedList<FragmentActivity>();
    public boolean isConnected = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnected = NetWorksUtils.isConnected(this);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        addActivity(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public static void addActivity(FragmentActivity activity) {
        mList.add(activity);
    }

    public static void exit() {
        try {
            for (FragmentActivity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
            mList = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }


    public static void exit(FragmentActivity ac) {
        try {
            for (FragmentActivity activity : mList) {

                if (activity != null){
                    if (activity.equals(ac)){
                    }else {
                        activity.finish();
                    }
                }
            }
            mList = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

}
