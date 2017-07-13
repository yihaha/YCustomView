package com.yibh.ycustomview.loadingview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yibh.ycustomview.R;

/**
 * Created by yibh on 2017/7/12.
 */

public class LoadingTest1 extends View {
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
        int color = typedArray.getColor(R.styleable.YWave_color, Color.rgb(41, 163, 254));
        String text = typedArray.getString(R.styleable.YWave_text);
        typedArray.recycle();

        //初始化画笔
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); //抗锯齿
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        paint.setDither(true);  //防抖动

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);   //粗体字

        Path path = new Path();


    }
}
