package www.jinke.com.library.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import www.jinke.com.library.R;

/**
 * Created by root on 16-11-14.
 */
public class NavigationView extends RelativeLayout implements View.OnClickListener {
    private TextView backView;
    private RelativeLayout layoutTitle;

    private TextView titleView;
    private ImageView titleImage;

    private TextView save;

    private TextView departLine;

    private Context mContext;
    private View view;

    public NavigationView(Context context) {
        super(context);
        mContext = context;
        findView();
    }

    private void findView() {
        LayoutInflater.from(mContext).inflate(R.layout.activity_customtitleview, this);
        backView = (TextView) findViewById(R.id.text_back);
        layoutTitle = (RelativeLayout) findViewById(R.id.layout_title);
        titleView = (TextView) findViewById(R.id.customtitle_title);
        titleImage = (ImageView) findViewById(R.id.image_title);
        departLine = (TextView) findViewById(R.id.text_line);
        save = (TextView) findViewById(R.id.customtitle_save);
        backView.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        findView();
    }

    /**
     * 设置右键图标
     *
     * @param id
     */
    public void setSaveDrawable(int id) {
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        save.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 设置右侧按钮字符
     *
     * @param text
     */
    public void setSave(String text) {
        save.setText(text);
    }


    /**
     * 设置右侧按钮字符颜色
     *
     * @param coloorId
     */
    public void setSaveColor(int coloorId) {
        save.setTextColor(coloorId);
    }

    /**
     * 设置保存 按钮是否可见
     *
     * @param VISIBLE
     */
    public void setSaveVISIBLE(int VISIBLE) {
        save.setVisibility(VISIBLE);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleView.setText(title);
    }

    /**
     * 设置标题图标是否可见
     *
     * @param VISIBLE
     */
    public void setTitleImageVisible(int VISIBLE) {
        titleImage.setVisibility(VISIBLE);
    }


    /**
     * 设置左侧按钮显示
     *
     * @param vis
     */
    public void setBackVisible(int vis) {
        backView.setVisibility(vis);
    }

    /**
     * 设置分割线显示
     *
     * @param VISIBLE
     */
    public void setDepartLineVisible(int VISIBLE) {
        departLine.setVisibility(VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.text_back) {
            callback.onBackClick();
            return;
        }
        if (id == R.id.customtitle_save) {
            saveCallback.onSaveBackClick();
            return;
        }
    }


    /**
     * 设置左侧回调接口
     *
     * @param callback
     */
    private OnNacigationTitleCallback callback;

    public void setOnNavigationCallback(OnNacigationTitleCallback callback) {
        this.callback = callback;
    }

    public interface OnNacigationTitleCallback {
        void onBackClick();
    }

    /**
     * 设置右侧点击回调接口
     *
     * @param callback
     */
    private OnNacigationSaveCallback saveCallback;

    public void setOnNavigationSave(OnNacigationSaveCallback callback) {
        this.saveCallback = callback;
    }

    public interface OnNacigationSaveCallback {
        void onSaveBackClick();
    }


    /**
     * 获取右侧按钮,默认不显示
     *
     * @return
     */
    public TextView getRightView() {
        return save;
    }

    /**
     * 获取标题控件
     *
     * @return
     */
    public TextView getTitleView() {
        return titleView;
    }

    /**
     * 获取返回按钮
     *
     * @return
     */
    public TextView getBackView() {
        return backView;
    }

}
