package com.example.code.coloredtextview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/3/21
 */
public class KTVTextView extends View {

    private Paint mPaint;

    private PorterDuffXfermode xformode;
    private String mText = "你是我生命里的一首歌";
    private int textWidth;
    private int textHeight;

    public KTVTextView(Context ctx) {
        this(ctx, null);
    }


    public KTVTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KTVTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xformode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

        initViewAndDatas();

        setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postIndex = 0;
                postInvalidate();
            }
        });

    }

    public void initViewAndDatas() {
        mPaint.setColor(Color.CYAN);
        mPaint.setTextSize(40.0f);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setXfermode(null);
        mPaint.setTextAlign(Paint.Align.LEFT);
        Rect bounds = new Rect();
        mPaint.getTextBounds(mText,0,mText.length(),bounds);
        textWidth = bounds.width();
        textHeight = bounds.height();
    }

    private int postIndex;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("TZB",getLeft()+":left"+getTop()+":top"+getRight()+":right"+getBottom()+":bottom");
        Log.e("TZB",getWidth()+":width"+getHeight()+":height");

        Bitmap srcBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas srcCanvas = new Canvas(srcBitmap);
        srcCanvas.drawText(mText, 0, textHeight, mPaint);

        mPaint.setXfermode(xformode);
        mPaint.setColor(Color.RED);
        RectF rectF = new RectF(0, 0, postIndex, getHeight());
        srcCanvas.drawRect(rectF, mPaint);

        canvas.drawBitmap(srcBitmap, 0, (getHeight()-textHeight)/2, null);
        initViewAndDatas();
        if (postIndex < getWidth()) {
            postIndex += 10;
            postInvalidateDelayed(30);
        }
    }

}
