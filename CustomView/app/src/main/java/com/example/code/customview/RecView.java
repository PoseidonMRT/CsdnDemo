package com.example.code.customview;

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
 * @Date 2018/9/12
 * @description
 * @since 1.0.0
 */
public class RecView extends View {

  private Paint mPaint;
  private Path mPath;

  public RecView(Context context) {
    super(context);
    init();
  }

  public RecView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public RecView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    //Path对象初始化
    mPath = new Path();
    //移动路径起点到（10,10）
    mPath.moveTo(10,10);
    //从点（10,10）绘制直线到点（200,200）
    mPath.lineTo(200,200);
    //从点（200,200）绘制直线到点（150,200）
    mPath.lineTo(150,200);
    //闭合该路径，从当前点绘制直线到起点
    mPath.close();

    canvas.drawPath(mPath,mPaint);
  }

  private void init(){
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setColor(Color.RED);
    mPaint.setStyle(Style.FILL);
  }
}
