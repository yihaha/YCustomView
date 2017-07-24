package com.yibh.ycustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yibh on 2017/7/20.
 */

public class TestView extends View {
    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private String text = "测试";

    @Override
    protected void onDraw(Canvas canvas) {
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.RED);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

//        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawText(text, 600, 200, textPaint);
        canvas.drawCircle(600, 200, 3, paint);

    }
}
