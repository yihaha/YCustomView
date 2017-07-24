package com.yibh.ycustomview.loadingview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.yibh.ycustomview.DimentionUtils;
import com.yibh.ycustomview.R;

/**
 * Created by yibh on 2017/7/12.
 */

public class LoadingTest1 extends View {

    private Paint mPaint;
    private Paint mTextPaint;
    private Path mPath;
    private int mWidth = DimentionUtils.dip2px(getContext(), 50);
    private int mHeight = DimentionUtils.dip2px(getContext(), 50);
    private int textSize = DimentionUtils.sp2px(getContext(), 25);
    private int mColor;
    private String mText;
    private float mCurrentPercent;

    public LoadingTest1(Context context) {
        this(context, null);
    }

    public LoadingTest1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingTest1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.YWave);
        mColor = typedArray.getColor(R.styleable.YWave_color, Color.rgb(41, 163, 254));
        mText = typedArray.getString(R.styleable.YWave_text);
        typedArray.recycle();

        //初始化画笔
        //抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
        mPaint.setDither(true);  //防抖动
        //文字画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);   //粗体字
        //闭合路径
        mPath = new Path();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPercent = animation.getAnimatedFraction();
                invalidate();
            }
        });
        valueAnimator.start();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }
        setMeasuredDimension(mWidth, mHeight);
        textSize = mWidth / 3;  //字体大小
        mTextPaint.setTextSize(textSize);

    }

    private Path getActionPath(float percent) {
        Path path = new Path();
        int x = -mWidth;
        x += percent * mWidth;
        path.moveTo(x, mHeight / 2);   //起点
        int quadWidth = mWidth / 4;
        int quadHeight = mHeight / 20 * 3;
        //第一个周期
        path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
        path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
        //第二个周期
        path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
        path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
        //右侧的直线
        path.lineTo(x + mWidth * 2, mHeight);
        //下边的直线
        path.lineTo(x, mHeight);
        path.close();   //自动闭合补出左边直线
        return path;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //底部文字
        mTextPaint.setColor(mColor);
        drawCenterText(canvas, mTextPaint, mText);

        //上层文字
        mTextPaint.setColor(Color.WHITE);
//        Path path = new Path();
//        path.addCircle(mWidth / 2, mHeight / 2, mWidth / 2, Path.Direction.CCW);
//        canvas.clipPath(path);
//        //生成闭合波浪路径
//        Path actionPath = getActionPath(mCurrentPercent);
//        //画波浪
//        canvas.drawPath(actionPath, mPaint);
//        //裁剪文字
//        canvas.clipPath(actionPath);
//        drawCenterText(canvas, mTextPaint, mText);

        Path actionPath = getActionPath(mCurrentPercent);
        canvas.clipPath(actionPath);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawPath(actionPath, mPaint);
        mPaint.setXfermode(null);
        drawCenterText(canvas, mTextPaint, mText);

    }

    private void drawCenterText(Canvas canvas, Paint textPaint, String text) {
        Rect rect = new Rect(0, 0, mWidth, mHeight);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        int centerY = (int) (rect.centerY() - top / 2 - bottom / 2);
//        int centerY = (int) (rect.centerY() - top - bottom);
        canvas.drawText(text, rect.centerX(), centerY, textPaint);
    }


}
