package com.example.code.ofopagedemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/1/5
 */
public class ArcBackgroundView extends View {
    private float mViewWidth;
    private float mViewHeight;
    private int mBackgroundColor;
    private Paint mArcPaint;
    private Path mPath;

    public ArcBackgroundView(Context context) {
        super(context);
    }

    public ArcBackgroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ArcBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ArcBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculatePath();
        canvas.clipPath(mPath);
        canvas.drawColor(mBackgroundColor);
    }

    private void init(Context context) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(R.styleable.ArcBackgroundView);
        mBackgroundColor = typedArray.getColor(R.styleable.ArcBackgroundView_backgroundColor, Color.YELLOW);
        mPath = new Path();
        mArcPaint = new Paint();
        mArcPaint.setColor(mBackgroundColor);
    }

    private void calculatePath() {

        mPath.moveTo(getLeft(), getTop()+(getBottom()-getTop())/10);
        mPath.quadTo(getLeft()+(getRight()-getLeft())/2 , getTop()-(getBottom()-getTop())/10, getRight() , getTop()+(getBottom()-getTop())/10);

        mPath.lineTo(getRight(), getBottom());
        mPath.lineTo(getLeft(), getBottom());

        mPath.close();
    }
}
