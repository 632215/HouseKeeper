package com.jinke.housekeeper.saas.report.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.jinke.housekeeper.R;

import www.jinke.com.library.utils.LogUtils;

/**
 * 首页进度条
 * Created by root on 17-3-22.
 */

public class MagicProgressCircle extends View {

    private int width, height;
    private Paint txPaint, mPaint, txGrape;
    private float arcAngle;
    private String bottomTx = null;
    private String centerTx;
    private int cireColor;

    public MagicProgressCircle(Context context) {
        super(context);
        init();
    }

    public MagicProgressCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MagicProgressCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        txPaint = new Paint();
        txPaint.setAntiAlias(true);
        txPaint.setColor(getResources().getColor(R.color.black));
        txPaint.setTextSize(getResources().getDimension(R.dimen.base_dimen_28));

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.base_dimen_13));

        txGrape = new Paint();
        txGrape.setColor(getResources().getColor(R.color.home_progress));
        txGrape.setAntiAlias(true);
        txGrape.setStyle(Paint.Style.STROKE);
        txGrape.setStrokeWidth(getResources().getDimension(R.dimen.base_dimen_13));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //判断控件初始化但没有设置值
        if (bottomTx == null)
            return;

        mPaint.setColor(cireColor);
        int centerX = width / 2;
        int centerY = height / 2 - 10;
        float half = height / 3;
        canvas.drawCircle(centerX, centerY, half, txGrape);
        RectF oval = new RectF();
        oval.set(centerX - half, centerY - half, centerX + half, centerY + half);
        canvas.drawArc(oval, 0, arcAngle, false, mPaint);
        //绘制底部的问题
        canvas.drawText(bottomTx, width / 4, height - 5, txPaint);
        //绘制中间的文字
        canvas.drawText(centerTx, width / 3, centerY + 5, txPaint);
    }

    /**
     * @param bottomTx  底部文字
     * @param cireColor 进度条的颜色
     * @param progress  进度值 百分比
     */
    public void setData(String bottomTx, String centerTx, int cireColor, float progress) {
        this.bottomTx = bottomTx;
        this.centerTx = centerTx;
        this.cireColor = cireColor;
        initAnimator(progress);
    }

    public void initAnimator(float progress) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, progress/10);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float pr = (float)animation.getAnimatedValue() ;
                arcAngle = 36 * pr;
                invalidate();
            }
        });
    }


}
