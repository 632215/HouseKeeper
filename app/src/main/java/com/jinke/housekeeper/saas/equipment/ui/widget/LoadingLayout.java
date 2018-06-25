package com.jinke.housekeeper.saas.equipment.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinke.housekeeper.R;
import com.jinke.housekeeper.utils.LoadingUtils;

import www.jinke.com.library.widget.ProgressWheel;


/**
 * Created by bruce on 8/19/15.
 */
public class LoadingLayout extends FrameLayout implements View.OnClickListener {

    public final static int Success = 0;
    public final static int Empty = 1;
    public final static int Error = 2;
    public final static int No_Network = 3;
    public final static int Loading = 4;
    public final static int Patrol_Empty_List = 5;

    private int state;

    private Context mContext;
    private View loadingPage;
    private View addPatrolList;
    private View errorPage;
    private View emptyPage;
    private View networkPage;
    private View defineLoadingPage;

    private ImageView errorImg;
    private ImageView emptyImg;
    private ImageView networkImg;

    private TextView errorText;
    private TextView emptyText;
    private TextView networkText;

    private TextView errorReloadBtn;
    private TextView networkReloadBtn;
    private TextView patrol_add;

    private View contentView;
    private OnReloadListener listener;
    //是否一开始显示contentview，默认不显示
    private boolean isFirstVisible;

