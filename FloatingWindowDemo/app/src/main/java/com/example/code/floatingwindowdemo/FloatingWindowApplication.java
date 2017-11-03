package com.example.code.floatingwindowdemo;

import android.app.Application;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2017/11/3
 */
public class FloatingWindowApplication extends Application {

    private MediaProjectionManager mMediaProjectionManager;
    private MediaProjection mMediaProjection;
    private static FloatingWindowApplication floatingWindowApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        floatingWindowApplication = this;
    }

    public static FloatingWindowApplication getInstance(){
        if (floatingWindowApplication == null){
            throw new IllegalStateException("FloatingWidow Application have not been init");
        }
        return floatingWindowApplication;
    }

    public MediaProjectionManager getMediaProjectionManager() {
        return mMediaProjectionManager;
    }

    public MediaProjection getMediaProjection() {
        return mMediaProjection;
    }

    public void setMediaProjection(MediaProjection mMediaProjection) {
        this.mMediaProjection = mMediaProjection;
    }
}
