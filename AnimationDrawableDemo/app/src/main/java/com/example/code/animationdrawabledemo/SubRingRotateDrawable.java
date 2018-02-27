package com.example.code.animationdrawabledemo;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/2/27
 */
public class SubRingRotateDrawable extends Drawable {
    private Paint mArcPaint;
    private int mRotate;
    private int mSwipe;

    public static final String KEY_ROTATE = "ROTATE";
    public static final String KEY_SWIPE_ANGLE = "SWIPE";

    private ValueAnimator mValueAnimator;

    public SubRingRotateDrawable(int color) {
        super();
        mArcPaint = new Paint();
        mArcPaint.setColor(color);
        mArcPaint.setStrokeWidth(10);
        mArcPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.rotate(mRotate,getBounds().left+getBounds().width()/2,getBounds().top+getBounds().height()/2);
        RectF rectF = new RectF(getBounds().left+10,getBounds().top+10,getBounds().right-10,getBounds().bottom-10);
        canvas.drawArc(rectF, 0, mSwipe, false, mArcPaint);
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
        PropertyValuesHolder mPropertySwipe = PropertyValuesHolder.ofInt(KEY_SWIPE_ANGLE, 330, 30);
        mValueAnimator = new ValueAnimator();

        mValueAnimator.setValues(mPropertySwipe,mPropertyRotate);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(1000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRotate = (int) animation.getAnimatedValue(KEY_ROTATE);
                mSwipe = (int) animation.getAnimatedValue(KEY_SWIPE_ANGLE);
                invalidateSelf();
            }
        });
        mValueAnimator.start();
    }
}
