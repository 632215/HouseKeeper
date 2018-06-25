package com.jinke.housekeeper.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jinke.housekeeper.bean.PointInfo;
import com.jinke.housekeeper.utils.PointEvaluator;

/**
 * Created by root on 17-3-14.
 */

public class VerticalProgressView extends View {

    public VerticalProgressView(Context context) {
        super(context);
        init();
    }

    public VerticalProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public VerticalProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    private int width;
    private int height;
    private Paint mPaint;
    private Paint txtPaint;
    private int leftColor;
    private int rightColor;
    private PointInfo currentPoint;
    private String leftText = "";
    private String rightText = "";


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth() - 1;
        height = getMeasuredHeight() - 1;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (currentPoint == null) {
            return;
        }

        //矩形圆角半径
        int half = width / 3;
        float leftValue = currentPoint.getLeft() / 100f;
        float rightValue = currentPoint.getRight() / 100f;

        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setColor(leftColor);
        txtPaint.setTextSize(15);

        //左边矩形
        RectF left = new RectF();
        left.set(0, height - leftValue * height, width / 2, height + half);
        canvas.drawRoundRect(left, half, half, txtPaint);
        //绘制文本文字
        canvas.drawText(leftText != null ? leftText : "0", 0, height - leftValue * height - 5, txtPaint);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(rightColor);
        mPaint.setTextSize(15);

        RectF fill = new RectF();
        fill.set(width / 2, height - rightValue * height, width, height + half);
        //绘制实心矩形rx：x方向上的圆角半径。ry：y方向上的圆角半径。
        canvas.drawRoundRect(fill, half, half, mPaint);
        //绘制文本文字
        canvas.drawText(rightText != null ? rightText : "0", width / 2, height - rightValue * height - 5, mPaint);
    }


    public void dodo(PointInfo info) {
        this.leftColor = info.getLeftColor();
        this.rightColor = info.getRightColor();
        this.leftText = info.getLeftText();
        this.rightText = info.getRightText();
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), 0f, info);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (PointInfo) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.setDuration(2000);
        anim.start();
    }
}
