package com.tt.clickableimagespan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.ImageSpan;
import android.view.View;

/**
 * Created by TuoZhaoBing on 2016/4/27 0027.
 */
public abstract class ClickableImageSpan extends ImageSpan {
    public static final String TAG = "ClickableImageSpan";

    public ClickableImageSpan(Context context, Bitmap b) {
        super(context, b);
    }

    public ClickableImageSpan(Context context, Bitmap b, int verticalAlignment) {
        super(context, b, verticalAlignment);
    }

    public ClickableImageSpan(Drawable d) {
        super(d);
    }

    public ClickableImageSpan(Drawable d, int verticalAlignment) {
        super(d, verticalAlignment);
    }

    public ClickableImageSpan(Drawable d, String source) {
        super(d, source);
    }

    public ClickableImageSpan(Drawable d, String source, int verticalAlignment) {
        super(d, source, verticalAlignment);
    }

    public ClickableImageSpan(Context context, Uri uri) {
        super(context, uri);
    }

    public ClickableImageSpan(Context context, Uri uri, int verticalAlignment) {
        super(context, uri, verticalAlignment);
    }

    public ClickableImageSpan(Context context, int resourceId) {
        super(context, resourceId);
    }

    public ClickableImageSpan(Context context, int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);
    }

    @Override
    public String getSource() {
        return super.getSource();
    }

    public abstract void onClick(View view);
}
