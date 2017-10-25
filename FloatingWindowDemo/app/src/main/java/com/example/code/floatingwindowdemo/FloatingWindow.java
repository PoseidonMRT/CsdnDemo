package com.example.code.floatingwindowdemo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2017/10/25
 */
public abstract class FloatingWindow {
    protected static final String TAG = "FloatingWindow";
    protected final Context mContext;
    protected final String mName;
    protected final int mWidth;
    protected final int mHeight;
    protected final int mGravity;

    protected FloatingWindow(Context mContext, String mName,int mWidth, int mHeight, int mGravity) {
        this.mContext = mContext;
        this.mName = mName;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.mGravity = mGravity;
    }

    public static FloatingWindow create(Context mContext, String mName,int mWidth, int mHeight, int mGravity){
        return new TextFloatingView(mContext,mName,mWidth,mHeight,mGravity);
    }

    public Context getContext(){
        return mContext;
    }

    public abstract void show();

    public abstract void dismiss();

    private static final class TextFloatingView extends FloatingWindow{

        private final WindowManager mWindowManager;
        private boolean mWindowVisiable;
        private View mWindowView;
        private WindowManager.LayoutParams mWindowParams;
        private final DisplayMetrics mDefaultDisplayMetrics = new DisplayMetrics();

        private GestureDetector mGestureDetector;

        private int mWindowX;
        private int mWindowY;

        private float mLiveTranslationX;
        private float mLiveTranslationY;

        protected TextFloatingView(Context mContext, String mName, int mWidth, int mHeight, int mGravity) {
            super(mContext, mName, mWidth, mHeight, mGravity);
            mWindowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        }

        @Override
        public void show() {
            mGestureDetector = new GestureDetector(mOnGestureListener);
            int mScreenWidth = mWindowManager.getDefaultDisplay().getWidth();
            int mScreenHeight = mWindowManager.getDefaultDisplay().getHeight();
            mWindowView = LayoutInflater.from(mContext).inflate(R.layout.default_text_floting_window_layout,null);
            mWindowView.setOnTouchListener(mOnTouchListener);
            mWindowParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST);

            mWindowParams.format = PixelFormat.RGBA_8888;
            mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mWindowParams.gravity = Gravity.CENTER;
            mWindowParams.x = mScreenWidth;
            mWindowParams.y = mScreenHeight;

            mWindowManager.addView(mWindowView,mWindowParams);

            mWindowVisiable = true;
        }

        @Override
        public void dismiss() {
            if (mWindowView != null && mWindowVisiable){
                mWindowManager.removeView(mWindowView);
                mWindowVisiable = false;
            }
        }

        private void relayout(){
            mWindowParams.x += (int) mLiveTranslationX / 2;
            mWindowParams.y += (int) mLiveTranslationY / 2;
            mWindowManager.updateViewLayout(mWindowView,mWindowParams);
        }

        private final View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        };

        private final GestureDetector.OnGestureListener mOnGestureListener =
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                            float distanceX, float distanceY) {
                        mLiveTranslationX -= distanceX;
                        mLiveTranslationY -= distanceY;
                        relayout();
                        return true;
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        Toast.makeText(mContext, "Click Me!", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                };


    }




}
