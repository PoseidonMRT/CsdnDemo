package com.example.code.animationdrawabledemo;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.RestrictionEntry;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/2/27
 */
public class BulletBallDrawable extends Drawable {

    public static final String KEY_TAP = "tap";
    private ValueAnimator mValueAnimator;
    private Paint mCirclePaint;
    private int mTap = 0;

    public BulletBallDrawable(int ballColor) {
        super();
        mCirclePaint = new Paint();
        mCirclePaint.setColor(ballColor);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Point centerPoint = getBallCenter();
        canvas.drawCircle(centerPoint.x, centerPoint.y + mTap, 50, mCirclePaint);
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
        int centerY = (getBounds().top + getBounds().bottom) / 2;
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofInt(KEY_TAP, centerY, centerY + 100, centerY - 200, centerY);
        mValueAnimator = new ValueAnimator();
        mValueAnimator.setValues(valuesHolder);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(1000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTap = (int) animation.getAnimatedValue(KEY_TAP);
                invalidateSelf();
            }
        });
        mValueAnimator.start();
    }

    private Point getBallCenter() {
        Rect bounds = getBounds();
        return new Point((bounds.left + bounds.right) / 2, (bounds.top + bounds.bottom) / 2);
    }
}
