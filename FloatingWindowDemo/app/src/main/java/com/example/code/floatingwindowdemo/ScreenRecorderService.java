package com.example.code.floatingwindowdemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import com.example.code.floatingwindowdemo.h264codec.ScreenProjectionListerner;
import com.example.code.floatingwindowdemo.h264codec.ScreenProjectionWrapper;

/**
 * Class description
 *
 * @author tuozhaobing
 * @date 2017/11/3
 */
public class ScreenRecorderService extends Service implements ScreenProjectionListerner{

    private ScreenProjectionWrapper mScreenrecorder;
    public static final String TAG = "ScreenShotHelper";
    private Context mContext;
    private VirtualDisplay mVirtualDisplay;

    public ScreenRecorderService() {
        super();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onEncodingH264RawCompeleted(byte[] h264Raw) {

    }

    public void startScreenRecord(){
        mScreenrecorder = new ScreenProjectionWrapper(Utils.getDeviceWidth(this),
                Utils.getAbsoluteDeviceHeight(this), ScreenProjectionConfig
                .SCREEN_PROJECTION_VIDEO_BITRATE, 1, FloatingWindowApplication.getInstance().getMediaProjection());
        mScreenrecorder.setScreenProjectionListerner(this);
        mScreenrecorder.start();
    }
}
