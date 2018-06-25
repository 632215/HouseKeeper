package com.jinke.housekeeper.saas.report.ui.widget;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.jinke.housekeeper.R;
/**
 * Created by root on 17-5-25.
 */
public class LifePaymentWindow extends PopupWindow {
    private Context mContext;
    private LayoutInflater inflater;
    private PopupWindow window;
    OnCheckingListener listener;
    public void setOnCheckingListener(OnCheckingListener listener) {
        this.listener = listener;
    }
    public LifePaymentWindow(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
        initView();
    }
    private void initView() {
        View view = inflater.inflate(R.layout.popu_inspection_registration, null);
        View rootView = view.findViewById(R.id.root_main); // 當前頁面的根佈局
        TextView txwCamera = (TextView) view.findViewById(R.id.txwCamera);
        TextView txwAlbum = (TextView) view.findViewById(R.id.txwAlbum);
        TextView txwCanel = (TextView) view.findViewById(R.id.txwCanel);
        window = new PopupWindow();
        window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setContentView(view);
        window.setOutsideTouchable(true);
        window.setFocusable(false);
        rootView.setOnClickListener(onclick);
        txwCamera.setOnClickListener(onclick);
        txwAlbum.setOnClickListener(onclick);
        txwCanel.setOnClickListener(onclick);
    }
    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.root_main:
                    dimisWindow();
                    break;
                case R.id.txwCamera:
                    listener.checkType("camera");
                    break;
                case R.id.txwAlbum:
                    listener.checkType("album");
                    break;
                case R.id.txwCanel:
                    dimisWindow();
                    break;
            }
        }
    };
    public void showWindow(View v) {
        if (window != null) {
            window.showAtLocation(v, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
        }
    }
    /**
     * 关闭对话框
     */
    public void dimisWindow() {
        window.dismiss();
    }
    public interface OnCheckingListener {
        void checkType(String type);
    }
}