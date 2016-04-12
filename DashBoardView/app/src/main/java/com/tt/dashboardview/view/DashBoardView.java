package com.tt.dashboardview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by tuozhaobing on 16-4-12.
 * Add Some Description There
 */
public class DashBoardView extends View implements ViewTreeObserver.OnGlobalLayoutListener{
    private Context mContext;
    private Paint mCirclePaint,mDegreePaint,mHourPaint,mMinPaint;
    private int mViewWidth,mViewHeight;

    public DashBoardView(Context context) {
        super(context);
        init(context);
    }

    public DashBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DashBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void onGlobalLayout() {
        mViewHeight = getHeight();
        mViewWidth = getWidth();
    }

    public void init(Context context){
        mContext = context;
        mCirclePaint =  new Paint(Paint.ANTI_ALIAS_FLAG);
        mDegreePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHourPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMinPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStrokeWidth(5);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mHourPaint.setStrokeWidth(20);
        mMinPaint.setStrokeWidth(10);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
        * 画表盘圆形
        * */
        canvas.drawCircle(mViewWidth/2,mViewHeight/2,mViewWidth/2,mCirclePaint);
        /*
        * 画刻度
        * */
        for (int i=0;i<24;i++){
            if(i==0 || i==6 || i==12 || i==18){
                /*
                * 画整点刻度
                * */
                mDegreePaint.setStrokeWidth(5);
                mDegreePaint.setTextSize(30);
                canvas.drawLine(mViewWidth/2,mViewHeight/2-mViewWidth/2,mViewWidth/2,mViewHeight/2-mViewWidth/2+60,mDegreePaint);
                String degree = String.valueOf(i);
                canvas.drawText(degree,mViewWidth/2-mDegreePaint.measureText(degree)/2,mViewHeight/2-mViewWidth/2+90,mDegreePaint);
            }else{
                mDegreePaint.setStrokeWidth(3);
                mDegreePaint.setTextSize(15);
                canvas.drawLine(mViewWidth/2,mViewHeight/2-mViewWidth/2,mViewWidth/2,mViewHeight/2-mViewWidth/2+30,mDegreePaint);
                String degree = String.valueOf(i);
                canvas.drawText(degree,mViewWidth/2-mDegreePaint.measureText(degree)/2,mViewHeight/2-mViewWidth/2+90,mDegreePaint);
            }
            /*
            * 通过旋转画布来画好所有的刻度
            * */
            canvas.rotate(15,mViewWidth/2,mViewHeight/2);
        }
        /*
        * 画指针
        * */
        mHourPaint.setStrokeWidth(20);
        mMinPaint.setStrokeWidth(10);
        canvas.save();
        canvas.translate(mViewWidth/2,mViewHeight/2);
        canvas.drawLine(0,0,100,100,mHourPaint);
        canvas.drawLine(0,0,100,200,mMinPaint);
        canvas.restore();
    }
}
