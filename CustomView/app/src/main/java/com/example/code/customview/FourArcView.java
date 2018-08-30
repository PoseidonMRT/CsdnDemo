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
public class FourArcView extends View {

  private Paint mPathPaint;

  private Path mPath1;
  private Path mPath2;
  private Path mPath3;
  private Path mPath4;

  private int mWidth;
  private int mHeight;

  private int mRadius = 200;
  private ValueAnimator mValueAnimator;
  private float mDistance = 0f;

  public FourArcView(Context context) {
    super(context);
    init();
  }

  public FourArcView(Context context, @Nullable AttributeSet attrs) {
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

    mPathPaint.setColor(Color.parseColor("#FA59FD"));
    canvas.drawPath(mPath1, mPathPaint);

    mPathPaint.setColor(Color.parseColor("#FDF259"));
    canvas.drawPath(mPath2, mPathPaint);

    mPathPaint.setColor(Color.parseColor("#FD9A59"));
    canvas.drawPath(mPath3, mPathPaint);

    mPathPaint.setColor(Color.parseColor("#60FCDC"));
    canvas.drawPath(mPath4, mPathPaint);

  }

  private void init() {
    mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPathPaint.setStyle(Style.FILL);

    mPath1 = new Path();
    mPath2 = new Path();
    mPath3 = new Path();
    mPath4 = new Path();
  }

  private void initPaths() {
    Log.e("TZB",mDistance+">>>>>>>");
    mPath1.reset();
    mPath1.moveTo(mWidth / 2 + mRadius, mHeight / 2);
    mPath1.addArc(new RectF(mWidth / 2 - mRadius, mHeight / 2 - mRadius, mWidth / 2 + mRadius,
        mHeight / 2 + mRadius), 0, 90);
    mPath1.lineTo(mWidth / 2 + mDistance, mHeight / 2 + mDistance);
    mPath1.close();

    mPath2.reset();
    mPath2.moveTo(mWidth / 2, mHeight / 2 + mRadius);
    mPath2.addArc(new RectF(mWidth / 2 - mRadius, mHeight / 2 - mRadius, mWidth / 2 + mRadius,
        mHeight / 2 + mRadius), 90, 90);
    mPath2.lineTo(mWidth / 2 - mDistance, mHeight / 2 + mDistance);
    mPath2.close();

    mPath3.reset();
    mPath3.moveTo(mWidth / 2 - mRadius, mHeight / 2);
    mPath3.addArc(new RectF(mWidth / 2 - mRadius, mHeight / 2 - mRadius, mWidth / 2 + mRadius,
        mHeight / 2 + mRadius), 180, 90);
    mPath3.lineTo(mWidth / 2 - mDistance, mHeight / 2 - mDistance);
    mPath3.close();

    mPath4.reset();
    mPath4.moveTo(mWidth / 2, mHeight / 2 - mRadius);
    mPath4.addArc(new RectF(mWidth / 2 - mRadius, mHeight / 2 - mRadius, mWidth / 2 + mRadius,
        mHeight / 2 + mRadius), 270, 90);
    mPath4.lineTo(mWidth / 2 + mDistance, mHeight / 2 - mDistance);
    mPath4.close();
  }

  public void startAnimation() {
    mValueAnimator = ValueAnimator.ofFloat(0f, mRadius / 2f);
    mValueAnimator.setDuration(5000);
    mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
    mValueAnimator.setRepeatCount(-1);
    mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mDistance = (float) valueAnimator.getAnimatedValue();
        postInvalidate();
      }
    });
    mValueAnimator.start();
  }
}
