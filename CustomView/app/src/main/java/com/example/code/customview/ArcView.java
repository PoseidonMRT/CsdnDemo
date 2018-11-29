package com.example.code.customview;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author
 * @Date 2018/8/30
 * @description
 * @since 1.0.0
 */
public class ArcView extends View {

  private Paint mPathPaint;

  private Path mPath;

  private int mWidth;
  private int mHeight;

  private int mRadius = 200;
  private ValueAnimator mValueAnimator;
  private float x = 0f;

  public ArcView(Context context) {
    super(context);
    init();
  }

  public ArcView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
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

    initPaths();

    mPathPaint.setColor(Color.parseColor("#FD9A59"));
    canvas.drawPath(mPath, mPathPaint);

  }

  private void init() {
    mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPathPaint.setStyle(Style.FILL);

    mPath = new Path();
  }

  private void initPaths() {
    mPath.reset();
    mPath.moveTo(mWidth / 2 - mRadius, mHeight / 2);
    mPath.addArc(new RectF(mWidth / 2 - mRadius, mHeight / 2 - mRadius, mWidth / 2 + mRadius,
        mHeight / 2 + mRadius), 180, 90);
    mPath.lineTo(mWidth / 2 - x, mHeight / 2 - x);
    mPath.close();
  }

  public void startAnimation() {
    mValueAnimator = ValueAnimator.ofFloat(0f, mRadius / 2f);
    mValueAnimator.setDuration(5000);
    mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
    mValueAnimator.setRepeatCount(-1);
    mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        x = (float) valueAnimator.getAnimatedValue();
        postInvalidate();
      }
    });
    mValueAnimator.start();
  }
}
