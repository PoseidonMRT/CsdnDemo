package com.example.code.floatingwindowdemo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
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
    protected final int mGravity ;

    protected FloatingWindow(Context mContext, String mName, int mGravity) {
        this.mContext = mContext;
        this.mName = mName;
        this.mGravity = mGravity;
    }

    public static class Builder{
        private Context context;
        private int windowType;
        private String name;
        private int gravity;
        private int layoutResource;
        private View contentView;
        private boolean dragEnabled;
        private boolean clickable;
        private int positionX;
        private int positionY;

        public Builder(Context context,int gravity) {
            this.context = context;
            this.gravity = gravity;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setDragEnabled(boolean dragEnabled) {
            this.dragEnabled = dragEnabled;
            return this;
        }

        public Builder setContentView(int resourceId){
            this.layoutResource = resourceId;
            return this;
        }

        public Builder setContentView(View content){
            this.contentView = content;
            return this;
        }

        public Builder setClickable(boolean clickable){
            this.clickable = clickable;
            return this;
        }

        public Builder setPositionX(int x){
            this.positionX = x;
            return this;
        }

        public Builder setPositionY(int y){
            this.positionY = y;
            return this;
        }

        public Builder setWindowType(int type){
            this.windowType = type;
            return this;
        }

        public FloatingWindow buildCustom(){
            return new CustomFloatingView(this.context,this.name,this.gravity,this.contentView,this.layoutResource,this.positionX,this.positionY,this.dragEnabled,this.clickable);
        }
    }

    public Context getContext(){
        return mContext;
    }

    public abstract void show();

    public abstract void dismiss();

    private static final class CustomFloatingView extends FloatingWindow{

        private final WindowManager mWindowManager;
        private boolean mWindowVisiable;
        private View mWindowView;
        private WindowManager.LayoutParams mWindowParams;
        private final DisplayMetrics mDefaultDisplayMetrics = new DisplayMetrics();

        private GestureDetector mGestureDetector;

        private int mWindowPositionX;
        private int mWindowPositionY;
        private int mContentResourceId;

        private View mContentView;
        private boolean mDragEnabled = true;
        private boolean mClickable = true;

        private float mLiveTranslationX;
        private float mLiveTranslationY;

        protected CustomFloatingView(Context mContext, String mName, int mGravity) {
            super(mContext, mName, mGravity);
            mWindowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        }

        public CustomFloatingView(Context mContext, String mName, int mGravity, View contentView,int resourceId,int mWindowPositionX, int mWindowPositionY,  boolean dragEnabled, boolean clickable) {
            super(mContext, mName, mGravity);
            mWindowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
            this.mWindowPositionX = mWindowPositionX;
            this.mWindowPositionY = mWindowPositionY;
            this.mContentView = contentView;
            this.mContentResourceId = resourceId;
            this.mDragEnabled = dragEnabled;
            this.mClickable = clickable;
        }

        @Override
        public void show() {
            mGestureDetector = new GestureDetector(mOnGestureListener);
            int mScreenWidth = mWindowManager.getDefaultDisplay().getWidth();
            int mScreenHeight = mWindowManager.getDefaultDisplay().getHeight();
            if (mContentResourceId != 0 && mContentView != null){
                throw new IllegalStateException("Content View and Content layout Resource can only choose one");
            }

            if (mContentResourceId == 0 && mContentView == null){
                throw new IllegalStateException("Window Content can not be null in custom mode");
            }
            if (mContentView != null){
                mWindowView = mContentView;
            }else if (mContentResourceId != 0){
                mWindowView = LayoutInflater.from(mContext).inflate(mContentResourceId,null);
            }

            mWindowView.setOnTouchListener(mOnTouchListener);

            mWindowParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST);

            mWindowParams.format = PixelFormat.RGBA_8888;
            mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mWindowParams.gravity = mGravity;

            mWindowParams.x = mWindowPositionX == 0 ? mScreenWidth : mWindowPositionX;
            mWindowParams.y = mWindowPositionY == 0 ? mScreenHeight : mWindowPositionY;

            mWindowManager.addView(mWindowView,mWindowParams);

            mWindowVisiable = true;
        }

        @Override
        public void dismiss() {
            if (mWindowView != null && mWindowVisiable){
                mWindowManager.removeView(mWindowView);
                mWindowView = null;
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
                        if (mDragEnabled){
                            mLiveTranslationX -= distanceX;
                            mLiveTranslationY -= distanceY;
                            relayout();
                        }
                        return true;
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        if (mClickable){
                            Toast.makeText(mContext, "Click Me!", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                };


    }
}
