package com.example.code.animationdrawabledemo;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/2/27
 */
public class LoopRectangleDrawable extends Drawable {
    private ValueAnimator mValueAnimator;
    private int index = 1;
    private Paint mNormalRectPaint, mSelectedRectPaint;
    private Paint mLinePaint;

    public LoopRectangleDrawable() {
        super();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int left = getBounds().left;
        int top = getBounds().top;
        int right = getBounds().right;
        int bottom = getBounds().bottom;
        int width = getRectangleWidth();
        canvas.drawRect(left,top,right,bottom,mNormalRectPaint);
        canvas.drawLine(left+width/100,top,left+width/100,bottom,mLinePaint);
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
        mValueAnimator = new ValueAnimator();

    }

    public int getRectangleWidth() {
        boolean oritation = getBounds().width() > getBounds().height();
        int verticalWidth = (getBounds().height() - getBounds().height() / 100) / 3;
        int horizentalWidth = (getBounds().width() - getBounds().width() / 100) / 3;
        return oritation ? verticalWidth : horizentalWidth;
    }
}
