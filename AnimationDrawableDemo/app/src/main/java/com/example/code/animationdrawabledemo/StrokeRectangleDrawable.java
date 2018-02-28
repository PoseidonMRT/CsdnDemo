package com.example.code.animationdrawabledemo;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.renderscript.Sampler.Value;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by tuozhaobing on 18/2/28.
 */

public class StrokeRectangleDrawable extends Drawable {

  private Paint mRectanglePaint;
  private int mRotate;
  private int mStrokeWidth;

  private ValueAnimator mValueAnimator;

  public static final String KEY_ROTATE = "ROTATE";
  public static final String KEY_STROKE = "STROKE";


  public StrokeRectangleDrawable(int color) {
    super();
    mRectanglePaint = new Paint();
    mRectanglePaint.setColor(color);
    mRectanglePaint.setStyle(Style.STROKE);
  }

  @Override
  public void draw(@NonNull Canvas canvas) {
    canvas.rotate(mRotate,getBounds().left+getBounds().width()/2,getBounds().top+getBounds().height()/2);
    mRectanglePaint.setStrokeWidth(mStrokeWidth);
    canvas.drawRect(new RectF(getBounds()),mRectanglePaint);
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
    PropertyValuesHolder mPropertyStroke = PropertyValuesHolder.ofInt(KEY_STROKE,5,100);
    mValueAnimator = new ValueAnimator();

    mValueAnimator.setValues(mPropertyStroke,mPropertyRotate);
    mValueAnimator.setDuration(1000);
    mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
    mValueAnimator.setRepeatCount(-1);
    mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        mRotate = (int) animation.getAnimatedValue(KEY_ROTATE);
        mStrokeWidth = (int) animation.getAnimatedValue(KEY_STROKE);
        invalidateSelf();
      }
    });
    mValueAnimator.start();
  }
}
