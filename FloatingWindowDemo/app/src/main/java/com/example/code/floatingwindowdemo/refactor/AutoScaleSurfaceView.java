package com.dfwd.wlkt.screenbroadcasting.refactor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Build;
import android.os.MemoryFile;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.dfwd.wlkt.util.StringUtils;

import java.io.IOException;

/**
 * 根据视频源大小自动放缩的SurfaceView
 *
 * @author tuozhaobing
 * @date 2018/2/7
 */
public class AutoScaleSurfaceView extends SurfaceView implements MediaCodecControl {

    private int mContentWidth = 0;
    private int mContentHeight = 0;

    private MediaCodec mMediaCodec;
    private DecodingThread mDecodingThread;

    private String mMimeType = "video/avc";
    private int mFrameRate = 15;

    private SurfaceAvaliableListener mSurfaceAvaliableListener = null;

    private SurfaceHolder.Callback mSurfaceHolderCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (mSurfaceAvaliableListener != null) {
                mSurfaceAvaliableListener.surfaceCreated(holder);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (mSurfaceAvaliableListener != null) {
                mSurfaceAvaliableListener.surfaceChanged(holder, format, width, height);
            } else {
                stop();
                release();
                init(holder.getSurface(), mMimeType, width, height, mFrameRate);
                start();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mSurfaceAvaliableListener != null) {
                mSurfaceAvaliableListener.surfaceDestroyed(holder);
            } else {
                stop();
                release();
            }
        }
    };

    public AutoScaleSurfaceView(Context context) {
        this(context, null);
    }

    public AutoScaleSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScaleSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AutoScaleSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mContentWidth = width;
        mContentHeight = height;
        requestLayout();
        getHolder().addCallback(mSurfaceHolderCallBack);
    }

    public void setAspectRadioAndInitCallBack(int width, int height, String mimeType, int frameRate, SurfaceAvaliableListener surfaceAvaliableListener) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mContentWidth = width;
        mContentHeight = height;
        mMimeType = mimeType;
        mFrameRate = frameRate;
        mSurfaceAvaliableListener = surfaceAvaliableListener;
        requestLayout();
        getHolder().addCallback(mSurfaceHolderCallBack);
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
            setMeasuredDimension(resultSize.x, resultSize.y);
        }
    }

    @Override
    public void init(Surface surface, MediaFormat mediaFormat) {
        final String mimeType = mediaFormat.getString(MediaFormat.KEY_MIME);
        final int frameRate = mediaFormat.getInteger(MediaFormat.KEY_FRAME_RATE);

        if (StringUtils.isNullOrEmpty(mimeType)) {
            throw new IllegalStateException("Could not config MediaCodec without a mime type of content");
        }

        try {
            mMediaCodec = MediaCodec.createDecoderByType(mimeType);
        } catch (IOException e) {
            throw new IllegalStateException("MediaCodec init failed with the content type of " + mimeType);
        }

        mMediaCodec.configure(mediaFormat, surface, null, 0);
        mDecodingThread = new DecodingThread(mMediaCodec, frameRate);
    }

    @Override
    public void init(Surface surface, String mimeType, int width, int height, int frameRate) {
        try {
            mMediaCodec = MediaCodec.createDecoderByType(mimeType);
        } catch (IOException e) {
            throw new IllegalStateException("MediaCodec init failed with the content type of " + mimeType);
        }
        final MediaFormat mediaformat = MediaFormat.createVideoFormat(mimeType, width, height);
        mediaformat.setInteger(MediaFormat.KEY_FRAME_RATE, frameRate);
        mMediaCodec.configure(mediaformat, surface, null, 0);
        mDecodingThread = new DecodingThread(mMediaCodec, frameRate);
    }

    @Override
    public void start() {
        mMediaCodec.start();
    }

    @Override
    public boolean inputH264RawFrame(byte[] rawFrame) {
        return mDecodingThread != null && mDecodingThread.decodeH264RawFrame(rawFrame);
    }

    @Override
    public void stop() {
        if (mMediaCodec != null) {
            mMediaCodec.stop();
        }
    }

    @Override
    public void release() {
        if (mMediaCodec != null) {
            mMediaCodec.release();
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
