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

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/2/28
 */
public class FourCircleRotateDrawable extends Drawable {

    private int mRotate;
    public static final String KEY_ROTATE = "ROTATE";

    private ValueAnimator mValueAnimator;
    private Paint mBiggerCiclePaint, mLittleCirclePaint;

    public FourCircleRotateDrawable() {
        super();
        mBiggerCiclePaint = new Paint();
        mBiggerCiclePaint.setColor(Color.GRAY);
        mLittleCirclePaint = new Paint();
        mLittleCirclePaint.setColor(Color.WHITE);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.rotate(mRotate,getBounds().left+getBounds().width()/2,getBounds().top+getBounds().height()/2);
        Rect bounds = getBounds();
        int centerX = bounds.left+bounds.width()/2;
        int centerY = bounds.top+bounds.height()/2;
        double angle = 3.1415926/180;
        double r = bounds.width() > bounds.height() ? bounds.height()*0.4:bounds.width()*0.4;
        canvas.drawCircle((float) (centerX), (float) (centerY),(float)(1.2*r),mBiggerCiclePaint);
        canvas.drawCircle((float) (centerX+r*Math.cos(0*angle)), (float) (centerY + (r * Math.sin(0*angle))),(float) (r*0.2),mLittleCirclePaint);
        canvas.drawCircle((float) (centerX+r*Math.cos(90*angle)), (float) (centerY + (r * Math.sin(90*angle))),(float) (r*0.2),mLittleCirclePaint);
        canvas.drawCircle((float) (centerX+r*Math.cos(180*angle)), (float) (centerY + (r * Math.sin(180*angle))),(float) (r*0.2),mLittleCirclePaint);
        canvas.drawCircle((float) (centerX+r*Math.cos(270*angle)), (float) (centerY + (r * Math.sin(270*angle))),(float) (r*0.2),mLittleCirclePaint);
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

    public void start() {
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
