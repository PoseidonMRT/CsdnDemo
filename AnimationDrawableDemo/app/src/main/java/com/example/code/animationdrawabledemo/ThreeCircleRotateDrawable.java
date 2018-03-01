package com.example.code.animationdrawabledemo;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by tuozhaobing on 18/3/1.
 */

public class ThreeCircleRotateDrawable extends Drawable {
  private int mRotate;

  public static final String KEY_ROTATE = "ROTATE";
  public static final String KEY_RADIUS = "RADIUS";

  private ValueAnimator mValueAnimator;
  private Paint mCirclePaint;
  private Paint mCenterPaint;
  private int mTapRadius;

  public ThreeCircleRotateDrawable() {
    super();
    mCirclePaint = new Paint();
    mCirclePaint.setColor(Color.BLUE);
    mCenterPaint = new Paint();
    mCenterPaint.setStyle(Style.STROKE);
    mCenterPaint.setColor(Color.BLUE);
  }

  @Override
  public void draw(@NonNull Canvas canvas) {
    canvas.rotate(mRotate, getBounds().left + getBounds().width() / 2,
        getBounds().top + getBounds().height() / 2);
    Rect bounds = getBounds();
    int centerX = bounds.left + bounds.width() / 2;
    int centerY = bounds.top + bounds.height() / 2;
    double angle = 3.1415926 / 180;
    double r = bounds.width() > bounds.height() ? bounds.height() * 0.4 : bounds.width() * 0.4;
    canvas.drawCircle(centerX,centerY,(float)(r*0.2),mCirclePaint);
    canvas.drawCircle(centerX,centerY,(float) (r+mTapRadius),mCenterPaint);
    canvas.drawCircle((float) (centerX + (r+mTapRadius) * Math.cos(0 * angle)),
        (float) (centerY + ((r+mTapRadius) * Math.sin(0 * angle))), (float) (r * 0.1), mCirclePaint);
    canvas.drawCircle((float) (centerX + (r+mTapRadius) * Math.cos(120 * angle)),
        (float) (centerY + ((r+mTapRadius) * Math.sin(120 * angle))), (float) (r * 0.1), mCirclePaint);
    canvas.drawCircle((float) (centerX + (r+mTapRadius) * Math.cos(240 * angle)),
        (float) (centerY + ((r+mTapRadius) * Math.sin(240 * angle))), (float) (r * 0.1), mCirclePaint);
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
    PropertyValuesHolder mPropertyRadius = PropertyValuesHolder.ofInt(KEY_RADIUS,15,10,5,1,0,-1,-5,-10,-15,-20,-25,-30);
    mValueAnimator = new ValueAnimator();

    mValueAnimator.setValues(mPropertyRadius,mPropertyRotate);
    mValueAnimator.setDuration(1000);
    mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
    mValueAnimator.setRepeatCount(1000);
    mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        mRotate = (int) animation.getAnimatedValue(KEY_ROTATE);
        mTapRadius = (int) animation.getAnimatedValue(KEY_RADIUS);
        invalidateSelf();
      }
    });
    mValueAnimator.start();
  }

}
