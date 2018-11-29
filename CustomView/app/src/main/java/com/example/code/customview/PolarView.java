package com.example.code.customview;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author
 * @Date 2018/8/28
 * @description
 * @since 1.0.0
 */
public class PolarView extends View {

  private int mWidth;
  private int mHeight;

  private int mTmpPointCount = 10;

  private ValueAnimator mValueAnimator;

  private int mRadius = 200;

  private Paint mPaint;

  public PolarView(Context context) {
    super(context);
    init();
  }

  public PolarView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public PolarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    if (w > 0 && h > 0) {
      mWidth = w;
      mHeight = h;
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawPath(makePath(), mPaint);
  }

  public void startAnimation(int count) {
    mValueAnimator = ValueAnimator.ofInt(3, count);
    mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mTmpPointCount = (int) valueAnimator.getAnimatedValue();
        postInvalidate();
      }
    });
    mValueAnimator.setDuration(5000);
    mValueAnimator.setRepeatCount(-1);
    mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
    mValueAnimator.start();
  }

  private Path makePath() {
    Path path = new Path();
    double angle = 2.0 * Math.PI / mTmpPointCount;
    path.moveTo(
        mWidth / 2 + (float) (mRadius * Math.cos(0.0)),
        mHeight / 2 + (float) (mRadius * Math.sin(0.0)));
    for (int i = 1; i < mTmpPointCount + 1; i++) {
      path.lineTo(
          mWidth / 2 + (float) (mRadius * Math.cos(angle * i)),
          mHeight / 2 + (float) (mRadius * Math.sin(angle * i)));
    }
    path.close();
    return path;
  }

  private void init() {
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setColor(Color.BLUE);
    mPaint.setStyle(Style.STROKE);
    mPaint.setStrokeWidth(10);
  }
}
