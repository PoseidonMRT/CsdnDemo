package com.tt.flashbackgroundtextview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/**
 * Created by TuoZhaoBing on 2016/4/12 0012.
 */
public class FlashBackGroundTextView extends TextView {
    public static final String TAG = "FlashBackGroundTextView";
    private Context mContext;
    private Paint mPaint;
    private LinearGradient mLinearGradient = null;
    private int mViewWidth;
    private Matrix mGradientMatrix = null;
    private int mTranslateSpeed;
    private boolean mAnimating = true;
    private float mScale = 0.1f;

    public FlashBackGroundTextView(Context context) {
        super(context);
    }

    public FlashBackGroundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlashBackGroundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAnimating && mGradientMatrix != null){
            mTranslateSpeed += mViewWidth/10;
            if(mTranslateSpeed > 2*mViewWidth){
                mTranslateSpeed = 0;
            }
            mGradientMatrix.setTranslate(mTranslateSpeed,0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0){
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0){
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0,0,mViewWidth,0,new int[]{Color.BLUE,0xffffffff,Color.BLUE},new float[]{0.0f,0.5f,1.0f}, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }
}
