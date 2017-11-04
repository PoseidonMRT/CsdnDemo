package com.example.code.floatingwindowdemo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;

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
    private DisplayMetrics mDisplayMetrics;
    private int mWindowWidth;
    private int mWindowHeight;
    private boolean mRotateEnabled = false;
    private FloatingWindow mFloatingWindow;

    /**
     * Broadcast for control screen record state
     */
    private BroadcastReceiver mSreenRecordReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    public ScreenRecorderService() {
        super();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mRotateEnabled){
            if (mScreenrecorder != null) {
                mScreenrecorder.quit();
                mScreenrecorder = null;
            }
            mWindowWidth = Utils.getDeviceWidth(this);
            mWindowHeight = Utils.getAbsoluteDeviceHeight(this);
            startScreenRecord();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ScreenRecorderBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWindowWidth = Utils.getDeviceWidth(this);
        mWindowHeight = Utils.getAbsoluteDeviceHeight(this);
        mDisplayMetrics = new DisplayMetrics();
        Utils.getScreenMetrics(this, mDisplayMetrics);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScreenrecorder.quit();
        mScreenrecorder = null;
    }

    @Override
    public void onEncodingH264RawCompeleted(byte[] h264Raw) {
        //h264Raw ------ video data

    }

    public void startScreenRecord(){
        if (FloatingWindowApplication.getInstance().getMediaProjection() == null){
            throw new IllegalStateException("MediaProjection Permission have not grated");
        }

        mScreenrecorder = new ScreenProjectionWrapper(mWindowWidth,mWindowHeight, ScreenProjectionConfig.SCREEN_PROJECTION_VIDEO_BITRATE,
                mDisplayMetrics.densityDpi, FloatingWindowApplication.getInstance().getMediaProjection());
        mScreenrecorder.setScreenProjectionListerner(this);
        mScreenrecorder.start();
    }

    public class ScreenRecorderBinder extends Binder{
        /**
         * used for live when screen oritation changed
         * @param enabled
         */
        void setScreenOritationEnabled(boolean enabled){
            mRotateEnabled = enabled;
        }

        /**
         * show floating window
         */
        void showFloatingWindow(){
            if (mFloatingWindow == null){
                mFloatingWindow = new FloatingWindow.Builder(ScreenRecorderService.this, Gravity.CENTER)
                        .setClickable(true)
                        .setDragEnabled(true)
                        .setContentView(R.layout.default_text_floting_window_layout)
                        .buildCustom();
            }
            mFloatingWindow.show();
        }

        /**
         * hide floating window
         */
        public void hideFloatingWindow(){
            if (mFloatingWindow != null){
                mFloatingWindow.dismiss();
                mFloatingWindow = null;
            }
        }

        /**
         * stop screen record
         */
        void stopScreenRecord(){
            if (mScreenrecorder != null){
                mScreenrecorder.quit();
                mScreenrecorder = null;
            }
        }

        /**
         * check media projection permission before call
         * start screen record
         */
        public void takeScreenRecord(){
            startScreenRecord();
        }
    }
}
