package com.example.code.customview;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author
 * @Date 2018/9/6
 * @description
 * @since 1.0.0
 */
public class WaveView extends View {

  /**
   * 绘制最中心圆的Paint
   */
  private Paint mInnerCirclePaint;

  /**
   * 绘制外部圆环的Paint
   */
  private Paint mOutterRingPaint;

  /**
   * View宽度
   */
  private int mWidth;

  /**
   * View高度
   */
  private int mHeight;

  /**
   * 内圆半径
   */
  private int mInnerRadius = 50;

  /**
   * 外环宽度
   */
  private int mOuttterRingWidth = 30;

  /**
   * 存储计算所得的外环半径
   */
  private float[] mRadius = new float[4];

  /**
   * 控制外环个数变化的属性动画对象
   */
  private ValueAnimator mValueAnimator;

  /**
   * 绘制的外环总个数
   */
  private int mOutterRingCount = 4;

  public WaveView(Context context) {
    super(context);
    init();
  }

  public WaveView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mInnerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mInnerCirclePaint.setColor(Color.BLUE);
    mInnerCirclePaint.setStyle(Style.FILL);

    mOutterRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mOutterRingPaint.setColor(Color.BLUE);
    mOutterRingPaint.setStyle(Style.STROKE);
    mOutterRingPaint.setStrokeWidth(mOuttterRingWidth);
  }

  private void calculateRadius() {
    for (int i = 0; i < 4; i++) {
      mRadius[i] = mInnerRadius + (i * 2 + 1) * mOutterRingPaint.getStrokeWidth() / 2;
    }
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
    calculateRadius();
    canvas.drawCircle(mWidth / 2, mHeight / 2, mInnerRadius, mInnerCirclePaint);
    for (int i = 0; i < mOutterRingCount; i++) {
      mOutterRingPaint.setAlpha(255 - (int) (255 * ((float) (i + 1) / 5)));
      canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius[i], mOutterRingPaint);
    }
  }

  public void startAnimation(){
    mValueAnimator = ValueAnimator.ofInt(0,5);
    mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
    mValueAnimator.setRepeatCount(-1);
    mValueAnimator.setDuration(2000);
    mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mOutterRingCount = (int) valueAnimator.getAnimatedValue();
        postInvalidate();
      }
    });
    mValueAnimator.start();
  }
}
