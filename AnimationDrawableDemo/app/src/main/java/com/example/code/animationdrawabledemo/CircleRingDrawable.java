package com.example.code.animationdrawabledemo;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by tuozhaobing on 18/2/28.
 */

public class CircleRingDrawable extends Drawable {

  public static final String KEY_ROTATE = "ROTATE";

  private ValueAnimator mValueAnimator;
  private Paint mCirclePaint;
  private Paint mRingPaint;

  private int mRotate;

  public CircleRingDrawable(int circleColor,int ringColor) {
    super();
    mCirclePaint = new Paint();
    mCirclePaint.setColor(circleColor);
    mRingPaint = new Paint();
    mRingPaint.setColor(ringColor);
    mRingPaint.setStyle(Style.STROKE);
    mRingPaint.setStrokeWidth(5);
  }

  @Override
  public void draw(@NonNull Canvas canvas) {
    canvas.rotate(mRotate, getBounds().left + getBounds().width() / 2,
        getBounds().top + getBounds().height() / 2);
    Rect bounds = getBounds();
    int centerX = bounds.left + bounds.width() / 2;
    int centerY = bounds.top + bounds.height() / 2;

    double radius = getBounds().width()>getBounds().height()?getBounds().height()*0.4:getBounds().width()*0.4;
    canvas.drawCircle(centerX,centerY,(float) radius,mCirclePaint);
    canvas.drawArc(getBounds().left+10 ,getBounds().top+10,getBounds().right-10,getBounds().bottom-10,0,120,false,mRingPaint);
    canvas.drawArc(getBounds().left+10,getBounds().top+10,getBounds().right-10,getBounds().bottom-10,180,120,false,mRingPaint);
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
