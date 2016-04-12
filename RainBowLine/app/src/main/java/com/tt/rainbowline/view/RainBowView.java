package com.tt.rainbowline.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuozhaobing on 16-4-12.
 * Add Some Description There
 */
public class RainBowView extends View {
    private Context mContext;
    private Paint mPaint;
    private int mViewWidth;
    private LinearGradient mLinearGradient;
    private int mLineWidth = 100;
    private int mOffset = 5;
    private int width = 100;
    private int startX = 0;
    private List<Point> points;
    private boolean firstCreated = true;
    public RainBowView(Context context) {
        super(context);
        mContext = context;
        points = new ArrayList<>();
    }

    public RainBowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        points = new ArrayList<>();
    }

    public RainBowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        points = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(20);
        mViewWidth = getWidth();
        mLinearGradient = new LinearGradient(0,0,width,0, Color.YELLOW,Color.BLUE, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int space = 30;
        if (startX < mViewWidth){
            canvas.drawLine(startX+space,5,startX+width,5,mPaint);
            points.add(new Point(startX+space,startX+width));
            postInvalidateDelayed(100);
        }
        startX = startX+width;
        drawLine(canvas);
    }

    public void drawLine(Canvas canvas){
        for (int i=0;i<points.size();i++){
            canvas.drawLine(points.get(i).x,5,points.get(i).y,5,mPaint);
            postInvalidateDelayed(100);
        }
    }
}
