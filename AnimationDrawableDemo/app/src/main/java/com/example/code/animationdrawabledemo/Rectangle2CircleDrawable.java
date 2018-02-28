package com.example.code.animationdrawabledemo;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.Animation;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/2/26
 */
public class Rectangle2CircleDrawable extends Drawable {

    public static final String KEY_RADIUS = "RADIUS";
    public static final String KEY_ROTATE = "ROTATE";

    private ValueAnimator mValueAnimator;

    private int mDrawingRadius = 0;
    private int mDrawingRotate = 0;

    private Paint mPaint;

    public Rectangle2CircleDrawable(int color) {
        super();
        mPaint = new Paint();
        mPaint.setColor(color);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        canvas.rotate(mDrawingRotate,bounds.left+bounds.width()/2,bounds.top+bounds.height()/2);
        canvas.drawRoundRect(bounds.left, bounds.top, bounds.right, bounds.bottom, mDrawingRadius, mDrawingRadius, mPaint);
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
        final PropertyValuesHolder mPropertyRadius = PropertyValuesHolder.ofInt(KEY_RADIUS, 0, 200);
        PropertyValuesHolder mPropertyRotate = PropertyValuesHolder.ofInt(KEY_ROTATE, 0, 360);
        mValueAnimator = new ValueAnimator();

        mValueAnimator.setValues(mPropertyRadius, mPropertyRotate);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(1000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDrawingRadius = (int) animation.getAnimatedValue(KEY_RADIUS);
                mDrawingRotate = (int) animation.getAnimatedValue(KEY_ROTATE);
                invalidateSelf();
            }
        });
        mValueAnimator.start();
    }

    public void stop() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
    }
}
