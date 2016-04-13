package com.tt.colormatrixdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by TuoZhaoBing on 2016/4/13 0013.
 */
public class ColorFilterView extends View {
    public static final String TAG = "ColorFilterView";
    private Context mContext;
    private Paint mPaint;
    private Bitmap mBitmap;
    private int x;
    private int y;

    public ColorFilterView(Context context) {
        super(context);
        init(context);
    }

    public ColorFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ColorFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        mContext =context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.test);
        x = (MeasureUtils.getScreenWidth(context)-mBitmap.getWidth())/2;
        y = (MeasureUtils.getScreenHeight(context) - mBitmap.getHeight())/2;
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                -1,0,0,1,1,
                0,-1,0,1,1,
                0,0,-1,1,1,
                0,0,0,1,0
        });
        ColorMatrix colorMatrix1 = new ColorMatrix(new float[]{
                0,0,1,0,0,
                0,1,0,0,0,
                1,0,0,0,0,
                0,0,0,1,0
        });
        ColorMatrix colorMatrix2 = new ColorMatrix(new float[]{
                0.393f,0.769f,0.189f,0,0,
                0.349f,0.686f,0.168f,0,0,
                0.272f,0.534f,0.131f,0,0,
                0,0,0,1,0
        });
        ColorMatrix colorMatrix3 = new ColorMatrix(new float[]{
                1.5f,1.5f,1.5f,0,-1,
                1.5f,1.5f,1.5f,0,-1,
                1.5f,1.5f,1.5f,0,-1,
                0,0,0,1,0
        });
        ColorMatrix colorMatrix4 = new ColorMatrix(new float[]{
                1.6f,1f,0.3f,0,0,
                0.689f,1f,0.5f,0,0,
                1.5f,1f,0.6f,0,-1,
                0,0,0,1,0
        });
        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix1);
        mPaint.setColorFilter(colorMatrixColorFilter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, x, y, mPaint);
    }
}
