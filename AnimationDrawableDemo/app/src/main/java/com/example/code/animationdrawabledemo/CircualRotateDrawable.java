package com.example.code.animationdrawabledemo;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Switch;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/2/28
 */
public class CircualRotateDrawable extends Drawable {

    private ValueAnimator mValueAnimator;
    private Paint mCiclePaint;

    private int mRotate;
    private int mOuterSwipe;
    private int mInnerSwipe;

    public static final String KEY_ROTATE = "ROTATE";
    public static final String KEY_SWIPE_INNER_ANGLE = "INNER_SWIPE";
    public static final String KEY_SWIPE_OUTER_ANGLE = "OUTER_SWIPE";

    public CircualRotateDrawable() {
        super();
        mCiclePaint = new Paint();
        mCiclePaint.setColor(Color.parseColor("#f08d43"));
        mCiclePaint.setStrokeWidth(5);
        mCiclePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.rotate(mRotate, getBounds().left + getBounds().width() / 2, getBounds().top + getBounds().height() / 2);
        RectF rectF = new RectF(getBounds().left + 10, getBounds().top + 10, getBounds().right - 10, getBounds().bottom - 10);
        canvas.drawArc(rectF, 0, mOuterSwipe, false, mCiclePaint);
        RectF innerRectF = new RectF(getBounds().left + 20, getBounds().top + 20, getBounds().right - 20, getBounds().bottom - 20);
        canvas.drawArc(innerRectF, -180, mInnerSwipe, false, mCiclePaint);
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
        PropertyValuesHolder mPropertyOuterSwipe = PropertyValuesHolder.ofInt(KEY_SWIPE_OUTER_ANGLE, 10, 270);
        PropertyValuesHolder mPropertyInnerSwipe = PropertyValuesHolder.ofInt(KEY_SWIPE_INNER_ANGLE, -170, 270);

        mValueAnimator = new ValueAnimator();

        mValueAnimator.setValues(mPropertyOuterSwipe,mPropertyInnerSwipe, mPropertyRotate);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(1000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRotate = (int) animation.getAnimatedValue(KEY_ROTATE);
                mOuterSwipe = (int) animation.getAnimatedValue(KEY_SWIPE_OUTER_ANGLE);
                mInnerSwipe = (int) animation.getAnimatedValue(KEY_SWIPE_INNER_ANGLE);
                invalidateSelf();
            }
        });
        mValueAnimator.start();
    }
}
