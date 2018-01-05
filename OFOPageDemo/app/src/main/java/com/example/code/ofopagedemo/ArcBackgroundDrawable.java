package com.example.code.ofopagedemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/1/5
 */
public class ArcBackgroundDrawable extends Drawable {

    private Path mPath;

    @Override
    public void draw(@NonNull Canvas canvas) {
        mPath = new Path();
        Rect bounds = getBounds();

        mPath.moveTo(bounds.left, bounds.top+(bounds.bottom-bounds.top)/10);
        mPath.quadTo(bounds.left+(bounds.right-bounds.left)/2 , bounds.top-(bounds.bottom-bounds.top)/10, bounds.right , bounds.top+(bounds.bottom-bounds.top)/10);

        mPath.lineTo(bounds.right, bounds.bottom);
        mPath.lineTo(bounds.left, bounds.bottom);

        mPath.close();
        canvas.clipPath(mPath);
        canvas.drawColor(Color.YELLOW);
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
}
