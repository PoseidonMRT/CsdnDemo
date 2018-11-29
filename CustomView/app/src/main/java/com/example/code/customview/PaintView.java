package com.example.code.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.icu.util.TimeZone.SystemTimeZoneType;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author
 * @Date 2018/9/4
 * @description View坐标系示例
 * @since 1.0.0
 */
public class PaintView extends View {

  /*
  画笔，用于View内部内容的绘制
   */
  private Paint mPaint;

  /*
  用于存储View的宽高
   */
  private int mWidth,mHeight;

  public PaintView(Context context) {
    super(context);
    init();
  }

  public PaintView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init(){
    //新建画笔对象
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //为画笔设置颜色
    mPaint.setColor(Color.BLUE);
    //设置画笔实心空心,Style.FILL--实心,Style.STROKE--空心
    mPaint.setStyle(Style.FILL);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    if (w > 0 && h > 0){
      mWidth = w;
      mHeight = h;
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    /**
     * 前两个参数分别代表圆心的X坐标和Y坐标
     * 第三个参数是圆半径
     * 第四个参数是绘制圆所使用的画笔
     */
    mPaint.setStyle(Style.FILL);
    canvas.drawCircle(mWidth/2,mHeight/2,60,mPaint);

    mPaint.setStyle(Style.STROKE);
    mPaint.setAlpha(200);
    mPaint.setStrokeWidth(20);

    canvas.drawCircle(mWidth/2,mHeight/2,70,mPaint);

    mPaint.setStyle(Style.STROKE);
    mPaint.setAlpha(150);
    mPaint.setStrokeWidth(20);

    canvas.drawCircle(mWidth/2,mHeight/2,90,mPaint);

    mPaint.setStyle(Style.STROKE);
    mPaint.setAlpha(100);
    mPaint.setStrokeWidth(20);

    canvas.drawCircle(mWidth/2,mHeight/2,110,mPaint);
  }
}
