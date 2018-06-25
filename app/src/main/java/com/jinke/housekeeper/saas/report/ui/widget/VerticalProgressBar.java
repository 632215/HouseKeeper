package com.jinke.housekeeper.saas.report.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.jinke.housekeeper.R;

/**
 * @author http://blog.csdn.net/lovehong0306/article/details/7803702
 */
public class VerticalProgressBar extends View {

    private Paint paint;// 画笔
    private int progress;// 进度值
    private int width;// 宽度值
    private int height;// 高度值
    private Paint textPaint;
    private Paint rectPaint;
    private Paint bgRectPaint;
    private int bgcolor = Color.rgb(55, 200, 255);
    private int rectcolor = getResources().getColor(R.color.progress_bg);

    public VerticalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VerticalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalProgressBar(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
        textPaint = new Paint();
        rectPaint = new Paint();
        bgRectPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth() - 1;// 宽度值
        height = getMeasuredHeight() - 1;// 高度值
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(rectcolor);// 设置画笔颜色
        rectPaint.setColor(rectcolor);
        rectPaint.setTextSize(20);
        bgRectPaint.setColor(getResources().getColor(R.color.progress_bg));
        canvas.drawText("20", 5, 20, rectPaint);
        canvas.drawRect(0, 30, width, height, bgRectPaint);// 画矩形
        canvas.drawRect(0, height - progress / 100f * height + 30, width, height, rectPaint);// 画矩形
        textPaint.setColor(Color.WHITE);// 设置画笔颜色为红色
        textPaint.setTextSize(width / 2);// 设置文字大小
        if (progress >= 80) {
            canvas.drawText(progress + "", (width - getTextWidth(progress + "")) / 3, height / 2, textPaint);// 画文字
        } else {
            canvas.drawText(progress + "", (width - getTextWidth(progress + "")) / 3, (height - progress / 100f * height) + 15, textPaint);
        }
        super.onDraw(canvas);
    }

    /**
     * 拿到文字宽度
     * @param str 传进来的字符串
     *            return 宽度
     */
    private int getTextWidth(String str) {
        // 计算文字所在矩形，可以得到宽高
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    /**
     * 设置progressbar进度
     */
    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }


    public void setTextColor(int color) {
        textPaint.setColor(getResources().getColor(color));
        postInvalidate();
    }

    public void setProgressBackgroupColor(int color) {
        this.bgcolor = color;
        invalidate();
    }

    public void setRectBorder(int color) {
        this.rectcolor = color;
        invalidate();
    }


}

