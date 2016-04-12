package com.tt.musicrectangleview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by TuoZhaoBing on 2016/4/12 0012.
 */
public class MusicRectangleView extends View {
    public static final String TAG = "MusicRectangleView";
    private Paint mPaint;
    public int mOffset = 10;
    public int mRectWidth ;
    public int mRectHeight ;
    private int mRectCount = 10;
    private float currentHeight;
    private int mWidth;
    private LinearGradient mLinearGradient;

    public MusicRectangleView(Context context) {
        super(context);
    }

    public MusicRectangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MusicRectangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaint = new Paint();
        mWidth = getWidth();
        mRectHeight = getHeight();
        mRectWidth = (int)(mWidth*0.6/mRectCount);
        mLinearGradient = new LinearGradient(0,0,mRectWidth,mRectHeight, Color.YELLOW,Color.BLUE, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0;i<mRectCount;i++){
            currentHeight = (float)(Math.random()*mRectHeight);
            canvas.drawRect((float)(mWidth*0.4/2+mRectWidth*i+mOffset),currentHeight,(float)(mWidth*0.4/2+mRectWidth*(i+1)),mRectHeight,mPaint);
            postInvalidateDelayed(300);
        }
    }
}