    //配置
    private static Config mConfig = new Config();
    private static String emptyStr = "暂无数据";
    private static String errorStr = "加载失败，请稍后重试···";
    private static String netwrokStr = "无网络连接，请检查网络···";
    private static String reloadBtnStr = "点击重试";
    private static int emptyImgId = R.drawable.empty_img;
    private static int errorImgId = R.drawable.layout_empty_wifi;
    private static int networkImgId = R.drawable.layout_empty_wifi;
    private static int reloadBtnId = R.drawable.selector_btn_back_gray;
    private static int tipTextSize = 14;
    private static int buttonTextSize = 14;
    private static int tipTextColor = R.color.black;
    private static int buttonTextColor = R.color.base_text_color_light;
    private static int buttonWidth = -1;
    private static int buttonHeight = -1;
    private ProgressWheel progressWheel;
    private static int loadingLayoutId = R.layout.widget_patrol_loading_page;
    private static int backgroundColor = R.color.base_loading_background;

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingLayout);
        isFirstVisible = a.getBoolean(R.styleable.LoadingLayout_isFirstVisible, false);
        a.recycle();
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public LoadingLayout(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException("LoadingLayout can host only one direct child");
        }
        contentView = this.getChildAt(0);
        if (!isFirstVisible) {
            contentView.setVisibility(View.GONE);
        }
        build();
    }

    private void build() {

        loadingPage = LayoutInflater.from(mContext).inflate(R.layout.widget_patrol_loading_page, null);
        errorPage = LayoutInflater.from(mContext).inflate(R.layout.widget_patrol_error_page, null);
        emptyPage = LayoutInflater.from(mContext).inflate(R.layout.widget_patrol_empty_page, null);
        networkPage = LayoutInflater.from(mContext).inflate(R.layout.widget_patrol_nonetwork_page, null);
        addPatrolList = LayoutInflater.from(mContext).inflate(R.layout.widget_addpatrol_patrol_empty_list, null);
        defineLoadingPage = null;
        progressWheel = LoadingUtils.findViewById(loadingPage, R.id.progressBarTwo);
        loadingPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        errorPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        emptyPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        networkPage.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));
        addPatrolList.setBackgroundColor(LoadingUtils.getColor(mContext, backgroundColor));

        errorText = LoadingUtils.findViewById(errorPage, R.id.error_text);
        emptyText = LoadingUtils.findViewById(emptyPage, R.id.empty_text);
        networkText = LoadingUtils.findViewById(networkPage, R.id.no_network_text);

        patrol_add = LoadingUtils.findViewById(addPatrolList, R.id.patrol_add);

        errorImg = LoadingUtils.findViewById(errorPage, R.id.error_img);
        emptyImg = LoadingUtils.findViewById(emptyPage, R.id.empty_img);
        networkImg = LoadingUtils.findViewById(networkPage, R.id.no_network_img);

        errorReloadBtn = LoadingUtils.findViewById(errorPage, R.id.error_reload_btn);
        networkReloadBtn = LoadingUtils.findViewById(networkPage, R.id.no_network_reload_btn);

        errorReloadBtn.setOnClickListener(this);
        networkReloadBtn.setOnClickListener(this);
        patrol_add.setOnClickListener(this);

        errorText.setText(errorStr);
        emptyText.setText(emptyStr);
        networkText.setText(netwrokStr);

        errorText.setTextSize(tipTextSize);
        emptyText.setTextSize(tipTextSize);
        networkText.setTextSize(tipTextSize);

        errorText.setTextColor(LoadingUtils.getColor(mContext, tipTextColor));
        emptyText.setTextColor(LoadingUtils.getColor(mContext, tipTextColor));
        networkText.setTextColor(LoadingUtils.getColor(mContext, tipTextColor));

        errorImg.setImageResource(errorImgId);
        emptyImg.setImageResource(emptyImgId);
        networkImg.setImageResource(networkImgId);


        networkReloadBtn.setBackgroundResource(reloadBtnId);

        networkReloadBtn.setText(reloadBtnStr);

        networkReloadBtn.setTextSize(buttonTextSize);

        networkReloadBtn.setTextColor(LoadingUtils.getColor(mContext, buttonTextColor));

        if (buttonHeight != -1) {

            networkReloadBtn.setHeight(LoadingUtils.dp2px(mContext, buttonHeight));
        }
        if (buttonWidth != -1) {

            networkReloadBtn.setWidth(LoadingUtils.dp2px(mContext, buttonWidth));
        }
        styleRandom(progressWheel, mContext);
        progressWheel.resetCount();
        progressWheel.setText("JK");
        progressWheel.startSpinning();
        this.addView(networkPage);
        this.addView(emptyPage);
        this.addView(errorPage);
        this.addView(loadingPage);
        this.addView(addPatrolList);
    }


    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onReload(v);
        }
    }

    /**
     * 加载动画
     *
     * @param wheel
     * @param ctx
     */
    private void styleRandom(ProgressWheel wheel, Context ctx) {
        wheel.setRimShader(null);
        wheel.setRimColor(0xFFFFFFFF);
        wheel.setCircleColor(0x00000000);
        wheel.setBarColor(0xFF000000);
        wheel.setContourColor(0xFFFFFFFF);
        wheel.setBarWidth(pxFromDp(ctx, 1));
        wheel.setBarLength(pxFromDp(ctx, 100));
        wheel.setSpinSpeed(4f);
        wheel.setDelayMillis(3);
    }

    public static int pxFromDp(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }


    public View getLoadingPltrolAdd() {
        return addPatrolList;
    }

    public void setStatus(@Flavour int status) {

        this.state = status;

        switch (status) {
            case Success:
                contentView.setVisibility(View.VISIBLE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
                addPatrolList.setVisibility(View.GONE);
                if (defineLoadingPage != null) {
                    defineLoadingPage.setVisibility(View.GONE);
                } else {
                    loadingPage.setVisibility(View.GONE);
                }
                break;

            case Loading:
                contentView.setVisibility(View.GONE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
                addPatrolList.setVisibility(View.GONE);
                if (defineLoadingPage != null) {
                    defineLoadingPage.setVisibility(View.VISIBLE);
                } else {
                    loadingPage.setVisibility(View.VISIBLE);
                }
                break;

            case Empty:
                contentView.setVisibility(View.GONE);
                emptyPage.setVisibility(View.VISIBLE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
                addPatrolList.setVisibility(View.GONE);
                if (defineLoadingPage != null) {
                    defineLoadingPage.setVisibility(View.GONE);
                } else {
                    loadingPage.setVisibility(View.GONE);
                }
                break;

            case Error:
                contentView.setVisibility(View.GONE);
                loadingPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.VISIBLE);
                networkPage.setVisibility(View.GONE);
                addPatrolList.setVisibility(View.GONE);
                if (defineLoadingPage != null) {
                    defineLoadingPage.setVisibility(View.GONE);
                } else {
                    loadingPage.setVisibility(View.GONE);
                }
                break;

            case No_Network:

                contentView.setVisibility(View.GONE);
                loadingPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.VISIBLE);
                addPatrolList.setVisibility(View.GONE);
                if (defineLoadingPage != null) {

                    defineLoadingPage.setVisibility(View.GONE);
                } else {
                    loadingPage.setVisibility(View.GONE);
                }
                break;
            case Patrol_Empty_List:
                contentView.setVisibility(View.GONE);
                loadingPage.setVisibility(View.GONE);
                emptyPage.setVisibility(View.GONE);
                errorPage.setVisibility(View.GONE);
                networkPage.setVisibility(View.GONE);
                addPatrolList.setVisibility(View.VISIBLE);
                if (defineLoadingPage != null) {
                    defineLoadingPage.setVisibility(View.GONE);
                } else {
                    loadingPage.setVisibility(View.GONE);
                }
                break;

            default:
                break;
        }

    }


    /**
     * 返回当前状态{Success, Empty, Error, No_Network, Loading}
     *
     * @return
     */
    public int getStatus() {

        return state;
    }

    /**
     * 设置Empty状态提示文本，仅对当前所在的地方有效
     *
     * @param text
     * @return
     */
    public LoadingLayout setEmptyText(String text) {

        emptyText.setText(text);
        return this;
    }

    /**
     * 设置Error状态提示文本，仅对当前所在的地方有效
     *
     * @param text
     * @return
     */
    public LoadingLayout setErrorText(String text) {

        errorText.setText(text);
        return this;
    }

    /**
     * 设置No_Network状态提示文本，仅对当前所在的地方有效
     *
     * @param text
     * @return
     */
    public LoadingLayout setNoNetworkText(String text) {

        networkText.setText(text);
        return this;
    }

    /**
     * 设置Empty状态显示图片，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setEmptyImage(@DrawableRes int id) {


        emptyImg.setImageResource(id);
        return this;
    }

    /**
     * 设置Error状态显示图片，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setErrorImage(@DrawableRes int id) {

        errorImg.setImageResource(id);
        return this;
    }

    /**
     * 设置No_Network状态显示图片，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setNoNetworkImage(@DrawableRes int id) {

        networkImg.setImageResource(id);
        return this;
    }

    /**
     * 设置Empty状态提示文本的字体大小，仅对当前所在的地方有效
     *
     * @param sp
     * @return
     */
    public LoadingLayout setEmptyTextSize(int sp) {

        emptyText.setTextSize(sp);
        return this;
    }

    /**
     * 设置Error状态提示文本的字体大小，仅对当前所在的地方有效
     *
     * @param sp
     * @return
     */
    public LoadingLayout setErrorTextSize(int sp) {

        errorText.setTextSize(sp);
        return this;
    }

    /**
     * 设置No_Network状态提示文本的字体大小，仅对当前所在的地方有效
     *
     * @param sp
     * @return
     */
    public LoadingLayout setNoNetworkTextSize(int sp) {

        networkText.setTextSize(sp);
        return this;
    }

    /**
     * 设置Empty状态图片的显示与否，仅对当前所在的地方有效
     *
     * @param bool
     * @return
     */
    public LoadingLayout setEmptyImageVisible(boolean bool) {

        if (bool) {
            emptyImg.setVisibility(View.VISIBLE);
        } else {
            emptyImg.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置Error状态图片的显示与否，仅对当前所在的地方有效
     *
     * @param bool
     * @return
     */
    public LoadingLayout setErrorImageVisible(boolean bool) {

        if (bool) {
            errorImg.setVisibility(View.VISIBLE);
        } else {
            errorImg.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置No_Network状态图片的显示与否，仅对当前所在的地方有效
     *
     * @param bool
     * @return
     */
    public LoadingLayout setNoNetworkImageVisible(boolean bool) {

        if (bool) {
            networkImg.setVisibility(View.VISIBLE);
        } else {
            networkImg.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置ReloadButton的文本，仅对当前所在的地方有效
     *
     * @param text
     * @return
     */
    public LoadingLayout setReloadButtonText(@NonNull String text) {

        errorReloadBtn.setText(text);
        networkReloadBtn.setText(text);
        return this;
    }

    /**
     * 设置ReloadButton的文本字体大小，仅对当前所在的地方有效
     *
     * @param sp
     * @return
     */
    public LoadingLayout setReloadButtonTextSize(int sp) {

        errorReloadBtn.setTextSize(sp);
        networkReloadBtn.setTextSize(sp);
        return this;
    }

    /**
     * 设置ReloadButton的文本颜色，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setReloadButtonTextColor(@ColorRes int id) {

        errorReloadBtn.setTextColor(LoadingUtils.getColor(mContext, id));
        networkReloadBtn.setTextSize(LoadingUtils.getColor(mContext, id));
        return this;
    }

    /**
     * 设置ReloadButton的背景，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setReloadButtonBackgroundResource(@DrawableRes int id) {

        errorReloadBtn.setBackgroundResource(id);
        networkReloadBtn.setBackgroundResource(id);
        return this;
    }


    /**
     * 设置ReloadButton的监听器
     *
     * @param listener
     * @return
     */
    public LoadingLayout setOnReloadListener(OnReloadListener listener) {

        this.listener = listener;
        return this;
    }


    /**
     * 自定义加载页面，仅对当前所在的Activity有效
     *
     * @param view
     * @return
     */
    public LoadingLayout setLoadingPage(View view) {

        defineLoadingPage = view;
        this.removeView(loadingPage);
        defineLoadingPage.setVisibility(View.GONE);
        this.addView(view);
        return this;
    }

    /**
     * 自定义加载页面，仅对当前所在的地方有效
     *
     * @param id
     * @return
     */
    public LoadingLayout setLoadingPage(@LayoutRes int id) {

        this.removeView(loadingPage);
        View view = LayoutInflater.from(mContext).inflate(id, null);
        defineLoadingPage = view;
        defineLoadingPage.setVisibility(View.GONE);
        this.addView(view);
        return this;
    }

    /**
     * 获取当前自定义的loadingpage
     *
     * @return
     */
    public View getLoadingPage() {

        return defineLoadingPage;
    }


    /**
     * 获取全局使用的loadingpage
     *
     * @return
     */
    public View getGlobalLoadingPage() {
        return loadingPage;
    }




    @IntDef({Success, Empty, Error, No_Network, Loading, Patrol_Empty_List})
    public @interface Flavour {

    }

    public interface OnReloadListener {

        void onReload(View v);
    }


    /**
     * 获取全局配置的class
     *
     * @return
     */
    public static Config getConfig() {

        return mConfig;
    }

    /**
     * 全局配置的Class，对所有使用到的地方有效
     */
    public static class Config {

        public Config setErrorText(@NonNull String text) {

            errorStr = text;
            return mConfig;
        }

        public Config setEmptyText(@NonNull String text) {

            emptyStr = text;
            return mConfig;
        }

        public Config setNoNetworkText(@NonNull String text) {

            netwrokStr = text;
            return mConfig;
        }

        public Config setReloadButtonText(@NonNull String text) {

            reloadBtnStr = text;
            return mConfig;
        }

        /**
         * 设置所有提示文本的字体大小
         *
         * @param sp
         * @return
         */
        public Config setAllTipTextSize(int sp) {

            tipTextSize = sp;
            return mConfig;
        }

        /**
         * 设置所有提示文本的字体颜色
         *
         * @param color
         * @return
         */
        public Config setAllTipTextColor(@ColorRes int color) {

            tipTextColor = color;
            return mConfig;
        }

        public Config setReloadButtonTextSize(int sp) {

            buttonTextSize = sp;
            return mConfig;
        }

        public Config setReloadButtonTextColor(@ColorRes int color) {

            buttonTextColor = color;
            return mConfig;
        }

        public Config setReloadButtonBackgroundResource(@DrawableRes int id) {

            reloadBtnId = id;
            return mConfig;
        }

        public Config setReloadButtonWidthAndHeight(int width_dp, int height_dp) {

            buttonWidth = width_dp;
            buttonHeight = height_dp;
            return mConfig;
        }

        public Config setErrorImage(@DrawableRes int id) {

            errorImgId = id;
            return mConfig;
        }

        public Config setEmptyImage(@DrawableRes int id) {

            emptyImgId = id;
            return this;
        }

        public Config setNoNetworkImage(@DrawableRes int id) {

            networkImgId = id;
            return mConfig;
        }

        public Config setLoadingPageLayout(@LayoutRes int id) {

            loadingLayoutId = id;
            return mConfig;
        }

        public Config setAllPageBackgroundColor(@ColorRes int color) {

            backgroundColor = color;
            return mConfig;
        }
    }
}

