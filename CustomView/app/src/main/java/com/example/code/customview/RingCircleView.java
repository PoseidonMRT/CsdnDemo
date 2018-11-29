package com.example.code.customview;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author
 * @Date 2018/10/26
 * @description
 * @since 1.0.0
 */
public class RingCircleView extends View {

  /**
   * 内部圆画笔
   */
  private Paint mInnerCirclePaint;

  /**
   * 内部圆半径
   */
  private int mInnerRadius;

  /**
   * 外部圆画笔
   */
  private Paint mOutterCirclePaint;

  /**
   * 外部圆半径
   */
  private int mOutterRadius;

  /**
   * 外部小圆运动轨迹
   */
  private Path mOutterCircleMovingPath;
  private PathMeasure mPathMeasure;

  /**
   * View中心点
   */
  private int mCenterX;
  private int mCenterY;

  /**
   * 外部小圆圆心
   */
  private float[] mOutterCirclePositions;

  private ValueAnimator mValueAnimator;

  public RingCircleView(Context context) {
    super(context);
    init();
  }

  public RingCircleView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public RingCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init(){
    mOutterCirclePositions = new float[2];

    mInnerRadius = 100;

    mInnerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mInnerCirclePaint.setColor(Color.BLUE);
    mInnerCirclePaint.setStyle(Style.FILL);

    mOutterRadius = 10;

    mOutterCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mOutterCirclePaint.setColor(Color.BLUE);
    mOutterCirclePaint.setStyle(Style.FILL);

    mOutterCircleMovingPath = new Path();
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    if (w > 0 && h > 0){
      mCenterX = w / 2;
      mCenterY = h / 2;
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    initOutterCircleMovingPath();
    canvas.drawCircle(mCenterX,mCenterY,mInnerRadius,mInnerCirclePaint);
    canvas.drawCircle(mOutterCirclePositions[0],mOutterCirclePositions[1],mOutterRadius,mOutterCirclePaint);
  }

  private void initOutterCircleMovingPath(){
    mOutterCircleMovingPath.reset();
    mOutterCircleMovingPath.addCircle(mCenterX,mCenterY,mInnerRadius+mOutterRadius,Direction.CW);
  }

  public void startAnimation(){
    Log.e("TZB","mCenterX:"+mCenterX+">"+mCenterY);
    mPathMeasure = new PathMeasure(mOutterCircleMovingPath, false);

    mValueAnimator = ValueAnimator.ofFloat(0f, mPathMeasure.getLength());
    mValueAnimator.setDuration(5000);
    mValueAnimator.setRepeatCount(-1);
    mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
    mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float value = (Float) valueAnimator.getAnimatedValue();
        /**
         * value - 当前点到Path起点距离
         * mOutterCirclePositions - 当前点坐标
         * tan -- 切点坐标，根据切点坐标可以计算出切角，从而获得切线
         */
        mPathMeasure.getPosTan(value, mOutterCirclePositions, null);
        postInvalidate();
      }
    });

    mValueAnimator.start();
  }
}
