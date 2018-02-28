package com.example.code.animationdrawabledemo;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.GridView;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/2/28
 */
public class FiveCircleRotateDrawable extends Drawable {

        private int mRotate;

    public static final String KEY_ROTATE = "ROTATE";

    private ValueAnimator mValueAnimator;
    private Paint mBluePaint,mRedPaint,mYellowPaint,mGreenPaint,mOrangePaint;

    public FiveCircleRotateDrawable() {
        super();
        mBluePaint = new Paint();
        mBluePaint.setColor(Color.BLUE);
        mRedPaint = new Paint();
        mRedPaint.setColor(Color.RED);
        mYellowPaint = new Paint();
        mYellowPaint.setColor(Color.YELLOW);
        mGreenPaint = new Paint();
        mGreenPaint.setColor(Color.GREEN);
        mOrangePaint = new Paint();
        mOrangePaint.setColor(Color.BLACK);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.rotate(mRotate,getBounds().left+getBounds().width()/2,getBounds().top+getBounds().height()/2);
        Rect bounds = getBounds();
        int centerX = bounds.left+bounds.width()/2;
        int centerY = bounds.top+bounds.height()/2;
        double angle = 3.1415926/180;
        double r = bounds.width() > bounds.height() ? bounds.height()*0.4:bounds.width()*0.4;
        canvas.drawCircle((float) (centerX+r*Math.cos(0*angle)), (float) (centerY + (r * Math.sin(0*angle))),(float) (r*0.1),mRedPaint);
        canvas.drawCircle((float) (centerX+r*Math.cos(72*angle)), (float) (centerY + (r * Math.sin(72*angle))),(float) (r*0.1),mOrangePaint);
        canvas.drawCircle((float) (centerX+r*Math.cos(144*angle)), (float) (centerY + (r * Math.sin(144*angle))),(float) (r*0.1),mYellowPaint);
        canvas.drawCircle((float) (centerX+r*Math.cos(216*angle)), (float) (centerY + (r * Math.sin(216*angle))),(float) (r*0.1),mGreenPaint);
        canvas.drawCircle((float) (centerX+r*Math.cos(288*angle)), (float) (centerY + (r * Math.sin(288*angle))),(float) (r*0.1),mBluePaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void start(){
        PropertyValuesHolder mPropertyRotate = PropertyValuesHolder.ofInt(KEY_ROTATE, 0, 360);
        mValueAnimator = new ValueAnimator();

        mValueAnimator.setValues(mPropertyRotate);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(1000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRotate = (int) animation.getAnimatedValue(KEY_ROTATE);
                invalidateSelf();
            }
        });
        mValueAnimator.start();
    }
}
