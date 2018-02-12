package com.dfwd.wlkt.screenbroadcasting.refactor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.TextureView;
import android.view.ViewConfiguration;
import android.view.WindowManager;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2018/2/7
 */
public class AutoScaleTextureView extends TextureView {

    private int mContentWidth = 0;
    private int mContentHeight = 0;
    public AutoScaleTextureView(Context context) {
        super(context);
    }

    public AutoScaleTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoScaleTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AutoScaleTextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

        public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mContentWidth = width;
        mContentHeight = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (0 == mContentWidth || 0 == mContentHeight) {
            setMeasuredDimension(width, height);
        } else {
            int deviceWidth = getDeviceSize().x;
            int deviceHeight = getDeviceSize().y;
            Point resultSize = new Point();
            float scaleWidth = (float) deviceWidth / mContentWidth;
            float scaleHeight = (float) deviceHeight / mContentHeight;
            float zoomFactor = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;

            resultSize.x = (int) (mContentWidth * zoomFactor);
            resultSize.y = (int) (mContentHeight * zoomFactor);
            setMeasuredDimension(resultSize.x,resultSize.y);
        }
    }

    private Point getDeviceSize() {
        Point devSize = new Point();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getRealSize(devSize);
        if (isNavigationBarShow()) {
            devSize.y -= getNavigationBarHeight();
        }
        return devSize;
    }

    private boolean isNavigationBarShow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(getContext()).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }

    private int getNavigationBarHeight() {
        if (!isNavigationBarShow()) {
            return 0;
        }
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
